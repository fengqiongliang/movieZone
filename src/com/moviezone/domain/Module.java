package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Module implements Serializable{
	private static final long serialVersionUID = -6913859738467569563L;
	private long modmvid;
	private long movieid;
	private String modname;
	private int orderseq;
	private Date createtime;
	public long getModmvid() {
		return modmvid;
	}
	public void setModmvid(long modmvid) {
		this.modmvid = modmvid;
	}
	public long getMovieid() {
		return movieid;
	}
	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}
	public String getModname() {
		return modname;
	}
	public void setModname(String modname) {
		this.modname = modname;
	}
	public int getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(int orderseq) {
		this.orderseq = orderseq;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
