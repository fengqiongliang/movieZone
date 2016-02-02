package com.pxe.iscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.Opcode;

/**
<pre>

10.16.  SNACK Request

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x10      |1|.|.|.| Type  | Reserved                      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN or Reserved                                               |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag or 0xffffffff                              |
     +---------------+---------------+---------------+---------------+
   20| Target Transfer Tag or SNACK Tag or 0xffffffff                |
     +---------------+---------------+---------------+---------------+
   24| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN                                                     |
     +---------------+---------------+---------------+---------------+
   32/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   40| BegRun                                                        |
     +---------------------------------------------------------------+
   44| RunLength                                                     |
     +---------------------------------------------------------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+

   If the implementation supports ErrorRecoveryLevel greater than zero,
   it MUST support all SNACK types.


   The SNACK is used by the initiator to request the retransmission of
   numbered-responses, data, or R2T PDUs from the target.  The SNACK
   request indicates the numbered-responses or data "runs" whose
   retransmission is requested by the target, where the run starts with
   the first StatSN, DataSN, or R2TSN whose retransmission is requested
   and indicates the number of Status, Data, or R2T PDUs requested
   including the first.  0 has special meaning when used as a starting
   number and length:

     - When used in RunLength, it means all PDUs starting with the
       initial.
     - When used in both BegRun and RunLength, it means all
       unacknowledged PDUs.

   The numbered-response(s) or R2T(s), requested by a SNACK, MUST be
   delivered as exact replicas of the ones that the target transmitted
   originally except for the fields ExpCmdSN, MaxCmdSN, and ExpDataSN,
   which MUST carry the current values.  R2T(s)requested by SNACK MUST
   also carry the current value of StatSN.

   The numbered Data-In PDUs, requested by a Data SNACK MUST be
   delivered as exact replicas of the ones that the target transmitted
   originally except for the fields ExpCmdSN and MaxCmdSN, which MUST
   carry the current values and except for resegmentation (see Section
   10.16.3 Resegmentation).

   Any SNACK that requests a numbered-response, Data, or R2T that was
   not sent by the target or was already acknowledged by the initiator,
   MUST be rejected with a reason code of "Protocol error".

10.16.1.  Type

   This field encodes the SNACK function as follows:

      0-Data/R2T SNACK - requesting retransmission of one or more Data-
        In or R2T PDUs.

      1-Status SNACK - requesting retransmission of one or more numbered
        responses.

      2-DataACK - positively acknowledges Data-In PDUs.

      3-R-Data SNACK - requesting retransmission of Data-In PDUs with
        possible resegmentation and status tagging.


   All other values are reserved.

   Data/R2T SNACK, Status SNACK, or R-Data SNACK for a command MUST
   precede status acknowledgement for the given command.

10.16.2.  Data Acknowledgement

   If an initiator operates at ErrorRecoveryLevel 1 or higher, it MUST
   issue a SNACK of type DataACK after receiving a Data-In PDU with the
   A bit set to 1.  However, if the initiator has detected holes in the
   input sequence, it MUST postpone issuing the SNACK of type DataACK
   until the holes are filled.  An initiator MAY ignore the A bit if it
   deems that the bit is being set aggressively by the target (i.e.,
   before the MaxBurstLength limit is reached).

   The DataACK is used to free resources at the target and not to
   request or imply data retransmission.

   An initiator MUST NOT request retransmission for any data it had
   already acknowledged.

10.16.3.  Resegmentation

   If the initiator MaxRecvDataSegmentLength changed between the
   original transmission and the time the initiator requests
   retransmission, the initiator MUST issue a R-Data SNACK (see Section
   10.16.1 Type).  With R-Data SNACK, the initiator indicates that it
   discards all the unacknowledged data and expects the target to resend
   it.  It also expects resegmentation.  In this case, the retransmitted
   Data-In PDUs MAY be different from the ones originally sent in order
   to reflect changes in MaxRecvDataSegmentLength.  Their DataSN starts
   with the BegRun of the last DataACK received by the target if any was
   received; otherwise it starts with 0 and is increased by 1 for each
   resent Data-In PDU.

   A target that has received a R-Data SNACK MUST return a SCSI Response
   that contains a copy of the SNACK Tag field from the R-Data SNACK in
   the SCSI Response SNACK Tag field as its last or only Response.  For
   example, if it has already sent a response containing another value
   in the SNACK Tag field or had the status included in the last Data-In
   PDU, it must send a new SCSI Response PDU.  If a target sends more
   than one SCSI Response PDU due to this rule, all SCSI responses must
   carry the same StatSN (see Section 10.4.4 SNACK Tag).  If an
   initiator attempts to recover a lost SCSI Response (with a
   Status SNACK, see Section 10.16.1 Type) when more than one response
   has been sent, the target will send the SCSI Response with the latest
   content known to the target, including the last SNACK Tag for the
   command.

   For considerations in allegiance reassignment of a task to a
   connection with a different MaxRecvDataSegmentLength, refer to
   Section 6.2.2 Allegiance Reassignment.

10.16.4.  Initiator Task Tag

   For Status SNACK and DataACK, the Initiator Task Tag MUST be set to
   the reserved value 0xffffffff.  In all other cases, the Initiator
   Task Tag field MUST be set to the Initiator Task Tag of the
   referenced command.

10.16.5.  Target Transfer Tag or SNACK Tag

   For an R-Data SNACK, this field MUST contain a value that is
   different from 0 or 0xffffffff and is unique for the task (identified
   by the Initiator Task Tag).  This value MUST be copied by the iSCSI
   target in the last or only SCSI Response PDU it issues for the
   command.

   For DataACK, the Target Transfer Tag MUST contain a copy of the
   Target Transfer Tag and LUN provided with the SCSI Data-In PDU with
   the A bit set to 1.

   In all other cases, the Target Transfer Tag field MUST be set to the
   reserved value of 0xffffffff.

10.16.6.  BegRun

   The DataSN, R2TSN, or StatSN of the first PDU whose retransmission is
   requested (Data/R2T and Status SNACK), or the next expected DataSN
   (DataACK SNACK).

   BegRun 0 when used in conjunction with RunLength 0 means resend all
   unacknowledged Data-In, R2T or Response PDUs.

   BegRun MUST be 0 for a R-Data SNACK.

10.16.7.  RunLength

   The number of PDUs whose retransmission is requested.

   RunLength 0 signals that all Data-In, R2T, or Response PDUs carrying
   the numbers equal to or greater than BegRun have to be resent.

   The RunLength MUST also be 0 for a DataACK SNACK in addition to
   R-Data SNACK.






   
   
</pre>
 * 
 *
 */
public class SNACKRequest {

	public enum Type {

 /**
<pre>
0 - Data/R2T SNACK
    requesting retransmission of one or more Data-In or R2T PDUs.
</pre>
 */
		R2TSNACK((byte) 0x00),

 /**
<pre>
1 - Status SNACK 
    requesting retransmission of one or more numbered responses.
</pre>
 */
		StatusSNACK ((byte) 0x01),
	
/**
<pre>
2-DataACK
  positively acknowledges Data-In PDUs.
</pre>
 */
		DataACK((byte) 0x02),

/**
<pre>
3-R-Data SNACK
  requesting retransmission of Data-In PDUs with possible resegmentation and status tagging.
</pre>
 */
		RDataSNACK((byte) 0x03);

		
	    private final byte value;
	    private static Map<Byte , Type> mapping;
	    static {
	    	Type.mapping = new HashMap<Byte , Type>();
	        for (Type s : values()) {
	        	Type.mapping.put(s.value, s);
	        }
	    }
	    private Type (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Type valueOf (final byte value) {
	        return Type.mapping.get(value);
	    }
	}
	
	private byte opcode = 0x10;
	private boolean isFinal = true;
	private byte type;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[]{0,0,0};
	private byte[] LUN = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private byte[] BegRun = new byte[4];
	private byte[] RunLength = new byte[4];
	public SNACKRequest(){}
	public SNACKRequest(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		type = (byte)(BHS[1] & 0x0f);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
		System.arraycopy(BHS, 40, BegRun, 0, BegRun.length);
		System.arraycopy(BHS, 44, RunLength, 0, RunLength.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.SNACK_REQUEST;
	}
	public boolean getFinal(){
		return isFinal;
	}
	public Type getType() {
		return Type.valueOf(this.type);
	}
	public void setType(Type type) {
		this.type =  type.value;
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
	public int getExpStatSN() {
		return ByteUtil.byteArrayToInt(ExpStatSN);
	}
	public void setExpStatSN(int ExpStatSN) {
		this.ExpStatSN = ByteUtil.intToByteArray(ExpStatSN);
	}
	public int getBegRun() {
		return ByteUtil.byteArrayToInt(BegRun);
	}
	public void setBegRun(int BegRun) {
		this.BegRun = ByteUtil.intToByteArray(BegRun);
	}
	public int getRunLength() {
		return ByteUtil.byteArrayToInt(RunLength);
	}
	public void setRunLength(int RunLength) {
		this.RunLength = ByteUtil.intToByteArray(RunLength);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" type : "+Type.valueOf(this.type));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(this.TargetTransferTag));
		build.append(System.getProperty("line.separator")+" ExpStatSN : "+ByteUtil.byteArrayToInt(this.ExpStatSN));
		build.append(System.getProperty("line.separator")+" BegRun : "+ByteUtil.byteArrayToInt(this.BegRun));
		build.append(System.getProperty("line.separator")+" RunLength : "+ByteUtil.byteArrayToInt(this.RunLength));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x10); //Opcode
			dos.writeByte(0x80|this.type);
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.ExpStatSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.BegRun);
			dos.write(this.RunLength);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		SNACKRequest original = new SNACKRequest();
		original.setType(Type.RDataSNACK);
		original.setLUN(new byte[]{0x0e});
		original.setInitiatorTaskTag(2);
		original.setTargetTransferTag(3);
		original.setExpStatSN(4);
		original.setBegRun(5);
		original.setRunLength(6);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		SNACKRequest after = new SNACKRequest(BHS);
		System.out.println(after);
		System.out.println(data.length);
	}
 	
	
	 
}
