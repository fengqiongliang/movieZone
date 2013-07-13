package com.moviezone.cache;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class CacheFactory {
	private static final Logger logger = LoggerFactory.getLogger(CacheFactory.class);
	private String ip;
	private int port;
	private int maxIdle;
	private int maxActive;
	private int maxWait;
	private JedisPool jedisPool;
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}
	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}
	public void init(){
		Config poolConfig = new Config();
		poolConfig.maxIdle = maxIdle;
		poolConfig.maxActive = maxActive;
		poolConfig.maxWait = maxWait;
		jedisPool=new JedisPool(poolConfig, ip, port);
		logger.info("init cacheFactory over");
	}
	public synchronized Jedis getJedis(){
		try{
			Jedis jedis = jedisPool.getResource();
			return jedis;
		}catch(Exception ex){
			logger.error("",ex);
		}
		return null;
	}
	public void destroy(){
		jedisPool.destroy();
	}
}
