package com.moviezone.service.support;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.constant.Constants;
import com.moviezone.dao.CommentDao;
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
	private CommentDao commentDao;
	
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

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
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
			data.setCmmtCount(commentDao.selectCommentCount(mv.getMovieid()));
			data.setFavoriteCount(movieDao.selectFavoriteMovieCount(mv.getMovieid()));
			datas.add(data);
		}
		return movieWrappers;
	}

	@Override
	public void saveMovie(long movieid, 
										String name, 
										String type,
										String shortdesc, 
										String longdesc, 
										float score,
										int approve,
										int download, 
										int browse,
										String publishDate,
										String[] attachInfos,
										String[] modnames, 
										String face650x500, 
										String face400x308,
										String face220x169, 
										String face150x220, 
										String face80x80,
										String[] pictures) throws Exception {
		Movie movie = movieid>0?movieDao.select(movieid):new Movie();
		movie.setMovieid(movieid>0?movieid:keyService.getMovieid());
		movie.setName(name.trim());
		movie.setType(type.trim());
		movie.setShortdesc(shortdesc.trim());
		movie.setLongdesc(longdesc.trim());
		movie.setScore(score);
		movie.setApprove(approve);
		movie.setDownload(download);
		movie.setBroswer(browse);
		movie.setPublishtime(Constants.formater.parse(publishDate));
		movie.setFace650x500(face650x500.replace(Constants.base, "").trim());
		movie.setFace400x308(face400x308.replace(Constants.base, "").trim());
		movie.setFace220x169(face220x169.replace(Constants.base, "").trim());
		movie.setFace150x220(face150x220.replace(Constants.base, "").trim());
		movie.setFace80x80(face80x80.replace(Constants.base, "").trim());
		JSONArray picJsonArray = new JSONArray();
		for(String picture:pictures){
			picJsonArray.add(picture.replace(Constants.base, "").trim());
		}
		movie.setPicture(picJsonArray.toString());
		if(movieid>0){ //更新
			movieDao.update(movie);
		}else{              //新增
			movieDao.insert(movie);
		}
		//附件
		if(movieid>0)movieDao.deleteAttach(movieid);
		for(int i=0;attachInfos!=null && i<attachInfos.length;i++){
			Attach attach = new Attach();
			attach.setMovieid(movie.getMovieid());
			attach.setAttachid(keyService.getAttachid());
			int startIndex       = attachInfos[i].indexOf("_http");
			int endIndex        = attachInfos[i].indexOf("_", startIndex+1);
			String new_name = attachInfos[i].substring(0, startIndex).trim();
			String attach_url  = attachInfos[i].substring(startIndex+1, endIndex).replace(Constants.base, "").trim();
			String old_name  = attachInfos[i].substring(endIndex+1);
			attach.setAttach_url(attach_url);
			attach.setNew_name(new_name);
			if(StringUtils.isNotBlank(old_name))attach.setOld_name(old_name);
			movieDao.insert(attach);
		}
		
		//模块 
		if(movieid>0)movieDao.deleteModule(movieid);
		for(int i=0;modnames!=null && i<modnames.length;i++){
			Module module = new Module();
			module.setMovieid(movie.getMovieid());
			module.setModmvid(keyService.getModmvid());
			module.setModname(modnames[i]);
			movieDao.insert(module);
		}
		
		
	}

	@Override
	public boolean deleteModuleById(long modmvid) {
		return movieDao.deleteModuleById(modmvid);
	}

	@Override
	public boolean mvModule(long fromModmvid, long toModmvid) {
		if(fromModmvid<1||toModmvid<1)return false;
		Module fromModule = movieDao.selectModuleById(fromModmvid);
		if(fromModule == null)return false;
		Module toModule = movieDao.selectModuleById(toModmvid);
		if(toModule==null)return false;
		long fromOrderseq = fromModule.getOrderseq();
		long toOrderseq      = toModule.getOrderseq();
		fromModule.setOrderseq(toOrderseq);
		toModule.setOrderseq(fromOrderseq);
		movieDao.update(fromModule);
		movieDao.update(toModule);
		return true;
	}

	@Override
	public List<Movie> selectFavoriteMovie(long userid, int pageNo, int pageSize) {
		if(userid<1)return new ArrayList<Movie>();
		return movieDao.selectFavoriteMovie(userid, pageNo, pageSize);
	}

}
