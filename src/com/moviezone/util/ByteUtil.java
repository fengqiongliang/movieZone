package com.moviezone.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {
	private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);
	
	public static int getBit(byte b,int index) throws Exception{
		if(index==0)return b & 0x80;
		if(index==1)return b & 0x40;
		if(index==2)return b & 0x20;
		if(index==3)return b & 0x10;
		if(index==4)return b & 0x8;
		if(index==5)return b & 0x4;
		if(index==6)return b & 0x2;
		if(index==7)return b & 0x1;
		throw new Exception("out of index : "+index);
	}
	
	public static byte[] longToByteArray(long l){
        byte[] b = new byte[8]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Long(l & 0xff).byteValue();// 将最低位保存在最低位 
            l = l >> 8; // 向右移8位 
        } 
        return b; 
    }
	public static long byteArrayToLong(byte[] b){
		long l = 0; 
        long l0 = b[0] & 0xff;// 最低位 
        long l1 = b[1] & 0xff; 
        long l2 = b[2] & 0xff; 
        long l3 = b[3] & 0xff; 
        long l4 = b[4] & 0xff;// 最低位 
        long l5 = b[5] & 0xff; 
        long l6 = b[6] & 0xff; 
        long l7 = b[7] & 0xff; 
 
        // s0不变 
        l1 <<= 8; 
        l2 <<= 16; 
        l3 <<= 24; 
        l4 <<= 32; 
        l5 <<= 40; 
        l6 <<= 48; 
        l7 <<= 56; 
        l = l0 | l1 | l2 | l3 | l4 | l5 | l6 | l7; 
        return l; 
	}
	
	public static byte[] intToByteArray(int i){
    	byte[] r = new byte[4];
    	r[0] = (byte) ((i & 0xff000000) >> 24);
    	r[1] = (byte) ((i & 0x00ff0000) >> 16);
    	r[2] = (byte) ((i & 0x0000ff00) >> 8);
    	r[3] = (byte) ((i & 0x000000ff) >> 0);
    	return r;
    }
	public static int byteArrayToInt(byte[] b){
		int i = 0;
		for(int t=0;t<b.length;t++){
			int offset = 24-t*8;
			i += (int)((b[t] & 0xff) << offset);
		}
		return i;
	}
	
	public static byte[] shortToByteArray(short s){
        byte[] r = new byte[2];
    	r[0] = (byte) ((s & 0xff00) >> 8);
    	r[1] = (byte) ((s & 0x00ff) >> 0);
        return r;
    }
	public static short byteArrayToShort(byte[] b){
		int i0 = (int)((b[0] & 0xff) << 8);
		int i1 = (int)((b[1] & 0xff) << 0);
		return (short)(i0+i1); 
	}
	
	public static String toString(byte b){
		StringBuilder build = new StringBuilder();
		try{
			for(int i=0;i<8;i++){
				if(getBit(b,i)==1)
					build.append("1");
				else
					build.append("0");
			}
		}catch(Exception ex){}
		return build.toString();
	}
	public static String toString(short s){
		StringBuilder build = new StringBuilder();
		byte[] b = shortToByteArray(s);
		for(int t=0;t<b.length;t++){
			build.append(toString(b[t]));
		}
		return build.toString();
	}
	public static String toString(int i){
		StringBuilder build = new StringBuilder();
		byte[] b = intToByteArray(i);
		for(int t=0;t<b.length;t++){
			build.append(toString(b[t]));
		}
		return build.toString();
	}
	public static String toString(long l){
		StringBuilder build = new StringBuilder();
		byte[] b = longToByteArray(l);
		for(int t=0;t<b.length;t++){
			build.append(toString(b[t]));
		}
		return build.toString();
	}
	
	/**
	 * 将一串十六进制字符串转换为二进制<br/>
	 * 以<strong>2字符-->1 byte</strong>为单位进行转换<br/>
	 * 如 2a(十六进制)-->42(十进制)-->00101010<br/>
	 * 0a(十六进制)-->10(十进制)-->00001010<br/>
	 * 
	 * @param hexString
	 *            十六进制字符串
	 * @return 二进制数组，如果传入的hexString不是偶数hexString.length % 2 !=0 时，返回null
	 */
	public static byte[] toByte(String hexString) {
		int length = hexString.length();
		if (length % 2 != 0) {
			System.out.println("传入的字符串[" + hexString + "] 不能被2整除");
			return null;
		}
		byte[] b = new byte[hexString.length() / 2];
		int index = 0;
		// 以二个字符串为单位进行计算
		for (int i = 0; i < length; i = i + 2) {
			String tmp = hexString.substring(i, i + 2);
			b[index++] = new Integer(Integer.parseInt(tmp, 16)).byteValue();
		}
		return b;
	}

	/**
	 * 将一组二进制转换为进制字符串<br/>
	 * 以<strong>1 byte --> 两个16进制字符串</strong>为 单位<br/>
	 * 如 byte a = Byte.parseByte("00101010",2);<br/>
	 * 00101010（二进制）-->42(十进制)-->2a(十六进制)<br/>
	 * byte b = Byte.parseByte("00001010",2);<br/>
	 * 00001010（二进制）-->10(十进制)-->0a(十六进制)<br/>
	 * 
	 * @param buf
	 *            一组二进制字节数据(1 byte = 8 bit)
	 * @return 返回16进制字符串，1 byte 转换不足时补0 00001010-->0a
	 */
	/**
	 * @param buf
	 * @return 返回以1 byte --> 2个16进制为字符串结果
	 */
	public static String toHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		for (int i = 0; i < buf.length; i++) {
			// 十六进制比较，小于00010000的补0
			if (((int) buf[i] & 0xff) < 0x10)strbuf.append("0");
			strbuf.append(Integer.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}
	
}
