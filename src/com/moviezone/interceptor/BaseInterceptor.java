package com.moviezone.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.controller.BaseController;

									 
public class BaseInterceptor implements HandlerInterceptor {
	
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
