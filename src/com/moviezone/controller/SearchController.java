package com.moviezone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;








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
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.SearchResult;
import com.moviezone.service.CommentService;
import com.moviezone.service.SearchService;

@Controller
public class SearchController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	@Autowired
	private SearchService searchService;
	
	
	@RequestMapping(value="/search.do",method=RequestMethod.GET)
	public ModelAndView search(ModelAndView mv,
												   HttpServletRequest request,
						 						   HttpServletResponse response,
						 						   HttpSession session,
						 						   @RequestParam(value="search")String search)throws Exception{
		if(StringUtils.isBlank(search))search = "空内容";
		mv.addObject("searchTitle", search.length()<5?search:search.substring(0, 5)+"...");
		mv.addObject("search", search);
		mv.addObject("searchResult", searchService.search(search,null,1));
		mv.setViewName("/search");
		return mv; 
	}
	
	@RequestMapping(value="/search.json",method=RequestMethod.GET)
	public ModelAndView searchJson(ModelAndView mv,
												   		 HttpServletRequest request,
												   		 HttpServletResponse response,
												   		 HttpSession session,
												   		 @RequestParam(value="search")String search,
												   		 @RequestParam(value="type")String type,
												   		 @RequestParam(value="pageNo")int pageNo)throws Exception{
		if(StringUtils.isBlank(search))search = "空内容";
		mv.addObject("searchTitle", search.length()<5?search:search.substring(0, 5)+"...");
		mv.addObject("search", search);
		mv.addObject("movies", searchService.searchAsPage(search,type,pageNo));
		mv.setViewName("/search_data");
		return mv; 
	}
	
	
	
	
}
