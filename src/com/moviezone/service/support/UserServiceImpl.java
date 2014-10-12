package com.moviezone.service.support;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.UserDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;

public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserDao userDao;
	
	@Override
	public void testTransaction() throws Exception{
		//userDao.selectById(1);
		/*
		User user = new User();
		user.setId(3);
		user.setNickname("ahone");
		user.setEmail("18210456549");
		user.setAreacode("123");
		user.setPassword("md5");
		user.setPlainpwd("123456");
		user.setLastlogtime(new Date());
		user.setCreateip("192.168.0.100");
		
		System.out.println(userDao.contain(null, -1, null));
		*/
	}
	
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	@Override
	public User select(long userid) {
		return userDao.select(userid);
	}


	@Override
	public List<User> select(User user) {
		return userDao.select(user);
	}
	
	@Override
	public List<User> select(User user, int pageNo, int pageSize) {
		return userDao.select(user, pageNo, pageSize);
	}


	@Override
	public Page<User> selectPage(User user) {
		return userDao.selectPage(user);
	}


	@Override
	public Page<User> selectPage(User user, int pageNo, int pageSize) {
		return userDao.selectPage(user, pageNo, pageSize);
	}

	@Override
	public long insert(User user) {
		return userDao.insert(user);
	}


	@Override
	public boolean update(User user) {
		return userDao.update(user);
	}


	@Override
	public boolean delete(User user) {
		return userDao.delete(user);
	}


	@Override
	public boolean delete(long userid) {
		return userDao.delete(userid);
	}


	
	
}
