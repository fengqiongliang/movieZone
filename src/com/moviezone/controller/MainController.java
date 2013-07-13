package com.moviezone.controller;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import net.sf.json.JSONObject;

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

import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.dao.support.UserDaoImpl;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.JsonUtil;

@Controller
public class MainController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="userCache")
	private UserCache userCache;
	
	@RequestMapping(value="/main.do",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request,
						 	  HttpServletResponse response,
						 	  HttpSession session,
							  @RequestHeader(value="User-Agent",required=false,defaultValue="firefox") String userAgent,
							  @CookieValue(value="JSESSIONID",required=false,defaultValue="haha") String cookie,
							  @RequestParam(value="file",required=false) MultipartFile file)throws Exception{
		userCache.test();
		ModelAndView mv = new ModelAndView();
		//userService.testTransaction();
		mv.addObject("recmmdMovie", recmmdMovie());
		mv.addObject("interestMovie", interestMovie());
		mv.addObject("hotTopic", hotTopic());
		mv.addObject("recmmdPeople", recmmdPeople());
		mv.addObject("interestPeople", interestPeople());
		mv.addObject("visitor", visitor());
		mv.setViewName("/main/index");
		return mv;
	}
	
	private List<JSONObject> recmmdMovie(){
		List<JSONObject> recmmdMovie = new ArrayList<JSONObject>();
		JSONObject movie1 = new JSONObject();
		movie1.put("mvId","10");
		movie1.put("mvImgUrl","img/6.jpg");
		movie1.put("mvName", "王的饭店");
		recmmdMovie.add(movie1);
		
		JSONObject movie2 = new JSONObject();
		movie2.put("mvId","11");
		movie2.put("mvImgUrl","img/1.jpg");
		movie2.put("mvName", "这里没有你");
		recmmdMovie.add(movie2);
		
		JSONObject movie3 = new JSONObject();
		movie3.put("mvId","12");
		movie3.put("mvImgUrl","img/2.jpg");
		movie3.put("mvName", "爱可以再重来");
		recmmdMovie.add(movie3);
		
		JSONObject movie4 = new JSONObject();
		movie4.put("mvId","40");
		movie4.put("mvImgUrl","img/4.jpg");
		movie4.put("mvName", "分手的瞬间");
		recmmdMovie.add(movie4);
		
		JSONObject movie5 = new JSONObject();
		movie5.put("mvId","5");
		movie5.put("mvImgUrl","img/5.jpg");
		movie5.put("mvName", "大话西游");
		recmmdMovie.add(movie5);
		
		JSONObject movie6 = new JSONObject();
		movie6.put("mvId","6");
		movie6.put("mvImgUrl","img/6.jpg");
		movie6.put("mvName", "再一次说爱你");
		recmmdMovie.add(movie6);
		
		JSONObject movie7 = new JSONObject();
		movie7.put("mvId","7");
		movie7.put("mvImgUrl","img/7.jpg");
		movie7.put("mvName", "对不起我爱你");
		recmmdMovie.add(movie7);
		
		JSONObject movie8 = new JSONObject();
		movie8.put("mvId","8");
		movie8.put("mvImgUrl","img/8.jpg");
		movie8.put("mvName", "可不可不爱你");
		recmmdMovie.add(movie8);
		
		JSONObject movie9 = new JSONObject();
		movie9.put("mvId","9");
		movie9.put("mvImgUrl","img/9.jpg");
		movie9.put("mvName", "I miss you");
		recmmdMovie.add(movie9);
		
		return recmmdMovie;
	}
	
	private List<JSONObject> interestMovie(){
		List<JSONObject> movies = new ArrayList<JSONObject>();
		JSONObject movie1 = new JSONObject();
		movie1.put("mvId","1");
		movie1.put("mvImgUrl","img/1.jpg");
		movie1.put("mvName", "王的饭店");
		movies.add(movie1);
		
		JSONObject movie2 = new JSONObject();
		movie2.put("mvId","2");
		movie2.put("mvImgUrl","img/2.jpg");
		movie2.put("mvName", "这里没有你");
		movies.add(movie2);
		
		JSONObject movie3 = new JSONObject();
		movie3.put("mvId","3");
		movie3.put("mvImgUrl","img/3.jpg");
		movie3.put("mvName", "爱可以再重来");
		movies.add(movie3);
		
		JSONObject movie4 = new JSONObject();
		movie4.put("mvId","4");
		movie4.put("mvImgUrl","img/4.jpg");
		movie4.put("mvName", "分手的瞬间");
		movies.add(movie4);
		
		JSONObject movie5 = new JSONObject();
		movie5.put("mvId","5");
		movie5.put("mvImgUrl","img/5.jpg");
		movie5.put("mvName", "大话西游");
		movies.add(movie5);
		
		JSONObject movie6 = new JSONObject();
		movie6.put("mvId","6");
		movie6.put("mvImgUrl","img/6.jpg");
		movie6.put("mvName", "再一次说爱你");
		movies.add(movie6);
		
		JSONObject movie7 = new JSONObject();
		movie7.put("mvId","7");
		movie7.put("mvImgUrl","img/7.jpg");
		movie7.put("mvName", "对不起我爱你");
		movies.add(movie7);
		
		
		
		return movies;
	}
	
	private List<JSONObject> hotTopic(){
		List<JSONObject> topics = new ArrayList<JSONObject>();
		JSONObject topic1 = new JSONObject();
		topic1.put("topicId", "1");
		topic1.put("topicTitle","九月新片");
		topics.add(topic1);
		
		JSONObject topic2 = new JSONObject();
		topic2.put("topicId", "2");
		topic2.put("topicTitle","六月的天");
		topics.add(topic2);
		
		JSONObject topic3 = new JSONObject();
		topic3.put("topicId", "3");
		topic3.put("topicTitle","性感猛片");
		topics.add(topic3);
		return topics;
	}
	
	
	
	private List<JSONObject> recmmdPeople(){
		List<JSONObject> people = new ArrayList<JSONObject>();
		JSONObject person1 = new JSONObject();
		person1.put("uid","5");
		person1.put("avatar","img/5.jpg");
		person1.put("nickname", "夜在神游");
		people.add(person1);
		
		JSONObject person2 = new JSONObject();
		person2.put("uid","6");
		person2.put("avatar","img/6.jpg");
		person2.put("nickname", "第二梦");
		people.add(person2);
		
		JSONObject person3 = new JSONObject();
		person3.put("uid","7");
		person3.put("avatar","img/7.jpg");
		person3.put("nickname", "fire me brother");
		people.add(person3);
		
		JSONObject person4 = new JSONObject();
		person4.put("uid","8");
		person4.put("avatar","img/8.jpg");
		person4.put("nickname", "天在看wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww看");
		people.add(person4);
		return people;
	}
	
	private List<JSONObject> interestPeople(){
		List<JSONObject> people = new ArrayList<JSONObject>();
		JSONObject person1 = new JSONObject();
		person1.put("uid","10");
		person1.put("avatar","img/7.jpg");
		person1.put("nickname", "fire dream");
		people.add(person1);
		
		JSONObject person2 = new JSONObject();
		person2.put("uid","11");
		person2.put("avatar","img/6.jpg");
		person2.put("nickname", "cool city");
		people.add(person2);
		
		JSONObject person3 = new JSONObject();
		person3.put("uid","13");
		person3.put("avatar","img/7.jpg");
		person3.put("nickname", "心跳");
		people.add(person3);
		
		JSONObject person4 = new JSONObject();
		person4.put("uid","14");
		person4.put("avatar","img/10.jpg");
		person4.put("nickname", "是你吗?");
		people.add(person4);
		return people;
	}
	
	private List<JSONObject> visitor(){
		List<JSONObject> people = new ArrayList<JSONObject>();
		JSONObject person1 = new JSONObject();
		person1.put("uid","10");
		person1.put("avatar","img/7.jpg");
		person1.put("nickname", "fire dream");
		people.add(person1);
		
		JSONObject person2 = new JSONObject();
		person2.put("uid","11");
		person2.put("avatar","img/6.jpg");
		person2.put("nickname", "cool city");
		people.add(person2);
		
		JSONObject person3 = new JSONObject();
		person3.put("uid","13");
		person3.put("avatar","img/7.jpg");
		person3.put("nickname", "心跳");
		people.add(person3);
		
		JSONObject person4 = new JSONObject();
		person4.put("uid","14");
		person4.put("avatar","img/10.jpg");
		person4.put("nickname", "是你吗?");
		people.add(person4);
		return people;
	}
	
}
