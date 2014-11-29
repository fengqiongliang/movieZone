package com.moviezone.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchResult implements Serializable{
	private static final long serialVersionUID = -2378455658756102117L;
	private String keyword;   
	private String matchType;            
	private List<String[]> types   = new ArrayList<String[]>();
	private Page<Movie> movies = new Page<Movie>();
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMatchType() {
		return matchType;
	}
	public void setMatchType(String matchType) {
		this.matchType = matchType;
	}
	public List<String[]> getTypes() {
		return types;
	}
	public void setTypes(List<String[]> types) {
		this.types = types;
	}
	public Page<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Page<Movie> movies) {
		this.movies = movies;
	}
	
}
