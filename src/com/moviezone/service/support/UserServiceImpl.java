package com.moviezone.service.support;

import java.util.Date;

import com.moviezone.dao.UserDao;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	@Override
	public void testTransaction() throws Exception{
		//userDao.selectById(1);
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
	
		
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
}
