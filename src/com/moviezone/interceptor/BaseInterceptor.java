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
import com.moviezone.controller.AdminCommentController;
import com.moviezone.controller.AdminModuleController;
import com.moviezone.controller.AdminMovieController;
import com.moviezone.controller.AdminSearchController;
import com.moviezone.controller.AdminUserController;
import com.moviezone.controller.BaseController;
import com.moviezone.domain.User;
import com.moviezone.service.SearchService;
import com.moviezone.service.StatService;
import com.moviezone.service.UserService;
import com.moviezone.util.HttpUtil;

									 
public class BaseInterceptor implements HandlerInterceptor {
	@Autowired
	private UserService userService;
	@Autowired
	private StatService statService;
	@Autowired
	private SearchService searchService;
	
	private static final Logger logger = LoggerFactory.getLogger(BaseInterceptor.class);
	
	private long beforeController = 0;
	private long afterController = 0;
	private long afterJsp = 0; 
	
	private String baseDir;
	private String staticDir;
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, 
							 				   HttpServletResponse response,
							 				   Object controller) throws Exception {
		beforeController = System.currentTimeMillis();
		response.setContentType("text/html; charset=utf-8");
		//设置基本变量
		String Scheme      = request.getScheme();
		String ServerName  = request.getServerName();
		int    ServerPort  = request.getServerPort();
		String contextPath = request.getContextPath();
		String webPath = Scheme+"://"+ServerName+(ServerPort==80?"":":"+ServerPort)+(contextPath.length()>0?contextPath:"");
		String staticName = Scheme+"://"+"www.movietest.com"+(ServerPort==80?"":":"+ServerPort)+(contextPath.length()>0?contextPath:"");
		baseDir = webPath;
		staticDir = staticName;
		Constants.base = baseDir;
		
		request.setAttribute("base",baseDir);
		request.setAttribute("static",staticDir);
		request.setAttribute("searchHot",searchService.selectHotword(null, null, 1));
		
		//确定ip是否被禁用
		if(userService.isForbitIp(request.getRemoteAddr())){
			logger.debug("发现禁用ip【"+request.getRemoteAddr()+"】登陆 ");
			response.getWriter().write("您的Ip已经被禁用");
			return false;
		}
		
		User dbUser          = null;
		String strUserid     = HttpUtil.getCookie(request,Constants.USERID);
		String strCookieid  = HttpUtil.getCookie(request,Constants.COOKIEID);
		long   userid          = 0;
		try{userid = Long.parseLong(strUserid);}catch(Exception ex){}
		if(userid > 0 && StringUtils.isNotBlank(strCookieid)){
			//查找用户并且设置request
			User param = new User();
			param.setUserid(userid);
			param.setCookie_id(strCookieid);
			List<User> users = userService.select(param);
			dbUser = users.size()>0?users.get(0):null;
			//禁用用户
			if(dbUser != null && dbUser.getForbit()){
				logger.debug("发现禁用用户【"+dbUser.getUsername()+"】登陆，【"+(dbUser.getRole().endsWith("admin")?"管理员":"非管理员")+"】");
				response.getWriter().write("您的用户已经被禁用");
				HttpUtil.clearCookie(request, response, Constants.USERID);
				HttpUtil.clearCookie(request, response, Constants.COOKIEID);
				return false;
			}
			//正常用户
			if(dbUser != null && !dbUser.getForbit()){
				if(isForbitUrl(request,response,controller,dbUser))return false;
				logger.debug("用户登陆【"+dbUser.getUsername()+"】登陆，【"+(dbUser.getRole().endsWith("admin")?"管理员":"非管理员")+"】");
				request.setAttribute(Constants.USER, dbUser);
				//统计区域访问量
				statService.addAreaStat(request.getRemoteAddr(),dbUser.getUserid());
				return true;
			}
		}
		
		if(isForbitUrl(request,response,controller,dbUser))return false;
		
		statService.addAreaStat(request.getRemoteAddr(),-1);
		
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
	
	/**是否是禁用的url访问 */
	private boolean isForbitUrl(HttpServletRequest request, 
			   									 HttpServletResponse response,
			   									 Object controller,
			   									 User dbUser){
		if(controller instanceof AdminCommentController && (dbUser == null || !"admin".equals(dbUser.getRole()))){
			logger.debug("发现违法用户访问【"+request.getRemoteAddr()+" -- > AdminCommentController】");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return true;
		}
		if(controller instanceof AdminModuleController && (dbUser == null || !"admin".equals(dbUser.getRole()))){
			logger.debug("发现违法用户访问【"+request.getRemoteAddr()+" -- > AdminModuleController】");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return true;
		}
		if(controller instanceof AdminMovieController && (dbUser == null || !"admin".equals(dbUser.getRole()))){
			logger.debug("发现违法用户访问【"+request.getRemoteAddr()+" -- > AdminMovieController】");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return true;
		}
		if(controller instanceof AdminSearchController && (dbUser == null || !"admin".equals(dbUser.getRole()))){
			logger.debug("发现违法用户访问【"+request.getRemoteAddr()+" -- > AdminSearchController】");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return true;
		}
		if(controller instanceof AdminUserController && (dbUser == null || !"admin".equals(dbUser.getRole()))){
			logger.debug("发现违法用户访问【"+request.getRemoteAddr()+" -- > AdminUserController】");
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return true;
		}
		return false;
	}
	
	
}
