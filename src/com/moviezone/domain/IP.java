package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.moviezone.constant.Constants;

public class IP implements Serializable{
	private static final long serialVersionUID = -2792819731378068219L;
	private long id;
	private long start_ip;
	private long end_ip;
	private String start_ip_str;
	private String end_ip_str;
	private String Province;
	private String City;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getStart_ip() {
		return start_ip;
	}
	public void setStart_ip(long start_ip) {
		this.start_ip = start_ip;
	}
	public long getEnd_ip() {
		return end_ip;
	}
	public void setEnd_ip(long end_ip) {
		this.end_ip = end_ip;
	}
	public String getStart_ip_str() {
		return start_ip_str;
	}
	public void setStart_ip_str(String start_ip_str) {
		this.start_ip_str = start_ip_str;
	}
	public String getEnd_ip_str() {
		return end_ip_str;
	}
	public void setEnd_ip_str(String end_ip_str) {
		this.end_ip_str = end_ip_str;
	}
	public String getProvince() {
		return Province;
	}
	public void setProvince(String province) {
		Province = province;
	}
	public String getCity() {
		return City;
	}
	public void setCity(String city) {
		City = city;
	}
}
