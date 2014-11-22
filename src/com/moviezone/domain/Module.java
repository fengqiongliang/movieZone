package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Module implements Serializable{
	private static final long serialVersionUID = 5068524979623315680L;
	private long modmvid;
	private long movieid;
	private String modname;
	private long orderseq;
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
	public long getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(long orderseq) {
		this.orderseq = orderseq;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
