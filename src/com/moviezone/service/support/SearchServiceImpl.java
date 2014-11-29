package com.moviezone.service.support;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.constant.Constants;
import com.moviezone.dao.SearchDao;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.service.SearchService;

public class SearchServiceImpl implements SearchService{
	private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	private SearchDao searchDao;
	
	@Override
	public SearchResult search(String keyword, String type, int pageNo)throws Exception {
		return searchDao.search(keyword, type, pageNo, 10);
	}

	@Override
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo)throws Exception {
		return searchDao.searchAsPage(keyword, type, pageNo, 10);
	}
	
	public void setSearchDao(SearchDao searchDao) {
		this.searchDao = searchDao;
	}

	
	
	
}
