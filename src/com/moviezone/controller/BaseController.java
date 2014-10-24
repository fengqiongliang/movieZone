package com.moviezone.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
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

@Controller
public abstract class BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/**
	@RequestMapping(value="/helloWorld.do",method=RequestMethod.GET)
	public ModelAndView helloDo(HttpServletRequest request,
						 		HttpServletResponse response,
						 		HttpSession session,
						 		@RequestParam(value="id", required=true) String id,
								@RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
								@CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
								@RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		response.getWriter().write("data from helloDo()");
		ModelAndView mv = new ModelAndView();
		mv.addObject("name", "ahone");
		mv.setViewName("/Login/a");
		return mv;
	}
	
	@RequestMapping(value="/helloWorld.json",method=RequestMethod.POST)
	public void helloJSON(HttpServletRequest request,
	 		  			  HttpServletResponse response,
	 		  			  HttpSession session,
	 		  			  @RequestParam(value="id", required=true) String id,
	 		  			  @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
	 		  			  @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
	 		  			  @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		logger.info("hello,world");
		System.out.println(response.getContentType());
		throw new NullPointerException("internal exception...");
	}
	
	**/
	
	/**
	 * 获得ip属于哪个地区：海口、北京等地方
	 * @param request
	 * @return
	 */
	protected String getFrom(HttpServletRequest request){
		String ip = request.getRemoteAddr();
		return "海口";
	}
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView notSupportMethod(Exception e,
								         HttpServletRequest request,
								         HttpServletResponse response,
								         HttpSession session) {
		logger.error("",e);
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		String uri = request.getRequestURI();
		if(uri.endsWith(".json")){
			try{
				response.getWriter().write(HttpCode.notSupportMethod+"");
				return null;
			}catch(Exception ex){}
		}else{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("");
			return mv;
		}
		return null;
	}
	
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ModelAndView missParam(Exception e,
								  						 HttpServletRequest request,
								  						 HttpServletResponse response,
								  						 HttpSession session){
		logger.error("",e);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String uri = request.getRequestURI();
		if(uri.endsWith(".json")){
			try{
				response.getWriter().write(HttpCode.missParam+"");
				return null;
			}catch(Exception ex){}
		}else{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("");
			return mv;
		}
		return null;
	}
	
	@ExceptionHandler(ConversionNotSupportedException.class)
	public ModelAndView paramError(Exception e,
								   						  HttpServletRequest request,
								   						  HttpServletResponse response,
								   						  HttpSession session){
		logger.error("",e);
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		String uri = request.getRequestURI();
		if(uri.endsWith(".json")){
			try{
				response.getWriter().write(HttpCode.paramError+"");
				return null;
			}catch(Exception ex){}
		}else{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("");
			return mv;
		}
		return null;
	}
	
	
	@ExceptionHandler(Exception.class)
	public ModelAndView anyProblem(Exception e,
								   						   HttpServletRequest request,
								   						   HttpServletResponse response,
								   						   HttpSession session){
		logger.error("",e);
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		String uri = request.getRequestURI();
		if(uri.endsWith(".json")){
			try{
				response.getWriter().write(e.getMessage());
				return null;
			}catch(Exception ex){}
		}else{
			ModelAndView mv = new ModelAndView();
			mv.setViewName("");
			return mv;
		}
		return null;
	}
	
	
}
