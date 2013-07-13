package com.moviezone.controller;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.dao.support.UserDaoImpl;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.JsonUtil;

@Controller
public class TestController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="userCache")
	private UserCache userCache;
	
	@RequestMapping(value="/test.do",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
						 	  HttpServletResponse response,
						 	  HttpSession session,
							  @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
							  @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
							  @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		ModelAndView mv = new ModelAndView();
		//userService.testTransaction();
		mv.setViewName("/Test");
		
		WebApplicationContext applicationContext = null;
		ServletContext context = session.getServletContext();
		Enumeration names = context.getAttributeNames();
		while(names.hasMoreElements()){
			String prefix = org.springframework.web.servlet.FrameworkServlet.SERVLET_CONTEXT_PREFIX;
			String name = (String)names.nextElement();
			if(name.startsWith(prefix)){
				applicationContext = (WebApplicationContext)context.getAttribute(name);
				System.out.println(name + " -> "+applicationContext);
			}
		}
		return mv;
	}
	
	
	
}
