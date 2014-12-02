package com.moviezone.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.constant.Constants;
import com.moviezone.service.MovieService;

public class Word implements Serializable{
	private static final long serialVersionUID = -2674160785292734032L;
	private long id;
	private String keyword;
	private Date createtime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getStrCreatetime(){
		if(createtime == null)return "";
		return Constants.formater.format(createtime);
	}
}
