package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.MovieDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.Movie;

public class MovieDaoImpl implements MovieDao{
	private static final Logger logger = LoggerFactory.getLogger(MovieDaoImpl.class);
	
	private SqlSession session;

	@Override
	public long insert(Movie movie) {
		return session.insert("insertMovie", movie)>0?movie.getMovieid():0;
	}

	@Override
	public Movie select(long movieid) {
		if(movieid<1)return null;
		Movie movie = new Movie();
		movie.setMovieid(movieid);
		List<Movie> movies = select(movie,1,1);
		return movies.size()!=1?null:movies.get(0);
	}
	
	@Override
	public List<Movie> select(Movie movie,int pageNo,int pageSize) {
		if(movie == null)return new ArrayList<Movie>();
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("movieid", movie.getMovieid());
		param.put("name", movie.getName());
		param.put("type", movie.getType());
		param.put("shortdesc", movie.getShortdesc());
		param.put("longdesc", movie.getLongdesc());
		param.put("face650x500", movie.getFace650x500());
		param.put("face220x169", movie.getFace220x169());
		param.put("face150x220", movie.getFace150x220());
		param.put("face80x80", movie.getFace80x80());
		param.put("picture", movie.getPicture());
		param.put("score", movie.getScore());
		param.put("approve", movie.getApprove());
		param.put("favorite", movie.getFavorite());
		param.put("download", movie.getDownload());
		param.put("broswer", movie.getBroswer());
		param.put("createtime", movie.getCreatetime());
		param.put("publishtime", movie.getPublishtime());
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectMovie", param);
	}
		
	
	@Override
	public Page<Movie> selectPage(Movie movie, int pageNo, int pageSize) {
		Page<Movie> page = new Page<Movie>();
		if(movie == null)return page;
		Map<String,Object>  result = session.selectOne("selectMovieCount",movie);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(select(movie,pageNo,pageSize));
		return page;
	}
	
	@Override
	public List<Movie> selectByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("modmvid", modmvid);
		param.put("modname", modname);
		param.put("isPublish", isPublish);
		param.put("isSortCreateTimeUp", isSortCreateTimeUp);
		param.put("isScoreUp", isScoreUp);
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectMovieByModule", param);
	}

	@Override
	public Page<Movie> selectPageByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize) {
		Page<Movie> page = new Page<Movie>();
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("modmvid", modmvid);
		param.put("modname", modname);
		param.put("isPublish", isPublish);
		param.put("isSortCreateTimeUp", isSortCreateTimeUp);
		param.put("isScoreUp", isScoreUp);
		Map<String,Object>  result = session.selectOne("selectMovieByModuleCount",param);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(selectByModule(modmvid, modname, isPublish, isSortCreateTimeUp,isScoreUp,pageNo, pageSize));
		return page;
	}
	
	@Override
	public boolean update(Movie movie) {
		if(movie.getMovieid()<1)return false;
		return session.update("updateMovie", movie)>0;
	}

	@Override
	public boolean delete(Movie movie) {
		if(movie==null || movie.getMovieid() <1)return false;
		return session.delete("deleteMovie", movie)>0;
	}
	
	@Override
	public boolean delete(long movieid) {
		if(movieid<1)return false;
		Movie movie = new Movie();
		movie.setMovieid(movieid);
		return delete(movie);
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	

	

}