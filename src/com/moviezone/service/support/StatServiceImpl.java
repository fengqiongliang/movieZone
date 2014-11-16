package com.moviezone.service.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.StatDao;
import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.StatService;
import com.moviezone.util.HttpUtil;

public class StatServiceImpl implements StatService{
	private static final Logger logger = LoggerFactory.getLogger(StatServiceImpl.class);
	private StatDao statDao;
	
	@Override
	public List<IP> select() {
		return statDao.select();
	}
	
	@Override
	public boolean update(IP ip) {
		return statDao.update(ip);
	}
	
	@Override
	public void addModuleStat(String fromModule) {
		System.out.println("======addModuleStat=========");
		
	}

	@Override
	public void addBrowserStat(long movieid) {
		System.out.println("======addBrowserStat=========");
	}
	
	@Override
	public void addDownloadStat(long movieid) {
		System.out.println("======addDownloadStat=========");
	}

	@Override
	public void addApproveStat(long movieid) {
		System.out.println("======addApproveStat=========");
		
	}
	
	@Override
	public void addFavorite(long movieid, long userid) {
		System.out.println("======addFavorite=========");
		
	}

	@Override
	public void addAreaStat(String ip,long userid) {
		if(StringUtils.isBlank(ip))return;
		if(userid<1);
		System.out.println("======addAreaStat=========");
	}
	
	public void setStatDao(StatDao statDao) {
		this.statDao = statDao;
	}

	

	

	
}
