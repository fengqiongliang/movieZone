package com.pxe.myiscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.Opcode;

/**
<pre>
10.18.  NOP-Out

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| 0x00      |1| Reserved                                    |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN or Reserved                                               |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag or 0xffffffff                              |
     +---------------+---------------+---------------+---------------+
   20| Target Transfer Tag or 0xffffffff                             |
     +---------------+---------------+---------------+---------------+
   24| CmdSN                                                         |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN                                                     |
     +---------------+---------------+---------------+---------------+
   32/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / DataSegment - Ping Data (optional)                            /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   A NOP-Out may be used by an initiator as a "ping request" to verify
   that a connection/session is still active and all its components are
   operational.  The NOP-In response is the "ping echo".

   A NOP-Out is also sent by an initiator in response to a NOP-In.

   A NOP-Out may also be used to confirm a changed ExpStatSN if another
   PDU will not be available for a long time.

   Upon receipt of a NOP-In with the Target Transfer Tag set to a valid
   value (not the reserved 0xffffffff), the initiator MUST respond with
   a NOP-Out.  In this case, the NOP-Out Target Transfer Tag MUST
   contain a copy of the NOP-In Target Transfer Tag.

10.18.1.  Initiator Task Tag

   The NOP-Out MUST have the Initiator Task Tag set to a valid value
   only if a response in the form of NOP-In is requested (i.e., the
   NOP-Out is used as a ping request).  Otherwise, the Initiator Task
   Tag MUST be set to 0xffffffff.

   When a target receives the NOP-Out with a valid Initiator Task Tag,
   it MUST respond with a Nop-In Response (see Section 10.19 NOP-In).

   If the Initiator Task Tag contains 0xffffffff, the I bit MUST be set
   to 1 and the CmdSN is not advanced after this PDU is sent.

10.18.2.  Target Transfer Tag

   A target assigned identifier for the operation.

   The NOP-Out MUST only have the Target Transfer Tag set if it is
   issued in response to a NOP-In with a valid Target Transfer Tag.  In
   this case, it copies the Target Transfer Tag from the NOP-In PDU.
   Otherwise, the Target Transfer Tag MUST be set to 0xffffffff.

   When the Target Transfer Tag is set to a value other than 0xffffffff,
   the LUN field MUST also be copied from the NOP-In.

10.18.3.  Ping Data

   Ping data are reflected in the NOP-In Response.  The length of the
   reflected data are limited to MaxRecvDataSegmentLength.  The length
   of ping data are indicated by the DataSegmentLength.  0 is a valid
   value for the DataSegmentLength and indicates the absence of ping
   data.


   
   
</pre>
 * 
 *
 */
public class NopOut {
	
	private boolean isImmediate;
	private byte opcode = 0x00;
	private boolean isFinal = true;
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private byte[] PingData = new byte[0];
	public NopOut(){}
	public NopOut(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, CmdSN, 0, CmdSN.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
		PingData = DataSegment;
	}
	
	public Opcode getOpcode() {
		return Opcode.NOP_OUT;
	}
	public boolean getImmediate(){
		return isImmediate;
	}
	public void setImmediate(boolean isImmediate){
		this.isImmediate = isImmediate;
	}
	public int getTotalAHSLength() {
		return TotalAHSLength;
	}
	public int getDataSegmentLength() { 
		return ByteUtil.byteArrayToInt(DataSegmentLength);
	}
	public byte[] getLUN() {
		return LUN;
	}
	public void setLUN(byte[] LUN) {
		System.arraycopy(LUN, 0, this.LUN, 0, Math.min(LUN.length, this.LUN.length));
	}
	public int getInitiatorTaskTag() {
		return ByteUtil.byteArrayToInt(InitiatorTaskTag);
	}
	public void setInitiatorTaskTag(int initiatorTaskTag) {
		InitiatorTaskTag = ByteUtil.intToByteArray(initiatorTaskTag);
	}
	public int getTargetTransferTag() {
		return ByteUtil.byteArrayToInt(TargetTransferTag);
	}
	public void setTargetTransferTag(int targetTransferTag) {
		TargetTransferTag = ByteUtil.intToByteArray(targetTransferTag);
	}
	public int getCmdSN() {
		return ByteUtil.byteArrayToInt(CmdSN);
	}
	public void setCmdSN(int CmdSN) {
		this.CmdSN = ByteUtil.intToByteArray(CmdSN);
	}
	public int getExpStatSN() {
		return ByteUtil.byteArrayToInt(ExpStatSN);
	}
	public void setExpStatSN(int ExpStatSN) {
		this.ExpStatSN = ByteUtil.intToByteArray(ExpStatSN);
	}
	public byte[] getPingData() {
		return PingData;
	}
	public void setPingData(byte[] pingData) {
		PingData = pingData;
		DataSegmentLength[0] = (byte) ((pingData.length & 0x00ff0000) >> 16);
		DataSegmentLength[1] = (byte) ((pingData.length & 0x0000ff00) >> 8);
		DataSegmentLength[2] = (byte) ((pingData.length & 0x000000ff) >> 0);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(this.TargetTransferTag));
		build.append(System.getProperty("line.separator")+" CmdSN : "+ByteUtil.byteArrayToInt(this.CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : "+ByteUtil.byteArrayToInt(this.ExpStatSN));
		build.append(System.getProperty("line.separator")+" PingData : 0x"+ByteUtil.toHex(this.PingData));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(this.isImmediate?0x40:0x00); //Opcode
			dos.writeByte(0x80); 
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(this.CmdSN);
			dos.write(this.ExpStatSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved 
			dos.write(this.PingData);
			//write (All PDU segments and digests are padded to the closest integer number of four byte words.)
			int count = this.PingData.length % 4 == 0?0:(4-this.PingData.length % 4);
			for(int i=0;i<count;i++){
				dos.writeByte(0);
			}  
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		NopOut original = new NopOut();
		original.setImmediate(true);
		original.setLUN(new byte[]{1});
		original.setInitiatorTaskTag(1);
		original.setTargetTransferTag(2);
		original.setCmdSN(3);
		original.setExpStatSN(4);
		original.setPingData("hfdsakfdlsajfldsa".getBytes());
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		NopOut after = new NopOut(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
