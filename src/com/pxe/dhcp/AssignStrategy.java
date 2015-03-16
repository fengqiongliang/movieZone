package com.pxe.dhcp;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.service.PXEService;

class ServerInfo{
	private static final Logger logger  = LoggerFactory.getLogger(ServerInfo.class);
	private InetAddress serverIP;
	private InetAddress submask;
	private InetAddress gateway;
	private AssignIP[] ips        = new AssignIP[244];  //分配10~253的ip可用
	private boolean[] isAssign = new boolean[244]; //标志是否ip已经被分配
	private boolean[] isDetect = new boolean[244]; //标志是否ip已经被探测
	private int lastIpIndex = -1;  //上次分配到哪里
	public  ServerInfo(InetAddress serverIP,InetAddress submask,InetAddress gateway){
		this.serverIP = serverIP;
		this.submask = submask;
		this.gateway  = gateway;
		Arrays.fill(isAssign, false);
		Arrays.fill(isDetect, false);
		init(); //初始化所有可以分配的ip
		//开启后台线程探测ip是否可用
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				detect();
			}
		});
		t.setDaemon(true);
		t.start();
	}
	public void init(){
		int leaseTime = 24*60*60;//默认租用时间24小时
		int renewSecs = 300;       //将所有的ip renew及rebind错开，防止所有客户端同时renew
		int rebindSecs = 525;
		for( int i=0;i<ips.length;i++){
			byte[] serverIPRaw  = serverIP.getAddress();
			byte[] submaskRaw = submask.getAddress();
			byte[] ipRaw            = new byte[]{0,0,0,0};
			ipRaw[0] = (byte) (serverIPRaw[0] & submaskRaw[0]);
			ipRaw[1] = (byte) (serverIPRaw[1] & submaskRaw[1]);
			ipRaw[2] = (byte) (serverIPRaw[2] & submaskRaw[2]);
			ipRaw[3] = (byte) ((serverIPRaw[3] & submaskRaw[3])+i+10);
			InetAddress ip = null;
			try{ ip = InetAddress.getByAddress(ipRaw);}catch(Exception ex){}
			if(ip.equals(serverIP)){isAssign[i]=true; isDetect[i]=true;} //标志服务器ip已经分配，防止分配服务器ip,并且测试其它可通的ip地址
			ips[i]          = new AssignIP(null,ip,submask,gateway,null/*默认文件名*/,leaseTime,renewSecs,rebindSecs);
			renewSecs  = renewSecs*(i+1);
			rebindSecs = rebindSecs*(i+1);
		}
	}
	//探测ip是否可用
	public void detect(){
		for(int i=0;i<ips.length;i++){
			detect(ips[i].getIp(),i);
		}
	}
	public AssignIP getAssignIP(String clientMac)throws Exception{
		int index = lastIpIndex;
		for(int i=(++index)%ips.length;i!=lastIpIndex;i=(++i)%ips.length){
			if(isAssign[i]==true)continue;
			if(isDetect[i]==false && detect(ips[i].getIp(),i))continue; //如果没有探测ip，则探测，并且探测结果不通才可分配ip
			isAssign[i] = true;
			lastIpIndex = i;
			ips[i].setMac(clientMac);
			return ips[i];
		}
		throw new Exception("ip pool is full,cannot allocate valid ip");
	}
	public void markInvalidIP(InetAddress invalidIp){
		for(int i=0;i<ips.length;i++){
			if(ips[i].getIp().equals(invalidIp))isAssign[i] = true; //标志已经分配
		}
	}
	public void markValidIP(InetAddress validIp){
		for(int i=0;i<ips.length;i++){
			if(ips[i].getIp().equals(validIp))isAssign[i] = false; //标志没有分配可以重新分配
		}
	}
	private boolean detect(InetAddress ip,int i){
		boolean isReach = false;
		int timeout = 3000;
		logger.info("Thread["+Thread.currentThread().getId()+"] 测试ip是否可用  --> "+ip.getHostAddress()+" 超时:"+timeout+"(毫秒)");
		try{isReach = ip.isReachable(timeout); }catch(Exception ex){} //ping ip 地址是否可用,等待1 second
		isAssign[i] = isReach;  //如果可通标志已经分配ip
		isDetect[i] = true;
		logger.info("Thread["+Thread.currentThread().getId()+"] 确定ip ["+ip.getHostAddress()+"]"+(isReach?"不可用":" 可用"));
		return isReach;
	}
}
public class AssignStrategy {
	private static final Logger logger                       = LoggerFactory.getLogger(AssignStrategy.class);
	private Map<String,AssignIP> clientMap              = new HashMap<String,AssignIP>();              //客户端IP分配表
	private Map<InetAddress,ServerInfo> serverMap = new HashMap<InetAddress,ServerInfo>();  //服务端IP池表
	private PXEService pxeService;
	public void init(List<DHCPSocket> sockets){
		for(DHCPSocket socket:sockets){
				ServerInfo info = new ServerInfo(socket.getSocketIp(),socket.getSocketSubmask(),socket.getSocketGateway());
				serverMap.put(socket.getSocketIp(), info);
		}
	}
	public synchronized AssignIP getIP(DHCPSocket socket,String clientMac) throws Exception{
		//第一步尝试从已经分配的表中或取
		AssignIP allocatedIP = clientMap.get(clientMac);
		if(allocatedIP!=null && !allocatedIP.isInvalid())return allocatedIP;
		//第二步尝试从ip池中分配(过期或是没有分配ip地址)
		 ServerInfo info = serverMap.get(socket.getSocketIp());
		 if(info==null){
			 info = new ServerInfo(socket.getSocketIp(),socket.getSocketSubmask(),socket.getSocketGateway());
			 serverMap.put(socket.getSocketIp(), info);
		 }
		 if(allocatedIP!=null && allocatedIP.isInvalid())markValidIP(socket,clientMac); //如果客户端ip过期则回收ip重新利用
		 allocatedIP = info.getAssignIP(clientMac);
		 //防止同一个ip地址分配给多个mac
		 String alreadyAllocatedMac = null;  //确定已经分配的ip地址
		 for(Entry<String,AssignIP> mac_ip:clientMap.entrySet()){
			 if(mac_ip.getValue().getIp().equals(allocatedIP.getIp())){
				 alreadyAllocatedMac = mac_ip.getKey();
				 break;
			 }
		 }
		 clientMap.remove(alreadyAllocatedMac);
		 clientMap.put(clientMac, allocatedIP);
		 //保存进数据库中
		 pxeService.saveDHCP(clientMac, allocatedIP.getIp().getHostAddress(), allocatedIP.getSubmask().getHostAddress(), allocatedIP.getGateway().getHostAddress(), allocatedIP.getFileName(),allocatedIP.getLeaseTime());
		 logger.debug("client["+clientMac+"] --> "+allocatedIP.getIp().getHostAddress());
		 return allocatedIP;
	}
	public void markInvalidIP(DHCPSocket socket,String clientMac){
		 ServerInfo info = serverMap.get(socket.getSocketIp());
		 if(info==null)return;  
		 AssignIP allocatedIP = clientMap.get(clientMac);
		 if(allocatedIP==null)return;
		 info.markInvalidIP(allocatedIP.getIp());
		 clientMap.remove(clientMac);
	}  
	public void markValidIP(DHCPSocket socket,String clientMac){
		ServerInfo info = serverMap.get(socket.getSocketIp());
		 if(info==null)return;  
		 AssignIP allocatedIP = clientMap.get(clientMac);
		 if(allocatedIP==null)return;
		 info.markValidIP(allocatedIP.getIp());
		 clientMap.remove(clientMac);
	}
	public void setPxeService(PXEService pxeService) {
		this.pxeService = pxeService;
	}
}
