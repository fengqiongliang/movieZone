package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;

public class UserDaoImpl implements UserDao{
	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	private UserCache userCache;
	private SqlSession session;

	@Override
	public long insert(User user) {
		return session.insert("insertUser", user)>0?user.getUserid():0;
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
		return select(user,1,10);
	}
	
	@Override
	public List<User> select(User user,int pageNo,int pageSize) {
		if(user == null)return new ArrayList<User>();
		Map<String,Object> parameter = new  HashMap<String,Object>();
		parameter.put("userid", user.getUserid());
		parameter.put("username", user.getUsername());
		parameter.put("password", user.getPassword());
		parameter.put("nickname", user.getNickname());
		parameter.put("faceurl", user.getFaceurl());
		parameter.put("cookie_id", user.getCookie_id());
		parameter.put("role", user.getRole());
		parameter.put("createip", user.getCreateip());
		parameter.put("createarea", user.getCreatearea());
		parameter.put("nextnick", user.getNextnick());
		parameter.put("nextface", user.getNextface());
		parameter.put("start", (pageNo-1)*pageSize);
		parameter.put("size", pageSize);
		return session.selectList("selectUser", parameter);
	}
	
	@Override
	public Page<User> selectPage(User user) {
		return selectPage(user,1,10);
	}

	@Override
	public Page<User> selectPage(User user, int pageNo, int pageSize) {
		Page<User> page = new Page<User>();
		if(user == null)return page;
		Map<String,Object>  result = session.selectOne("selectUserCount",user);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(select(user,pageNo,pageSize));
		return page;
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
