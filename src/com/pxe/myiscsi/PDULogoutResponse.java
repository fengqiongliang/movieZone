package com.pxe.myiscsi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.target.connection.SessionType;

/**
<pre>

10.15.  Logout Response

   The Logout Response is used by the target to indicate if the cleanup
   operation for the connection(s) has completed.

   After Logout, the TCP connection referred by the CID MUST be closed
   at both ends (or all connections must be closed if the logout reason
   was session close).

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x26      |1| Reserved    | Response      | Reserved      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------------------------------------------------------+
    8/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   24| StatSN                                                        |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36| Reserved                                                      |
     +---------------------------------------------------------------+
   40| Time2Wait                     | Time2Retain                   |
     +---------------+---------------+---------------+---------------+
   44| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+

10.15.1.  Response

   Logout Response:

      0 - connection or session closed successfully.

      1 - CID not found.

      2 - connection recovery is not supported.  If Logout reason code
         was recovery and target does not support it as indicated by the
         ErrorRecoveryLevel.

      3 - cleanup failed for various reasons.

10.15.2.  TotalAHSLength and DataSegmentLength

   For this PDU TotalAHSLength and DataSegmentLength MUST be 0.

10.15.3.  Time2Wait

   If the Logout Response code is 0 and if the operational
   ErrorRecoveryLevel is 2, this is the minimum amount of time, in
   seconds, to wait before attempting task reassignment.  If the Logout
   Response code is 0 and if the operational ErrorRecoveryLevel is less
   than 2, this field is to be ignored.

   This field is invalid if the Logout Response code is 1.

   If the Logout response code is 2 or 3, this field specifies the
   minimum time to wait before attempting a new implicit or explicit
   logout.

   If Time2Wait is 0, the reassignment or a new Logout may be attempted
   immediately.

10.15.4.  Time2Retain

   If the Logout response code is 0 and if the operational
   ErrorRecoveryLevel is 2, this is the maximum amount of time, in
   seconds, after the initial wait (Time2Wait), the target waits for the
   allegiance reassignment for any active task after which the task
   state is discarded.  If the Logout response code is 0 and if the
   operational ErrorRecoveryLevel is less than 2, this field is to be
   ignored.

   This field is invalid if the Logout response code is 1.

   If the Logout response code is 2 or 3, this field specifies the
   maximum amount of time, in seconds, after the initial wait
   (Time2Wait), the target waits for a new implicit or explicit logout.

   If it is the last connection of a session, the whole session state is
   discarded after Time2Retain.

   If Time2Retain is 0, the target has already discarded the connection
   (and possibly the session) state along with the task states.  No
   reassignment or Logout is required in this case.





   
</pre>
 * 
 *
 */
public class PDULogoutResponse {
	
	private byte Opcode = 0x26;
	private boolean isFinal = true;
	private byte Response; 
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] Time2Wait = new byte[2];
	private byte[] Time2Retain = new byte[2];
	public PDULogoutResponse(){}
	public PDULogoutResponse(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		Response =BHS[2];
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 40, Time2Wait, 0, Time2Wait.length);
		System.arraycopy(BHS, 42, Time2Retain, 0, Time2Retain.length);
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.LOGOUT_RESPONSE;
	}
	public boolean getFinal() {
		return isFinal;
	}
	public PDUResponseEnum getResponse() {
		return PDUResponseEnum.valueOf(Response);
	}
	public void setResponse(PDUResponseEnum response) {
		Response = response.value();
	}
	public byte getTotalAHSLength() {
		return TotalAHSLength;
	}
	public int getDataSegmentLength() {
		return ByteUtil.byteArrayToInt(DataSegmentLength);
	}
	public int getInitiatorTaskTag() {
		return ByteUtil.byteArrayToInt(InitiatorTaskTag);
	}
	public void setInitiatorTaskTag(int initiatorTaskTag) {
		InitiatorTaskTag = ByteUtil.intToByteArray(initiatorTaskTag);
	}
	public int getStatSN() {
		return ByteUtil.byteArrayToInt(StatSN);
	}
	public void setStatSN(int statSN) {
		StatSN = ByteUtil.intToByteArray(statSN);
	}
	public int getExpCmdSN() {
		return ByteUtil.byteArrayToInt(ExpCmdSN);
	}
	public void setExpCmdSN(int expCmdSN) {
		ExpCmdSN = ByteUtil.intToByteArray(expCmdSN);
	}
	public int getMaxCmdSN() {
		return ByteUtil.byteArrayToInt(MaxCmdSN);
	}
	public void setMaxCmdSN(int maxCmdSN) {
		MaxCmdSN = ByteUtil.intToByteArray(maxCmdSN);
	}
	public short getTime2Wait() {
		return ByteUtil.byteArrayToShort(Time2Wait);
	}
	public void setTime2Wait(short time2Wait) {
		Time2Wait = ByteUtil.shortToByteArray(time2Wait);
	}
	public short getTime2Retain() {
		return ByteUtil.byteArrayToShort(Time2Retain);
	}
	public void setTime2Retain(short time2Retain) {
		Time2Retain = ByteUtil.shortToByteArray(time2Retain);
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+PDUOpcodeEnum.valueOf(Opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" Response : "+PDUResponseEnum.valueOf(Response));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.toHex(StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : 0x"+ByteUtil.toHex(ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : 0x"+ByteUtil.toHex(MaxCmdSN));
		build.append(System.getProperty("line.separator")+" Time2Wait : 0x"+ByteUtil.toHex(Time2Wait));
		build.append(System.getProperty("line.separator")+" Time2Retain : 0x"+ByteUtil.toHex(Time2Retain));
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		PDULogoutResponse original = new PDULogoutResponse();
		original.setResponse(PDUResponseEnum.connRecoveryNotSupport);
		original.setInitiatorTaskTag(10);
		original.setStatSN(1);
		original.setExpCmdSN(2);
		original.setMaxCmdSN(3);
		original.setTime2Wait((short)4);
		original.setTime2Retain((short)5);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		PDULogoutResponse after = new PDULogoutResponse(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		byte[] b = ByteUtil.intToByteArray(-1);
		System.out.println(ByteUtil.toHex(b));
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x26); //Opcode
			dos.writeByte(0x80);
			dos.writeByte(Response);
			dos.write(new byte[]{0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.InitiatorTaskTag);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.Time2Wait);
			dos.write(this.Time2Retain);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	 
}
