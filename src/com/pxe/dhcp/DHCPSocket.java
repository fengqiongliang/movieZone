package com.pxe.dhcp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;

public class DHCPSocket {
	private DatagramSocket socket;
	private String ethName;
	private String ethDisplayName;
	private InetAddress ip;
	private InetAddress submask;
	private InetAddress gateway;
	public DHCPSocket(DatagramSocket socket,InterfaceAddress ipv4,String ethName,String ethDisplayName) throws Exception{
		this.socket = socket;
		this.ethName = ethName;
		this.ethDisplayName = ethDisplayName;
		this.ip = ipv4.getAddress();
		this.submask = getSubmask(ipv4.getNetworkPrefixLength());
		//设置默认路由，默认路由是ip & submask + 1 ,即192.168.2.231(255.255.255.0) --> 192.168.2.1
		this.gateway = getGateway();  
		/* 这里可以设置别的默认路由
		if(this.ip.equals(InetAddress.getByName("192.168.1.132")))this.gateway = InetAddress.getByName("192.168.1.3");
		if(this.ip.equals(InetAddress.getByName("192.168.4.110")))this.gateway = InetAddress.getByName("192.168.4.254");
		*/
	}
	public DatagramSocket getSocket() {
		return socket;
	}
	public InetAddress getSocketIp(){
		return ip;
	}
	public InetAddress getSocketSubmask(){
		return submask;
	}
	public InetAddress getSocketGateway(){
		return gateway;
	}
	@Override
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(ethName);
		build.append("("+ethDisplayName+")");
		build.append("-"+ip.getHostAddress()+":"+socket.getLocalPort()+"("+submask.getHostAddress()+")_"+gateway.getHostAddress());
		return build.toString();
	}
	private InetAddress getSubmask(short maskLen) throws Exception{
		return InetAddress.getByAddress(intToByteArray(0xffffffff<<(32-maskLen)));
	}
	private InetAddress getGateway() throws Exception{
		byte[] ipRaw      = ip.getAddress();
		byte[] maskRaw = submask.getAddress();
		byte[] gateRaw  = new byte[]{0,0,0,0};
		gateRaw[0] = (byte)(ipRaw[0]&maskRaw[0]);
		gateRaw[1] = (byte)(ipRaw[1]&maskRaw[1]);
		gateRaw[2] = (byte)(ipRaw[2]&maskRaw[2]);
		gateRaw[3] = (byte)(ipRaw[3]&maskRaw[3]+1);
		return InetAddress.getByAddress(gateRaw);
	}
	private byte[] intToByteArray(int i){
	    	byte[] r = new byte[4];
	    	r[0] = (byte) ((i & 0xff000000) >> 24);
	    	r[1] = (byte) ((i & 0x00ff0000) >> 16);
	    	r[2] = (byte) ((i & 0x0000ff00) >> 8);
	    	r[3] = (byte) ((i & 0x000000ff) >> 0);
	    	return r;
	}
}
