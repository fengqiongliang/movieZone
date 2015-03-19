package com.pxe.tftp;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static com.pxe.dhcp.DHCPConstants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pxe.dhcp.DHCPConstants;

public class TFTPServlet {
	private static final Logger logger = LoggerFactory.getLogger(TFTPServlet.class);
	private static Map<Integer,byte[]> gpxe = new HashMap<Integer,byte[]>();   //记录每个一个block的字节数,初始化时记录，减少Arrays.copyRang性能低下问题
	public TFTPServlet() throws Exception{
		InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("resource/"+DHCPConstants.defFileName);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int block = 1;
		while(true){
			byte[] buf = new byte[TFTPPacket.maxTftpData]; //512 byte 读取(tftp每块512字节)
			int len = is.read(buf);
			if(len<=0)break;
			baos.write(buf, 0, len);
			gpxe.put(block++, len==TFTPPacket.maxTftpData?buf:Arrays.copyOfRange(buf, 0, len)); //这里注意对不满512字节的块处理
		}
		baos.close();
		is.close();
	}
	
	public TFTPPacket doTFTPRead(TFTPRead p) throws Exception{
		logger.debug("TFTPRead packet received");
		if(!DHCPConstants.defFileName.equals(p.getFilename())){
			logger.warn("read file name["+p.getFilename()+"] != ["+DHCPConstants.defFileName+"]dhcp defName");
			// return p.getFileNotFoundError(); //? 是否禁用文件名不存在时的操作 
		}
		//return ack data with 1 block
		return new TFTPData(p.getRemote_ip(),p.getRemote_port(),(short)1,gpxe.get(1));
	}

	public TFTPPacket doTFTPWrite(TFTPWrite p) throws Exception{
		logger.debug("TFTPWrite packet received");
		return p.getInvalidOpError(); //暂不支持写操作
	}

	public TFTPPacket doTFTPData(TFTPData p) throws Exception {
		logger.debug("TFTPData packet received");
		return p.getInvalidOpError();  //暂不支持写入服务器操作
	}

	public TFTPPacket doTFTPAck(TFTPAck p) throws Exception {
		logger.debug("TFTPAck packet received");
		int currBlock = p.getBlock(); //确认第几个包已经传完
		int nextBlock = (short)(currBlock<gpxe.size()?currBlock+1:currBlock); 
		//如果是最后一个包，传送一个确认包表示正常中断
		if(nextBlock==currBlock)return new TFTPAck(p.getRemote_ip(),p.getRemote_port(),(short)nextBlock);
		return new TFTPData(p.getRemote_ip(),p.getRemote_port(),(short)nextBlock,gpxe.get(nextBlock));
	}

	public TFTPPacket doTFTPError(TFTPError p) throws Exception {
		logger.debug("TFTPError packet received");
		logger.error("get tftp error detail : "+p);
		return null;
	}
	
    private byte[] intToByteArray(int i){
    	byte[] r = new byte[4];
    	r[0] = (byte) ((i & 0xff000000) >> 24);
    	r[1] = (byte) ((i & 0x00ff0000) >> 16);
    	r[2] = (byte) ((i & 0x0000ff00) >> 8);
    	r[3] = (byte) ((i & 0x000000ff) >> 0);
    	return r;
    }
    
}
