package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.StatDao;
import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;

public class StatDaoImpl implements StatDao{
	private static final Logger logger = LoggerFactory.getLogger(StatDaoImpl.class);
	
	private SqlSession session;

	@Override
	public boolean addBrowserStat(long movieid,long broswer){
		if(movieid<1)return false;
		Movie param = new Movie();
		param.setMovieid(movieid);
		param.setBroswer(broswer);
		return updateMovie(param);
	}
	
	@Override
	public boolean addDownloadStat(long movieid,long download){
		if(movieid<1)return false;
		Movie param = new Movie();
		param.setMovieid(movieid);
		param.setDownload(download);
		return updateMovie(param);
	}
	
	@Override
	public boolean addApproveStat(long movieid,long approve){
		if(movieid<1)return false;
		Movie param = new Movie();
		param.setMovieid(movieid);
		param.setApprove(approve);;
		return updateMovie(param);
	}
	
	@Override
	public boolean updateMovie(Movie movie) {
		if(movie==null||movie.getMovieid()<1)return false;
		return session.update("updateMoviestat", movie)>0;
	}
	
	@Override
	public boolean updateModulestat(String stat_id, long statCount) {
		if(StringUtils.isBlank(stat_id))return false;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("stat_id", stat_id);
		param.put("statCount", statCount);
		return session.update("updateModulestat", param)>0;
	}
	
	@Override
	public boolean addIpstat(long ip,long statCount){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("ip", ip);
		param.put("statCount", statCount);
		return session.update("updateIpstat", param)>0;
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	
	

	

}
