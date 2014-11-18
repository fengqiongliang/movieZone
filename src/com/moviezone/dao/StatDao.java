package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;


public interface StatDao {

	public boolean addBrowserStat(long movieid, long broswer);
	public boolean addDownloadStat(long movieid, long download);
	public boolean addApproveStat(long movieid, long approve);
	public boolean updateMovie(Movie movie);
	public boolean updateModulestat(String stat_id,long statCount);
	public boolean addIpstat(long ip, long statCount);
}
