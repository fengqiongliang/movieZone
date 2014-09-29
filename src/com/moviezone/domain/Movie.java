package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Movie implements Serializable{
	private static final long serialVersionUID = 7152286936033811037L;
	private long id;
	private String name;
	private String imgurl;
	private String score;
	private String country;
	private List<Map<String,String>> attachs;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Map<String, String>> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<Map<String, String>> attachs) {
		this.attachs = attachs;
	}
	
	
}
