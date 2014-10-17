package com.moviezone.test;



import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import com.moviezone.util.SecurityUtil;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		File inDir  = new File("C:/Users/ahone-outer/Desktop/l图片/output");
		File outDir = new File("C:/Users/ahone-outer/Desktop/l图片/output/fileout");
		for(File file:inDir.listFiles()){
			if(file.isDirectory())continue;
			//
			
			FileInputStream fis    = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			while(true){
				int size = fis.read(b);
				if(size<1)break;
				baos.write(b, 0, size);
			}
			byte[] buf = baos.toByteArray();
			String name = SecurityUtil.encryptMD5(buf);
			File outFile = new File(outDir,name+".jpg");
			FileOutputStream fos = new FileOutputStream(outFile);
			fos.write(buf);
			
			fis.close();
			fos.close();
			baos.close();
			System.out.println(file.getName());
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
