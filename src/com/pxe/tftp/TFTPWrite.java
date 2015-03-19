package com.pxe.tftp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Arrays;


/**
 * <pre>
 *    2 bytes     string    1 byte     string   1 byte
 *   ------------------------------------------------
 * | Opcode |  Filename  |   0  |    Mode    |   0  |
 *   ------------------------------------------------
 *        Figure 5-1: RRQ/WRQ packet
 *        
 *        RRQ and WRQ packets (opcodes 1 and 2 respectively) have the format
   shown in Figure 5-1.  The file name is a sequence of bytes in
   netascii terminated by a zero byte.  The mode field contains the
   string "netascii", "octet", or "mail" (or any combination of upper
   and lower case, such as "NETASCII", NetAscii", etc.) in netascii
   indicating the three modes defined in the protocol.  A host which
   receives netascii mode data must translate the data to its own
   format.  Octet mode is used to transfer a file that is in the 8-bit
   format of the machine from which the file is being transferred.  It
   is assumed that each type of machine has a single 8-bit format that
   is more common, and that that format is chosen.  For example, on a
   DEC-20, a 36 bit machine, this is four 8-bit bytes to a word with
   four bits of breakage.  If a host receives a octet file and then
   returns it, the returned file must be identical to the original.
   Mail mode uses the name of a mail recipient in place of a file and
   must begin with a WRQ.  Otherwise it is identical to netascii mode.
   The mail recipient string should be of the form "username" or
   "username@hostname".  If the second form is used, it allows the
   option of mail forwarding by a relay computer.

   The discussion above assumes that both the sender and recipient are
   operating in the same mode, but there is no reason that this has to
   be the case.  For example, one might build a storage server.  There
   is no reason that such a machine needs to translate netascii into its
   own form of text.  Rather, the sender might send files in netascii,
   but the storage server might simply store them without translation in
   8-bit format.  Another such situation is a problem that currently
   exists on DEC-20 systems.  Neither netascii nor octet accesses all
   the bits in a word.  One might create a special mode for such a
   machine which read all the bits in a word, but in which the receiver
   stored the information in 8-bit format.  When such a file is
   retrieved from the storage site, it must be restored to its original
   form to be useful, so the reverse mode must also be implemented.  The
   user site will have to remember some information to achieve this.  In
   both of these examples, the request packets would specify octet mode
   to the foreign host, but the local host would be in some other mode.
   No such machine or application specific modes have been specified in
   TFTP, but one would be compatible with this specification.

   It is also possible to define other modes for cooperating pairs of
   hosts, although this must be done with care.  There is no requirement
   that any other hosts implement these.  There is no central authority
   that will define these modes or assign them names.
 *</pre>
 */
public class TFTPWrite extends TFTPPacket{
	private String filename;
	private String mode;
	public TFTPWrite(InetAddress remote_ip,int remote_port){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode = 2;
		this.filename = "null";
		this.mode     = "octet";
	}
	public TFTPWrite(InetAddress remote_ip,int remote_port,String filename,String mode){
		this.remote_ip = remote_ip;
		this.remote_port = remote_port;
		this.opcode  = 2;
		this.filename = filename;
		this.mode    = mode;
	}
	public TFTPWrite(DatagramPacket p) throws Exception{
		byte[] data = p.getData();
		short op = (short)data[1];
		if(op!=2) throw new Exception("not TFTP Write packet!! opcode :"+op);
		this.remote_ip = p.getAddress();
		this.remote_port = p.getPort();
		this.opcode = 2;
		int fileNameStart = 2;
		int fileNameEnd  = -1;
		for(int i=fileNameStart;i<data.length;i++){
			if(data[i]==0x00){
				fileNameEnd = i-1;
				break;
			}
		}
		this.filename = new String(Arrays.copyOfRange(data, fileNameStart, fileNameEnd+1));
		int modeStart = fileNameEnd+2;
		int modeEnd   = -1;
		for(int i=modeStart;i<data.length;i++){
			if(data[i]==0x00){
				modeEnd = i-1;
				break;
			}
		}
		this.mode = new String(Arrays.copyOfRange(data, modeStart, modeEnd+1));
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	@Override
	public DatagramPacket toDatagram() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos        = new DataOutputStream(bos);
		try {
			dos.writeShort(this.opcode);
			dos.write(this.filename.getBytes());
			dos.writeByte(0x00);
			dos.write(this.mode.getBytes());
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
		return "TFTPWrite Packet : opcode:"+this.opcode+" filename:"+this.filename+" mode:"+this.mode;
	}
}
