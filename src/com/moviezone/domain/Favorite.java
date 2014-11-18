package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.moviezone.constant.Constants;

public class Favorite implements Serializable{
	private static final long serialVersionUID = -407092464981315547L;
	private long favoriteid;
	private long userid;
	private long movieid;
	private Date createtime;
	public long getFavoriteid() {
		return favoriteid;
	}
	public void setFavoriteid(long favoriteid) {
		this.favoriteid = favoriteid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getMovieid() {
		return movieid;
	}
	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
}
