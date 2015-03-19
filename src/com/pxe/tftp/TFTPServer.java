package com.pxe.tftp;


import java.io.FileInputStream;
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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.util.SecurityUtil;


class DaemonFactory implements ThreadFactory{
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	}
}
public class TFTPServer {
	private static final Logger logger = LoggerFactory.getLogger(TFTPServer.class);
	private LinkedBlockingQueue<DatagramPacket> socketQueue     = new LinkedBlockingQueue<DatagramPacket>();
	private LinkedBlockingQueue<TFTPPacket> servletQueue  = new LinkedBlockingQueue<TFTPPacket>();
	private final int dataThreads    = 3;
	private final int servletThreads = 5;
	private ThreadFactory daemonFactory = new DaemonFactory();
	private ExecutorService dataPool         = Executors.newFixedThreadPool(dataThreads,daemonFactory);  //数据采集线程
	private ExecutorService servletPool      = Executors.newFixedThreadPool(servletThreads,daemonFactory);  //servletPool服务线程
	private final int port = 69;
    private TFTPServlet servlet;
    private DatagramSocket socket;  //操作命令请求 WRQ(write request)、RRQ(read request)
	public TFTPServer(TFTPServlet servlet) throws Exception{
		if(servlet==null){
			logger.error("servlet cannot be null");
			throw new Exception("servlet cannot be null");
		}
		this.servlet   = servlet;
		
		this.socket = new DatagramSocket(port,InetAddress.getByName("0.0.0.0"));
		
		//开启线程监听命令请求
		Thread cmmdT = new Thread(new Runnable(){
			@Override
			public void run() {
				accept();
			}
		});
		cmmdT.setDaemon(true);
		cmmdT.start();
		
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
		logger.info("Success start TFTPServer . . .");
	}
	
	
	//接受udp包
	private void accept(){
		while(true){
			DatagramPacket p = new DatagramPacket(new byte[TFTPPacket.PACKET_MAX],TFTPPacket.PACKET_MAX);
			try {
				this.socket.receive(p);
				logger.info("Socket Thread["+Thread.currentThread().getId()+"] "+this.socket+" accept packet");
				if(checkPacket(p))socketQueue.add(p);  //合法包放入socket队列中
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
		if(p.getLength()<TFTPPacket.PACKET_MIN || p.getData().length<TFTPPacket.PACKET_MIN)return false;
		if(p.getLength()>TFTPPacket.PACKET_MAX || p.getData().length>TFTPPacket.PACKET_MAX)return false;
		return true;
	}
	
	//封装包进servletQueue
	private void wrapDatagram(){
		while(true){
			try {
				DatagramPacket p = socketQueue.take();
				logger.debug("Data Thread["+Thread.currentThread().getId()+"] wraping packet from "+p.getAddress()+":"+p.getPort());
				TFTPPacket packet = TFTPPacket.fromDatagram(p);
				if(packet!=null && !servletQueue.contains(packet))servletQueue.add(packet);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("",e);
			}
		}
	}
	
	//开始调用
	private void callServlet(){
		while(true){
			try {
				TFTPPacket p = servletQueue.take();
				logger.debug("Servlet Thread["+Thread.currentThread().getId()+"] call servlet");
				TFTPPacket response = null;
				if(p instanceof TFTPRead) response = this.servlet.doTFTPRead((TFTPRead)p);
				if(p instanceof TFTPWrite) response = this.servlet.doTFTPWrite((TFTPWrite)p);
				if(p instanceof TFTPData)  response = this.servlet.doTFTPData((TFTPData)p);
				if(p instanceof TFTPAck)   response = this.servlet.doTFTPAck((TFTPAck)p);
				if(p instanceof TFTPError) response = this.servlet.doTFTPError((TFTPError)p);
				if(response==null){
					logger.error("Servlet Thread["+Thread.currentThread().getId()+"] response null packet");
					continue;
				}
				DatagramPacket  packet = response.toDatagram();
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
				this.socket.send(packet);  //一定要用senderSocket发送请求,否则tftp client会认为有问题
			} catch (Exception e) {
				logger.error("",e);
				e.printStackTrace();
			}
		}
	}
	
}


