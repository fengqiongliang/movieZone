package com.moviezone.dao.support;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.cache.UserCache;
import com.moviezone.dao.IncrementDao;
import com.moviezone.domain.Increment;

public class IncrementDaoImpl implements IncrementDao{
	private static final Logger logger = LoggerFactory.getLogger(IncrementDaoImpl.class);
	private UserCache userCache;
	private SqlSession session;
	
	@Override
	public List<Increment> select() {
		return session.selectList("selectIncre");
	}

	@Override
	public boolean update(Increment incre) {
		return session.update("updateIncre", incre)>0;
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	public void setUserCache(UserCache userCache) {
		this.userCache = userCache;
	}

	

	
	

}
