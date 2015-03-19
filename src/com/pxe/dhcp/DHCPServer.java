package com.pxe.dhcp;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class DaemonFactory implements ThreadFactory{
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
}

class ObjectHold{
	private String ethName;
	private String ethDisplayName;
	private InterfaceAddress netAddr;
	public ObjectHold(String ethName,String ethDisplayName,InterfaceAddress netAddr){
		this.ethName = ethName;
		this.ethDisplayName = ethDisplayName;
		this.netAddr = netAddr;
	}
	public String getEthName() {
		return ethName;
	}
	public void setEthName(String ethName) {
		this.ethName = ethName;
	}
	public String getEthDisplayName() {
		return ethDisplayName;
	}
	public void setEthDisplayName(String ethDisplayName) {
		this.ethDisplayName = ethDisplayName;
	}
	public InterfaceAddress getNetAddr() {
		return netAddr;
	}
	public void setNetAddr(InterfaceAddress netAddr) {
		this.netAddr = netAddr;
	}
}

public class DHCPServer {
	private static final Logger logger = LoggerFactory.getLogger(DHCPServer.class);
	private ConcurrentLinkedQueue<DHCPGram> socketQueue     = new ConcurrentLinkedQueue<DHCPGram>();
	private ConcurrentLinkedQueue<DHCPPacket> servletQueue  = new ConcurrentLinkedQueue<DHCPPacket>();
	private final int dataThreads    = 3;
	private final int servletThreads = 5;
	private ThreadFactory daemonFactory = new DaemonFactory();
	private ExecutorService dataPool         = Executors.newFixedThreadPool(dataThreads,daemonFactory);  //数据采集线程
	private ExecutorService servletPool      = Executors.newFixedThreadPool(servletThreads,daemonFactory);  //servletPool服务线程
	private ExecutorService socketPool; 
	private final int port = 67;
	private AssignStrategy strategy;
    private DHCPServlet servlet;
	public DHCPServer(DHCPServlet servlet,AssignStrategy strategy) throws Exception{
		if(servlet==null){
			logger.error("servlet cannot be null");
			throw new Exception("servlet cannot be null");
		}
		this.servlet   = servlet;
		this.strategy = strategy;
		final List<ObjectHold> ipv4s = Collections.synchronizedList(getAllInetAddress());
		if(ipv4s.size()<1){
			logger.error("failure to start DHCPServer, no avaible ip address of hard interfaces");
			throw new Exception("failure to start DHCPServer, no avaible ip address of hard interfaces");
		}
		logger.debug("success to get avaible ip address on local , then going to listen");
		socketPool = Executors.newFixedThreadPool(ipv4s.size(),daemonFactory);
		
		
		//开启线程监听ip:port
		final List<DHCPSocket> successSocket   = Collections.synchronizedList(new ArrayList<DHCPSocket>());
		for(int i=0;i<ipv4s.size();i++){
			socketPool.execute(new Runnable(){
				@Override
				public void run() {
					ObjectHold ip = ipv4s.remove(0);
					DatagramSocket socket = listen(ip.getNetAddr().getAddress());
					if(socket==null){
						logger.debug("cannot start Thread["+Thread.currentThread().getId()+"] to listen --> "+ip+ " Thread exist");
						return;
					}
					DHCPSocket dhcpSocket  = null;
					try{
						dhcpSocket = new DHCPSocket(socket,ip.getNetAddr(),ip.getEthName(),ip.getEthDisplayName());
					} catch (Exception e) {return;}
					successSocket.add(dhcpSocket);
					accept(dhcpSocket);
				}
			});
		}
		logger.debug("going to sleep 1 second for thread listen over ");
		TimeUnit.SECONDS.sleep(1);
		if(successSocket.size()<1)throw new Exception("failure to start DHCPServer, no successful socket listen");
		
		strategy.init(successSocket);   //测试化并查询ip池中的其它地址是否可用
		
		//开启数据线程封装数据进servletQueue
		logger.debug("going to start data thread wrap Datagram to DHCPPacket");
		for(int i=0;i<dataThreads;i++){
			dataPool.execute(new Runnable(){
				@Override
				public void run() {
					wrapDatagram();
				}
			});
		}
		
		//开启servlet线程调用servletQueue
		logger.debug("going to start servlet thread proccess DHCPPacket and response by socket that packet receive");
		for(int i=0;i<servletThreads;i++){
			servletPool.execute(new Runnable(){
				@Override
				public void run() {
					callServlet();
				}
			});
		}
		logger.info("Success start DHCPServer . . .");
	}
	
	
	
	/**
	 * 获得所有有效的ip地址，用于socket监听
	 * @return
	 */
	private List<ObjectHold> getAllInetAddress(){
		List<ObjectHold> result = new ArrayList<ObjectHold>();
		try {
			for(NetworkInterface face:getAllNetworkInterface()){
				for(InterfaceAddress ipv4:face.getInterfaceAddresses()){
					InetAddress address = ipv4.getAddress();
					if(address==null)continue;
					if(address.getAddress().length!=4)continue;
					result.add(new ObjectHold(face.getName(),face.getDisplayName(),ipv4));
				}
			}
		} catch (Exception e) {e.printStackTrace();}
		return result;
	}
	
	/**
	 * 获得每一个有效的物理网卡
	 * @return
	 */
	private List<NetworkInterface> getAllNetworkInterface(){
		List<NetworkInterface> result = new ArrayList<NetworkInterface>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface eth = interfaces.nextElement();
				byte[] b = eth.getHardwareAddress();
				if (b == null || b.length != 6)continue;
				result.add(eth);
			}
		} catch (SocketException e) {e.printStackTrace();}
		return result;
	}
	
	//临听socket
	private DatagramSocket listen(InetAddress ip){
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(port,ip);
			logger.info("Socket Thread["+Thread.currentThread().getId()+"] successful to listen "+ip+":"+port);
		} catch (SocketException e) {
			e.printStackTrace();
			logger.error("",e);
			logger.error("Socket Thread["+Thread.currentThread().getId()+"] cannot listen "+ip+":"+port+"exit");
			return null;
		}
		return socket;
	}
	//接受udp包
	private void accept(DHCPSocket socket){
		while(true){
			DatagramPacket p = new DatagramPacket(new byte[DHCPPacket.PACKET_MAX],DHCPPacket.PACKET_MAX);
			try {
				socket.getSocket().receive(p);
				logger.info("Socket Thread["+Thread.currentThread().getId()+"] "+socket+" accept packet");
				if(checkPacket(p)){
					DHCPGram packet = new DHCPGram(socket,p);
					socketQueue.add(packet);  //合法包放入socket队列中
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("",e);
				logger.error("Socket Thread["+Thread.currentThread().getId()+"] happen exception when accept packet, exit");
				return;
			}
		}
	} 
	
	//检查包是否合法
	private boolean checkPacket(DatagramPacket p){
		if(p==null)return false;
		if(p.getAddress()==null)return false;
		if(p.getPort()<1)return false;
		if(p.getData()==null||p.getData().length<1)return false;
		if(p.getLength()<1)return false;
		if(p.getOffset()<0)return false;
		if(p.getLength()<DHCPPacket.PACKET_MIN || p.getData().length<DHCPPacket.PACKET_MIN)return false;
		if(p.getLength()>DHCPPacket.PACKET_MAX || p.getData().length>DHCPPacket.PACKET_MAX)return false;
		return true;
	}
	
	//封装包进servletQueue
	private void wrapDatagram(){
		while(true){
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				DHCPGram p = socketQueue.poll();
				if(p==null)continue;
				logger.debug("Data Thread["+Thread.currentThread().getId()+"] wraping packet from "+p.getPacket().getAddress()+":"+p.getPacket().getPort());
				DHCPPacket packet = wrap(p);
				if(packet!=null && !servletQueue.contains(packet))servletQueue.add(packet);
			} catch (InterruptedException e) {
				e.printStackTrace();
				logger.error("",e);
			}
		}
	}
	
	//开始封装进包里 MyDatagramPacket --> DHCPPacket
	private DHCPPacket wrap(DHCPGram p){
		try{
			DatagramPacket packet   = p.getPacket();
			DHCPPacket dhcpPacket = new DHCPPacket(p.getDHCPSocket(),packet.getAddress(),packet.getPort());
			dhcpPacket.fromDatagramPacket(packet);
			return dhcpPacket;
		}catch(Exception e){
			logger.error("", e);
			String msg = p.getSocket().getLocalAddress()+":"+p.getSocket().getLocalPort()+" <-- "+p.getPacket().getAddress()+":"+p.getPacket().getPort();
			logger.error("Thread["+Thread.currentThread().getId()+"] error packet ... "+msg);
			e.printStackTrace();
		}
		return null;
	}
	
	//开始调用
	private void callServlet(){
		while(true){
			try {
				TimeUnit.MILLISECONDS.sleep(1);
				DHCPPacket p = servletQueue.poll();
				if(p==null)continue;
				logger.debug("Servlet Thread["+Thread.currentThread().getId()+"] call servlet");
				DHCPPacket response = null;
				switch(p.getMessageType()){
					case 1: response = this.servlet.doDiscover(p,strategy);break;
	            	case 3: response = this.servlet.doRequest(p,strategy);break;
	            	case 8: response = this.servlet.doInform(p,strategy);break;
	            	case 4: response = this.servlet.doDecline(p,strategy);break;
	            	case 7: response = this.servlet.doRelease(p,strategy);break;
	            	default:
            	    logger.info("Unsupported message type " + p.getMessageType());
				}
				if(response==null){
					logger.error("Servlet Thread["+Thread.currentThread().getId()+"] response null packet");
					continue;
				}
				//response packet from socket where accept packet to client
				DatagramSocket  socket = response.getSocket();
				DatagramPacket  packet = response.toDatagramPacket();
				if(socket==null){
					logger.warn("Servlet Thread["+Thread.currentThread().getId()+"]  get invalid socket (null socket)");
					continue;
				}
				if(packet==null){
					logger.warn("Servlet Thread["+Thread.currentThread().getId()+"]  get invalid packet (null packet)");
					continue;
				}
				if(packet.getAddress()==null){
					logger.warn("Servlet Thread["+Thread.currentThread().getId()+"]  get invalid packet (null client's InetAddress where to send)");
					continue;
				}
				if(packet.getPort()<1){
					logger.warn("Servlet Thread["+Thread.currentThread().getId()+"]  get invalid packet (null client's port where to send)");
					continue;
				}
				socket.send(packet);
			} catch (Exception e) {
				logger.error("",e);
				e.printStackTrace();
			}
		}
	}
	
	
}


