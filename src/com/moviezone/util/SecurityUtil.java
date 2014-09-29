package com.moviezone.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityUtil {
	private static final Logger logger = LoggerFactory.getLogger(SecurityUtil.class);
	
	public static final String AESKey = "a4ceda3d15a1a621764ab813f87ac188";
	
	/**
	 * md5加密
	 * @param content
	 * @return 返回长度一制的加密字符串,或是""
	 */
	public static String encryptMD5(byte[] content){
		try {
			byte[] input = content;
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input);
			return toHex(algorithm.digest());
		} catch (Exception e) {
			logger.error("",e);
		}
		return "";
	}
	
	public static String encryptMD5(String content){
		try {
			byte[] input = content.getBytes("UTF-8");
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input);
			return toHex(algorithm.digest());
		} catch (Exception e) {
			logger.error("",e);
		}
		return "";
	}
	/**AES加密
	 * @param content 密码（明文）
	 * @return 加密后的密文 （十六进制字符串）<br/>
	 *         返回空串"",如果content为空
	 */

	public static String encryptAES(String content) {
		if (StringUtils.isBlank(content))return "";
		byte[] key = toByte(AESKey);
		try {
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			return toHex(cipher.doFinal(content.getBytes()));
		} catch (Exception e) {
			logger.error("",e);
		}
		return "";

	}

	/**AES 解密
	 * @param encryptedContent 密码（密文） 十六进制形式
	 * @return 如果抛出异常则null
	 */
	public static String decryptAES(String encryptedContent) {
		if(StringUtils.isBlank(encryptedContent))return "";
		byte[] encrypedPWD = toByte(encryptedContent);
		byte[] privateKey = toByte(AESKey);
		try{
			SecretKeySpec skeySpec = new SecretKeySpec(privateKey, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			return new String(cipher.doFinal(encrypedPWD));
		}catch(Exception ex){
			logger.error("",ex);
		}
		return "";
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
