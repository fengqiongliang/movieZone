package com.pxe.myiscsi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;

/**
<pre>

10.19.  NOP-In

   Byte/     0       |       1       |       2       |       3       |
      /             |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x20      |1| Reserved                                    |
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
   24| StatSN                                                        |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / DataSegment - Return Ping Data                                /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   NOP-In is either sent by a target as a response to a NOP-Out, as a
   "ping" to an initiator, or as a means to carry a changed ExpCmdSN
   and/or MaxCmdSN if another PDU will not be available for a long time
   (as determined by the target).

   When a target receives the NOP-Out with a valid Initiator Task Tag
   (not the reserved value 0xffffffff), it MUST respond with a NOP-In
   with the same Initiator Task Tag that was provided in the NOP-Out
   request.  It MUST also duplicate up to the first
   MaxRecvDataSegmentLength bytes of the initiator provided Ping Data.
   For such a response, the Target Transfer Tag MUST be 0xffffffff.

   Otherwise, when a target sends a NOP-In that is not a response to a
   Nop-Out received from the initiator, the Initiator Task Tag MUST be
   set to 0xffffffff and the Data Segment MUST NOT contain any data
   (DataSegmentLength MUST be 0).

10.19.1.  Target Transfer Tag

   If the target is responding to a NOP-Out, this is set to the reserved
   value 0xffffffff.

   If the target is sending a NOP-In as a Ping (intending to receive a
   corresponding NOP-Out), this field is set to a valid value (not the
   reserved 0xffffffff).

   If the target is initiating a NOP-In without wanting to receive a
   corresponding NOP-Out, this field MUST hold the reserved value of
   0xffffffff.

10.19.2.  StatSN

   The StatSN field will always contain the next StatSN.  However, when
   the Initiator Task Tag is set to 0xffffffff, StatSN for the
   connection is not advanced after this PDU is sent.

10.19.3.  LUN

   A LUN MUST be set to a correct value when the Target Transfer Tag is
   valid (not the reserved value 0xffffffff).


   
   
</pre>
 * 
 *
 */
public class PDUNopIn {
	
	private byte Opcode = 0x20;
	private boolean isFinal = true;
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] PingData = new byte[0];
	public PDUNopIn(){}
	public PDUNopIn(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		PingData = DataSegment;
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.NOP_IN;
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
	public int getStatSN() {
		return ByteUtil.byteArrayToInt(StatSN);
	}
	public void setStatSN(int StatSN) {
		this.StatSN = ByteUtil.intToByteArray(StatSN);
	}
	public int getExpCmdSN() {
		return ByteUtil.byteArrayToInt(ExpCmdSN);
	}
	public void setExpCmdSN(int ExpCmdSN) {
		this.ExpCmdSN = ByteUtil.intToByteArray(ExpCmdSN);
	}
	public int getMaxCmdSN() {
		return ByteUtil.byteArrayToInt(MaxCmdSN);
	}
	public void setMaxCmdSN(int MaxCmdSN) {
		this.MaxCmdSN = ByteUtil.intToByteArray(MaxCmdSN);
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
		build.append(System.getProperty("line.separator")+" Opcode : "+PDUOpcodeEnum.valueOf(Opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(this.TargetTransferTag));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		build.append(System.getProperty("line.separator")+" PingData : 0x"+ByteUtil.toHex(this.PingData));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x20); //Opcode
			dos.writeByte(0x80); 
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
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
		PDUNopIn original = new PDUNopIn();
		original.setLUN(new byte[]{1});
		original.setInitiatorTaskTag(1);
		original.setTargetTransferTag(2);
		original.setStatSN(3);
		original.setExpCmdSN(4);
		original.setMaxCmdSN(5);
		original.setPingData("hfdsakfdlsajfldsa".getBytes());
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		PDUNopIn after = new PDUNopIn(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
