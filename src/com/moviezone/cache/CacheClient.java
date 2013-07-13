package com.moviezone.cache;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.domain.User;
import com.moviezone.util.ObjectUtil;

import redis.clients.jedis.Jedis;

public class CacheClient {
	private static final Logger logger = LoggerFactory.getLogger(CacheClient.class);
	public static final String charset = "UTF-8";
	private ObjectUtil objUtil = new ObjectUtil();
	private CacheFactory factory;
	public CacheClient(CacheFactory factory){
		try{
			this.factory = factory;
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public <T> T getObject(final String key,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return null;
		if(StringUtils.isBlank(key))return null;
		byte[] Key;
		try {
			Key = key.getBytes(charset);
			byte[] b = redisClient.get(Key);
			T o = objUtil.derial(b,clasz);
			if(o==null)redisClient.del(Key);
			return o;
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	public void putObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(value==null)return;
		try {
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.set(Key, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public <T> List<T> lGetObject(final String key,final int start,final int end,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return new ArrayList<T>();
		if(StringUtils.isBlank(key))return new ArrayList<T>();
		try{
			byte[] Key = key.getBytes(charset);
			List<byte[]> bs = redisClient.lrange(Key, start, end);
			List<T> objs = new ArrayList<T>();
			for(byte[] b:bs){
				T o = objUtil.derial(b,clasz);
				if(o==null)redisClient.lrem(Key,1,b);
				if(o!=null)objs.add(o);
			}
			return objs;
		}catch(Exception ex){
			logger.error("",ex);
		}
		return new ArrayList<T>();
	}
	public void lPutObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.rpush(Key, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public void lDelObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(value==null)return ;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.lrem(Key, 1, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public long lLength(final String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return 0;
		if(StringUtils.isBlank(key))return 0;
		try{
			byte[] Key = key.getBytes(charset);
			return redisClient.llen(Key);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return 0;
	}
	public <T> Set<T> sGetObject(final String key,int start,int end,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return new LinkedHashSet<T>();
		if(StringUtils.isBlank(key))return new LinkedHashSet<T>();
		try{
			byte[] Key = key.getBytes(charset);
			Set<T> objs = new LinkedHashSet<T>();
			Set<byte[]> bs = redisClient.smembers(Key);
			int count=-1;
			for(byte[] b:bs){
				count++;
				if(count<start)continue;
				if(count>end&&end!=-1)break;
				T o = objUtil.derial(b,clasz);
				if(o==null)redisClient.srem(Key, b);
				if(o!=null)objs.add(o);
			}
		}catch(Exception ex){
			logger.error("",ex);
		}
		return new LinkedHashSet<T>();
	}
	public void sPutObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(value==null)return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.sadd(Key, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public void sDelObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(value==null)return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.srem(Key, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public long sLength(final String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return 0;
		if(StringUtils.isBlank(key))return 0;
		try{
			byte[] Key = key.getBytes(charset);
			return redisClient.scard(Key);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return 0;
	}
	public <T> Set<T> zGetObject(final String key,final int start,final int end,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return new LinkedHashSet<T>();
		if(StringUtils.isBlank(key))return new LinkedHashSet<T>();
		try{
			byte[] Key = key.getBytes(charset);
			Set<byte[]> bs = redisClient.zrange(Key, start, end);
			Set<T> objs = new LinkedHashSet<T>();
			for(byte[] b:bs){
				T o = objUtil.derial(b,clasz);
				if(o==null)redisClient.zrem(Key, b);
				if(o!=null)objs.add(o);
			}
			return objs; 
		}catch(Exception ex){
			logger.error("",ex);
		}
		return new LinkedHashSet<T>();
	}
	public void zPutObject(final String key,final double score,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(value==null)return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.zadd(Key, score, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public void zDelObject(final String key,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return ;
		if(value==null)return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.zrem(Key, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public long zLength(final String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return 0;
		if(StringUtils.isBlank(key))return 0;
		try{
			byte[] Key = key.getBytes(charset);
			return redisClient.zcard(Key);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return 0;
	}
	public <T> T hGetObject(final String key,final String field,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return null;
		if(StringUtils.isBlank(key))return null;
		if(StringUtils.isBlank(field))return null;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Field = field.getBytes(charset);
			byte[] b = redisClient.hget(Key, Field);
			T o = objUtil.derial(b,clasz);
			if(o==null)redisClient.hdel(Key, Field);
			return o;
		}catch(Exception ex){
			logger.error("",ex);
		}
		return null;
	}
	public <T> Map<String,T> hGetAllObject(final String key,Class<T> clasz){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return new HashMap<String,T>();
		if(StringUtils.isBlank(key))return new HashMap<String,T>();
		try{
			byte[] Key = key.getBytes(charset);
			Map<byte[], byte[]> values = redisClient.hgetAll(Key);
			Map<String,T> objs = new HashMap<String,T>();
			for(Entry<byte[],byte[]> entry:values.entrySet()){
				byte[] Field = entry.getKey();
				byte[] Value = entry.getValue();
				T o = objUtil.derial(Value,clasz);
				if(o==null)redisClient.hdel(Key, Field);
				if(o!=null)objs.put(objUtil.derial(Field, String.class), o);
			}
			return objs;
		}catch(Exception ex){
			logger.error("",ex);
		}
		return new HashMap<String,T>();
	}
	public long hLength(String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return 0;
		if(StringUtils.isBlank(key))return 0;
		try{
			byte[] Key = key.getBytes(charset);
			return redisClient.hlen(Key);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return 0;
	}
	public void hPutObject(final String key,final String field,final Object value){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(StringUtils.isBlank(field))return;
		if(value==null)return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Field = field.getBytes(charset);
			byte[] Value = objUtil.serial(value);
			if(Value!=null)redisClient.hset(Key, Field, Value);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public long hIncByOne(final String key,final String field){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return -1;
		if(StringUtils.isBlank(key))return -1;
		if(StringUtils.isBlank(field))return -1;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Field = field.getBytes(charset);
			return redisClient.hincrBy(Key, Field, 1);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return -1;
	}
	public void hDelObject(final String key,final String field){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		if(StringUtils.isBlank(field))return;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Field = field.getBytes(charset);
			redisClient.hdel(Key, Field);
		}catch(Exception ex){
			logger.error("",ex);
		}
	}
	public boolean hExist(final String key,final String field){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return false;
		if(StringUtils.isBlank(key))return false;
		if(StringUtils.isBlank(field))return false;
		try{
			byte[] Key = key.getBytes(charset);
			byte[] Field = field.getBytes(charset);
			return redisClient.hexists(Key, Field);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return false;
	}
	public void expire(final String key,final long millSecond){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		try {
			byte[] Key = key.getBytes(charset);
			redisClient.expire(Key, (int)millSecond/1000);
		} catch (UnsupportedEncodingException e) {}
	}
	public void remove(final String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return;
		if(StringUtils.isBlank(key))return;
		try {
			byte[] Key = key.getBytes(charset);
			redisClient.del(Key);
		} catch (UnsupportedEncodingException e) {}
	}
	public boolean exist(final String key){
		Jedis redisClient = factory.getJedis();
		if(redisClient==null)return false;
		if(StringUtils.isBlank(key))return false;
		try{
			byte[] Key = key.getBytes(charset);
			return redisClient.exists(Key);
		}catch(Exception ex){
			logger.error("",ex);
		}
		return false;
	}
}
