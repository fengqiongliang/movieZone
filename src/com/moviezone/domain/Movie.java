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

public class Movie implements Serializable{
	private static final long serialVersionUID = 6581274685981927890L;
	private long modmvid;   /*有可能为空,即0*/
	private long movieid;
	private String name;
	private String type;
	private String shortdesc;
	private String longdesc;
	private String face650x500;
	private String face400x308;
	private String face220x169;
	private String face150x220;
	private String face80x80;
	private String picture;
	private float score;
	private long approve;
	private long download;
	private long broswer;
	private Date createtime;
	private Date publishtime;
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
	public String getFace400x308() {
		return face400x308;
	}
	public void setFace400x308(String face400x308) {
		this.face400x308 = face400x308;
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
	public JSONArray getPictureAsArray(){
		if(picture == null)return new JSONArray();
		try{
			return JSONArray.fromObject(picture);
		}catch(Exception ex){
			return new JSONArray();
		}
	}
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public long getApprove() {
		return approve;
	}
	public void setApprove(long approve) {
		this.approve = approve;
	}
	public long getDownload() {
		return download;
	}
	public void setDownload(long download) {
		this.download = download;
	}
	public long getBroswer() {
		return broswer;
	}
	public void setBroswer(long broswer) {
		this.broswer = broswer;
	}
	public float getRecommand(){
		float recommand = 0.0f;
		if(6 < score && score < 8)  recommand = recommand + 1;
		if(score>=8)                        recommand = recommand + 2;
		if(download > 10)                recommand = recommand + 1;
		if(broswer > 100)                recommand = recommand + 1;
		if(approve>0)                      recommand = recommand + 1;
		return recommand;
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
	public String getStrTimeFromNow() {
		if(getPublishtime() == null)return "";
		long times = getPublishtime().getTime() - System.currentTimeMillis();
		if(times<=0)return "0天 00:00:00";
		long days = times/86400000;
		long hour = (times - days*86400000)/3600000;
		long min   = (times-days*86400000-hour*3600000)/60000;
		long sec    = (times-days*86400000-hour*3600000-min*60000)/1000;
		return days+"天 "+(hour<10?"0"+hour:hour)+":"+(min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec);
	}
	public String getStrPubTime() {
		if(getPublishtime() == null)return "";
		return Constants.formater.format(getPublishtime());
	}
	public int getFullStarCount() {
		return (int)getRecommand();
	}
	public int getPartStarCount() {
		return (int)(getRecommand()/0.5)-(int)(getFullStarCount()*2);
	}
	public int getBlankStarCount() {
		return 5-getFullStarCount()-getPartStarCount();
	}
	
}
