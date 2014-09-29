package com.moviezone.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.constant.Constants;
import com.moviezone.util.HttpUtil;

@Controller
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@RequestMapping(value="/register.do",method=RequestMethod.GET)
	public ModelAndView register(ModelAndView mv,
													HttpServletRequest request,
						 	   	 					HttpServletResponse response,
						 	   	 					HttpSession session)throws Exception{
		HttpUtil.setCookie(response, Constants.USERID, "123456");
		mv.setViewName("/admin_movie");
		return mv;
	}
	
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public ModelAndView login(ModelAndView mv,
												HttpServletRequest request,
												HttpServletResponse response,
												HttpSession session)throws Exception{
		HttpUtil.setCookie(response, Constants.USERID, "123456");
		mv.setViewName("/admin_movie");
		return mv;
	}
	
	@RequestMapping(value="/logout.json",method=RequestMethod.POST)
	public void logout(HttpServletRequest request,
								   HttpServletResponse response,
								   HttpSession session)throws Exception{
		HttpUtil.clearCookie(request, Constants.USERID);
	}
	
}
