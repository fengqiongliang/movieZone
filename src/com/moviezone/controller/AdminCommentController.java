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
import com.moviezone.service.StatService;
import com.moviezone.service.UserService;

@Controller
public class AdminCommentController extends BaseController {
	private static final Logger logger    = LoggerFactory.getLogger(AdminCommentController.class);
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private StatService statService;
	
	
	@RequestMapping(value="/admin_cmmt.do",method=RequestMethod.GET)
	public ModelAndView movieView(ModelAndView mv,
														 HttpServletRequest request,
														 HttpServletResponse response)throws Exception{
		mv.addObject("onlineSceneCmmts", commentService.selectRecmmdCmmtPage(null, 1));
		mv.addObject("totalSceneCmmts", commentService.selectPage(null, null, null, null, 1));
		mv.setViewName("/admin_comment");
		return mv;
	}
	
	@RequestMapping(value="/onlineCmmt.json",method=RequestMethod.GET)
	public ModelAndView onlineCmmt(ModelAndView mv,
														   HttpServletRequest request,
														   HttpServletResponse response,
														   @RequestParam(value="type1",required=false) String type,
														   @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("sceneCmmts", commentService.selectRecmmdCmmtPage(type, pageNo));
		mv.setViewName("/admin_cmmt_online");
		return mv;
	}
	
	@RequestMapping(value="/totalCmmt.json",method=RequestMethod.GET)
	public ModelAndView totalCmmt(ModelAndView mv,
														 HttpServletRequest request,
														 HttpServletResponse response,
														 @RequestParam(value="commentid",required=false) Long commentid,
														 @RequestParam(value="movieid",required=false) Long movieid,
														 @RequestParam(value="userid",required=false) Long userid,
														 @RequestParam(value="type2",required=false) String type,
														 @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("sceneCmmts",commentService.selectPage(commentid, movieid, userid, type, pageNo));
		mv.setViewName("/admin_cmmt_total");
		return mv;
	}
	
	@RequestMapping(value="/delCmmt.json",method=RequestMethod.POST)
	public void delCmmt(HttpServletRequest request,
							    	   HttpServletResponse response,
									   @RequestParam(value="commentid",required=false,defaultValue="-1") long commentid)throws Exception{
		if(commentid<1)throw new Exception("commentid can not be null");
		commentService.delete(commentid);
	}
	
	@RequestMapping(value="/sceneCmmt.json",method=RequestMethod.POST)
	public void sceneCmmt(HttpServletRequest request,
							    	   	   HttpServletResponse response,
							    	   	   @RequestParam(value="commentid",required=false,defaultValue="-1") long commentid)throws Exception{
		if(commentid<1)throw new Exception("commentid can not be null");
		commentService.sceneCmmt(commentid);
	}
	
	@RequestMapping(value="/unSceneCmmt.json",method=RequestMethod.POST)
	public void unSceneCmmt(HttpServletRequest request,
							    	   	   		HttpServletResponse response,
							    	   	   		@RequestParam(value="commentid",required=false,defaultValue="-1") long commentid)throws Exception{
		if(commentid<1)throw new Exception("commentid can not be null");
		commentService.unSceneCmmt(commentid);
	}
	
	@RequestMapping(value="/mvCmmt.json",method=RequestMethod.POST)
	public void mvCmmt(HttpServletRequest request,
							    	   HttpServletResponse response,
							    	   @RequestParam(value="fromCmmtid",required=false,defaultValue="-1") long fromCmmtid,
							    	   @RequestParam(value="toCmmtid",required=false,defaultValue="-1") long toCmmtid)throws Exception{
		if(fromCmmtid<1||toCmmtid<1)throw new Exception("commentid can not be null");
		commentService.mvCmmt(fromCmmtid, toCmmtid);
	}
	
	@RequestMapping(value="/statComment.json",method=RequestMethod.GET)
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
