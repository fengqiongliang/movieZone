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


10.4.  SCSI Response

   The format of the SCSI Response PDU is:

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x21      |1|. .|o|u|O|U|.| Response      | Status        |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| Reserved                                                      |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| SNACK Tag or Reserved                                         |
     +---------------+---------------+---------------+---------------+
   24| StatSN                                                        |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36| ExpDataSN or Reserved                                         |
     +---------------+---------------+---------------+---------------+
   40| Bidirectional Read Residual Count or Reserved                 |
     +---------------+---------------+---------------+---------------+
   44| Residual Count or Reserved                                    |
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / Data Segment (Optional)                                       /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+


10.4.1.  Flags (byte 1)

     bit 1-2 Reserved.

     bit 3 - (o) set for Bidirectional Read Residual Overflow.  In this
       case, the Bidirectional Read Residual Count indicates the number
       of bytes that were not transferred to the initiator because the
       initiator's Expected Bidirectional Read Data Transfer Length was
       not sufficient.

     bit 4 - (u) set for Bidirectional Read Residual Underflow.  In this
       case, the Bidirectional Read Residual Count indicates the number
       of bytes that were not transferred to the initiator out of the
       number of bytes expected to be transferred.

     bit 5 - (O) set for Residual Overflow.  In this case, the Residual
       Count indicates the number of bytes that were not transferred
       because the initiator's Expected Data Transfer Length was not
       sufficient.  For a bidirectional operation, the Residual Count
       contains the residual for the write operation.

     bit 6 - (U) set for Residual Underflow.  In this case, the Residual
       Count indicates the number of bytes that were not transferred out
       of the number of bytes that were expected to be transferred.  For
       a bidirectional operation, the Residual Count contains the
       residual for the write operation.

     bit 7 - (0) Reserved.

   Bits O and U and bits o and u are mutually exclusive (i.e., having
   both o and u or O and U set to 1 is a protocol error).  For a
   response other than "Command Completed at Target", bits 3-6 MUST be
   0.

10.4.2.  Status

   The Status field is used to report the SCSI status of the command (as
   specified in [SAM2]) and is only valid if the Response Code is
   Command Completed at target.

   Some of the status codes defined in [SAM2] are:

     0x00 GOOD
     0x02 CHECK CONDITION
     0x08 BUSY
     0x18 RESERVATION CONFLICT
     0x28 TASK SET FULL
     0x30 ACA ACTIVE
     0x40 TASK ABORTED

   See [SAM2] for the complete list and definitions.

   If a SCSI device error is detected while data from the initiator is
   still expected (the command PDU did not contain all the data and the
   target has not received a Data PDU with the final bit Set), the
   target MUST wait until it receives a Data PDU with the F bit set in
   the last expected sequence before sending the Response PDU.

10.4.3.  Response

   This field contains the iSCSI service response.

   iSCSI service response codes defined in this specification are:

     0x00 - Command Completed at Target
     0x01 - Target Failure
     0x80-0xff - Vendor specific

   All other response codes are reserved.

   The Response is used to report a Service Response.  The mapping of
   the response code into a SCSI service response code value, if needed,
   is outside the scope of this document.  However, in symbolic terms
   response value 0x00 maps to the SCSI service response (see [SAM2] and
   [SPC3]) of TASK COMPLETE or LINKED COMMAND COMPLETE.  All other
   Response values map to the SCSI service response of SERVICE DELIVERY
   OR TARGET FAILURE.

   If a PDU that includes SCSI status (Response PDU or Data-In PDU
   including status) does not arrive before the session is terminated,
   the SCSI service response is SERVICE DELIVERY OR TARGET FAILURE.

   A non-zero Response field indicates a failure to execute the command
   in which case the Status and Flag fields are undefined.

10.4.4.  SNACK Tag

   This field contains a copy of the SNACK Tag of the last SNACK Tag
   accepted by the target on the same connection and for the command for
   which the response is issued.  Otherwise it is reserved and should be
   set to 0.

   After issuing a R-Data SNACK the initiator must discard any SCSI
   status unless contained in an SCSI Response PDU carrying the same
   SNACK Tag as the last issued R-Data SNACK for the SCSI command on the
   current connection.

   For a detailed discussion on R-Data SNACK see Section 10.16 SNACK
   Request.

10.4.5.  Residual Count

   The Residual Count field MUST be valid in the case where either the U
   bit or the O bit is set.  If neither bit is set, the Residual Count
   field is reserved.  Targets may set the residual count and initiators
   may use it when the response code is "completed at target" (even if
   the status returned is not GOOD).  If the O bit is set, the Residual
   Count indicates the number of bytes that were not transferred because
   the initiator's Expected Data Transfer Length was not sufficient.  If
   the U bit is set, the Residual Count indicates the number of bytes
   that were not transferred out of the number of bytes expected to be
   transferred.

10.4.6.  Bidirectional Read Residual Count

   The Bidirectional Read Residual Count field MUST be valid in the case
   where either the u bit or the o bit is set.  If neither bit is set,
   the Bidirectional Read Residual Count field is reserved.  Targets may
   set the Bidirectional Read Residual Count and initiators may use it
   when the response code is "completed at target".  If the o bit is
   set, the Bidirectional Read Residual Count indicates the number of
   bytes that were not transferred to the initiator because the
   initiator's Expected Bidirectional Read Transfer Length was not
   sufficient.  If the u bit is set, the Bidirectional Read Residual
   Count indicates the number of bytes that were not transferred to the
   initiator out of the number of bytes expected to be transferred.

10.4.7.  Data Segment - Sense and Response Data Segment

   iSCSI targets MUST support and enable autosense.  If Status is CHECK
   CONDITION (0x02), then the Data Segment MUST contain sense data for
   the failed command.

   For some iSCSI responses, the response data segment MAY contain some
   response related information, (e.g., for a target failure, it may
   contain a vendor specific detailed description of the failure).

   If the DataSegmentLength is not 0, the format of the Data Segment is
   as follows:

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|SenseLength                    | Sense Data                    |
     +---------------+---------------+---------------+---------------+
    x/ Sense Data                                                    /
     +---------------+---------------+---------------+---------------+
    y/ Response Data                                                 /
     /                                                               /
     +---------------+---------------+---------------+---------------+
    z|

10.4.7.1.  SenseLength

   Length of Sense Data.

10.4.7.2.  Sense Data

   The Sense Data contains detailed information about a check condition
   and [SPC3] specifies the format and content of the Sense Data.

   Certain iSCSI conditions result in the command being terminated at
   the target (response Command Completed at Target) with a SCSI Check
   Condition Status as outlined in the next table:

   +--------------------------+----------+---------------------------+
   | iSCSI Condition          |Sense     | Additional Sense Code &   |
   |                          |Key       | Qualifier                 |
   +--------------------------+----------+---------------------------+
   | Unexpected unsolicited   |Aborted   | ASC = 0x0c ASCQ = 0x0c    |
   | data                     |Command-0B| Write Error               |
   +--------------------------+----------+---------------------------+
   | Incorrect amount of data |Aborted   | ASC = 0x0c ASCQ = 0x0d    |
   |                          |Command-0B| Write Error               |
   +--------------------------+----------+---------------------------+
   | Protocol Service CRC     |Aborted   | ASC = 0x47 ASCQ = 0x05    |
   | error                    |Command-0B| CRC Error Detected        |
   +--------------------------+----------+---------------------------+
   | SNACK rejected           |Aborted   | ASC = 0x11 ASCQ = 0x13    |
   |                          |Command-0B| Read Error                |
   +--------------------------+----------+---------------------------+

   The target reports the "Incorrect amount of data" condition if during
   data output the total data length to output is greater than
   FirstBurstLength and the initiator sent unsolicited non-immediate
   data but the total amount of unsolicited data is different than
   FirstBurstLength.  The target reports the same error when the amount
   of data sent as a reply to an R2T does not match the amount
   requested.

10.4.8.  ExpDataSN

   The number of R2T and Data-In (read) PDUs the target has sent for the
   command.

   This field MUST be 0 if the response code is not Command Completed at
   Target or the target sent no Data-In PDUs for the command.

10.4.9.  StatSN - Status Sequence Number

   StatSN is a Sequence Number that the target iSCSI layer generates per
   connection and that in turn, enables the initiator to acknowledge
   status reception.  StatSN is incremented by 1 for every
   response/status sent on a connection except for responses sent as a
   result of a retry or SNACK.  In the case of responses sent due to a
   retransmission request, the StatSN MUST be the same as the first time
   the PDU was sent unless the connection has since been restarted.

10.4.10.  ExpCmdSN - Next Expected CmdSN from this Initiator

   ExpCmdSN is a Sequence Number that the target iSCSI returns to the
   initiator to acknowledge command reception.  It is used to update a
   local variable with the same name.  An ExpCmdSN equal to MaxCmdSN+1
   indicates that the target cannot accept new commands.

10.4.11.  MaxCmdSN - Maximum CmdSN from this Initiator

   MaxCmdSN is a Sequence Number that the target iSCSI returns to the
   initiator to indicate the maximum CmdSN the initiator can send.  It
   is used to update a local variable with the same name.  If MaxCmdSN
   is equal to ExpCmdSN-1, this indicates to the initiator that the
   target cannot receive any additional commands.  When MaxCmdSN changes
   at the target while the target has no pending PDUs to convey this
   information to the initiator, it MUST generate a NOP-IN to carry the
   new MaxCmdSN.



   
</pre>
 * 
 *
 */
public class SCSIResponse {
	public enum Status {

 /**
<pre>
0x00 - GOOD
</pre>
 */
		GOOD((byte) 0x00),

 /**
<pre>
0x02 - CHECK CONDITION
</pre>
 */
		CheckCondition((byte) 0x02),
	
/**
<pre>
0x08 - BUSY
</pre>
 */
		BUSY((byte) 0x08),

/**
<pre>
0x18 - RESERVATION CONFLICT
</pre>
 */
		ReservationConflict((byte) 0x18),

/**
<pre>
0x28 - TASK SET FULL
</pre>
 */
		TaskSetFull((byte) 0x28),

/**
<pre>
0x30 - ACA ACTIVE
</pre>
 */
		AcaActive((byte) 0x30),

/**
<pre>
0x40 - TASK ABORTED
</pre>
 */
		TaskAborted((byte) 0x40);
		
	    private final byte value;
	    private static Map<Byte , Status> mapping;
	    static {
	    	Status.mapping = new HashMap<Byte , Status>();
	        for (Status s : values()) {
	        	Status.mapping.put(s.value, s);
	        }
	    }
	    private Status (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Status valueOf (final byte value) {
	        return Status.mapping.get(value);
	    }
	}

	public enum Response {

 /**
<pre>
0x00 - Command Completed at Target
</pre>
 */
		CommandComplete((byte) 0x00),

 /**
<pre>
0x01 - Target Failure
</pre>
 */
		TargetFailure((byte) 0x01),
	
/**
<pre>
0x80-0xff - Vendor specific
</pre>
 */
		VendorSpecific((byte) 0x80);

		
	    private final byte value;
	    private static Map<Byte , Response> mapping;
	    static {
	    	Response.mapping = new HashMap<Byte , Response>();
	        for (Response s : values()) {
	        	Response.mapping.put(s.value, s);
	        }
	    }
	    private Response (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Response valueOf (final byte value) {
	        return Response.mapping.get(value);
	    }
	}
	
	private byte opcode = 0x21;
	private boolean isFinal = true;
	private boolean isReadResidualOverflow;
	private boolean isReadResidualUnderflow;
	private boolean isResidualOverflow;
	private boolean isResidualUnderflow;
	private byte response;
	private byte status;
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] SNACKTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] ExpDataSN = new byte[4];
	private byte[] BReadResidualCount = new byte[4];
	private byte[] ResidualCount = new byte[4];
	public SCSIResponse(){}
	public SCSIResponse(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isReadResidualOverflow = (ByteUtil.getBit(BHS[1], 3)==1);
		isReadResidualUnderflow = (ByteUtil.getBit(BHS[1], 4)==1);
		isResidualOverflow = (ByteUtil.getBit(BHS[1], 5)==1);
		isResidualUnderflow = (ByteUtil.getBit(BHS[1], 6)==1);
		response = BHS[2];
		status = BHS[3];
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, SNACKTag, 0, SNACKTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 36, ExpDataSN, 0, ExpDataSN.length);
		System.arraycopy(BHS, 40, BReadResidualCount, 0, BReadResidualCount.length);
		System.arraycopy(BHS, 44, ResidualCount, 0, ResidualCount.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.SCSI_RESPONSE;
	}
	public boolean getReadResidualOverflow() {
		return isReadResidualOverflow;
	}
	public void setReadResidualOverflow(boolean isReadResidualOverflow) {
		this.isReadResidualOverflow = isReadResidualOverflow;
	}
	public boolean getReadResidualUnderflow() {
		return isReadResidualUnderflow;
	}
	public void setReadResidualUnderflow(boolean isReadResidualUnderflow) {
		this.isReadResidualUnderflow = isReadResidualUnderflow;
	}
	public boolean getResidualOverflow() {
		return isResidualOverflow;
	}
	public void setResidualOverflow(boolean isResidualOverflow) {
		this.isResidualOverflow = isResidualOverflow;
	}
	public boolean getResidualUnderflow() {
		return isResidualUnderflow;
	}
	public void setResidualUnderflow(boolean isResidualUnderflow) {
		this.isResidualUnderflow = isResidualUnderflow;
	}
	public Response getResponse() {
		return Response.valueOf(this.response);
	}
	public void setResponse(Response response) {
		this.response = response.value;
	}
	public Status getStatus() {
		return Status.valueOf(this.status);
	}
	public void setStatus(Status status) {
		this.status = status.value;
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
	public byte[] getSNACKTag() {
		return SNACKTag;
	}
	public void setSNACKTag(byte[] SNACKTag) {
		System.arraycopy(SNACKTag, 0, this.SNACKTag, 0, Math.min(SNACKTag.length, this.SNACKTag.length));
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
	public int getExpDataSN() {
		return ByteUtil.byteArrayToInt(ExpDataSN);
	}
	public void setExpDataSN(int expDataSN) {
		ExpDataSN = ByteUtil.intToByteArray(expDataSN);
	}
	public int getBReadResidualCount() {
		return ByteUtil.byteArrayToInt(BReadResidualCount);
	}
	public void setBReadResidualCount(int bReadResidualCount) {
		BReadResidualCount = ByteUtil.intToByteArray(bReadResidualCount);
	}
	public int getResidualCount() {
		return ByteUtil.byteArrayToInt(ResidualCount);
	}
	public void setResidualCount(int residualCount) {
		ResidualCount = ByteUtil.intToByteArray(residualCount);
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" isReadResidualOverflow : "+isReadResidualOverflow);
		build.append(System.getProperty("line.separator")+" isReadResidualUnderflow : "+isReadResidualUnderflow);
		build.append(System.getProperty("line.separator")+" isResidualOverflow : "+isResidualOverflow);
		build.append(System.getProperty("line.separator")+" isResidualUnderflow : "+isResidualUnderflow);
		build.append(System.getProperty("line.separator")+" Response : "+Response.valueOf(this.response));
		build.append(System.getProperty("line.separator")+" Status : "+Status.valueOf(this.status));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" SNACKTag : "+ByteUtil.byteArrayToInt(SNACKTag));
		build.append(System.getProperty("line.separator")+" StatSN : 0x"+ByteUtil.toHex(StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : 0x"+ByteUtil.toHex(ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : 0x"+ByteUtil.toHex(MaxCmdSN));
		build.append(System.getProperty("line.separator")+" ExpDataSN : 0x"+ByteUtil.toHex(ExpDataSN));
		build.append(System.getProperty("line.separator")+" Bidirectional Read Residual Count : 0x"+ByteUtil.toHex(this.BReadResidualCount));
		build.append(System.getProperty("line.separator")+" Residual Count  : 0x"+ByteUtil.toHex(this.ResidualCount));
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		SCSIResponse original = new SCSIResponse();
		original.setReadResidualOverflow(false);
		original.setReadResidualUnderflow(true);
		original.setResidualOverflow(false);
		original.setResidualUnderflow(true);
		original.setResponse(Response.TargetFailure);
		original.setStatus(Status.CheckCondition);
		original.setInitiatorTaskTag(3);
		original.setSNACKTag(new byte[]{0x04,0,0});
		original.setStatSN(5);
		original.setExpCmdSN(6);
		original.setMaxCmdSN(7);
		original.setExpDataSN(8);
		original.setBReadResidualCount(9);
		original.setResidualCount(10);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		SCSIResponse after = new SCSIResponse(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x21); //Opcode
			byte b = (byte)0x80;
			b = (byte)(this.isReadResidualOverflow?(b|0x10):b);
			b = (byte)(this.isReadResidualUnderflow?(b|0x8):b);
			b = (byte)(this.isResidualOverflow?(b|0x4):b);
			b = (byte)(this.isResidualUnderflow?(b|0x2):b);
			dos.writeByte(b);
			dos.writeByte(this.response);
			dos.writeByte(this.status);
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.InitiatorTaskTag);
			dos.write(this.SNACKTag);
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.write(this.ExpDataSN);
			dos.write(this.BReadResidualCount);
			dos.write(this.ResidualCount);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	 
}
