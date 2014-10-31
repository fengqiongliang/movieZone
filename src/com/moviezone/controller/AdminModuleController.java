package com.moviezone.controller;


import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;










import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
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
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.MovieService;
import com.moviezone.service.UserService;

@Controller
public class AdminModuleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminModuleController.class);
	@Autowired
	private MovieService movieService;
	
	
	@RequestMapping(value="/admin_module.do",method=RequestMethod.GET)
	public ModelAndView moduleView(ModelAndView mv,
														   HttpServletRequest request,
														   HttpServletResponse response)throws Exception{
		mv.setViewName("/admin_module");
		return mv;
	}
	
}