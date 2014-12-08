package com.moviezone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class FileUtil {
	
	public enum FileType {
		txt,html,jsp,asp,xml,js,css,rar,zip,tgz,torrent,bmp,jpg,png,gif,doc,xls,ppt,docx,xlsx,pptx,rtf,pdf,mp3,mpeg,rmvb,swf,unknown;
		public String ContentType(){
			if(this == txt)return "text/plain";
			if(this == html)return "text/html";
			if(this == jsp)return "text/html";
			if(this == asp)return "text/html";
			if(this == xml)return "application/xml";
			if(this == js)return "application/javascript";
			if(this == css)return "text/css";
			if(this == rar)return "application/octet-stream";
			if(this == zip)return "application/octet-stream";
			if(this == tgz)return "application/octet-stream";
			if(this == torrent)return "application/x-bittorrent";
			if(this == bmp)return "application/x-bmp";
			if(this == jpg)return "image/jpeg";
			if(this == png)return "image/png";
			if(this == gif)return "image/gif";
			if(this == doc)return "application/msword";
			if(this == xls)return "application/-excel";
			if(this == ppt)return "applications-powerpoint";
			if(this == docx)return "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
			if(this == xlsx)return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
			if(this == pptx)return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
			if(this == rtf)return "application/msword";
			if(this == pdf)return "application/pdf";
			if(this == mp3)return "audio/mp3";
			if(this == mpeg)return "video/mpg";
			if(this == rmvb)return "application/vnd.rn-realmedia-vbr";
			if(this == swf)return "application/x-shockwave-flash";
			return "application/octet-stream";
		}
		
	}
	
	public static byte[] readFileToByteArray(File file) throws IOException{
		return FileUtils.readFileToByteArray(file);
	}
	
	public static FileType getFileType(File file){
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			byte[] head = new byte[2];
			fis.read(head, 0, 2);
			return getFileType(head);
		}catch(Exception e){
			e.printStackTrace();  
		}finally{
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {}
			}
		}
		return FileType.unknown;
	}
	
	public static FileType getFileType(byte[] head){
		try{
			String hex = SecurityUtil.toHex(head);
			if("6664".equals(hex))return FileType.txt;
			if("3c21".equals(hex))return FileType.html;
			if("3c25".equals(hex))return FileType.jsp;
			if("7366".equals(hex))return FileType.asp;
			if("3c3f".equals(hex))return FileType.xml;
			if("efbb".equals(hex))return FileType.js;
			if("4063".equals(hex))return FileType.css;
			if("5261".equals(hex))return FileType.rar;
			if("504b".equals(hex))return FileType.zip;
			if("1f8b".equals(hex))return FileType.tgz;
			if("6438".equals(hex))return FileType.torrent;
			if("424d".equals(hex))return FileType.bmp;
			if("ffd8".equals(hex))return FileType.jpg;
			if("8950".equals(hex))return FileType.png;
			if("4749".equals(hex))return FileType.gif;
			if("d0cf".equals(hex))return FileType.doc;
			if("504b".equals(hex))return FileType.docx;
			if("7b5c".equals(hex))return FileType.rtf;
			if("2550".equals(hex))return FileType.pdf;
			if("4944".equals(hex))return FileType.mp3;
			if("0000".equals(hex))return FileType.png;
			if("2e52".equals(hex))return FileType.rmvb;
			if("4357".equals(hex))return FileType.swf;
		}catch(Exception e){e.printStackTrace();}
		
		return FileType.unknown;
	}
	
	public static boolean isFileNewer(File file,long timeMillis){
		return FileUtils.isFileNewer(file, timeMillis);
	}
	
	public static void main(String[] args){
		File file = new File("D:/360Downloads/1");
		FileType a = FileUtil.getFileType(file);
		System.out.println(a.ContentType());
	}
	
	
	
}
