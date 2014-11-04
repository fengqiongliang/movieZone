package com.moviezone.service.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.MovieDao;
import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.MovieWrapper;
import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;
import com.moviezone.service.CommentService;
import com.moviezone.service.KeyService;
import com.moviezone.service.MovieService;

public class MovieServiceImpl implements MovieService{
	private static final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
	private MovieDao movieDao;
	private KeyService keyService;
	private CommentService commentService;
	
	@Override
	public Movie select(long movieid) {
		return movieDao.select(movieid);
	}
	
	@Override
	public MovieWrapper selectAsWrapper(long movieid) {
		if(movieid<1)return null;
		Movie mv = select(movieid);
		MovieWrapper wrapper = new MovieWrapper();
		wrapper.setMovie(mv);
		wrapper.setModules(selectModule(mv.getMovieid()));
		wrapper.setAttachs(selectAttach(mv.getMovieid()));
		return wrapper;
	}
	
	@Override
	public List<Module> selectModule(long movieid) {
		return movieDao.selectModule(movieid);
	}

	@Override
	public List<Attach> selectAttach(long movieid) {
		return movieDao.selectAttach(movieid);
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
		return movieDao.selectByModule(-1L, modname, isPublish, null,null,pageNo, pageSize);
	}

	@Override
	public Page<Movie> selectPageByModule(String modname, Boolean isPublish,int pageNo, int pageSize) {
		return movieDao.selectPageByModule(-1L, modname, isPublish, null,null,pageNo, pageSize);
	}
	
	@Override
	public Page<Movie> selectPageByModule(String modname, Boolean isPublish,Boolean isSortCreateTimeUp, Boolean isScoreUp, int pageNo,int pageSize) {
		return movieDao.selectPageByModule(-1L, modname, isPublish, isSortCreateTimeUp,isScoreUp,pageNo, pageSize);
	}
	
	@Override
	public Page<MovieWrapper> selectOnlineMovie(Long movieid,String name,String[] type,String[] sort,int pageNo) {
		Movie movie = new Movie();
		if(movieid !=null)movie.setMovieid(movieid);
		if(StringUtils.isNotBlank(name))movie.setName(name);
		if(type!=null && type.length == 1 && StringUtils.isNotBlank(type[0]))movie.setType(type[0]);
		return getMovieWraper(movieDao.selectPage(movie, true, pageNo, 25));
	}

	@Override
	public Page<MovieWrapper> selectOfflineMovie(Long movieid,String name,String[] type,int pageNo) {
		Movie movie = new Movie();
		if(movieid !=null)movie.setMovieid(movieid);
		if(StringUtils.isNotBlank(name))movie.setName(name);
		if(type!=null && type.length == 1 && StringUtils.isNotBlank(type[0]))movie.setType(type[0]);
		return getMovieWraper(movieDao.selectPage(movie, false, pageNo, 25));
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
	
	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	private Page<MovieWrapper> getMovieWraper(Page<Movie> movies){
		List<MovieWrapper> datas = new ArrayList<MovieWrapper>();
		Page<MovieWrapper> movieWrappers = new Page<MovieWrapper>();
		movieWrappers.setPageNo(movies.getPageNo());
		movieWrappers.setPageSize(movies.getPageSize());
		movieWrappers.setTotal(movies.getPageTotal());
		movieWrappers.setData(datas);
		for(Movie mv:movies.getData()){
			MovieWrapper data = new MovieWrapper();
			data.setMovie(mv);
			data.setModules(selectModule(mv.getMovieid()));
			data.setCmmtCount(commentService.selectCommentCount(mv.getMovieid()));
			datas.add(data);
		}
		return movieWrappers;
	}

	

	
	

	

	

}
