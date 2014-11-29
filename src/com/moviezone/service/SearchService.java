package com.moviezone.service;

import java.util.List;

import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;



public interface SearchService {
	
	public SearchResult search(String keyword, String type, int pageNo)throws Exception;
	public Page<Movie> searchAsPage(String keyword, String type, int pageNo) throws Exception;
	
}
