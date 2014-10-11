package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.moviezone.cache.CacheClient;
import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.domain.User;

public class UserDaoImpl implements UserDao{
	private UserCache userCache;
	private SqlSession session;

	@Override
	public long insert(User user) {
		long userid = 12345;              //id生成策略
		user.setUserid(userid);
		return session.insert("insertUser", user)>0?userid:0;
	}

	@Override
	public User select(long userid) {
		if(userid<1)return null;
		User user = new User();
		user.setUserid(userid);
		List<User> users = select(user);
		return users.size()!=1?null:users.get(0);
	}

	@Override
	public List<User> select(User user) {
		if(user == null)return new ArrayList<User>(); 
		return session.selectList("selectUser", user);
	}
	
	@Override
	public boolean update(User user) {
		if(user.getUserid()<1)return false;
		return session.update("updateUser", user)>0;
	}

	@Override
	public boolean delete(User user) {
		if(user==null || user.getUserid() <1)return false;
		return session.delete("deleteUser", user)>0;
	}
	
	@Override
	public boolean delete(long userid) {
		if(userid<1)return false;
		User user = new User();
		user.setUserid(userid);
		return delete(user);
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

}
