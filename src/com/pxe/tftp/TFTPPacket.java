package com.pxe.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class TFTPPacket {
	protected short opcode = -1;
	protected InetAddress remote_ip;
	protected int remote_port;
	public static int PACKET_MAX = 516;
	public static int PACKET_MIN = 4;
	public static int maxTftpData = 512;
	public short getOpcode() {
		return opcode;
	}
	public void setOpcode(short opcode) {
		this.opcode = opcode;
	}
	public InetAddress getRemote_ip() {
		return remote_ip;
	}
	public void setRemote_ip(InetAddress remote_ip) {
		this.remote_ip = remote_ip;
	}
	public int getRemote_port() {
		return remote_port;
	}
	public void setRemote_port(int remote_port) {
		this.remote_port = remote_port;
	}
	public static TFTPPacket fromDatagram(DatagramPacket p){
		try{
			if(p==null)throw new Exception("null package  ");
			if(p.getLength()>PACKET_MAX)throw new Exception("out of max package size : "+PACKET_MAX);
			byte[] data = p.getData();
			short op = (short)data[1];
			if(op==1) return new TFTPRead(p);
			if(op==2) return new TFTPWrite(p);
			if(op==3) return new TFTPData(p);
			if(op==4) return new TFTPAck(p);
			if(op==5) return new TFTPError(p);
			System.err.print("Invalid Packet --> op:"+op+" ("+p.getAddress().getHostAddress()+":"+p.getPort()+")");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	public abstract DatagramPacket toDatagram();
	
	public TFTPError getNotDefinedError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)0,"Not defined, see error message (if any).");
	}
	public TFTPError getFileNotFoundError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)1,"File not found.");
	}
	public TFTPError getAccessError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)2,"Access violation.");
	}
	public TFTPError getDiskFullError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)3,"Disk full or allocation exceeded.");
	}
	public TFTPError getInvalidOpError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)4,"Illegal TFTP operation.");
	}
	public TFTPError getUnknowIDError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)5,"Unknown transfer ID.");
	}
	public TFTPError getFileExistError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)6,"File already exists.");
	}
	public TFTPError getInvalidUserError(){
		return new TFTPError(this.remote_ip,this.remote_port,(short)7,"No such user.");
	}
}
