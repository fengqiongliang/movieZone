package com.moviezone.service;

import java.util.List;








import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public boolean deleteModuleById(long modmvid);
	public Page<MovieWrapper> selectOnlineMovie(Long movieid,String name,String[] type,String[] sort,int pageNo);
	public Page<MovieWrapper> selectOfflineMovie(Long movieid,String name,String[] type,int pageNo);
	public MovieWrapper selectAsWrapper(long movieid);
	public void saveMovie(long movieid,String name,String type,String shortdesc, String longdesc,float score, int approve, int download,int browse,String publishDate,String[] attachInfos,String[] modnames,String face650x500,String face400x308,String face220x169,String face150x220,String face80x80,String[] pictures) throws Exception;
	public boolean mvModule(long fromModmvid,long toModmvid);
	public List<Movie> selectFavoriteMovie(long userid,int pageNo,int pageSize);
}
