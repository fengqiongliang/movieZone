package com.moviezone.controller;


import java.util.ArrayList;
import java.util.List;

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
import com.moviezone.domain.Movie;
import com.moviezone.service.MovieService;

@Controller
public class TvController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(TvController.class);
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/tv.do",method=RequestMethod.GET)
	public ModelAndView tv(ModelAndView mv,
											HttpServletRequest request,
						 					HttpServletResponse response,
						 					HttpSession session)throws Exception{
		List<Object> sceneMovies = new ArrayList<Object>();
		List<Object> sceneCmmts  = new ArrayList<Object>();
		sceneMovies.add(new Object());
		sceneCmmts.add(new Object());
		mv.addObject("sceneMovies",movieService.selectByModule("电视剧-展示区", true, 1, 5));
		mv.addObject("sceneCmmts", sceneCmmts);
		mv.setViewName("/tv");
		return mv; 
	}
}
