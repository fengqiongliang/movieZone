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
import com.moviezone.domain.Stat;

public class StatDaoImpl implements StatDao{
	private static final Logger logger = LoggerFactory.getLogger(StatDaoImpl.class);
	
	private SqlSession session;
	
	@Override
	public List<Stat> selectApproveStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectApproveStat", param);
	}

	@Override
	public List<Stat> selectDownloadStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectDownloadStat", param);
	}

	@Override
	public List<Stat> selectBrowserStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectBrowserStat", param);
	}

	@Override
	public List<Stat> selectCommentStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectCommentStat", param);
	}

	@Override
	public List<Stat> selectModuleStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectModuleStat", param);
	}

	@Override
	public List<Stat> selectAreaStat(int pageNo, int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectAreaStat", param);
	}
	
	@Override
	public List<String> moduleIds() {
		return session.selectList("selectModuleIds");
	}
	
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
