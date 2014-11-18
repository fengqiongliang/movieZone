package com.moviezone.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;

public interface StatService {
	public void addBrowserStat(long movieid);
	public void addDownloadStat(long movieid);
	public void addApproveStat(long movieid);
	public void addModuleStat(String fromModule);
	public void addAreaStat(String ip,long userid);
}
