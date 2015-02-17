package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.constant.Constants;
import com.moviezone.service.MovieService;
import com.moviezone.util.HttpUtil;

public class Comment implements Serializable{
	private static final long serialVersionUID = 494845959575546516L;
	private long commentid;
	private long userid;
	private long movieid;
	private String content;
	private long orderseq;
	private String createarea;
	private Date createtime;
	private User user;
	private Movie movie;
	private long betweenip;
	public long getCommentid() {
		return commentid;
	}
	public void setCommentid(long commentid) {
		this.commentid = commentid;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = HttpUtil.filterEmotion(content);
	}
	public long getOrderseq() {
		return orderseq;
	}
	public void setOrderseq(long orderseq) {
		this.orderseq = orderseq;
	}
	public String getCreatearea() {
		return createarea;
	}
	public void setCreatearea(String createarea) {
		this.createarea = createarea;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public long getBetweenip() {
		return betweenip;
	}
	public void setBetweenip(long betweenip) {
		this.betweenip = betweenip;
	}
	public String getStrCreatetime(){
		if(createtime == null)return "";
		return Constants.formater.format(createtime);
	}
	
	
}
