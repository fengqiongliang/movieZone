package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.service.MovieService;
import com.moviezone.util.HttpUtil;

public class Reply implements Serializable{
	private static final long serialVersionUID = 2180866094923582444L;
	private long replyid;
	private long commentid;
	private long userid;
	private String content;
	private String createarea;
	private Date createtime;
	private User user;
	public long getReplyid() {
		return replyid;
	}
	public void setReplyid(long replyid) {
		this.replyid = replyid;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = HttpUtil.filterEmotion(content);
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
	
}
