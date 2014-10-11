package com.moviezone.service;

import java.util.List;

import com.moviezone.domain.User;

public interface UserService {
	public User select(long userid);
	public List<User> select(User user);
	public long insert(User user);
	public boolean update(User user);
	public boolean delete(User user);
	public boolean delete(long userid);
	public void testTransaction()throws Exception;
}
