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
	private static final long serialVersionUID = -6485173889051832687L;
	private Movie movie = new Movie();
	private List<Module> modules = new ArrayList<Module>();
	private List<Attach> attachs = new ArrayList<Attach>();
	private long cmmtCount;
	private long favoriteCount;
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
	public long getCmmtCount() {
		return cmmtCount;
	}
	public void setCmmtCount(long cmmtCount) {
		this.cmmtCount = cmmtCount;
	}
	public long getFavoriteCount() {
		return favoriteCount;
	}
	public void setFavoriteCount(long favoriteCount) {
		this.favoriteCount = favoriteCount;
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
