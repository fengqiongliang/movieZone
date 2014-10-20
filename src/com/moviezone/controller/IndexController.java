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
		mv.addObject("sceneCmmts", sceneCmmts);
		mv.addObject("sceneMovies",movieService.selectByModule("首页-展示区", true, 1, 5));
		mv.addObject("circuitMovies",movieService.selectByModule("首页-论播区", true, 1, 13));
		mv.addObject("siterMovies",movieService.selectByModule("首页-站长区", true, 1, 6));
		mv.addObject("Movies480p",movieService.selectByModule("首页-电影-480p", true, 1, 7));
		mv.addObject("Movies720p",movieService.selectByModule("首页-电影-720p", true, 1, 7));
		mv.addObject("Movies1080p",movieService.selectByModule("首页-电影-1080p", true, 1, 7));
		mv.addObject("otherMVMovies",movieService.selectByModule("首页-电影-其它", true, 1, 7));
		mv.addObject("rankMVMovies",movieService.selectByModule("首页-电影-排行榜", true, 1, 12));
		mv.addObject("americaMovies",movieService.selectByModule("首页-电视剧-英美", true, 1, 7));
		mv.addObject("japanMovies",movieService.selectByModule("首页-电视剧-日韩", true, 1, 7));
		mv.addObject("hongkongMovies",movieService.selectByModule("首页-电视剧-港台", true, 1, 7));
		mv.addObject("chinaMovies",movieService.selectByModule("首页-电视剧-内地", true, 1, 7));
		mv.addObject("rankTVMovies",movieService.selectByModule("首页-电视剧-排行榜", true, 1, 12));
		mv.setViewName("/index");
		return mv; 
	}
}
