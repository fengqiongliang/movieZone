package com.moviezone.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;


import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


public class ImageUtil {
	
	
	public static boolean scaleImg(int newWidth,int newHeight,InputStream imgIn,OutputStream imgOut){
		if(newWidth  <= 0)return false;
		if(newHeight <= 0)return false;
		if(imgIn  == null)return false;
		if(imgOut == null)return false;
		try {
			BufferedImage inImg  = ImageIO.read(imgIn);
			if(inImg.getWidth()<=0 || inImg.getHeight()<=0)return false;
			BufferedImage outImg = new BufferedImage(newWidth,newHeight,BufferedImage.TYPE_INT_RGB);
			outImg.getGraphics().drawImage(inImg.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH), 0, 0,Color.white,null);
			
			//写到流中去
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(imgOut);   
	        encoder.encode(outImg);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
