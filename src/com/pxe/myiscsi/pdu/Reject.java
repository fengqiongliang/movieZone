package com.pxe.myiscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.Opcode;

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
public class Reject {
	public enum Reason {

 /**
<pre>
0x02 - Data (payload) Digest Error 
</pre>
 */
		DataDigestError((byte) 0x02),

 /**
<pre>
0x03 - SNACK Reject
</pre>
 */
		SNACKReject((byte) 0x03),
	
/**
<pre>
0x04 - Protocol Error 
    (e.g., SNACK request for a status that was already acknowledged)
</pre>
 */
		ProtocolError((byte) 0x04),

/**
<pre>
0x05 - Command not supported 
</pre>
 */
		CommandNotSupported((byte) 0x05),

/**
<pre>
0x06 - Immediate Command Reject - too many immediate commands   
</pre>
 */
		ImmediateCommandReject((byte) 0x06),

/**
<pre>
0x07 - Task in progress
</pre>
 */
		TaskInProgress((byte) 0x07),

/**
<pre>
0x08 - Invalid Data ACK
</pre>
 */
		InvalidDataACK((byte) 0x08),
		
/**
<pre>
0x09 - Invalid PDU field
</pre>
 */
		InvalidPDUField((byte) 0x09),

/**
<pre>
0x0a - Long Operation Reject  
    Can't generate Target Transfer Tag - out of resources 
</pre>
 */
		LongOperationReject((byte) 0x0a),

/**
<pre>
0x0b - Negotiation Reset 
</pre>
 */
		NegotiationReset((byte) 0x0b),

/**
<pre>
0x0c - Waiting for Logout 
</pre>
 */
		WaitingLogout((byte) 0x0c);
		
	    private final byte value;
	    private static Map<Byte , Reason> mapping;
	    static {
	    	Reason.mapping = new HashMap<Byte , Reason>();
	        for (Reason s : values()) {
	        	Reason.mapping.put(s.value, s);
	        }
	    }
	    private Reason (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Reason valueOf (final byte value) {
	        return Reason.mapping.get(value);
	    }
	}
	
	private byte opcode = 0x3f;
	private boolean isFinal = true;
	private byte reason;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] DataSN = new byte[4]; //DataSN/R2TSN or Reserved 
	public Reject(){}
	public Reject(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		reason = BHS[2];
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length); 
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 36, DataSN, 0, DataSN.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.REJECT;
	}
	public boolean getFinal(){
		return isFinal;
	}
	public Reason getReason() {
		return Reason.valueOf(this.reason);
	}
	public void setReason(Reason reason) {
		this.reason = reason.value;
	}
	public int getTotalAHSLength() {
		return TotalAHSLength;
	}
	public int getDataSegmentLength() { 
		return ByteUtil.byteArrayToInt(DataSegmentLength);
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
	public int getDataSN() {
		return ByteUtil.byteArrayToInt(DataSN);
	}
	public void setDataSN(int DataSN) {
		this.DataSN = ByteUtil.intToByteArray(DataSN);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" Reason : "+Reason.valueOf(this.reason));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		build.append(System.getProperty("line.separator")+" DataSN/R2TSN : "+ByteUtil.byteArrayToInt(this.DataSN));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x3f); //Opcode
			dos.writeByte(0x80);
			dos.writeByte(this.reason);
			dos.write(new byte[]{0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.writeInt(-1);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.write(this.DataSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		Reject original = new Reject();
		original.setReason(Reason.DataDigestError);
		original.setStatSN(2);
		original.setExpCmdSN(3);
		original.setMaxCmdSN(4);
		original.setDataSN(5);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		Reject after = new Reject(BHS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
