package com.pxe.dhcp;

import java.net.InetAddress;
import java.util.Date;

public class AssignIP {
	private String mac;
	private InetAddress ip;
	private InetAddress submask;
	private InetAddress gateway;
	private String fileName = DHCPConstants.defFileName; //启动文件名
	private int leaseTime    = 1*24*60*60;       //1天(24小时)
	private int renewSecs   = 300;                  //下次renew时间(秒为单位)
	private int rebindSecs  = 525;                  //下次rebind时间(秒为单位)
	private Date assignTime = new Date();      //分酏时间
	public AssignIP(String mac,InetAddress ip,InetAddress submask,InetAddress gateway,String fileName,Integer leaseTime,Integer renewSecs,Integer rebindSecs){
		if(this.mac!=null)this.mac = mac;
		this.ip    = ip;
		this.submask = submask;
		this.gateway = gateway;
		if(fileName!=null)this.fileName = fileName;
		if(leaseTime!=null)this.leaseTime = leaseTime;
		if(renewSecs!=null)this.renewSecs = renewSecs;
		if(rebindSecs!=null)this.rebindSecs = rebindSecs;
	} 
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public InetAddress getIp() {
		return ip;
	}
	public InetAddress getSubmask() {
		return submask;
	}
	public InetAddress getGateway() {
		return gateway;
	}
	public int getRenewSecs() {
		return renewSecs;
	}
	public void setRenewSecs(int renewSecs) {
		this.renewSecs = renewSecs;
	}
	public int getRebindSecs() {
		return rebindSecs;
	}
	public void setRebindSecs(int rebindSecs) {
		this.rebindSecs = rebindSecs;
	}
	public int getLeaseTime() {
		return leaseTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getAssignTime() {
		return assignTime;
	}
	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}
	public boolean isInvalid(){
		return System.currentTimeMillis()>(assignTime.getTime()+leaseTime*1000);
	}
}
