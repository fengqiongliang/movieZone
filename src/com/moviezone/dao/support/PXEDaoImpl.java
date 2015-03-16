package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.PXEDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.PxeDhcp;
import com.moviezone.domain.User;

public class PXEDaoImpl implements PXEDao{
	private static final Logger logger = LoggerFactory.getLogger(PXEDaoImpl.class);
	private Random rand = new Random();
	
	private SqlSession session;

	@Override
	public List<PxeDhcp> selectDhcpList(PxeDhcp param, int pageNo, int pageSize) {
		if(param == null)return new ArrayList<PxeDhcp>();
		Map<String,Object> parameter = new  HashMap<String,Object>();
		parameter.put("client_mac", param.getClient_mac());
		parameter.put("client_ip", param.getClient_ip());
		parameter.put("client_submask", param.getClient_submask());
		parameter.put("client_gateway", param.getClient_gateway());
		parameter.put("file_name", param.getFile_name());
		parameter.put("createtime", param.getCreatetime());
		parameter.put("invalidtime", param.getInvalidtime());
		parameter.put("start", (pageNo-1)*pageSize);
		parameter.put("size", pageSize);
		return session.selectList("selectUser", parameter);
	}

	@Override
	public Page<PxeDhcp> selectDhcpPage(PxeDhcp param, int pageNo, int pageSize) {
		Page<PxeDhcp> page = new Page<PxeDhcp>();
		if(param == null)return page;
		Map<String,Object>  result = session.selectOne("selectPxeDhcpCount",param);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(selectDhcpList(param,pageNo,pageSize));
		return page;
	}

	@Override
	public boolean insertDhcp(PxeDhcp param) {
		if(param==null || StringUtils.isBlank(param.getClient_mac()))return false;
		return session.insert("insertPxeDhcp", param)>0;
	}


	@Override
	public boolean updateDhcp(PxeDhcp param) {
		if(param==null || StringUtils.isBlank(param.getClient_mac()))return false;
		return session.update("updatePxeDhcp", param)>0;
	}

	@Override
	public boolean deleteDhcp(PxeDhcp param) {
		if(param==null || StringUtils.isBlank(param.getClient_mac()))return false;
		return session.delete("deletePxeDhcp", param)>0;
	}

	@Override
	public boolean deleteDhcp(String clientMac) {
		PxeDhcp param = new PxeDhcp();
		param.setClient_mac(clientMac);
		return deleteDhcp(param);
	}

	
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	

}
