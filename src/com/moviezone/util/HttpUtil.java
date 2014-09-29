package com.moviezone.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	
	private final static String regxpForHtml = "<([^>]*)>";
	
	public static String encoding(String html){
		if(StringUtils.isBlank(html))return "";
		return StringEscapeUtils.escapeHtml(html);
	}
	public static String decoding(String html){
		if(StringUtils.isBlank(html))return "";
		return StringEscapeUtils.unescapeHtml(html);
	}
	
	public static String filterHtml(String str) {
		if (str == null) {
			return null;
		}
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, genSysLengthStr(matcher.group()));
			result1 = matcher.find();
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static String getCookie(HttpServletRequest request,String name){
		if(request == null || name == null || "".equals(name))return null;
		Cookie[] cookies = request.getCookies();
		if(cookies == null)return null;
		for(Cookie cookie:cookies){
			if(name.equals(cookie.getName()))return cookie.getValue();
		}
		return null;
	}
	
	public static void setCookie(HttpServletResponse response,String name,String value){
		if(response == null || name == null || value == null || "".equals(name) || "".equals(value))return;
		Cookie cookie = new Cookie(name,value);
		cookie.setPath("/");
		cookie.setMaxAge(3000);
		response.addCookie(cookie);
	}
	
	public static void clearCookie(HttpServletRequest request,String name){
		if(request == null || name == null || "".equals(name))return;
		Cookie[] cookies = request.getCookies();
		if(cookies == null)return;
		for(Cookie cookie:cookies){
			if(name.equals(cookie.getName()))cookie.setMaxAge(0);
		}
	}
	
	
	/**
	 * 制造相同长度的非替换字符 1个字节的替换成, 2个字节的替换成2字节
	 * @param str
	 * @return
	 */
	private static String genSysLengthStr(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			Character charStr = str.charAt(i);
			if (charStr.toString().getBytes().length == 1)
				result.append("");
			if (charStr.toString().getBytes().length == 2)
				result.append("");
		}
		return result.toString();
	}
	
	
	public static void main(String[] args){
		String html = "<p style='display:none;'>这是新的测试哦</p>还有的代码是这样的<div style='position:abosule'>内容</div>";
		String encodeHtml = HttpUtil.encoding(html);
		String decodeHtml = HttpUtil.decoding(html);
		System.out.println(encodeHtml);
		System.out.println(decodeHtml);
	}
}
