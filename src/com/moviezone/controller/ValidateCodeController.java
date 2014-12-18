package com.moviezone.controller;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
















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

import com.moviezone.constant.Constants;
import com.moviezone.constant.HttpCode;
import com.moviezone.domain.Attach;
import com.moviezone.domain.Module;
import com.moviezone.domain.Movie;
import com.moviezone.domain.User;
import com.moviezone.domain.ValidateCode;
import com.moviezone.service.CommentService;
import com.moviezone.service.MovieService;
import com.moviezone.service.UserService;
import com.moviezone.util.HttpUtil;

@Controller
public class ValidateCodeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);
	@Autowired
	private MovieService movieService;
	@Autowired
	private CommentService commentService;
	
	@RequestMapping(value="/code.do",method=RequestMethod.GET)
	public void index(HttpServletRequest request,
								 HttpServletResponse response,
								 HttpSession session)throws Exception{
		ValidateCode code = new ValidateCode();
		session.setAttribute(Constants.VALIDATECODE, code.getCode());
		response.setContentType("image/png");
		code.write(response.getOutputStream());
	}
	
	
	
}
