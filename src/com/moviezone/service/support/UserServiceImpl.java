package com.moviezone.service.support;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.UserDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.KeyService;
import com.moviezone.service.UserService;

public class UserServiceImpl implements UserService{
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private UserDao userDao;
	private KeyService keyService;
	
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
		if(user==null)return 0;
		user.setUserid(keyService.getUserid());
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


	


	@Override
	public List<User> selectForbit(User user, int pageNo, int pageSize) {
		return userDao.selectForbit(user, pageNo, pageSize);
	}


	@Override
	public Page<User> selectForbitPage(User user, int pageNo, int pageSize) {
		return userDao.selectForbitPage(user, pageNo, pageSize);
	}


	@Override
	public Page<User> selectForbitUser(String userid, String nickname,String createarea, String createip, int pageNo) {
		User user = new User();
		user.setRole("user");
		if(StringUtils.isNotBlank(userid))user.setUserid(Long.parseLong(userid));
		if(StringUtils.isNotBlank(nickname))user.setNickname(nickname);
		if(StringUtils.isNotBlank(createarea))user.setCreatearea(createarea);
		if(StringUtils.isNotBlank(createip))user.setCreateip(createip);
		return selectForbitPage(user, pageNo, 25);
	}


	@Override
	public Page<User> selectNormalUser(String userid, String nickname,String createarea, String createip, int pageNo) {
			User user = new User();
			user.setRole("user");
			user.setIsForbit("false");
			if(StringUtils.isNotBlank(userid))user.setUserid(Long.parseLong(userid));
			if(StringUtils.isNotBlank(nickname))user.setNickname(nickname);
			if(StringUtils.isNotBlank(createarea))user.setCreatearea(createarea);
			if(StringUtils.isNotBlank(createip))user.setCreateip(createip);
			return selectPage(user, pageNo, 25);
	}

	@Override
	public boolean delForbit(long userid) {
		User user = select(userid);
		if(user != null){
			user.setIsForbit("false");
			return update(user);
		}
		user = new User();
		user.setUserid(userid);
		return userDao.delForbit(user);
	}


	@Override
	public void permitModify(long userid) {
		User user = select(userid);
		if(user==null)return;
		userDao.updateNickOrFace(user);
	}


	@Override
	public void addNormalForbit(long userid) {
		User user = select(userid);
		if(user==null)return;
		user.setIsForbit("true");
		update(user);
	}


	@Override
	public void addSystemForbit(String createip,String createarea) {
		User user = new User();
		user.setUserid(keyService.getUserid());
		user.setNickname("系统禁用ip-"+user.getUserid());
		user.setCreateip(createip);
		user.setCreatearea(createarea);
		userDao.insertForbit(user);
	}

	public KeyService getKeyService() {
		return keyService;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}


	@Override
	public boolean isForbitIp(String ip) {
		User param = new User();
		param.setCreateip(ip);
		return userDao.selectSystemForbit(param, 1, 1).size()>0;
	}
	
	
}
