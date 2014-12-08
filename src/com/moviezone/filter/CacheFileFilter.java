package com.moviezone.filter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.domain.CacheFile;

public class CacheFileFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(CacheFileFilter.class);
	private SimpleDateFormat dateFormater   = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss",Locale.US);
	private Map<String,CacheFile> cacheFiles = new HashMap<String,CacheFile>();
	private String realPath = ".";
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("CacheFileFilter init");
		dateFormater.setTimeZone(TimeZone.getTimeZone("GMT"));
		String dirs        = config.getInitParameter("dir");
		realPath = config.getServletContext().getRealPath("/");
		Set<File> files  = new LinkedHashSet<File>();
		for(String dir:dirs.split(",")){
			if(StringUtils.isBlank(dir))continue;
			File file = new File(realPath+dir);
			if(!file.exists())continue;
			files.add(file);
		}
		logger.debug("=================");
		logger.debug("dir        ：   "+dirs);
		logger.debug("realPath：   "+realPath);
		for(File file:files){
			logger.debug("file        ：   "+file.getAbsolutePath());
		}
		logger.debug("=================");
		logger.debug("begin to cache files");
		for(File dir:files){
			try {
				cacheFile(dir);
			} catch (Exception e) {
				logger.error("",e);
				e.printStackTrace();
			}
		}
		
		logger.debug("end to cache files");
		
	}
	

	@Override
	public void doFilter(ServletRequest arg0, 
						 			ServletResponse arg1,
						 			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request     = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String uriName    = request.getServletPath();
		File     requestFile = new File(realPath+uriName);
		boolean isCache = cacheFiles.containsKey(uriName);
		boolean fileExist = isCache && cacheFiles.get(uriName).fileExist()?true:false;
		boolean realExist= requestFile.exists();
		//请求文件已被缓存，且缓存文件存在
		if( isCache && fileExist){
			sendFile(request,response,uriName);
			return;
		}
		//请求文件已被缓存，但缓存文件不存在或已经被删除
		if( isCache && !fileExist){
			cacheFiles.remove(uriName);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		} 
		//请求文件没被缓存，且文件存在或新增加
		if(!isCache && realExist){
			//先进行缓存
			try {
				cacheFile(requestFile);
			} catch (Exception e) {e.printStackTrace();}
			//再进行传送
			sendFile(request,response,uriName);
			return;
		}
		//请求文件没被缓存，且文件不存在
		if(!isCache && !realExist){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("=========destory========");
	}
	
	private  void sendFile(HttpServletRequest request,HttpServletResponse response,String uriName) throws IOException{
		CacheFile cacheFile      = cacheFiles.get(uriName);
		String LastModified     = dateFormater.format(new Date(cacheFile.getLastModify()))+" GMT";
		String IfModifiedSince = (String)request.getHeader("If-Modified-Since");
		//没衣被修改过，返回304状态码节省传输空间，提高服务性能
		if(LastModified.equals(IfModifiedSince)){
			response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
			return;
		}
		byte[] data              = cacheFile.getData();
		response.setHeader("Accept-Ranges", "bytes");
		response.setContentType(cacheFile.getFileType().ContentType());
		response.setContentLength(data.length);
		response.setHeader("Last-Modified", LastModified);
		response.getOutputStream().write(data);
	}
	
	private void cacheFile(File dir) throws Exception{
		if(dir.isFile()){
			cacheFileHelper(dir);
			return;
		}
		String dirPath  = dir.getAbsolutePath();
		logger.debug("cache file in the directory : "+dirPath);
		List<File> childrenDirs = new ArrayList<File>();
		for(File f:dir.listFiles()){
			if(f.isDirectory()){
				childrenDirs.add(f);
				continue;
			}
			cacheFileHelper(f);
		}
		for(File childrenDir:childrenDirs){
			cacheFile(childrenDir);
		}
	}
	
	private void cacheFileHelper(File f) throws Exception{
		String filePath    = f.getAbsolutePath();
		String uriName  = "/"+filePath.replace(realPath, "").replaceAll("\\\\", "/");
		CacheFile cacheFile = new CacheFile(uriName,f);
		logger.debug("cache file "+cacheFile.getName() + "  -->  "+cacheFile.getData().length+" byte "+" "+cacheFile.getFileType().ContentType());
		cacheFiles.put(uriName, cacheFile);
	}
	
	
}
