package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Page;
import com.moviezone.domain.User;


public interface UserDao {
	public User select(long userid);
	public List<User> select(User user);
	public List<User> select(User user,int pageNo,int pageSize);
	public Page<User> selectPage(User user);
	public Page<User> selectPage(User user,int pageNo,int pageSize);
	public long insert(User user);
	public boolean update(User user);
	public boolean delete(User user);
	public boolean delete(long userid);
}
