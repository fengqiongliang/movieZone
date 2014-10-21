package com.moviezone.controller;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import com.moviezone.domain.Module;
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
		
		//计算推荐星级
		float f =  movie.getRecommand();
		int fullStarCount    = (int)f;
		int partStarCount   = (int)(f/0.5)-(int)(fullStarCount*2);
		int blankStarCount = 5-fullStarCount-partStarCount;

		mv.addObject("fullStarCount",fullStarCount);
		mv.addObject("partStarCount",partStarCount);
		mv.addObject("blankStarCount",blankStarCount);
		mv.addObject("type",getType(movieService.selectModule(movie.getMovieid())));
		mv.addObject("attachs",movieService.selectAttach(movie.getMovieid()));
		mv.addObject("movie",movie);
		mv.setViewName("/content");
		return mv;
	}
	
	private String getType(List<Module> modules){
		Map<String,Boolean> typeHelp1  = new LinkedHashMap<String,Boolean>();
		typeHelp1.put("电影", false);
		typeHelp1.put("电视剧", false);
		
		Map<String,Boolean> typeHelp2  = new LinkedHashMap<String,Boolean>();
		typeHelp2.put("480p", false);
		typeHelp2.put("720p", false);
		typeHelp2.put("1080p", false);
		typeHelp2.put("其它", false);
		typeHelp2.put("英美", false);
		typeHelp2.put("日韩", false);
		typeHelp2.put("港台", false);
		typeHelp2.put("内地", false);
		
		StringBuilder type = new StringBuilder();
		for(Module module:modules){
			if("电影-480p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("480p", true);}
			if("首页-电影-480p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("480p", true);}
			if("电影-720p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("720p", true);}
			if("首页-电影-720p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("720p", true);}
			if("电影-1080p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("1080p", true);}
			if("首页-电影-1080p".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("1080p", true);}
			if("电影-其它".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("其它", true);}
			if("首页-电影-其它".equals(module.getModname())){typeHelp1.put("电影", true);typeHelp2.put("其它", true);}
			if("电影-展示区".equals(module.getModname())){typeHelp1.put("电影", true);}
			if("首页-电影-排行榜".equals(module.getModname())){typeHelp1.put("电影", true);}
			if("电视剧-英美".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("英美", true);}
			if("首页-电视剧-英美".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("英美", true);}
			if("电视剧-日韩".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("日韩", true);}
			if("首页-电视剧-日韩".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("日韩", true);}
			if("电视剧-港台".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("港台", true);}
			if("首页-电视剧-港台".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("港台", true);}
			if("电视剧-内地".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("内地", true);}
			if("首页-电视剧-内地".equals(module.getModname())){typeHelp1.put("电视剧", true);typeHelp2.put("内地", true);}
			if("电视剧-展示区".equals(module.getModname())){typeHelp1.put("电视剧", true);}
			if("首页-电视剧-排行榜".equals(module.getModname())){typeHelp1.put("电视剧", true);}
		}
		int count = 0;
		for(Entry<String,Boolean> entry:typeHelp1.entrySet()){
			String     key = entry.getKey();
			boolean val  = entry.getValue();
			count = count+1;
			if(val==false)continue;
			type.append((count>1?"/":"")+key);
		}
		
		type.append(" ");
		
		count = 0;
		for(Entry<String,Boolean> entry:typeHelp2.entrySet()){
			String     key = entry.getKey();
			boolean val  = entry.getValue();
			count = count+1;
			if(val==false)continue;
			type.append((count>1?"/":"")+key);
		}
		
		return type.toString();
	}
	
}
