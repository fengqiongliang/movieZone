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
import com.moviezone.domain.Page;
import com.moviezone.service.CommentService;
import com.moviezone.service.MovieService;

@Controller
public class MovieController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/movie.do",method=RequestMethod.GET)
	public ModelAndView movie(ModelAndView mv,
												  HttpServletRequest request,
						 						  HttpServletResponse response,
						 						  HttpSession session)throws Exception{
		mv.addObject("sceneCmmts", commentService.selectRecmmdCmmt("mv", 1, 10));
		mv.addObject("sceneMovies",movieService.selectByModule("电影-展示区", true,1, 5));
		mv.addObject("Movies480p",movieService.selectPageByModule("电影-480p", true, false,true,1, 14));
		mv.addObject("Movies720p",movieService.selectPageByModule("电影-720p", true, false,true,1, 14));
		mv.addObject("Movies1080p",movieService.selectPageByModule("电影-1080p", true, false,true,1, 14));
		mv.addObject("otherMVMovies",movieService.selectPageByModule("电影-其它", true, false,true,1, 14));
		mv.addObject("fromModule", "movieShow");
		mv.setViewName("/movie");
		return mv; 
	}
	
	@RequestMapping(value="/tv.do",method=RequestMethod.GET)
	public ModelAndView tv(ModelAndView mv,
											HttpServletRequest request,
						 					HttpServletResponse response,
						 					HttpSession session)throws Exception{
		mv.addObject("sceneCmmts", commentService.selectRecmmdCmmt("tv", 1, 10));
		mv.addObject("sceneMovies",movieService.selectByModule("电视剧-展示区", true, 1, 5));
		mv.addObject("americaMovies",movieService.selectPageByModule("电视剧-英美", true, false,true,1, 14));
		mv.addObject("japanMovies",movieService.selectPageByModule("电视剧-日韩", true, false,true,1, 14));
		mv.addObject("hongkongMovies",movieService.selectPageByModule("电视剧-港台", true,false,true, 1, 14));
		mv.addObject("chinaMovies",movieService.selectPageByModule("电视剧-内地", true, false,true,1, 14));
		mv.addObject("fromModule", "tvShow");
		mv.setViewName("/tv");
		return mv; 
	}
	
	@RequestMapping(value="/movie.json",method=RequestMethod.GET)
	public ModelAndView getMovies(ModelAndView mv,
												  		 HttpServletRequest request,
												  		 HttpServletResponse response,
												  		 @RequestParam(value="type") String type,
												  		 @RequestParam(value="timeSort") String timeSort,
												  		 @RequestParam(value="scoreSort") String scoreSort,
												  		 @RequestParam(value="pageNo") int pageNo,
												  		 @RequestParam(value="pageType") String pageType)throws Exception{
		String modname = "";
		if("480p".endsWith(type)){modname = "电影-480p";mv.addObject("fromModule", "movie480p");}
		if("720p".endsWith(type)){modname = "电影-720p";mv.addObject("fromModule", "movie720p");}
		if("1080p".endsWith(type)){modname = "电影-1080p";mv.addObject("fromModule", "movie1080p");}
		if("other".endsWith(type)){modname = "电影-其它";mv.addObject("fromModule", "movieOther");}
		if("america".endsWith(type)){modname = "电视剧-英美";mv.addObject("fromModule", "tvAmerica");}
		if("japan".endsWith(type)){modname = "电视剧-日韩";mv.addObject("fromModule", "tvJapan");}
		if("hongkong".endsWith(type)){modname = "电视剧-港台";mv.addObject("fromModule", "tvHongkong");}
		if("china".endsWith(type)){modname = "电视剧-内地";mv.addObject("fromModule", "tvChina");}
		boolean isSortCreateTimeUp  = "asc".equals(timeSort)?true:false;
		boolean isScoreUp  = "asc".equals(scoreSort)?true:false;
		pageNo    = pageNo <1?1:pageNo;
		int pageSize   =  "14".equals(pageType)?14:28;
		mv.addObject("movies",movieService.selectPageByModule(modname, true, isSortCreateTimeUp,isScoreUp,pageNo, 2));
		mv.setViewName("/movie_tv_list");
		return mv; 
	}
}
