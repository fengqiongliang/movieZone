package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Page;
import com.moviezone.domain.PxeDhcp;




public interface PXEDao {
	public List<PxeDhcp> selectDhcpList(PxeDhcp param,int pageNo,int pageSize);
	public Page<PxeDhcp> selectDhcpPage(PxeDhcp param,int pageNo,int pageSize);
	public boolean insertDhcp(PxeDhcp param);
	public boolean updateDhcp(PxeDhcp param);
	public boolean deleteDhcp(PxeDhcp param);
	public boolean deleteDhcp(String clientMac);
}
