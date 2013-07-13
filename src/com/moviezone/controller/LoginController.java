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
public class LoginController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="userCache")
	private UserCache userCache;
	
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
						 	  HttpServletResponse response,
						 	  HttpSession session,
						 	  @RequestParam(value="id", required=true) String id,
							  @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
							  @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
							  @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		userCache.test();
		ModelAndView mv = new ModelAndView();
		//userService.testTransaction();
		mv.setViewName("/login");
		return mv;
	}
	
	@RequestMapping(value="/getNewData.json",method=RequestMethod.GET)
	public void getNewData(HttpServletRequest request,
						   HttpServletResponse response,
						   HttpSession session,
						   @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						   @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						   @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		List<JSONObject> jsons = new ArrayList<JSONObject>();
		JSONObject json1 = new JSONObject();
		json1.put("name", "第一名");
		jsons.add(json1);
		JSONObject json2 = new JSONObject();
		json2.put("name", "第二名");
		jsons.add(json2);
		JSONObject json3 = new JSONObject();
		json3.put("name", "第三名");
		jsons.add(json3);
		JsonUtil.write(response, jsons);
	}
	
	
}
