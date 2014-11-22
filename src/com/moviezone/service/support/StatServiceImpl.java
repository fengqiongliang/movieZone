package com.moviezone.service.support;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.StatDao;
import com.moviezone.domain.IP;
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.Stat;
import com.moviezone.domain.User;
import com.moviezone.service.KeyService;
import com.moviezone.service.StatService;
import com.moviezone.util.HttpUtil;


public class StatServiceImpl implements StatService{
	
	class DaemonFactory implements ThreadFactory {
    	private ThreadFactory factory = Executors.defaultThreadFactory();
    	@Override
    	public Thread newThread(Runnable r) {
    		Thread t = factory.newThread(r);
    		t.setDaemon(true);
    		return t;
    	}
    }
	
	private static final Logger logger                 = LoggerFactory.getLogger(StatServiceImpl.class);
	private ExecutorService executor                 = Executors.newSingleThreadExecutor(new DaemonFactory());
	private ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor(new DaemonFactory());
	private Map<String,Long> approveMap      = new HashMap<String,Long>();  //赞统计保存
	private Map<String,Long> downloadMap   = new HashMap<String,Long>();  //下载量统计保存
	private Map<String,Long> browserMap      = new HashMap<String,Long>();  //浏览量统计保存
	private Map<String,Long> ipMap                = new HashMap<String,Long>();  //ip统计保存
	private Map<String,Long> moduleMap       = new HashMap<String,Long>();  //模块统计保存
	private List<String>           moduleIds          = new ArrayList<String>();            //模块的统计id
	private StatDao statDao;
	private KeyService keyService;
	 
	public StatServiceImpl(){
		logger.info("going to schedule clear count ...");
		schedule.scheduleAtFixedRate(clearCmmd(), 0, 500, TimeUnit.MILLISECONDS);
	}
	
	@Override
	public List<Stat> selectApproveStat(int pageNo, int pageSize) {
		return statDao.selectApproveStat(pageNo, pageSize);
	}

	@Override
	public List<Stat> selectDownloadStat(int pageNo, int pageSize) {
		return statDao.selectDownloadStat(pageNo, pageSize);
	}

	@Override
	public List<Stat> selectBrowserStat(int pageNo, int pageSize) {
		return statDao.selectBrowserStat(pageNo, pageSize);
	}

	@Override
	public List<Stat> selectCommentStat(int pageNo, int pageSize) {
		return statDao.selectCommentStat(pageNo, pageSize);
	}

	@Override
	public List<Stat> selectModuleStat(int pageNo, int pageSize) {
		return statDao.selectModuleStat(pageNo, pageSize);
	}

	@Override
	public List<Stat> selectAreaStat(int pageNo, int pageSize) {
		return statDao.selectAreaStat(pageNo, pageSize);
	}
	
	@Override
	public List<Stat> selectCmmtUserStat(int pageNo, int pageSize) {
		return statDao.selectCmmtUserStat(null,null,pageNo, pageSize);
	}

	@Override
	public List<Stat> selectCmmtMvStat(int pageNo, int pageSize) {
		return statDao.selectCmmtMvStat(null,null,pageNo, pageSize);
	}

	@Override
	public List<Stat> selectCmmtUserMonthStat(int pageNo, int pageSize) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, -1);
		Date startTime = c.getTime();
		Date endTime   = new Date();
		return statDao.selectCmmtUserStat(startTime,endTime,pageNo, pageSize);
	}

	@Override
	public List<Stat> selectCmmtMvMonthStat(int pageNo, int pageSize) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, -1);
		Date startTime = c.getTime();
		Date endTime   = new Date();
		return statDao.selectCmmtMvStat(startTime,endTime,pageNo, pageSize);
	}
	
	@Override
	public void addModuleStat(String fromModule) {
		if(StringUtils.isBlank(fromModule))return;
		if(moduleIds.size()<1)moduleIds = statDao.moduleIds();
		if(!moduleIds.contains(fromModule))return;
		executor.execute(addCmmd(moduleMap,fromModule));
	}

	@Override
	public void addBrowserStat(long movieid) {
		executor.execute(addCmmd(browserMap,movieid+""));
	}
	
	@Override
	public void addDownloadStat(long movieid) {
		executor.execute(addCmmd(downloadMap,movieid+""));
	}

	@Override
	public void addApproveStat(long movieid) {
		executor.execute(addCmmd(approveMap,movieid+""));
	}
	
	@Override
	public void addAreaStat(String ip,long userid) {
		if(StringUtils.isBlank(ip))return;
		if(userid>0);
		executor.execute(addCmmd(ipMap,HttpUtil.ipToLong(ip)+""));
	}
	
	public void setStatDao(StatDao statDao) {
		this.statDao = statDao;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}
	
	private Runnable addCmmd(final Map<String,Long> countMap,final String key){
		return new Runnable(){
			@Override
			public void run() {
				try {
					addCount(countMap,key);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	private Runnable clearCmmd(){
		return new Runnable(){
			@Override
			public void run() {
				try {
					clearCount();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("",e);
				}finally{
					approveMap.clear();
					downloadMap.clear();
					browserMap.clear();
					ipMap.clear();
					moduleMap.clear();
				}
			}
		};
	}
	
	
	private void addCount(Map<String,Long> countMap,String key){
		Long val = countMap.get(key);
		countMap.put(key, val==null?1:val+1);
	}
	
	private void clearCount(){
		if(!approveMap.isEmpty()){    //赞
			logger.debug("start clear approve count . . .");
			for(Entry<String,Long>entry:approveMap.entrySet()){
				statDao.addApproveStat(Long.parseLong(entry.getKey()), entry.getValue());
			}
			logger.debug("end  clear approve count . . .");
		}
		if(!downloadMap.isEmpty()){ //下载量
			logger.debug("start clear download count . . .");
			for(Entry<String,Long>entry:downloadMap.entrySet()){
				statDao.addDownloadStat(Long.parseLong(entry.getKey()), entry.getValue());
			}
			logger.debug("end  clear download count . . .");
		}
		if(!browserMap.isEmpty()){ //浏览量
			logger.debug("start clear browser count . . .");
			for(Entry<String,Long>entry:browserMap.entrySet()){
				statDao.addBrowserStat(Long.parseLong(entry.getKey()), entry.getValue());
			}
			logger.debug("end  clear browser count . . .");
		}
		if(!ipMap.isEmpty()){ //浏览量
			logger.debug("start clear ip count . . .");
			for(Entry<String,Long>entry:ipMap.entrySet()){
				statDao.addIpstat(Long.parseLong(entry.getKey()), entry.getValue());
			}
			logger.debug("end  clear ip count . . .");
		}
		if(!moduleMap.isEmpty()){ //模块
			logger.debug("start clear module count . . .");
			for(Entry<String,Long>entry:moduleMap.entrySet()){
				statDao.updateModulestat(entry.getKey(), entry.getValue());
			}
			logger.debug("end  clear module count . . .");
		}
	}

	@Override
	public IP selectAreaOf(String ip) {
		return statDao.selectAreaOf(ip);
	}

	

	
	
	

	
}
