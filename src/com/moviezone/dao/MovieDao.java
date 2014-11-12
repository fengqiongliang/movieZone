package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;


public interface MovieDao {
	public Movie select(long movieid);
	public Module selectModuleById(long modmvid);
	public List<Movie> select(Movie movie,int pageNo,int pageSize);
	public List<Movie> select(Movie movie,Boolean isPublish,int pageNo,int pageSize);
	public Page<Movie> selectPage(Movie movie,int pageNo,int pageSize);
	public Page<Movie> selectPage(Movie movie,Boolean isPublish,int pageNo,int pageSize);
	public List<Movie> selectByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize);
	public Page<Movie> selectPageByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize);
	public List<Module> selectModule(long movieid);
	public List<Attach> selectAttach(long movieid);
	public long insert(Movie movie);
	public long insert(Attach attach);
	public long insert(Module module);
	public boolean update(Movie movie);
	public boolean update(Module module);
	public boolean delete(Movie movie);    
	public boolean delete(long movieid);   
	public boolean deleteAttach(long movieid);   
	public boolean deleteModule(long movieid);   
	public boolean deleteModuleById(long modmvid);
}
