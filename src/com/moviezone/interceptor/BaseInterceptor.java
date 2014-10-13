package com.moviezone.interceptor;

import java.util.List;

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
	private UserService userService;
	
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
		
		String strUserid     = HttpUtil.getCookie(request,Constants.USERID);
		String strCookieid  = HttpUtil.getCookie(request,Constants.COOKIEID);
		long   userid          = 0;
		try{userid = Long.parseLong(strUserid);}catch(Exception ex){}
		if(userid > 0 && StringUtils.isNotBlank(strCookieid)){
			User user = new User();
			user.setUserid(userid);
			user.setCookie_id(strCookieid);
			List<User> users = userService.select(user);
			if(users.size()>0){
				User dbUser = users.get(0);
				logger.debug("用户登陆【"+dbUser.getUsername()+"】登陆，【"+(dbUser.getRole().endsWith("admin")?"管理员":"非管理员")+"】");
				request.setAttribute(Constants.USER, dbUser);
			}
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
