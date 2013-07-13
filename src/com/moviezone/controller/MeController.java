package com.moviezone.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.dao.support.UserDaoImpl;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.JsonUtil;

@Controller
public class MeController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MeController.class);
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="userCache")
	private UserCache userCache;
	
	@RequestMapping(value="/showFollow.json",method=RequestMethod.GET)
	public ModelAndView follow(HttpServletRequest request,
						 	  HttpServletResponse response,
						 	  HttpSession session,
						 	  @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	  @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	  @RequestParam(value="page", required=false,defaultValue="1") int page)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", page);
		mv.setViewName("/me/follow");
		return mv;
	}
	
	@RequestMapping(value="/showFans.json",method=RequestMethod.GET)
	public ModelAndView fans(HttpServletRequest request,
						 	 HttpServletResponse response,
						 	 HttpSession session,
						 	 @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	 @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	 @RequestParam(value="page", required=false,defaultValue="1") int page)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", page);
		mv.setViewName("/me/fans");
		return mv;
	}
	
	@RequestMapping(value="/showContent.json",method=RequestMethod.GET)
	public ModelAndView content(HttpServletRequest request,
						 	    HttpServletResponse response,
						 	    HttpSession session,
						 	    @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	    @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	    @RequestParam(value="page", required=false,defaultValue="1") int page)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", page);
		mv.setViewName("/me/content");
		return mv;
	}
	
	@RequestMapping(value="/showDownload.json",method=RequestMethod.GET)
	public ModelAndView download(HttpServletRequest request,
						 	     HttpServletResponse response,
						 	     HttpSession session,
						 	     @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	     @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	     @RequestParam(value="page", required=false,defaultValue="1") int page)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", page);
		mv.setViewName("/me/download");
		return mv;
	}
	
}
