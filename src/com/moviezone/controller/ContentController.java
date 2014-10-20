package com.moviezone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;






import net.sf.json.JSONArray;

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
public class ContentController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ContentController.class);
	@Autowired
	private MovieService movieService;
	
	@RequestMapping(value="/content.do",method=RequestMethod.GET)
	public ModelAndView content(ModelAndView mv,
													HttpServletRequest request,
													HttpServletResponse response,
													@RequestParam(value="id") long movieid)throws Exception{
		Movie movie = movieService.select(movieid);
		if(movie==null){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("该影片已经被删除或已不存在");
			return null;
		}
		float f =  movie.getRecommand();
		int fullStarCount    = (int)f;
		int partStarCount   = (int)(f/0.5)-(int)(fullStarCount*2);
		int blankStarCount = 5-fullStarCount-partStarCount;
		mv.addObject("fullStarCount",fullStarCount);
		mv.addObject("partStarCount",partStarCount);
		mv.addObject("blankStarCount",blankStarCount);
		mv.addObject("movie",movie);
		mv.setViewName("/content");
		return mv;
	}
	
}
