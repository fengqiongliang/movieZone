package com.pxe.myiscsi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;

/**
<pre>

10.17.  Reject

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x3f      |1| Reserved    | Reason        | Reserved      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   16| 0xffffffff                                                    |
     +---------------+---------------+---------------+---------------+
   20| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   24| StatSN                                                        |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36| DataSN/R2TSN or Reserved                                      |
     +---------------+---------------+---------------+---------------+
   40| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   44| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
   xx/ Complete Header of Bad PDU                                    /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   yy/Vendor specific data (if any)                                  /
     /                                                               /
     +---------------+---------------+---------------+---------------+
   zz| Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   Reject is used to indicate an iSCSI error condition (protocol,
   unsupported option, etc.).

10.17.1.  Reason

   The reject Reason is coded as follows:

   +------+----------------------------------------+------------------+
   | Code | Explanation                            | Can the original |
   | (hex)|                                        | PDU be re-sent?  |
   +------+----------------------------------------+------------------+
   | 0x01 | Reserved                               | no               |
   |      |                                        |                  |
   | 0x02 | Data (payload) Digest Error            | yes  (Note 1)    |
   |      |                                        |                  |
   | 0x03 | SNACK Reject                           | yes              |
   |      |                                        |                  |
   | 0x04 | Protocol Error (e.g., SNACK request for| no               |
   |      | a status that was already acknowledged)|                  |
   |      |                                        |                  |
   | 0x05 | Command not supported                  | no               |
   |      |                                        |                  |
   | 0x06 | Immediate Command Reject - too many    | yes              |
   |      | immediate commands                     |                  |
   |      |                                        |                  |
   | 0x07 | Task in progress                       | no               |
   |      |                                        |                  |
   | 0x08 | Invalid Data ACK                       | no               |
   |      |                                        |                  |
   | 0x09 | Invalid PDU field                      | no   (Note 2)    |
   |      |                                        |                  |
   | 0x0a | Long Operation Reject - Can't generate | yes              |
   |      | Target Transfer Tag - out of resources |                  |
   |      |                                        |                  |
   | 0x0b | Negotiation Reset                      | no               |
   |      |                                        |                  |
   | 0x0c | Waiting for Logout                     | no               |
   +------+----------------------------------------+------------------+

   Note 1: For iSCSI, Data-Out PDU retransmission is only done if the
   target requests retransmission with a recovery R2T.  However, if this
   is the data digest error on immediate data, the initiator may choose
   to retransmit the whole PDU including the immediate data.

   Note 2: A target should use this reason code for all invalid values
   of PDU fields that are meant to describe a task,  a response, or a
   data transfer.  Some examples are invalid TTT/ITT, buffer offset, LUN
   qualifying a TTT, and an invalid sequence number in a SNACK.

   All other values for Reason are reserved.

   In all the cases in which a pre-instantiated SCSI task is terminated
   because of the reject, the target MUST issue a proper SCSI command
   response with CHECK CONDITION as described in Section 10.4.3
   Response.  In these cases in which a status for the SCSI task was
   already sent before the reject, no additional status is required.  If
   the error is detected while data from the initiator is still expected
   (i.e., the command PDU did not contain all the data and the target
   has not received a Data-Out PDU with the Final bit set to 1 for the
   unsolicited data, if any, and all outstanding R2Ts, if any), the
   target MUST wait until it receives the last expected Data-Out PDUs
   with the F bit set to 1 before sending the Response PDU.

   For additional usage semantics of Reject PDU, see Section 6.3 Usage
   Of Reject PDU in Recovery.

10.17.2.  DataSN/R2TSN

   This field is only valid if the rejected PDU is a Data/R2T SNACK and
   the Reject reason code is "Protocol error" (see Section 10.16 SNACK
   Request).  The DataSN/R2TSN is the next Data/R2T sequence number that
   the target would send for the task, if any.

10.17.3.  StatSN, ExpCmdSN and MaxCmdSN

   These fields carry their usual values and are not related to the
   rejected command. StatSN is advanced after a Reject.

10.17.4.  Complete Header of Bad PDU

   The target returns the header (not including digest) of the PDU in
   error as the data of the response.






   
   
</pre>
 * 
 *
 */
public class PDUReject {
	
	private byte Opcode = 0x3f;
	private boolean isFinal = true;
	private byte Reason;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] DataSN = new byte[4]; //DataSN/R2TSN or Reserved 
	public PDUReject(){}
	public PDUReject(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 36, DataSN, 0, DataSN.length);
		System.arraycopy(BHS, 40, BufferOffset, 0, BufferOffset.length);
		System.arraycopy(BHS, 44, DesiredDataLength, 0, DesiredDataLength.length);
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.R2T;
	}
	public boolean getFinal(){
		return isFinal;
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
	public int getR2TSN() {
		return ByteUtil.byteArrayToInt(DataSN);
	}
	public void setR2TSN(int R2TSN) {
		this.DataSN = ByteUtil.intToByteArray(R2TSN);
	}
	public int getBufferOffset() {
		return ByteUtil.byteArrayToInt(BufferOffset);
	}
	public void setBufferOffset(int BufferOffset) {
		this.BufferOffset = ByteUtil.intToByteArray(BufferOffset);
	}
	public int getDesiredDataLength() {
		return ByteUtil.byteArrayToInt(DesiredDataLength);
	}
	public void setDesiredDataLength(int DesiredDataLength) {
		this.DesiredDataLength = ByteUtil.intToByteArray(DesiredDataLength);
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
		build.append(System.getProperty("line.separator")+" R2TSN : "+ByteUtil.byteArrayToInt(this.DataSN));
		build.append(System.getProperty("line.separator")+" BufferOffset : "+ByteUtil.byteArrayToInt(this.BufferOffset));
		build.append(System.getProperty("line.separator")+" DesiredDataLength : "+ByteUtil.byteArrayToInt(this.DesiredDataLength));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x31); //Opcode
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
			dos.write(this.DataSN);
			dos.write(this.BufferOffset);
			dos.write(this.DesiredDataLength);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		PDUReject original = new PDUReject();
		original.setLUN(new byte[]{1});
		original.setInitiatorTaskTag(1);
		original.setTargetTransferTag(2);
		original.setStatSN(3);
		original.setExpCmdSN(4);
		original.setMaxCmdSN(5);
		original.setR2TSN(6);
		original.setBufferOffset(7);
		original.setDesiredDataLength(8);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		PDUReject after = new PDUReject(BHS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
