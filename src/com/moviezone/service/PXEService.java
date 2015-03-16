package com.moviezone.service;


public interface PXEService {
	
	public void saveDHCP(String clientMac, String clientIp, String clientSubmask,String clientGateway, String fileName, int leaseTime);
	
}
