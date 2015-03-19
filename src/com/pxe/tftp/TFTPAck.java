package com.pxe.tftp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * <pre>
 * 
 *  2 bytes     2 bytes    
 *  ------------------------
 * | Opcode |   Block #  |
 *  ------------------------
 *       Figure 5-3: Ack packet
 *       
 *  All  packets other than duplicate ACK's and those used for
   termination are acknowledged unless a timeout occurs [4].  Sending a
   DATA packet is an acknowledgment for the first ACK packet of the
   previous DATA packet. The WRQ and DATA packets are acknowledged by
   ACK or ERROR packets, while RRQ and ACK packets are acknowledged by  DATA  or ERROR packets.  
   Figure 5-3 depicts an ACK packet; the opcode is 4.  The  block  number  in
   an  ACK echoes the block number of the DATA packet being
   acknowledged.  A WRQ is acknowledged with an ACK packet having a
   block number of zero.
 *</pre>
 */
public class TFTPAck extends TFTPPacket{
	private short block = 0;    /* begin 1 incease by 1*/
	public TFTPAck(InetAddress remote_ip,int remote_port){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 4;
		this.block    = 0;
	}
	public TFTPAck(InetAddress remote_ip,int remote_port,short block) throws Exception{
		if(block<1)throw new Exception("block start with 1 and increase by 1!!");
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 4;
		this.block = block;
	}
	public TFTPAck(DatagramPacket p) throws Exception{
		byte[] data = p.getData();
		short op = (short)data[1];
		if(op!=4) throw new Exception("not TFTP Ack packet!! opcode :"+op);
		this.remote_ip = p.getAddress();
		this.remote_port = p.getPort();
		this.opcode = 4;
		this.block = (short)(data[2] <<8 | data[3]);
		
	}
	public short getBlock() {
		return block;
	}
	public void setBlock(short block) {
		this.block = block;
	}
	@Override
	public DatagramPacket toDatagram() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos        = new DataOutputStream(bos);
		try {
			dos.writeShort(this.opcode);
			dos.writeShort(this.block);
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
		return "TFTPAck Packet : opcode:"+this.opcode+" block:"+this.block;
	}

}
