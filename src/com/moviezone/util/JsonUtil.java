package com.moviezone.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class JsonUtil {
	public static void write(HttpServletResponse response,Object o) throws IOException{
		JSONObject res = new JSONObject();
		res.put("data", o==null?"[]":o.toString());
		response.getWriter().write(res.toString());
	}
}
