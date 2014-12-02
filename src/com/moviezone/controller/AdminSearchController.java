package com.moviezone.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.Movie;
import com.moviezone.domain.MovieWrapper;
import com.moviezone.domain.Page;
import com.moviezone.domain.Stat;
import com.moviezone.domain.User;
import com.moviezone.service.CommentService;
import com.moviezone.service.MovieService;
import com.moviezone.service.SearchService;
import com.moviezone.service.StatService;
import com.moviezone.service.UserService;

@Controller
public class AdminSearchController extends BaseController {
	private static final Logger logger    = LoggerFactory.getLogger(AdminSearchController.class);
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private StatService statService;
	@Autowired
	private SearchService searchService;
	
	
	@RequestMapping(value="/admin_search.do",method=RequestMethod.GET)
	public ModelAndView searchView(ModelAndView mv,
														  HttpServletRequest request,
														  HttpServletResponse response)throws Exception{
		mv.addObject("words", searchService.selectWord(null, null, 1));
		mv.addObject("unwords", searchService.selectUnword(null, null, 1));
		mv.addObject("hotwords", searchService.selectHotword(null, null, 1));
		mv.setViewName("/admin_search");
		return mv;
	}
	
	@RequestMapping(value="/search_word.json",method=RequestMethod.GET)
	public ModelAndView word(ModelAndView mv,
												HttpServletRequest request,
												HttpServletResponse response,
												@RequestParam(value="id",required=false) Long id,
												@RequestParam(value="keyword",required=false) String keyword,
												@RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("words", searchService.selectWord(id, keyword, pageNo));
		mv.setViewName("/admin_search_word");
		return mv;
	}
	
	@RequestMapping(value="/search_unword.json",method=RequestMethod.GET)
	public ModelAndView unword(ModelAndView mv,
													HttpServletRequest request,
													HttpServletResponse response,
													@RequestParam(value="id",required=false) Long id,
													@RequestParam(value="keyword",required=false) String keyword,
													@RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("unwords", searchService.selectUnword(id, keyword, pageNo));
		mv.setViewName("/admin_search_unword");
		return mv;
	}
	
	@RequestMapping(value="/search_hot.json",method=RequestMethod.GET)
	public ModelAndView hotword(ModelAndView mv,
													  HttpServletRequest request,
													  HttpServletResponse response,
													  @RequestParam(value="id",required=false) Long id,
													  @RequestParam(value="keyword",required=false) String keyword,
													  @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("hotwords", searchService.selectHotword(id, keyword, pageNo));
		mv.setViewName("/admin_search_hot");
		return mv;
	}
	
	@RequestMapping(value="/delWord.json",method=RequestMethod.POST)
	public void delWord(HttpServletRequest request,
							    	  HttpServletResponse response,
									  @RequestParam(value="id",required=false,defaultValue="-1") long id)throws Exception{
		if(id<1)throw new Exception("id can not be null");
		searchService.deleteWord(id);
	}
	
	@RequestMapping(value="/delUnword.json",method=RequestMethod.POST)
	public void delUnword(HttpServletRequest request,
							    	  	 HttpServletResponse response,
							    	  	 @RequestParam(value="id",required=false,defaultValue="-1") long id)throws Exception{
		if(id<1)throw new Exception("id can not be null");
		searchService.deleteUnword(id);
	}
	
	@RequestMapping(value="/delHotword.json",method=RequestMethod.POST)
	public void delHotword(HttpServletRequest request,
							    	  	   HttpServletResponse response,
							    	  	   @RequestParam(value="id",required=false,defaultValue="-1") long id)throws Exception{
		if(id<1)throw new Exception("id can not be null");
		searchService.deleteHotword(id);
	}
	
	@RequestMapping(value="/statSearch.json",method=RequestMethod.GET)
	public void statMovie(HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam(value="sort") String sort)throws Exception{
		JSONObject result = new JSONObject();
		List<Stat> stats     = new ArrayList<Stat>();
		if("user".equals(sort))stats = statService.selectCmmtUserStat(1, 13);
		if("movie".equals(sort))stats = statService.selectCmmtMvStat(1, 13);
		if("userMonth".equals(sort))stats = statService.selectCmmtUserMonthStat(1, 13);
		if("movieMonth".equals(sort))stats = statService.selectCmmtMvMonthStat(1, 13);
		
		JSONArray   data   = new JSONArray();
		for(Stat stat:stats){
			JSONObject o = new JSONObject();
			if(!sort.startsWith("user"))o.put("id", stat.getId());
			o.put("name", stat.getName());
			o.put("value",stat.getValue());
			data.add(o);
		}
		result.put("result", data);
		
		response.getWriter().write(result.toString());
	}
	
	
}
