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
 *  2 bytes     2 bytes      n bytes
 *  ----------------------------------
 * | Opcode |   Block #  |   Data     |
 *  ----------------------------------
 *       Figure 5-2: DATA packet
 *       
 *  Data is actually transferred in DATA packets depicted in Figure 5-2.
 *  DATA packets (opcode = 3) have a block number and data field.  The
 *  block numbers on data packets begin with one and increase by one for
 *  each new block of data.  This restriction allows the program to use a
 *  single number to discriminate between new packets and duplicates.
 *  The data field is from zero to 512 bytes long.  If it is 512 bytes
 *  long, the block is not the last block of data; if it is from zero to
 *  511 bytes long, it signals the end of the transfer.
 *</pre>
 */
public class TFTPData extends TFTPPacket{
	private short block;    /* begin 1 incease by 1*/
	private byte[] data;
	public TFTPData(InetAddress remote_ip,int remote_port){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 3;
		this.block    = 0;
		this.data      = new byte[0];
	}
	public TFTPData(InetAddress remote_ip,int remote_port,short block,byte[] data) throws Exception{
		if(block<1)throw new Exception("block start with 1 and increase by 1!!");
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 3;
		this.block = block;
		this.data   = data;
	}
	public TFTPData(DatagramPacket p) throws Exception{
		byte[] data = p.getData();
		short op = (short)data[1];
		if(op!=3) throw new Exception("not TFTP Data packet!! opcode :"+op);
		this.remote_ip = p.getAddress();
		this.remote_port = p.getPort();
		this.opcode = 3;
		this.block = (short)(data[2] <<8 | data[3]);
		this.data  = Arrays.copyOfRange(data, 4, data.length);
	}
	public short getBlock() {
		return block;
	}
	public void setBlock(short block) {
		this.block = block;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	@Override
	public DatagramPacket toDatagram() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos        = new DataOutputStream(bos);
		try {
			dos.writeShort(this.opcode);
			dos.writeShort(this.block);
			dos.write(this.data);
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
		return "TFTPData Packet : opcode:"+this.opcode+" block:"+this.block+" data.length:"+this.data.length;
	}
}
