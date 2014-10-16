package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = -2311831316980842512L;
	private long userid;                   
	private String username;   
	private String password;            
	private String nickname; 
	private String faceurl;
	private String cookie_id;
	private String role;
	private Date lastlogtime;
	private Date updatetime;
	private String createip;
	private Date createtime;
	private String createarea;
	private String nextnick;
	private String nextface;
	private String isForbit;
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getFaceurl() {
		return faceurl;
	}
	public void setFaceurl(String faceurl) {
		this.faceurl = faceurl;
	}
	public String getCookie_id() {
		return cookie_id;
	}
	public void setCookie_id(String cookie_id) {
		this.cookie_id = cookie_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Date getLastlogtime() {
		return lastlogtime;
	}
	public void setLastlogtime(Date lastlogtime) {
		this.lastlogtime = lastlogtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getCreateip() {
		return createip;
	}
	public void setCreateip(String createip) {
		this.createip = createip;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreatearea() {
		return createarea;
	}
	public void setCreatearea(String createarea) {
		this.createarea = createarea;
	}
	public String getNextnick() {
		return nextnick;
	}
	public void setNextnick(String nextnick) {
		this.nextnick = nextnick;
	}
	public String getNextface() {
		return nextface;
	}
	public void setNextface(String nextface) {
		this.nextface = nextface;
	}
	public String getIsForbit() {
		return isForbit;
	}
	public void setIsForbit(String isForbit) {
		this.isForbit = isForbit;
	}
	public boolean getForbit() {
		try{
			return Boolean.parseBoolean(isForbit);
		}catch(Exception ex){return false;}
	}
	
}
