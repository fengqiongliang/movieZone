package com.moviezone.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(MyFilter.class);
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("=========init========");
	}
	

	@Override
	public void doFilter(ServletRequest arg0, 
						 ServletResponse arg1,
			             FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		logger.info("=============doFilter========");
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("=========destory========");
	}
	
}
