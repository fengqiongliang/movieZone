package com.moviezone.controller;


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
import com.moviezone.domain.Page;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;

@Controller
public class AdminUserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(AdminUserController.class);
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/admin_user.do",method=RequestMethod.GET)
	public ModelAndView userView(ModelAndView mv,
													  HttpServletRequest request,
													  HttpServletResponse response)throws Exception{
		mv.addObject("forbitUsers", userService.selectForbitUser(null, null, null, null,1));
		mv.addObject("normalUsers", userService.selectNormalUser(null, null, null, null,1));
		mv.setViewName("/admin_user");
		return mv;
	}
	
	@RequestMapping(value="/adminForbitUser.json",method=RequestMethod.GET)
	public ModelAndView forbitUser(ModelAndView mv,
													  	HttpServletRequest request,
													  	HttpServletResponse response,
													  	@RequestParam(value="userid",required=false) String userid,
													  	@RequestParam(value="nickname",required=false) String nickname,
													  	@RequestParam(value="createarea",required=false) String createarea,
													  	@RequestParam(value="createip",required=false) String createip,
													  	@RequestParam(value="pageNo",required=false,defaultValue="1") int pageNo)throws Exception{	
		mv.addObject("forbitUsers", userService.selectForbitUser(userid, nickname, createarea, createip, pageNo));
		mv.setViewName("/admin_user_forbit");
		return mv;
	}
	
	@RequestMapping(value="/adminNormalUser.json",method=RequestMethod.GET)
	public ModelAndView normalUser(ModelAndView mv,
													  	  HttpServletRequest request,
													  	  HttpServletResponse response,
													  	 @RequestParam(value="userid",required=false) String userid,
													  	 @RequestParam(value="nickname",required=false) String nickname,
													  	 @RequestParam(value="createarea",required=false) String createarea,
													  	 @RequestParam(value="createip",required=false) String createip,
													  	 @RequestParam(value="pageNo",required=false,defaultValue="1") int pageNo)throws Exception{	
		mv.addObject("normalUsers", userService.selectNormalUser(userid, nickname, createarea, createip, pageNo));
		mv.setViewName("/admin_user_normal");
		return mv;
	}
	
	@RequestMapping(value="/delForbidUser.json",method=RequestMethod.GET)
	public void delForbitUser(HttpServletRequest request,
											 HttpServletResponse response,
											 @RequestParam(value="userid") String userid)throws Exception{
		userService.delForbit(Long.parseLong(userid));
	}
	
	@RequestMapping(value="/delNormalUser.json",method=RequestMethod.GET)
	public void delNormalUser(	HttpServletRequest request,
											    HttpServletResponse response,
											    @RequestParam(value="userid") String userid)throws Exception{
		userService.delete(Long.parseLong(userid));
	}
	
	@RequestMapping(value="/permitModify.json",method=RequestMethod.GET)
	public void permitModify(HttpServletRequest request,
											  HttpServletResponse response,
											  @RequestParam(value="userid") String userid)throws Exception{
		userService.permitModify(Long.parseLong(userid));
	}
	
	@RequestMapping(value="/addNormalForbit.json",method=RequestMethod.GET)
	public void addNormalForbit(	HttpServletRequest request,
													HttpServletResponse response,
													@RequestParam(value="userid") String userid)throws Exception{
		userService.addNormalForbit(Long.parseLong(userid));
	}
	
	@RequestMapping(value="/addSystemForbit.json",method=RequestMethod.GET)
	public void addSystemForbit(	HttpServletRequest request,
													HttpServletResponse response,
													@RequestParam(value="createip") String createip)throws Exception{
		userService.addSystemForbit(createip);
	}
	
	@RequestMapping(value="/statActiveUser.json",method=RequestMethod.GET)
	public void statActiveUser(	HttpServletRequest request,
												HttpServletResponse response)throws Exception{
		Random rand = new Random();
		JSONObject result = new JSONObject();
		JSONArray   data   = new JSONArray();
		JSONObject o1 = new JSONObject();
		o1.put("name", "本草纲目");
		o1.put("value",rand.nextInt(10) );
		JSONObject o2 = new JSONObject();
		o2.put("name", "大地飞歌");
		o2.put("value",rand.nextInt(10) );
		JSONObject o3 = new JSONObject();
		o3.put("name", "天工开物");
		o3.put("value",rand.nextInt(10) );
		JSONObject o4 = new JSONObject();
		o4.put("name", "欣欣向荣");
		o4.put("value",rand.nextInt(10) );
		JSONObject o5 = new JSONObject();
		o5.put("name", "神之子");
		o5.put("value",rand.nextInt(10) );
		JSONObject o6 = new JSONObject();
		o6.put("name", "我的小苹果");
		o6.put("value",rand.nextInt(10) );
		JSONObject o7 = new JSONObject();
		o7.put("name", "我是成龙");
		o7.put("value",rand.nextInt(10) );
		JSONObject o8 = new JSONObject();
		o8.put("name", "流泪的鱼");
		o8.put("value",rand.nextInt(10) );
		JSONObject o9 = new JSONObject();
		o9.put("name", "迷失的风筝");
		o9.put("value",rand.nextInt(10) );
		JSONObject o10 = new JSONObject();
		o10.put("name", "今夜是否安好");
		o10.put("value",rand.nextInt(10) );
		data.add(o1);
		data.add(o2);
		data.add(o3);
		data.add(o4);
		data.add(o5);
		data.add(o6);
		data.add(o7);
		data.add(o8);
		data.add(o9);
		data.add(o10);
		result.put("result", data);
		response.getWriter().write(result.toString());
	}
	
	@RequestMapping(value="/statArea.json",method=RequestMethod.GET)
	public void statArea(	HttpServletRequest request,
										HttpServletResponse response)throws Exception{
		Random rand = new Random();
		JSONObject result = new JSONObject();
		JSONArray   data   = new JSONArray();
		JSONObject o1 = new JSONObject();
		o1.put("name", "北京");
		o1.put("value",rand.nextInt(10) );
		JSONObject o2 = new JSONObject();
		o2.put("name", "上海");
		o2.put("value",rand.nextInt(10) );
		JSONObject o3 = new JSONObject();
		o3.put("name", "广东");
		o3.put("value",rand.nextInt(10) );
		JSONObject o4 = new JSONObject();
		o4.put("name", "广西");
		o4.put("value",rand.nextInt(10) );
		JSONObject o5 = new JSONObject();
		o5.put("name", "辽宁");
		o5.put("value",rand.nextInt(10) );
		JSONObject o6 = new JSONObject();
		o6.put("name", "黑龙江");
		o6.put("value",rand.nextInt(10) );
		JSONObject o7 = new JSONObject();
		o7.put("name", "浙江");
		o7.put("value",rand.nextInt(10) );
		JSONObject o8 = new JSONObject();
		o8.put("name", "江苏");
		o8.put("value",rand.nextInt(10) );
		JSONObject o9 = new JSONObject();
		o9.put("name", "内蒙古");
		o9.put("value",rand.nextInt(10) );
		JSONObject o10 = new JSONObject();
		o10.put("name", "香港");
		o10.put("value",rand.nextInt(10) );
		data.add(o1);
		data.add(o2);
		data.add(o3);
		data.add(o4);
		data.add(o5);
		data.add(o6);
		data.add(o7);
		data.add(o8);
		data.add(o9);
		data.add(o10);
		result.put("result", data);
		response.getWriter().write(result.toString());
	}
	
}
