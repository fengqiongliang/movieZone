package com.moviezone.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.service.MovieService;

public class MovieWrapper implements Serializable{
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final long serialVersionUID = -6485173889051832687L;
	private Movie movie = new Movie();
	private List<Module> modules = new ArrayList<Module>();
	private List<Attach> attachs = new ArrayList<Attach>();
	private long cmmtCount;
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public List<Module> getModules() {
		return modules;
	}
	public void setModules(List<Module> modules) {
		this.modules = modules;
	}
	public List<Attach> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<Attach> attachs) {
		this.attachs = attachs;
	}
	public String getStrPubTime() {
		return movie.getPublishtime() == null?"":formatter.format(movie.getPublishtime());
	}
	public long getCmmtCount() {
		return cmmtCount;
	}
	public void setCmmtCount(long cmmtCount) {
		this.cmmtCount = cmmtCount;
	}
	public String getStrTimeFromNow() {
		long times = movie.getPublishtime().getTime() - System.currentTimeMillis();
		if(times<=0)return "0天 00:00:00";
		long days = times/86400000;
		long hour = (times - days*86400000)/3600000;
		long min   = (times-days*86400000-hour*3600000)/60000;
		long sec    = (times-days*86400000-hour*3600000-min*60000)/1000;
		return days+"天 "+(hour<10?"0"+hour:hour)+":"+(min<10?"0"+min:min)+":"+(sec<10?"0"+sec:sec);
	}
	public boolean getInIndexShow(){
		return hasModule("首页-展示区");
	}
	public boolean getInIndexCarrousel(){
		return hasModule("首页-论播区");
	}
	public boolean getInIndexSiter(){
		return hasModule("首页-站长区");
	}
	public boolean getInIndexMovie480p(){
		return hasModule("首页-电影-480p");
	}
	public boolean getInIndexMovie720p(){
		return hasModule("首页-电影-720p");
	}
	public boolean getInIndexMovie1080p(){
		return hasModule("首页-电影-1080p");
	}
	public boolean getInIndexMovieOther(){
		return hasModule("首页-电影-其它");
	}
	public boolean getInIndexMovieRank(){
		return hasModule("首页-电影-排行榜");
	}
	public boolean getInIndexTvAmerica(){
		return hasModule("首页-电视剧-英美");
	}
	public boolean getInIndexTvJapan(){
		return hasModule("首页-电视剧-日韩");
	}
	public boolean getInIndexTvHongkong(){
		return hasModule("首页-电视剧-港台");
	}
	public boolean getInIndexTvChina(){
		return hasModule("首页-电视剧-内地");
	}
	public boolean getInIndexTvRank(){
		return hasModule("首页-电视剧-排行榜");
	}
	public boolean getInMovieShow(){
		return hasModule("电影-展示区");
	}
	public boolean getInMovie480p(){
		return hasModule("电影-480p");
	}
	public boolean getInMovie720p(){
		return hasModule("电影-720p");
	}
	public boolean getInMovie1080p(){
		return hasModule("电影-1080p");
	}
	public boolean getInMovieOther(){
		return hasModule("电影-其它");
	}
	public boolean getInTvShow(){
		return hasModule("电视剧-展示区");
	}
	public boolean getInTvAmerica(){
		return hasModule("电视剧-英美");
	}
	public boolean getInTvJapan(){
		return hasModule("电视剧-日韩");
	}
	public boolean getInTvHongkong(){
		return hasModule("电视剧-港台");
	}
	public boolean getInTvChina(){
		return hasModule("电视剧-内地");
	}
	private boolean hasModule(String modname){
		for(Module module:modules){
			if(module.getModname().equals(modname))return true;
		}
		return false;
	}
	
}
