package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.moviezone.constant.Constants;

public class PxeDhcp implements Serializable{
	private static final long serialVersionUID = -3613317301104710453L;
	private String client_mac;
	private String client_ip;
	private String client_submask;
	private String client_gateway;
	private String file_name;
	private Date createtime;
	private Date invalidtime;
	public String getClient_mac() {
		return client_mac;
	}
	public void setClient_mac(String client_mac) {
		this.client_mac = client_mac;
	}
	public String getClient_ip() {
		return client_ip;
	}
	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}
	public String getClient_submask() {
		return client_submask;
	}
	public void setClient_submask(String client_submask) {
		this.client_submask = client_submask;
	}
	public String getClient_gateway() {
		return client_gateway;
	}
	public void setClient_gateway(String client_gateway) {
		this.client_gateway = client_gateway;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getInvalidtime() {
		return invalidtime;
	}
	public void setInvalidtime(Date invalidtime) {
		this.invalidtime = invalidtime;
	}
	
}
