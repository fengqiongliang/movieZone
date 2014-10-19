package com.moviezone.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;











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
import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.Movie;
import com.moviezone.service.MovieService;
import com.moviezone.service.UserService;

@Controller
public class IndexController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/index.do",method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mv,
												 HttpServletRequest request,
												 HttpServletResponse response,
												 HttpSession session)throws Exception{
		List<Object> sceneCmmts  = new ArrayList<Object>();
		sceneCmmts.add(new Object());
		mv.addObject("sceneMovies",movieService.selectByModule("首页-展示区", true, 1, 5));
		mv.addObject("sceneCmmts", sceneCmmts);
		mv.setViewName("/index");
		return mv; 
	}
}
