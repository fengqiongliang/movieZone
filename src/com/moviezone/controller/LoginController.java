package com.moviezone.controller;


import java.util.Date;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;













import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.moviezone.constant.Constants;
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.KeyService;
import com.moviezone.service.UserService;
import com.moviezone.util.HttpUtil;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	private String[] nicknames = {"可爱的她","新人求指教","花落满地"};
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
		User user = new User();
		user.setUsername(username);
		if(userService.select(user).size()>0){
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
	
	@RequestMapping(value="/modifyNick.json",method=RequestMethod.POST)
	public void modifyNick(HttpServletRequest request,
										   HttpServletResponse response,
										   HttpSession session,
										   @RequestParam(value="nickname") String nickname)throws Exception{
		User user = (User)request.getAttribute(Constants.USER);
		if(user == null || StringUtils.isBlank(nickname)){
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		user.setNextnick(nickname.trim());
		userService.update(user);
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
		//验证基本信息
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
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		//注册
		if(type == 1){
			User oldUser = new User();
			oldUser.setUsername(username);
			if(userService.select(oldUser).size()>0){
				json.put("resultCode", -1);
				json.put("resultInfo", "用户名已经存在");
				response.getWriter().write(json.toString());
				return;
			}
			user.setNickname(nicknames[rand.nextInt(nicknames.length)]);   //设置随机昵称
			user.setFaceurl("/img/92x71/"+(rand.nextInt(50)+5)+".gif");      //设置随机头像
			user.setRole("user");                                                                    //设置一般的用户角色
			user.setCreateip(request.getRemoteAddr());                                //设置注册ip
			user.setCreatearea(getFrom(request));                                        //设置注册地址
			if(userService.insert(user)<1){
				json.put("resultCode", -1);
				json.put("resultInfo", "保存数据库失败");
				response.getWriter().write(json.toString());
				return;
			}
		}
		
		//登录
		if(type == 2 && userService.select(user).size()<1){
			json.put("resultCode", -1);
			json.put("resultInfo", "用户名和密码不正确");
			response.getWriter().write(json.toString());
			return;
		}
		
		user = userService.select(user).get(0);
		
		//将userid 和 随机数字保存至数据库中方便下次无需登录
		String cookieid   =  ""+rand.nextInt(1000);
		user.setCookie_id(cookieid);
		
		HttpUtil.setCookie(response, Constants.USERID, user.getUserid()+"");
		HttpUtil.setCookie(response, Constants.COOKIEID, cookieid);
		
		user.setLastlogtime(new Date());
		userService.update(user);
		
		json.put("resultCode", 0);
		json.put("resultInfo", "成功登陆或注册");
		response.getWriter().write(json.toString());
		
		logger.debug("用户【"+username+"】成功登陆【"+password+"】 ");
	}
	
	
}
