package com.moviezone.controller;


import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.moviezone.service.StatService;

@Controller
public class SearchController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);
	private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Autowired
	private SearchService searchService;
	@Autowired
	private StatService statService;
	
	
	@RequestMapping(value="/search.do",method=RequestMethod.GET)
	public ModelAndView search(ModelAndView mv,
												   HttpServletRequest request,
						 						   HttpServletResponse response,
						 						   HttpSession session,
						 						   @RequestParam(value="search")String search)throws Exception{
		if(StringUtils.isBlank(search))search = "空内容";
		search = search.trim();
		
		//加入统计
		statService.addKeywordStat(search.substring(0,search.length()<30?search.length():30));
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
	
	@RequestMapping(value="/sitemap.xml",method=RequestMethod.GET)
	public void sitemap(HttpServletRequest request,
									 HttpServletResponse response,
									 HttpSession session)throws Exception{
		String domain = (String)request.getAttribute("base");
		String now      = dayFormat.format(new Date());
		
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"\n");
		builder.append("<urlset>"+"\n");
		/** 首页 */
		builder.append("	<url>"+"\n");
		builder.append("			<loc>"+domain+"/index.do</loc>"+"\n");
		builder.append("			<lastmod>"+now+"</lastmod>"+"\n");
		builder.append("			<changefreq>always</changefreq>"+"\n");
		builder.append("			<priority>1.0</priority>"+"\n");
		builder.append("	</url>"+"\n");
		/** 电影 */
		builder.append("	<url>"+"\n");
		builder.append("			<loc>"+domain+"/movie.do</loc>"+"\n");
		builder.append("			<lastmod>"+now+"</lastmod>"+"\n");
		builder.append("			<changefreq>always</changefreq>"+"\n");
		builder.append("			<priority>0.8</priority>"+"\n");
		builder.append("	</url>"+"\n");
		/** 电视剧 */
		builder.append("	<url>"+"\n");
		builder.append("			<loc>"+domain+"/tv.do</loc>"+"\n");
		builder.append("			<lastmod>"+now+"</lastmod>"+"\n");
		builder.append("			<changefreq>always</changefreq>"+"\n");
		builder.append("			<priority>0.8</priority>"+"\n");
		builder.append("	</url>"+"\n");
		/** 内容页 */
		builder.append("	<url>"+"\n");
		builder.append("			<loc>"+domain+"/content.do?id=1</loc>"+"\n");
		builder.append("			<lastmod>"+now+"</lastmod>"+"\n");
		builder.append("			<changefreq>always</changefreq>"+"\n");
		builder.append("			<priority>0.5</priority>"+"\n");
		builder.append("	</url>"+"\n");
		
		builder.append("</urlset>"+"\n");
		response.setHeader("Content-Type", "application/xml");
		response.getWriter().write(builder.toString());
	}  
	
	@RequestMapping(value="/robots.txt",method=RequestMethod.GET)
	public void robots(HttpServletRequest request,
												  HttpServletResponse response,
												  HttpSession session)throws Exception{
		String domain = (String)request.getAttribute("base");
		
		StringBuilder builder = new StringBuilder();
		builder.append("User-agent: *"+"\n");
		builder.append("Disallow: "+"/mvCmmt.json"+"\n");
		builder.append("Disallow: "+"/statComment.json"+"\n");
		builder.append("Disallow: "+"/admin_cmmt.do"+"\n");
		builder.append("Disallow: "+"/onlineCmmt.json"+"\n");
		builder.append("Disallow: "+"/totalCmmt.json"+"\n");
		builder.append("Disallow: "+"/delCmmt.json"+"\n");
		builder.append("Disallow: "+"/sceneCmmt.json"+"\n");
		builder.append("Disallow: "+"/unSceneCmmt.json"+"\n");
		builder.append("Disallow: "+"/admin_module.do"+"\n");
		builder.append("Disallow: "+"/admin_module_online.json"+"\n");
		builder.append("Disallow: "+"/admin_module_offline.json"+"\n");
		builder.append("Disallow: "+"/mvModule.json"+"\n");
		builder.append("Disallow: "+"/delModule.json"+"\n");
		builder.append("Disallow: "+"/statModule.json"+"\n");
		builder.append("Disallow: "+"/statMovie.json"+"\n");
		builder.append("Disallow: "+"/onlineMovies.json"+"\n");
		builder.append("Disallow: "+"/offlineMovies.json"+"\n");
		builder.append("Disallow: "+"/admin_movieAction.json"+"\n");
		builder.append("Disallow: "+"/addMovie.json"+"\n");
		builder.append("Disallow: "+"/admin_movie.do"+"\n");
		builder.append("Disallow: "+"/delMovie.json"+"\n");
		builder.append("Disallow: "+"/addNormalForbit.json"+"\n");
		builder.append("Disallow: "+"/addSystemForbit.json"+"\n");
		builder.append("Disallow: "+"/statActiveUser.json"+"\n");
		builder.append("Disallow: "+"/admin_user.do"+"\n");
		builder.append("Disallow: "+"/adminForbitUser.json"+"\n");
		builder.append("Disallow: "+"/adminNormalUser.json"+"\n");
		builder.append("Disallow: "+"/delForbidUser.json"+"\n");
		builder.append("Disallow: "+"/delNormalUser.json"+"\n");
		builder.append("Disallow: "+"/permitModify.json"+"\n");
		builder.append("Disallow: "+"/statArea.json"+"\n");
		builder.append("Disallow: "+"/statSearch.json"+"\n");
		builder.append("Disallow: "+"/admin_search.do"+"\n");
		builder.append("Disallow: "+"/search_unword.json"+"\n");
		builder.append("Disallow: "+"/search_hot.json"+"\n");
		builder.append("Disallow: "+"/addWord.json"+"\n");
		builder.append("Disallow: "+"/addUnword.json"+"\n");
		builder.append("Disallow: "+"/addHotword.json"+"\n");
		builder.append("Disallow: "+"/delWord.json"+"\n");
		builder.append("Disallow: "+"/delUnword.json"+"\n");
		builder.append("Disallow: "+"/delHotword.json"+"\n");
		builder.append("Disallow: "+"/reCreateIndex.json"+"\n");
		builder.append("Disallow: "+"/search_word.json"+"\n");
		builder.append("Disallow: "+"/comment.json"+"\n");
		builder.append("Disallow: "+"/reply.json"+"\n");
		builder.append("Disallow: "+"/checkUName.json"+"\n");
		builder.append("Disallow: "+"/modifyNick.json"+"\n");
		builder.append("Disallow: "+"/regOrlog.json"+"\n");
		builder.append("Disallow: "+"/upUserPic.json"+"\n");
		builder.append("Disallow: "+"/upMoviePic.json"+"\n");
		builder.append("Sitemap:"+domain+"/sitemap.xml"+"\n");
		
		response.setHeader("Content-Type", "text/plain");
		response.getWriter().write(builder.toString());
	}
	
	public static void main(String[] args) throws Exception{
		String[] classes = new String[]{"com.moviezone.controller.AdminCommentController",
														"com.moviezone.controller.AdminModuleController",
														"com.moviezone.controller.AdminMovieController",
														"com.moviezone.controller.AdminUserController",
														"com.moviezone.controller.AdminSearchController"};
		
		for(String cls:classes){
			Class<?> c = Class.forName(cls);
			for(Method m : c.getMethods()){
				RequestMapping requestMap = m.getAnnotation(RequestMapping.class);
				if(requestMap == null)continue;
				for(String url : requestMap.value()){
					System.out.println("builder.append(\"Disallow: \"+\""+url+"\"+\"\\n\");");
				}
			}
		}
	}
	
	
	
}
