package com.moviezone.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.moviezone.domain.Movie;
import com.moviezone.domain.Page;
import com.moviezone.domain.Stat;
import com.moviezone.domain.User;
import com.moviezone.service.MovieService;
import com.moviezone.service.StatService;
import com.moviezone.service.UserService;

@Controller
public class AdminModuleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminModuleController.class);
	private static final int pageSize = 20;
	@Autowired
	private MovieService movieService;
	@Autowired
	private StatService statService;
	private String[] modnames = {
													"首页-展示区","首页-论播区","首页-站长区","首页-电影-480p","首页-电影-720p","首页-电影-1080p","首页-电影-其它","首页-电影-排行榜","首页-电视剧-英美","首页-电视剧-日韩","首页-电视剧-港台","首页-电视剧-内地","首页-电视剧-排行榜",
													"电影-展示区","电影-480p","电影-720p","电影-1080p","电影-其它",
													"电视剧-展示区","电视剧-英美","电视剧-日韩","电视剧-港台","电视剧-内地"
													};
	
	@RequestMapping(value="/admin_module.do",method=RequestMethod.GET)
	public ModelAndView moduleView(ModelAndView mv,
														   HttpServletRequest request,
														   HttpServletResponse response)throws Exception{
		List<Map<String,Object>> modules = new ArrayList<Map<String,Object>>();
		for(String modname:modnames){
			Map<String,Object> module = new HashMap<String,Object>();
			module.put("modname", modname);
			module.put("onlineMovies", movieService.selectPageByModule(modname,true,1,pageSize));
			module.put("offlineMovies", movieService.selectPageByModule(modname,false,1,pageSize));
			modules.add(module);
		}
		mv.addObject("modules", modules);
		mv.setViewName("/admin_module");
		return mv;
	}
	@RequestMapping(value="/admin_module_online.json",method=RequestMethod.GET)
	public ModelAndView moduleOnline(ModelAndView mv,
														   	  HttpServletRequest request,
														   	  HttpServletResponse response,
														   	  @RequestParam(value="modname") String modname,
														   	  @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("movies", movieService.selectPageByModule(modname,true,pageNo,pageSize));
		mv.setViewName("/admin_module_online");
		return mv;
	}
	@RequestMapping(value="/admin_module_offline.json",method=RequestMethod.GET)
	public ModelAndView moduleOffline(ModelAndView mv,
														   	   HttpServletRequest request,
														   	   HttpServletResponse response,
														   	   @RequestParam(value="modname") String modname,
														   	   @RequestParam(value="pageNo") int pageNo)throws Exception{
		mv.addObject("movies", movieService.selectPageByModule(modname,false,pageNo,pageSize));
		mv.setViewName("/admin_module_offline");
		return mv;
	}
	@RequestMapping(value="/mvModule.json",method=RequestMethod.GET)
	public void mvModule(HttpServletRequest request,
										 HttpServletResponse response,
										 @RequestParam(value="fromid") long fromModmvid,
										 @RequestParam(value="toid") long toModmvid)throws Exception{
		movieService.mvModule(fromModmvid,toModmvid);
	}
	@RequestMapping(value="/delModule.json",method=RequestMethod.GET)
	public void delModule(HttpServletRequest request,
										 HttpServletResponse response,
										 @RequestParam(value="modmvid") long modmvid)throws Exception{
		movieService.deleteModuleById(modmvid);
	}
	@RequestMapping(value="/statModule.json",method=RequestMethod.GET)
	public void statModule(HttpServletRequest request,
										  HttpServletResponse response)throws Exception{
		JSONObject result = new JSONObject();
		List<Stat> stats     = statService.selectModuleStat(1, 13);

		JSONArray   data   = new JSONArray();
		for(Stat stat:stats){
			JSONObject o = new JSONObject();
			o.put("name", stat.getName());
			o.put("value",stat.getValue());
			data.add(o);
		}
		result.put("result", data);
		
		response.getWriter().write(result.toString());
	}
	
}
