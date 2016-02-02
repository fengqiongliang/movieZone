package com.moviezone.controller;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;












import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.constant.Constants;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.ImageUtil;
import com.moviezone.util.SecurityUtil;


@Controller
public class PXEController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(PXEController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/gpxe.do",method=RequestMethod.GET)
	public void movie(ModelAndView mv,
												  HttpServletRequest request,
						 						  HttpServletResponse response,
						 						  HttpSession session)throws Exception{
		response.setContentType("text/plain");
		response.getWriter().write("#!gpxe\n"); 
		response.getWriter().write("sanboot iscsi:192.168.98.1::::iqn.2007-08.name.dns.target.my:iscsiboot\n");
	}
	
}
