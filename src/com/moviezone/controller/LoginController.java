package com.moviezone.controller;


import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;







import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.constant.Constants;
import com.moviezone.util.HttpUtil;

@Controller
public class LoginController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	private Random rand = new Random();
	
	@RequestMapping(value="/checkUName.json",method=RequestMethod.POST)
	public void checkUName(HttpServletRequest request,
										   	 HttpServletResponse response,
										   	 HttpSession session,
										   	 @RequestParam(value="username") String username)throws Exception{
		JSONObject json = new JSONObject();
		if(username == null || StringUtils.isBlank(username.trim())){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名不能为空");
			response.getWriter().write(json.toString());
			return;
		}
		if(1==2){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名已经被占用");
			response.getWriter().write(json.toString());
			return;
		}
		//保存至数据库中
		json.put("resultCode", 0);
		json.put("resultInfo", "用户名可以正常使用");
		response.getWriter().write(json.toString());
	}
	
	@RequestMapping(value="/regOrlog.json",method=RequestMethod.POST)
	public void regOrlog(ModelAndView mv,
									  HttpServletRequest request,
									  HttpServletResponse response,
									  HttpSession session,
									  @RequestParam(value="username") String username,
									  @RequestParam(value="password") String password,
									  @RequestParam(value="regCode") String regCode,
									  @RequestParam(value="type") int type)throws Exception{
		JSONObject json = new JSONObject();
		if(StringUtils.isBlank(username)){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名不能为空");
			response.getWriter().write(json.toString());
			return;
		}
		if(StringUtils.isBlank(password)){
			json.put("resultCode", -1);
			json.put("resultInfo", "密码不能为空");
			response.getWriter().write(json.toString());
			return;
		}
		
		//注册
		if(type == 1 && StringUtils.isBlank(regCode)){
			json.put("resultCode", -1);
			json.put("resultInfo", "验证码不能为空");
			response.getWriter().write(json.toString());
			return;
		}
		if(type == 1 && 1==2){
			json.put("resultCode", -1);
			json.put("resultInfo", "验证码不正确");
			response.getWriter().write(json.toString());
			return;
		}
		if(type == 1 && 1==2){
			json.put("resultCode", -1);
			json.put("resultInfo", "保存数据库失败");
			response.getWriter().write(json.toString());
			return;
		}
		
		//登录
		if(type == 2 && 1==2){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名不存在");
			response.getWriter().write(json.toString());
			return;
		}
		if(type == 2 && 1==2){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名和密码不正确");
			response.getWriter().write(json.toString());
			return;
		}
		//将userid 和 随机数字保存至数据库中方便下次无需登录
		String userid      = "123456";
		String randNum =  ""+rand.nextInt(1000);
		HttpUtil.setCookie(response, Constants.USERID, userid);
		HttpUtil.setCookie(response, Constants.RADNNUM, randNum);
		
		
		json.put("resultCode", 0);
		json.put("resultInfo", "成功登陆或注册");
		response.getWriter().write(json.toString());
	}
	
	@RequestMapping(value="/logout.json",method=RequestMethod.POST)
	public void logout(HttpServletRequest request,
								   HttpServletResponse response,
								   HttpSession session)throws Exception{
		HttpUtil.clearCookie(request, response,Constants.USERID);
		HttpUtil.clearCookie(request, response,Constants.RADNNUM);
	}
	
}
