package com.moviezone.service.support;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.PXEDao;
import com.moviezone.domain.PxeDhcp;
import com.moviezone.service.KeyService;
import com.moviezone.service.PXEService;

public class PXEServiceImpl implements PXEService{
	private static final Logger logger = LoggerFactory.getLogger(PXEServiceImpl.class);
	private PXEDao pxeDao;
	private KeyService keyService;
	
	@Override
	public void saveDHCP(String clientMac, String clientIp,String clientSubmask,String clientGateway,String fileName,int leaseTime) {
		PxeDhcp dhcp = new PxeDhcp();
		dhcp.setClient_mac(clientMac);
		dhcp.setClient_ip(clientIp);
		dhcp.setClient_submask(clientSubmask);
		dhcp.setClient_gateway(clientGateway);
		dhcp.setFile_name(fileName);
		dhcp.setCreatetime(new Date());
		dhcp.setInvalidtime(new Date(System.currentTimeMillis()+leaseTime*1000));
		pxeDao.deleteDhcp(dhcp);
		pxeDao.insertDhcp(dhcp);
	}

	public void setPxeDao(PXEDao pxeDao) {
		this.pxeDao = pxeDao;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}
	
	
}
