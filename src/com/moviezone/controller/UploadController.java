package com.moviezone.controller;



import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
public class UploadController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/upUserPic.json",method=RequestMethod.POST)
	public void upUserPic(HttpServletRequest request,
						 	  			HttpServletResponse response,
						 	  			HttpSession session,
						 	  			@RequestParam(value="upFile",required=false) MultipartFile file)throws Exception{
		User user = (User)request.getAttribute(Constants.USER);
		if(user == null ){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		//错误判断
		if(file == null)return;
		if(file.getBytes().length<1)return;
		
		//获得基本变量
		String Scheme         = request.getScheme();
		String ServerName  = request.getServerName();
		int    ServerPort       = request.getServerPort();
		String contextPath  = request.getContextPath();
		String webPath        = Scheme+"://"+ServerName+(ServerPort==80?"":":"+ServerPort)+(contextPath.length()>0?contextPath:"");
		String realpath         = session.getServletContext().getRealPath("/");
		String name             = SecurityUtil.encryptMD5(file.getBytes())+".jpg";
		String finalName      = "/upload/"+name;
		File     jpgFile            = new File(realpath+finalName);
		
		//切成92x71的jpg图片保存至/upload目录中
		boolean writeSuccess = true;
		if( jpgFile.length()<1){
			InputStream is             = file.getInputStream();
			FileOutputStream fos  = new FileOutputStream(jpgFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			long before = System.currentTimeMillis();
			writeSuccess = ImageUtil.scaleImg(92, 71, is, bos);
			long after    = System.currentTimeMillis();
			logger.info((writeSuccess?"成功":"失败")+" 花费 "+(after-before)+"ms 将文件【"+file.getOriginalFilename()+"】 --> 【"+finalName+"】 写入磁盘  "+jpgFile);
			is.close(); 
			bos.close();
		}
		
		if(writeSuccess){
			JSONObject json = new JSONObject();
			json.put("faceImgUrl", webPath+finalName);
			response.getWriter().write(json.toString());
			//保存至数据库中
			user.setNextface(finalName);;
			userService.update(user);
			logger.debug("头像更新 "+user.getUserid()+".【"+user.getUsername()+"】  ---> "+finalName+"  ");
		}
		
	}
	
	@RequestMapping(value="/upload/video.json",method=RequestMethod.POST)
	public ModelAndView upVideo(HttpServletRequest request,
						 	  	HttpServletResponse response,
						 	  	HttpSession session,
						 	  	@RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	  	@CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	  	@RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		return null;
	}
	
	@RequestMapping(value="/upload/bt.json",method=RequestMethod.POST)
	public ModelAndView upBt(HttpServletRequest request,
						 	 HttpServletResponse response,
						 	 HttpSession session,
						 	 @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	 @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	 @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getInputStream());
		//response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		JSONObject json = new JSONObject();
		json.put("url", "img/bt.jpg");
		response.getWriter().write(json.toString());
		return null;
	}
	
	@RequestMapping(value="/upload/music.json",method=RequestMethod.POST)
	public ModelAndView upMusic(HttpServletRequest request,
						 	  	HttpServletResponse response,
						 	  	HttpSession session,
						 	  	@RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
						 	  	@CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
						 	  	@RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getInputStream());
		//response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		JSONObject json = new JSONObject();
		json.put("url", "img/music.jpg");
		response.getWriter().write(json.toString());
		return null;
	}
	
	
}
