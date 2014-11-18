package com.moviezone.service;

import java.util.List;



public interface FavoriteService {
	/**
	 * 用户收藏电影/电视剧（注：用户最多能收藏6个电影）
	 * 
	 * @param userid
	 * @param movieid
	 */
	public void saveFavorite(long userid,long movieid);
}
