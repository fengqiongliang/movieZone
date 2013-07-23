package com.moviezone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


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

import com.moviezone.constant.HttpCode;

@Controller
public class ContentController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
	
	
	@RequestMapping(value="/content.do",method=RequestMethod.GET)
	public ModelAndView helloDo(HttpServletRequest request,
						 		HttpServletResponse response,
						 		HttpSession session)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/content/content");
		return mv;
	}
}
