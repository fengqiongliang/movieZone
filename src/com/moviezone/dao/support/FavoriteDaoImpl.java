package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.FavoriteDao;
import com.moviezone.domain.Favorite;

public class FavoriteDaoImpl implements FavoriteDao{
	private static final Logger logger = LoggerFactory.getLogger(FavoriteDaoImpl.class);
	private SqlSession session;
	
	@Override
	public Favorite select(long favoriteid) {
		if(favoriteid<1)return null;
		Favorite favorite = new Favorite();
		favorite.setFavoriteid(favoriteid);
		List<Favorite> result = select(favorite,1,1);
		return result.size()>0?result.get(0):null;
	}

	@Override
	public List<Favorite> select(Favorite favorite, int pageNo, int pageSize) {
		if(favorite==null)return new ArrayList<Favorite>();
		Map<String,Object> parameter = new  HashMap<String,Object>();
		parameter.put("favoriteid", favorite.getFavoriteid());
		parameter.put("userid", favorite.getUserid());
		parameter.put("movieid", favorite.getMovieid());
		parameter.put("start", (pageNo-1)*pageSize);
		parameter.put("size", pageSize);
		return session.selectList("selectFavorite", parameter);
	}

	@Override
	public long insert(Favorite favorite) {
		if(favorite==null)return 0;
		return session.insert("insertFavorite", favorite)>0?favorite.getFavoriteid():0;
	}

	@Override
	public boolean update(Favorite favorite) {
		if(favorite==null)return false;
		return session.update("updateFavorite", favorite)>0?true:false;
	}

	@Override
	public boolean delete(long favoriteid) {
		if(favoriteid<1)return false;
		Favorite favorite = new Favorite();
		favorite.setFavoriteid(favoriteid);
		return delete(favorite);
	}

	@Override
	public boolean delete(Favorite favorite) {
		if(favorite==null||favorite.getFavoriteid()<1)return false;
		return session.delete("deleteFavorite", favorite)>0?true:false;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}

}
