package com.moviezone.constant;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Constants {
	public static void main(String[] args) throws UnsupportedEncodingException{
		String a  = "";
		a.replaceAll("", "");
		URLDecoder decoder = new URLDecoder();
		String result = decoder.decode("http://hz.youku.com/red/click.php?tp=1&cp=4000353&cpp=1000208&url=http%3A//v.t.sina.com.cn/share/share.php%3Fappkey%3D2684493555%26url%3Dhttp%3A//v.youku.com/v_show/id_XNDc1MDcxNzI4.html%26title%3D%25E3%2580%2590%25E8%25A7%2586%25E9%25A2%2591%25EF%25BC%259A%25E5%25BA%25B7%25E7%2586%2599%25E6%259D%25A5%25E4%25BA%2586+121114%25E3%2580%2591%26ralateUid%3D1642904381%26source%3D%25E4%25BC%2598%25E9%2585%25B7%25E7%25BD%2591%26sourceUrl%3Dhttp%253A%252F%252Fwww.youku.com%252F%26content%3Dutf8", "UTF-8");
		System.out.println(decoder.decode(result,"UTF-8"));
		System.out.println("http://img1.yytcdn.com/uploads/artists/25127/8267013B021804ED4FFF1874E9394C7F.jpg");
	}
}
