package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.moviezone.constant.Constants;

public class Attach implements Serializable{
	private static final long serialVersionUID = -494037651708381235L;
	private long attachid;
	private long movieid;
	private String new_name;
	private String old_name;
	private String attach_url;
	private Date createtime;
	public long getAttachid() {
		return attachid;
	}
	public void setAttachid(long attachid) {
		this.attachid = attachid;
	}
	public long getMovieid() {
		return movieid;
	}
	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}
	public String getNew_name() {
		return new_name;
	}
	public void setNew_name(String new_name) {
		this.new_name = new_name;
	}
	public String getOld_name() {
		return old_name;
	}
	public void setOld_name(String old_name) {
		this.old_name = old_name;
	}
	public String getAttach_url() {
		return attach_url;
	}
	public void setAttach_url(String attach_url) {
		this.attach_url = attach_url;
		if(attach_url!=null && !attach_url.toLowerCase().startsWith("http")) this.attach_url =  Constants.base + attach_url;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
