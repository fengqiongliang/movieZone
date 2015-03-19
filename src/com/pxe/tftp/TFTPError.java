package com.pxe.tftp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

import com.moviezone.util.SecurityUtil;

/**
 * <pre>
 *     2 bytes     2 bytes      string    1 byte
    -----------------------------------------
   | Opcode |  ErrorCode |   ErrMsg   |   0  |
    -----------------------------------------
         Figure 5-4: ERROR packet
         
   An ERROR packet (opcode 5) takes the form depicted in Figure 5-4.  An
   ERROR packet can be the acknowledgment of any other type of packet.
   The error code is an integer indicating the nature of the error.  A
   table of values and meanings is given in the appendix.  (Note that
   several error codes have been added to this version of this
   document.) The error message is intended for human consumption, and
   should be in netascii.  Like all other strings, it is terminated with
   a zero byte.
 *</pre>
 */
public class TFTPError extends TFTPPacket{
	private short ErrorCode;
	private String ErrorMsg;
	public TFTPError(InetAddress remote_ip,int remote_port){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 5;
		this.ErrorCode = -1;
		this.ErrorMsg  = "";
	}
	public TFTPError(InetAddress remote_ip,int remote_port,short ErrorCode,String ErrorMsg){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode  = 5;
		this.ErrorCode = ErrorCode;
		this.ErrorMsg = ErrorMsg;
	}
	public TFTPError(DatagramPacket p) throws Exception{
		byte[] data = p.getData();
		short op = (short)data[1];
		if(op!=5) throw new Exception("not TFTP Error packet!! opcode :"+op);
		this.remote_ip = p.getAddress();
		this.remote_port = p.getPort();
		this.opcode = 5;
		this.ErrorCode =  (short)(data[2] <<8 | data[3]);
		int ErrorMsgStart = 4;
		int ErrorMsgEnd  = -1;
		for(int i=ErrorMsgStart;i<data.length;i++){
			if(data[i]==0x00){
				ErrorMsgEnd = i-1;
				break;
			}
		}
		this.ErrorMsg = new String(Arrays.copyOfRange(data, ErrorMsgStart, ErrorMsgEnd+1));
		
	}
	public short getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(short errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorMsg() {
		return ErrorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		ErrorMsg = errorMsg;
	}
	@Override
	public DatagramPacket toDatagram() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos        = new DataOutputStream(bos);
		try {
			dos.writeShort(this.opcode);
			dos.writeShort(this.ErrorCode);
			dos.write(this.ErrorMsg.getBytes());
			dos.writeByte(0x00);
			byte[] data = bos.toByteArray();
			dos.close();
			return new DatagramPacket(data, data.length, this.remote_ip, this.remote_port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public String toString(){
		return "TFTPError Packet : opcode:"+this.opcode+" ErrorCode:"+this.ErrorCode+" ErrorMsg:"+this.ErrorMsg;
	}
}
