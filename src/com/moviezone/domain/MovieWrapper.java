package com.moviezone.domain;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.service.MovieService;

public class MovieWrapper implements Serializable{
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static final long serialVersionUID = -6485173889051832687L;
	private Movie movie;
	private List<Module> modules;
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
	public String getStrPubTime() {
		return formatter.format(movie.getPublishtime());
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
	
}
