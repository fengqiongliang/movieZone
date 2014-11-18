package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Favorite;



public interface FavoriteDao {
	public Favorite select(long favoriteid);
	public List<Favorite> select(Favorite favorite,int pageNo,int pageSize);
	public long insert(Favorite favorite);
	public boolean update(Favorite favorite);
	public boolean delete(long favoriteid);
	public boolean delete(Favorite favorite);
}
