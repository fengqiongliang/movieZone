package com.moviezone.service.support;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.IncrementDao;
import com.moviezone.domain.Increment;
import com.moviezone.service.KeyService;

public class KeyServiceImpl implements KeyService{
	private static final Logger logger = LoggerFactory.getLogger(KeyServiceImpl.class);
	private IncrementDao increDao;
	private int addInterval = 10000;    //一次增加多少;
	private Map<String,Increment>  keys = new HashMap<String,Increment>();
	
	@Override
	public  long getAttachid() {
		return getNext("attachid");
	}
	@Override
	public  long getCommentid() {
		return getNext("commentid");
	}
	@Override
	public  long getFavoriteid() {
		return getNext("favoriteid");
	}
	@Override
	public  long getModmvid() {
		return getNext("modmvid");
	}
	@Override
	public  long getMovieid() {
		return getNext("movieid");
	}
	@Override
	public  long getUserid() {
		return getNext("userid");
	}
	
	public void setIncreDao(IncrementDao increDao) {
		this.increDao = increDao;
	}
	
	private long getNext(String field){
		Increment key = keys.get(field);
		if(key != null && key.getStart() <= key.getEnd()){
			long start = key.getStart();
			key.setStart(start+1);
			return start;
		}
		for(Increment incr:increDao.select()){
			if(keys.get(incr.getField()) == null || incr.getField().equals(field)){
				keys.put(incr.getField(), incr);
				Increment newIncre = new Increment();
				newIncre.setField(incr.getField());
				newIncre.setStart(newIncre.getStart()+addInterval);
				newIncre.setEnd(newIncre.getEnd()+addInterval);
				increDao.update(newIncre);
			}
		}
		key = keys.get(field);
		long start = key.getStart();
		key.setStart(start+1);
		return start;
	}
	
}
