package com.moviezone.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.moviezone.domain.User;
import com.moviezone.service.MovieService;
import com.moviezone.service.UserService;

@Controller
public class AdminMovieController extends BaseController {
	private static final Logger logger    = LoggerFactory.getLogger(AdminMovieController.class);
	@Autowired
	private MovieService movieService;
	
	
	
	@RequestMapping(value="/admin_movie.do",method=RequestMethod.GET)
	public ModelAndView movieView(ModelAndView mv,
														 HttpServletRequest request,
														 HttpServletResponse response)throws Exception{
		mv.addObject("offlineMovies", movieService.selectOfflineMovie(null,null,null,1));
		mv.addObject("onlineMovies", movieService.selectOnlineMovie(null,null,null,null,1));
		mv.setViewName("/admin_movie");
		return mv;
	}
	
	@RequestMapping(value="/offlineMovies.json",method=RequestMethod.GET)
	public ModelAndView offlineMovies(ModelAndView mv,
														     HttpServletRequest request,
														     HttpServletResponse response,
														     @RequestParam(value="id",required=false) Long movieid,
														     @RequestParam(value="name",required=false) String name,
														     @RequestParam(value="type",required=false) String[] type,
														     @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("wrappers", movieService.selectOfflineMovie(movieid,name,type,pageNo));
		mv.setViewName("/admin_movie_offline");
		return mv;
	}
	
	@RequestMapping(value="/onlineMovies.json",method=RequestMethod.GET)
	public ModelAndView onlineMovies(ModelAndView mv,
														 	HttpServletRequest request,
														 	HttpServletResponse response,
														 	@RequestParam(value="id",required=false) Long movieid,
														 	@RequestParam(value="name",required=false) String name,
														 	@RequestParam(value="type",required=false) String[] type,
														 	@RequestParam(value="sort",required=false) String[] sort,
														 	@RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("wrappers", movieService.selectOnlineMovie(movieid,name,type,sort, pageNo));
		mv.setViewName("/admin_movie_online");
		return mv;
	}
	
	@RequestMapping(value="/admin_movieAction.json",method=RequestMethod.GET)
	public ModelAndView movieAction(ModelAndView mv,
														 	HttpServletRequest request,
														 	HttpServletResponse response,
														 	@RequestParam(value="id",required=false,defaultValue="-1") Long movieid)throws Exception{
		MovieWrapper wrapper = movieService.selectAsWrapper(movieid);
		mv.addObject("wrapper", wrapper==null?new MovieWrapper():wrapper);
		mv.setViewName("/admin_movieAction");
		return mv;
	}
	
	@RequestMapping(value="/addMovie.json",method=RequestMethod.POST)
	public void saveMovie(ModelAndView mv,
						 	  			 HttpServletRequest request,
										 HttpServletResponse response,
										 @RequestParam(value="id",required=false,defaultValue="-1") long movieid,
										 @RequestParam(value="name") String name,
										 @RequestParam(value="type") String type,
										 @RequestParam(value="shortDesc") String shortdesc,
										 @RequestParam(value="longDesc") String longdesc,
										 @RequestParam(value="score") float score,
										 @RequestParam(value="approve") int approve,
										 @RequestParam(value="download") int download,
										 @RequestParam(value="browse") int browse,
										 @RequestParam(value="publishDate") String publishDate,
										 @RequestParam(value="attachs",required=false) String[] attachInfos,
										 @RequestParam(value="modules",required=false) String[] modnames,
										 @RequestParam(value="face650x500") String face650x500,
										 @RequestParam(value="face400x308") String face400x308,
										 @RequestParam(value="face220x169") String face220x169,
										 @RequestParam(value="face150x220") String face150x220,
										 @RequestParam(value="face80x80") String face80x80,
										 @RequestParam(value="pictures") String[] pictures	)throws Exception{
		movieService.saveMovie(movieid, name, type, shortdesc, longdesc, score, approve, download, browse, publishDate, attachInfos, modnames, face650x500, face400x308, face220x169, face150x220, face80x80, pictures);
	}
	
	@RequestMapping(value="/delMovie.json",method=RequestMethod.POST)
	public void delMovie(HttpServletRequest request,
							    	   HttpServletResponse response,
									   @RequestParam(value="movieid",required=false,defaultValue="-1") long movieid)throws Exception{
		if(movieid<1)throw new Exception("movieid can not be null");
		Movie movie = new Movie();
		movie.setMovieid(movieid);
		movieService.delete(movie);
	}
	
	
	@RequestMapping(value="/statMovie.json",method=RequestMethod.GET)
	public void statMovie(HttpServletRequest request,
										HttpServletResponse response,
										@RequestParam(value="sort") String sort)throws Exception{
		Random rand = new Random();
		JSONObject result = new JSONObject();
		JSONArray   data   = new JSONArray();
		JSONObject o1 = new JSONObject();
		o1.put("id", 1);
		o1.put("name", "驯龙高手3D");
		o1.put("value",rand.nextInt(10) );
		JSONObject o2 = new JSONObject();
		o2.put("id", 2);
		o2.put("name", "精灵旅社");
		o2.put("value",rand.nextInt(10) );
		JSONObject o3 = new JSONObject();
		o3.put("id", 3);
		o3.put("name", "大药纺");
		o3.put("value",rand.nextInt(10) );
		JSONObject o4 = new JSONObject();
		o4.put("id", 4);
		o4.put("name", "后会无期");
		o4.put("value",rand.nextInt(10) );
		JSONObject o5 = new JSONObject();
		o5.put("id", 5);
		o5.put("name", "催眠大师");
		o5.put("value",rand.nextInt(10) );
		JSONObject o6 = new JSONObject();
		o6.put("id", 6);
		o6.put("name", "反贪风暴");
		o6.put("value",rand.nextInt(10) );
		JSONObject o7 = new JSONObject();
		o7.put("id", 7);
		o7.put("name", "分手大师");
		o7.put("value",rand.nextInt(10) );
		JSONObject o8 = new JSONObject();
		o8.put("id", 8);
		o8.put("name", "小时代3");
		o8.put("value",rand.nextInt(10) );
		JSONObject o9 = new JSONObject();
		o9.put("id", 9);
		o9.put("name", "使徒行者");
		o9.put("value",rand.nextInt(10) );
		JSONObject o10 = new JSONObject();
		o10.put("id", 10);
		o10.put("name", "对我而言，可爱的她");
		o10.put("value",rand.nextInt(10) );
		data.add(o1);
		data.add(o2);
		data.add(o3);
		data.add(o4);
		data.add(o5);
		data.add(o6);
		data.add(o7);
		data.add(o8);
		data.add(o9);
		data.add(o10);
		result.put("result", data);
		response.getWriter().write(result.toString());
	}
}
