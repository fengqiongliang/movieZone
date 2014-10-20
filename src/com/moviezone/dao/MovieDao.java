package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;


public interface MovieDao {
	public Movie select(long movieid);
	public List<Movie> select(Movie movie,int pageNo,int pageSize);
	public Page<Movie> selectPage(Movie movie,int pageNo,int pageSize);
	public List<Movie> selectByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize);
	public Page<Movie> selectPageByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize);
	public long insert(Movie movie);
	public boolean update(Movie movie);
	public boolean delete(Movie movie);    
	public boolean delete(long movieid);   
}