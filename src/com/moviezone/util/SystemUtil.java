package com.moviezone.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

public class SystemUtil {
	
	public static List<NetworkInterface> getAllNetworkInterface(){
		List<NetworkInterface> result = new ArrayList<NetworkInterface>();
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface eth = interfaces.nextElement();
				byte[] b = eth.getHardwareAddress();
				if (b == null || b.length != 6)continue;
				result.add(eth);
			}
		} catch (SocketException e) {e.printStackTrace();}
		return result;
	}
	
	public static String getHardwareAddress(byte[] macAddress){
		if(macAddress==null || macAddress.length!=6)return "";
		StringBuffer strbuf = new StringBuffer(macAddress.length * 2+5);
		for (int i = 0; i < macAddress.length; i++) {
			// 十六进制比较，小于00010000的补0
			if (((int) macAddress[i] & 0xff) < 0x10)strbuf.append("0");
			strbuf.append(Integer.toString((int) macAddress[i] & 0xff, 16));
			if(i!=macAddress.length-1)strbuf.append("-");
		}
		return strbuf.toString();
	}
	
	public static void main(String[] args) throws IOException{
		List<NetworkInterface> a = SystemUtil.getAllNetworkInterface();
		for(NetworkInterface i:a){
			System.out.println(SystemUtil.getHardwareAddress(i.getHardwareAddress()));
		}
		
	}
	
	
	
}
