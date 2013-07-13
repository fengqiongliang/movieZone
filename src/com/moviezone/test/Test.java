package com.moviezone.test;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.CharSet;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.moviezone.cache.CacheClient;
import com.moviezone.cache.CacheFactory;
import com.moviezone.cache.UserCache;
import com.moviezone.dao.UserDao;
import com.moviezone.domain.User;
import com.moviezone.service.UserService;
import com.moviezone.util.ObjectUtil;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		File dir  = new File("C:\\Documents and Settings\\sand\\桌面\\vsProject\\Gloox\\Gloox\\gloox\\");
		for(File file:dir.listFiles()){
			String fileName = file.getName();
			if(fileName.contains("stdafx.h"))continue;
			if(fileName.contains("targetver.h"))continue;
			if(fileName.contains("config.h.win"))continue;
			if(!fileName.endsWith(".h")&&!fileName.endsWith(".cpp"))continue;
			StringBuilder builder = new StringBuilder();
			String contents = test.readFile(file);
			int flag = 0;
			for(String line:contents.split("\n")){
				if(line.contains("#include"))flag++;
				if(flag==1&&line.contains("#include")&&!line.contains("stdafx.h")){
					builder.append("\n#include \"stdafx.h\"\n");
					flag++;
				}
				builder.append(line+"\n");
			}
			test.writeFile(file,builder.toString());
		}
	}
	
	private String readFile(File fileName) throws Exception{
		FileInputStream fis = new FileInputStream(fileName);
		List<Byte> its = new ArrayList<Byte>();
		while(true){
			byte[] buf = new byte[1024];
			int size = fis.read(buf);
			if(size <= 0)break;
			for(int i=0;i<size;i++)its.add(buf[i]);
		}
		fis.close();
		Object[] tmp = its.toArray();
		byte[] bs  = new byte[tmp.length];
		for(int i=0;i<tmp.length;i++)bs[i]=(Byte)tmp[i];
		return new String(bs,Charset.forName("gbk"));
	}
	
	private void writeFile(File fileName,String content) throws Exception {
		FileWriter writer = new FileWriter(fileName);
		writer.write(content);
		writer.flush();
	}
	
	
	private String toChar(byte[] buf,int size){
		StringBuilder builder = new StringBuilder();
		char hex[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		for(int i=0;i<size;i++){
			byte b = buf[i];
			builder.append(hex[(b&0xf0)>>4]);
			builder.append(hex[b&0x0f]);
		}
		return builder.toString();
	}	
	
}
