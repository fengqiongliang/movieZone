package com.moviezone.constant;

import java.text.SimpleDateFormat;

public class Constants {
	/** 保存用户登陆情况，设置随机变量cookieid防止cookie注入 */
	public static final String USERID = "userid";
	public static final String COOKIEID = "cookie_id";
	
	/** 保存至浏览历史记录变量 */
	public static final String COOKIE_MOVIE = "cookie_movie";  //cookie的名字
	public static final String COOKIE_MOVIE_SPLITOR = ",";       //设置以,分隔
	public static final int      broswerMovieSize = 10;                   //最多能存10个影片的浏览历史记录
	
	/** 保存至request.setAttribute变量时的参数名 */
	public static final String USER = "user";
	public static String base = "";
	public static SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
}
