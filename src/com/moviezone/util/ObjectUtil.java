package com.moviezone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectUtil {
	private Logger logger = LoggerFactory.getLogger(ObjectUtil.class);
	public byte[] serial(Object o){
		if(o==null)return null;
		ByteArrayOutputStream bao = null;
		ObjectOutputStream oos = null;
		try {
			bao = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bao);
			oos.writeObject(o);
			oos.flush();
			byte[] b = bao.toByteArray();
			return  b;
		} catch (Exception e) {
			logger.error("",e);
		}finally{
			try {
				if(bao!=null)oos.close();
				if(oos!=null)bao.close();
			} catch (IOException e) {}
		}
		return null;
	}
	public <T> T derial(byte[] b,Class<T> clasz){
		if(b==null||b.length==0)return null;
		ByteArrayInputStream bai = null;
		ObjectInputStream ois = null;
		try {
			bai = new ByteArrayInputStream(b);
			ois = new ObjectInputStream(bai);
			Object o = ois.readObject();
			ois.close();
			bai.close();
			return clasz.cast(o);
		} catch (Exception e) {
			logger.error("",e);
		}finally{
			try{
				if(bai!=null)bai.close();
				if(ois!=null)ois.close();
			}catch(IOException e){}
		}
		return null;
	}
}
