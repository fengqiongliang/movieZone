package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;



public interface SearchDao {
	public SearchResult search(String keyword, String type, int pageNo, int pageSize)throws Exception;
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo,int pageSize) throws Exception;
}
