package com.moviezone.service.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.FavoriteDao;
import com.moviezone.domain.Favorite;
import com.moviezone.service.FavoriteService;
import com.moviezone.service.KeyService;


public class FavoriteServiceImpl implements FavoriteService{
	private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);
	private FavoriteDao favoriteDao;
	private KeyService keyService;
	
	@Override
	public void saveFavorite(long userid, long movieid) {
		//查询确认用户是否已经收藏过该电影
		Favorite param = new Favorite();
		param.setUserid(userid);
		List<Favorite> favorites = favoriteDao.select(param, 1, 6);
		for(Favorite favorite:favorites){
			if(favorite.getMovieid()==movieid)return; 
		}
		//用户没有收藏过该电影，并且收藏电影足够6个，则更新收藏
		if(favorites.size()==6){
			favorites.get(5).setMovieid(movieid);;
			favoriteDao.update(favorites.get(5));
			return;
		}
		
		//用户没有收藏过该电影，并且收藏电影不足6个，则插入收藏
		Favorite newFavorite = new Favorite();
		newFavorite.setFavoriteid(keyService.getFavoriteid());
		newFavorite.setUserid(userid);
		newFavorite.setMovieid(movieid);
		favoriteDao.insert(newFavorite);
		
	}
	
	public void setFavoriteDao(FavoriteDao favoriteDao) {
		this.favoriteDao = favoriteDao;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	
	
	
}
