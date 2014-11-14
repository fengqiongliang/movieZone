package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.StatDao;
import com.moviezone.domain.IP;

public class StatDaoImpl implements StatDao{
	private static final Logger logger = LoggerFactory.getLogger(StatDaoImpl.class);
	
	private SqlSession session;

	
	@Override
	public List<IP> select() {
		Map<String,Object> parameter = new  HashMap<String,Object>();
		parameter.put("start", 0);
		parameter.put("size", 2292340);
		return session.selectList("selectIp", parameter);
	}
	
	@Override
	public boolean update(IP ip) {
		if(ip.getId()<1)return false;
		return session.update("updateIp", ip)>0;
	}

	public void setSession(SqlSession session) {
		this.session = session;
	}
	

	

}
