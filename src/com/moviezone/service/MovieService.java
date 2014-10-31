package com.moviezone.service;

import java.util.List;





import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.MovieWrapper;
import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;

public interface MovieService {
	public Movie select(long movieid);
	public List<Module> selectModule(long movieid);
	public List<Attach> selectAttach(long movieid);
	public List<Movie> select(Movie movie,int pageNo,int pageSize);
	public Page<Movie> selectPage(Movie movie,int pageNo,int pageSize);
	public List<Movie> selectByModule(String modname,Boolean isPublish,int pageNo,int pageSize);
	public Page<Movie> selectPageByModule(String modname,Boolean isPublish,int pageNo,int pageSize);
	public Page<Movie> selectPageByModule(String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize);
	public long insert(Movie movie);
	public boolean update(Movie movie);
	public boolean delete(Movie movie);
	public boolean delete(long userid);
	public Page<MovieWrapper> selectOnlineMovie(Movie movie,int pageNo,int pageSize);
	public Page<MovieWrapper> selectOfflineMovie(Movie movie,int pageNo,int pageSize);
}
