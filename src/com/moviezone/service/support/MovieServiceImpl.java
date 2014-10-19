package com.moviezone.service.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.MovieDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;
import com.moviezone.service.KeyService;
import com.moviezone.service.MovieService;

public class MovieServiceImpl implements MovieService{
	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	private MovieDao movieDao;
	private KeyService keyService;

	@Override
	public Movie select(long movieid) {
		return movieDao.select(movieid);
	}

	@Override
	public List<Movie> select(Movie movie, int pageNo, int pageSize) {
		return movieDao.select(movie, pageNo, pageSize);
	}

	@Override
	public Page<Movie> selectPage(Movie movie, int pageNo, int pageSize) {
		return movieDao.selectPage(movie, pageNo, pageSize);
	}
	
	@Override
	public List<Movie> selectByModule(String modname, Boolean isPublish,int pageNo, int pageSize) {
		return movieDao.selectByModule(-1L, modname, isPublish, pageNo, pageSize);
	}

	@Override
	public Page<Movie> selectPageByModule(String modname, Boolean isPublish,int pageNo, int pageSize) {
		return movieDao.selectPageByModule(-1L, modname, isPublish, pageNo, pageSize);
	}
	
	@Override
	public long insert(Movie movie) {
		movie.setMovieid(keyService.getMovieid());
		return movieDao.insert(movie);
	}

	@Override
	public boolean update(Movie movie) {
		return movieDao.update(movie);
	}

	@Override
	public boolean delete(Movie movie) {
		return movieDao.delete(movie);
	}

	@Override
	public boolean delete(long movieid) {
		return movieDao.delete(movieid);
	}
	
	public void setMovieDao(MovieDao movieDao) {
		this.movieDao = movieDao;
	}
	
	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

}
