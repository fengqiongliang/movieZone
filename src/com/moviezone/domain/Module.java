package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Module implements Serializable{
	private static final long serialVersionUID = -326032264857354685L;
	private long movieid;
	private String name;
	private String type;
	private String shortdesc;
	private String longdesc;
	private String face650x500;
	private String face220x169;
	private String face150x220;
	private String face80x80;
	private String picture;
	private float score;
	private int approve;
	private int favorite;
	private int download;
	private int broswer;
	private Date createtime;
	private Date publishtime;
	private List<Map<String,String>> attachs;
	public long getMovieid() {
		return movieid;
	}
	public void setMovieid(long movieid) {
		this.movieid = movieid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getShortdesc() {
		return shortdesc;
	}
	public void setShortdesc(String shortdesc) {
		this.shortdesc = shortdesc;
	}
	public String getLongdesc() {
		return longdesc;
	}
	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}
	public String getFace650x500() {
		return face650x500;
	}
	public void setFace650x500(String face650x500) {
		this.face650x500 = face650x500;
	}
	public String getFace220x169() {
		return face220x169;
	}
	public void setFace220x169(String face220x169) {
		this.face220x169 = face220x169;
	}
	public String getFace150x220() {
		return face150x220;
	}
	public void setFace150x220(String face150x220) {
		this.face150x220 = face150x220;
	}
	public String getFace80x80() {
		return face80x80;
	}
	public void setFace80x80(String face80x80) {
		this.face80x80 = face80x80;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public int getApprove() {
		return approve;
	}
	public void setApprove(int approve) {
		this.approve = approve;
	}
	public int getFavorite() {
		return favorite;
	}
	public void setFavorite(int favorite) {
		this.favorite = favorite;
	}
	public int getDownload() {
		return download;
	}
	public void setDownload(int download) {
		this.download = download;
	}
	public int getBroswer() {
		return broswer;
	}
	public void setBroswer(int broswer) {
		this.broswer = broswer;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getPublishtime() {
		return publishtime;
	}
	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}
	public List<Map<String, String>> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<Map<String, String>> attachs) {
		this.attachs = attachs;
	}
	
	
}
