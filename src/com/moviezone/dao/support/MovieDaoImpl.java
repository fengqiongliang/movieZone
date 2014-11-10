package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.MovieDao;
import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
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
	public long insert(Attach attach) {
		return session.insert("insertAttach", attach)>0?attach.getAttachid():0;
	}

	@Override
	public long insert(Module module) {
		return session.insert("insertModule", module)>0?module.getModmvid():0;
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
	public List<Movie> select(Movie movie, int pageNo,int pageSize) {
		return select(movie,null,pageNo,pageSize);
	}
	
	@Override
	public List<Movie> select(Movie movie,Boolean isPublish,int pageNo,int pageSize) {
		if(movie == null)return new ArrayList<Movie>();
		return session.selectList("selectMovie", getMovieParamMap(movie,isPublish,pageNo,pageSize));
	}
	
	@Override
	public Page<Movie> selectPage(Movie movie, int pageNo,int pageSize) {
		return selectPage(movie, null,pageNo, pageSize) ;
	}
	
	@Override
	public Page<Movie> selectPage(Movie movie, Boolean isPublish,int pageNo, int pageSize) {
		Page<Movie> page = new Page<Movie>();
		if(movie == null)return page;
		Map<String,Object>  result = session.selectOne("selectMovieCount",getMovieParamMap(movie,isPublish,pageNo,pageSize));
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(select(movie,isPublish,pageNo,pageSize));
		return page;
	}
	
	
	@Override
	public List<Movie> selectByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize) {
		return session.selectList("selectMovieByModule", getModuleParamMap(modmvid,modname,isPublish,isSortCreateTimeUp,isScoreUp,pageNo,pageSize));
	}

	@Override
	public Page<Movie> selectPageByModule(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize) {
		Page<Movie> page = new Page<Movie>();
		Map<String,Object>  result = session.selectOne("selectMovieByModuleCount",getModuleParamMap(modmvid,modname,isPublish,isSortCreateTimeUp,isScoreUp,pageNo,pageSize));
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(selectByModule(modmvid, modname, isPublish, isSortCreateTimeUp,isScoreUp,pageNo, pageSize));
		return page;
	}
	
	@Override
	public List<Module> selectModule(long movieid) {
		if(movieid<1)return new ArrayList<Module>();
		Module param = new Module();
		param.setMovieid(movieid);
		return session.selectList("selectModule", param);
	}

	@Override
	public List<Attach> selectAttach(long movieid) {
		if(movieid<1)return new ArrayList<Attach>();
		Attach param = new Attach();
		param.setMovieid(movieid);
		return session.selectList("selectAttach", param);
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
	@Override
	public boolean deleteAttach(long movieid) {
		if(movieid<1)return false;
		Attach attach = new Attach();
		attach.setMovieid(movieid);
		return session.delete("deleteAttachByMovieid", attach)>0;
	}

	@Override
	public boolean deleteModule(long movieid) {
		if(movieid<1)return false;
		Module module = new Module();
		module.setMovieid(movieid);
		return session.delete("deleteModuleByMovieid", module)>0;
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	
	private Map<String,Object> getMovieParamMap(Movie movie, Boolean isPublish,int pageNo, int pageSize) {
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
		param.put("isPublish",isPublish);
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return param;
	}
	
	private Map<String,Object> getModuleParamMap(Long modmvid,String modname,Boolean isPublish,Boolean isSortCreateTimeUp,Boolean isScoreUp,int pageNo,int pageSize) {
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("modmvid", modmvid);
		param.put("modname", modname);
		param.put("isPublish", isPublish);
		param.put("isSortCreateTimeUp", isSortCreateTimeUp);
		param.put("isScoreUp", isScoreUp);
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return param;
	}

	

	
	

	

	

	

}
