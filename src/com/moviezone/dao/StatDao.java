package com.moviezone.dao;

import java.util.Date;
import java.util.List;

import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Stat;


public interface StatDao {
	public IP selectAreaOf(String ip);
	public List<Stat> selectApproveStat(int pageNo,int pageSize);
	public List<Stat> selectDownloadStat(int pageNo,int pageSize);
	public List<Stat> selectBrowserStat(int pageNo,int pageSize);
	public List<Stat> selectCommentStat(int pageNo,int pageSize);
	public List<Stat> selectModuleStat(int pageNo,int pageSize);
	public List<Stat> selectAreaStat(int pageNo,int pageSize);
	public List<String> moduleIds();
	public boolean addBrowserStat(long movieid, long broswer);
	public boolean addDownloadStat(long movieid, long download);
	public boolean addApproveStat(long movieid, long approve);
	public boolean updateMovie(Movie movie);
	public boolean updateModulestat(String stat_id,long statCount);
	public boolean addIpstat(long ip, long statCount);
	public List<Stat> selectCmmtUserStat(Date startTime,Date endTime,int pageNo, int pageSize);
	public List<Stat> selectCmmtMvStat(Date startTime,Date endTime,int pageNo, int pageSize);
}
