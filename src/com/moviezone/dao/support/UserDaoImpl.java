package com.moviezone.dao.support;


import java.util.HashMap;
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
	public User selectById(long id) {
		if(id<0)return null;
		User user = userCache.getById(id);
		if(user!=null)return user;
		user = (User)session.selectOne("selectById", id);
		userCache.putUser(user);
		return user;
	}

	@Override
	public User selectByNickName(String nickname) {
		if(StringUtils.isBlank(nickname))return null;
		User user = userCache.getByNickName(nickname);
		if(user!=null)return user;
		user = (User)session.selectOne("selectByNickName", nickname);
		userCache.putUser(user);
		return user;
	}

	@Override
	public User selectByMobile(long mobile) {
		if(mobile<0)return null;
		User user = userCache.getByMobile(mobile);
		if(user!=null)return user;
		user = (User)session.selectOne("selectByMobile", mobile);
		userCache.putUser(user);
		return user;
	}

	@Override
	public User selectByEmail(String email) {
		if(StringUtils.isBlank(email))return null;
		User user = userCache.getByEmail(email);
		if(user!=null)return user;
		user = (User)session.selectOne("selectByEmail", email);
		userCache.putUser(user);
		return user;
	}

	@Override
	public long insert(User user) {
		//从缓存中生成id
		long newId = userCache.getNewId();
		if(newId<=0){
			Map rs = (Map)session.selectOne("selectMaxId");
			newId = (Long)rs.get("maxId")+1; 
		}
		user.setId(newId);
		if(session.insert("insertUser", user)>0){
			userCache.putUser(user);
			return newId;
		}
		return -1;
	}

	@Override
	public boolean update(User user) {
		if(user.getId()<1)return false;
		if(session.update("updateUser", user)>0){
			userCache.putUser(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean delete(long id) {
		if(id<0)return false;
		if(session.delete("deleteUser", id)>0){
			User user = selectById(id);
			userCache.delUser(user);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean contain(String nickname, long mobile, String email) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nickname", nickname);
		params.put("mobile", mobile);
		params.put("email",email);
		return session.selectList("contain", params).size()>0?true:false;
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}
}
