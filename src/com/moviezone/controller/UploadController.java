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
public class UploadController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/upUserPic.json",method=RequestMethod.POST)
	public void upUserPic(HttpServletRequest request,
						 	  			HttpServletResponse response,
						 	  			HttpSession session,
						 	  			@RequestParam(value="creationdate") Date creationdate,
						 	  			@RequestParam(value="modificationdate") Date modificationdate,
						 	  			@RequestParam(value="upFile") MultipartFile file)throws Exception{
		User user = (User)request.getAttribute(Constants.USER);
		if(user == null ){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		String originalName =  new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"utf-8");
		
		String fileName = writeFile(session,file,true);
		
		if(StringUtils.isNotBlank(fileName)){
			JSONObject json = new JSONObject();
			json.put("faceImgUrl", Constants.base+fileName);
			json.put("upFileName", originalName);
			response.getWriter().write(json.toString());
			//保存至数据库中
			user.setNextface(fileName);;
			userService.update(user);
			logger.debug("头像更新 "+user.getUserid()+".【"+user.getUsername()+"】  ---> "+fileName+"  originalName："+originalName);
		}
		
	}
	
	@RequestMapping(value="/upMoviePic.json",method=RequestMethod.POST)
	public void upMoviePic(HttpServletRequest request,
						 	  			   HttpServletResponse response,
						 	  			   HttpSession session,
						 	  			   @RequestParam(value="creationdate") Date creationdate,
						 	  			   @RequestParam(value="modificationdate") Date modificationdate,
						 	  			   @RequestParam(value="upFile") MultipartFile file)throws Exception{
		User user = (User)request.getAttribute(Constants.USER);
		if(user == null ){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		
		String originalName =  new String(file.getOriginalFilename().getBytes("ISO-8859-1"),"utf-8");
		System.out.println("originalName:"+originalName);
		System.out.println(creationdate);
		System.out.println(modificationdate);
		
		String fileName = writeFile(session,file,false);
		
		if(StringUtils.isNotBlank(fileName)){
			JSONObject json = new JSONObject();
			json.put("faceImgUrl", Constants.base+fileName);
			json.put("upFileName", originalName);
			response.getWriter().write(json.toString());
			logger.debug("文件上传成功   ---> "+fileName+"  originalName："+originalName);
			
		}
		
	}
	
	private String writeFile(HttpSession session,MultipartFile file,boolean isUpUserface)throws Exception{
		//错误判断
		if(file == null)return null;
		if(file.getBytes().length<1)return null;
		
		//获得基本变量
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
			//如果是上传用户头像则切图像
			if(isUpUserface)writeSuccess = ImageUtil.scaleImg(92, 71, is, bos);
			//如果是上传其它文件，则原样保存
			if(!isUpUserface){
				bos.write(file.getBytes());
			}
			long after    = System.currentTimeMillis();
			logger.info((writeSuccess?"成功":"失败")+" 花费 "+(after-before)+"ms 将文件【"+file.getOriginalFilename()+"】 --> 【"+finalName+"】 写入磁盘  "+jpgFile);
			is.close(); 
			bos.close();
		}
		if(writeSuccess)return finalName;
		return null;
	}
	
	
	
}
