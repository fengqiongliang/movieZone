package com.moviezone.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.constant.Constants;
import com.moviezone.controller.BaseController;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.HttpUtil;

									 
public class BaseInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userUser;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	
	private long beforeController = 0;
	private long afterController = 0;
	private long afterJsp = 0; 
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 				   HttpServletResponse response,
							 				   Object controller) throws Exception {
		beforeController = System.currentTimeMillis();
		response.setContentType("text/html; charset=utf-8");
		
		String userid     = HttpUtil.getCookie(request,Constants.USERID);
		String randnum = HttpUtil.getCookie(request,Constants.COOKIEID);
		//查询数据库select * from user t1,user_cookie t2 where t1.userid = t2.userid and t2.userid = {userid} and t2.randnum = {randnum}
		if(StringUtils.isNotBlank(userid) && 1==1){
			User user = new User();
			user.setId(123456);
			user.setNickname("精灵旅社");
			user.setRole("admin");
			user.setFaceurl("/img/blank92x71.gif");
			request.setAttribute(Constants.USER, user);
		}
		return true; 
	}
	
	@Override
	public void postHandle(HttpServletRequest request, 
						   				  HttpServletResponse response,
						   				  Object controller, 
						   				  ModelAndView mv) throws Exception {
		afterController = System.currentTimeMillis();
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
												  HttpServletResponse response, 
												  Object controller, 
												  Exception ex)throws Exception {
		afterJsp = System.currentTimeMillis();
		long hanlderCost = afterJsp - afterController;
		long jspCost = afterController - beforeController;
		long fullCost = afterJsp - beforeController;
		logger.info("["+controller+"] cost:["+hanlderCost+"] in handler method");
		logger.info("["+controller+"] cost:["+jspCost+"] in parse jsp");
		logger.info("["+controller+"] cost:["+fullCost+"] finish request");
	}
	
}
