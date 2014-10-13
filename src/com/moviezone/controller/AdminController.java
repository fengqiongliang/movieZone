package com.moviezone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.moviezone.constant.HttpCode;
import com.moviezone.service.UserService;

@Controller
public class AdminController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/admin_movie.do",method=RequestMethod.GET)
	public ModelAndView movieView(ModelAndView mv,
														 HttpServletRequest request,
														 HttpServletResponse response,
														 HttpSession session)throws Exception{
		mv.setViewName("/admin_movie");
		return mv;
	}
	
	@RequestMapping(value="/admin_module.do",method=RequestMethod.GET)
	public ModelAndView moduleView(ModelAndView mv,
														   HttpServletRequest request,
														   HttpServletResponse response,
														   HttpSession session)throws Exception{
		mv.setViewName("/admin_module");
		return mv;
	}
	
	@RequestMapping(value="/admin_user.do",method=RequestMethod.GET)
	public ModelAndView userView(ModelAndView mv,
													  HttpServletRequest request,
													  HttpServletResponse response,
													  HttpSession session)throws Exception{
		mv.setViewName("/admin_user");
		return mv;
	}
}
