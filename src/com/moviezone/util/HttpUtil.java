package com.moviezone.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class HttpUtil {
	private final static String[] searchStr  = new String[]{"+","-", "&&","||","!", "(" ,")","{","}","[","]","^", "~", "*","?",":","\\","/"};
	private final static String[] replaceStr = new String[]{"\\+","\\-", "\\&&","\\||","\\!", "\\(","\\)","\\{","\\}","\\[","\\]","\\^", "\\~", "\\*","\\?","\\:","\\\\","\\/"};
	
	private final static String raplceHtmlForEmotion = "<img src='./img/qqemotion/$1.gif'></img>";
	private final static Pattern regexForEmotion = Pattern.compile("\\{emotion:([0-9]+)\\}");
	private final static String regxpForHtml = "<([^>]*)>";
	
	public static String encoding(String html){
		if(StringUtils.isBlank(html))return "";
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            switch (c) {
            case '<':
                buffer.append("&lt;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '&':
                buffer.append("&amp;");
                break;
            case '"':
                buffer.append("&quot;");
                break;
            case 10:
            case 13:
                break;
            default:
                buffer.append(c);
            }
        }
        return buffer.toString();
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
	public static String filterEmotion(String str) {
		if (str == null) {
			return null;
		}
		Matcher m = regexForEmotion.matcher(str);
		StringBuffer sb  = new StringBuffer();
		while(m.find()){
			m.appendReplacement(sb,raplceHtmlForEmotion);
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	public static String filterSearchForLucene(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		/*
		System.out.println("=======================");
		System.out.println(str);
		System.out.println(StringUtils.join(searchStr, "    "));
		System.out.println(StringUtils.join(replaceStr, "    "));
		System.out.println(StringUtils.replaceEach(str, searchStr, replaceStr));
		*/
		return StringUtils.replaceEach(str, searchStr, replaceStr);
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
		cookie.setMaxAge(31536000);        //1年 1*365*24*60*60
		response.addCookie(cookie);
	}
	
	public static void clearCookie(HttpServletRequest request,HttpServletResponse response,String name){
		if(request == null || name == null || "".equals(name))return;
		Cookie[] cookies = request.getCookies();
		if(cookies == null)return;
		for(Cookie cookie:cookies){
			if(!name.equals(cookie.getName()))continue;
			cookie.setPath("/");
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
	
	public static long ipToLong(String ip){
		String[] items = ip.split("\\.");
		return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
	}
	
	public static String ipToString(long ip){
		StringBuilder sb = new StringBuilder();
        sb.append(ip & 0xFF).append(".");
        sb.append((ip >> 8) & 0xFF).append(".");
        sb.append((ip >> 16) & 0xFF).append(".");
        sb.append((ip >> 24) & 0xFF);
        return sb.toString();
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
	
}
