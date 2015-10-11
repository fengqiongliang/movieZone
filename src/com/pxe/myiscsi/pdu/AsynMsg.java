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

10.9.  Asynchronous Message

   An Asynchronous Message may be sent from the target to the initiator
   without correspondence to a particular command.  The target specifies
   the reason for the event and sense data.

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x32      |1| Reserved                                    |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN or Reserved                                               |
     +                                                               +
   12|                                                               |
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
   36| AsyncEvent    | AsyncVCode    | Parameter1 or Reserved        |
     +---------------+---------------+---------------+---------------+
   40| Parameter2 or Reserved        | Parameter3 or Reserved        |
     +---------------+---------------+---------------+---------------+
   44| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / DataSegment - Sense Data and iSCSI Event Data                 /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   Some Asynchronous Messages are strictly related to iSCSI while others
   are related to SCSI [SAM2].

   StatSN counts this PDU as an acknowledgeable event (StatSN is
   advanced), which allows for initiator and target state
   synchronization.


10.9.1.  AsyncEvent

   The codes used for iSCSI Asynchronous Messages (events) are:

      0 - a SCSI Asynchronous Event is reported in the sense data.
          Sense Data that accompanies the report, in the data segment,
          identifies the condition.  The sending of a SCSI Event
          (Asynchronous Event Reporting in SCSI terminology) is
          dependent on the target support for SCSI asynchronous event
          reporting (see [SAM2]) as indicated in the standard INQUIRY
          data (see [SPC3]).  Its use may be enabled by parameters in
          the SCSI Control mode page (see [SPC3]).

      1 - target requests Logout.  This Async Message MUST be sent on
          the same connection as the one requesting to be logged out.
          The initiator MUST honor this request by issuing a Logout as
          early as possible, but no later than Parameter3 seconds.
          Initiator MUST send a Logout with a reason code of "Close the
          connection" OR "Close the session" to close all the
          connections.  Once this message is received, the initiator
          SHOULD NOT issue new iSCSI commands on the connection to be
          logged out.  The target MAY reject any new I/O requests that
          it receives after this Message with the reason code "Waiting
          for Logout".  If the initiator does not Logout in Parameter3
          seconds, the target should send an Async PDU with iSCSI event
          code "Dropped the connection" if possible, or simply terminate
          the transport connection.  Parameter1 and Parameter2 are
          reserved.

      2 - target indicates it will drop the connection.  The Parameter1
          field indicates the CID of the connection that is going to be
          dropped.

          The Parameter2 field (Time2Wait) indicates, in seconds, the
          minimum time to wait before attempting to reconnect or
          reassign.

          The Parameter3 field (Time2Retain) indicates the maximum time
          allowed to reassign commands after the initial wait (in
          Parameter2).

          If the initiator does not attempt to reconnect and/or reassign
          the outstanding commands within the time specified by
          Parameter3, or if Parameter3 is 0, the target will terminate
          all outstanding commands on this connection.  In this case, no
          other responses should be expected from the target for the
          outstanding commands on this connection.

          A value of 0 for Parameter2 indicates that reconnect can be
          attempted immediately.

      3 - target indicates it will drop all the connections of this
          session.

          Parameter1 field is reserved.

          The Parameter2 field (Time2Wait) indicates, in seconds, the
          minimum time to wait before attempting to reconnect.  The
          Parameter3 field (Time2Retain) indicates the maximum time
          allowed to reassign commands after the initial wait (in
          Parameter2).

          If the initiator does not attempt to reconnect and/or reassign
          the outstanding commands within the time specified by
          Parameter3, or if Parameter3 is 0, the session is terminated.

          In this case, the target will terminate all outstanding
          commands in this session; no other responses should be
          expected from the target for the outstanding commands in this
          session.  A value of 0 for Parameter2 indicates that reconnect
          can be attempted immediately.

      4 - target requests parameter negotiation on this connection.  The
          initiator MUST honor this request by issuing a Text Request
          (that can be empty) on the same connection as early as
          possible, but no later than Parameter3 seconds, unless a Text
          Request is already pending on the connection, or by issuing a
          Logout Request.  If the initiator does not issue a Text
          Request the target may reissue the Asynchronous Message
          requesting parameter negotiation.

      255 - vendor specific iSCSI Event.  The AsyncVCode details the
            vendor code, and data MAY accompany the report.

   All other event codes are reserved.

10.9.2.  AsyncVCode

   AsyncVCode is a vendor specific detail code that is only valid if the
   AsyncEvent field indicates a vendor specific event.  Otherwise, it is
   reserved.

10.9.3.  LUN

   The LUN field MUST be valid if AsyncEvent is 0.  Otherwise, this
   field is reserved.

10.9.4.  Sense Data and iSCSI Event Data

   For a SCSI event, this data accompanies the report in the data
   segment and identifies the condition.

   For an iSCSI event, additional vendor-unique data MAY accompany the
   Async event.  Initiators MAY ignore the data when not understood
   while processing the rest of the PDU.

   If the DataSegmentLength is not 0, the format of the DataSegment is
   as follows:

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|SenseLength                    | Sense Data                    |
     +---------------+---------------+---------------+---------------+
    x/ Sense Data                                                    /
     +---------------+---------------+---------------+---------------+
    y/ iSCSI Event Data                                              /
     /                                                               /
     +---------------+---------------+---------------+---------------+
    z|

10.9.4.1.  SenseLength

   This is the length of Sense Data.  When the Sense Data field is empty
   (e.g., the event is not a SCSI event) SenseLength is 0.






   
   
</pre>
 * 
 *
 */
public class AsynMsg {
	public enum AsyncEvent {

 /**
<pre>
0 - a SCSI Asynchronous Event is reported in the sense data.
    Sense Data that accompanies the report, in the data segment,
    identifies the condition.  The sending of a SCSI Event
    (Asynchronous Event Reporting in SCSI terminology) is
    dependent on the target support for SCSI asynchronous event
    reporting (see [SAM2]) as indicated in the standard INQUIRY
    data (see [SPC3]).  Its use may be enabled by parameters in
    the SCSI Control mode page (see [SPC3]).
</pre>
 */
		SCSIAsynEvent((byte) 0x00),

 /**
<pre>
1 - target requests Logout.  This Async Message MUST be sent on
    the same connection as the one requesting to be logged out.
    The initiator MUST honor this request by issuing a Logout as
    early as possible, but no later than Parameter3 seconds.
    Initiator MUST send a Logout with a reason code of "Close the
    connection" OR "Close the session" to close all the
    connections.  Once this message is received, the initiator
    SHOULD NOT issue new iSCSI commands on the connection to be
    logged out.  The target MAY reject any new I/O requests that
    it receives after this Message with the reason code "Waiting
    for Logout".  If the initiator does not Logout in Parameter3
    seconds, the target should send an Async PDU with iSCSI event
    code "Dropped the connection" if possible, or simply terminate
    the transport connection.  Parameter1 and Parameter2 are
    reserved.
</pre>
 */
	TargetReqLogout((byte) 0x01),
	
/**
<pre>
2 - target indicates it will drop the connection.  The Parameter1
    field indicates the CID of the connection that is going to be
    dropped.

    The Parameter2 field (Time2Wait) indicates, in seconds, the
    minimum time to wait before attempting to reconnect or
    reassign.

    The Parameter3 field (Time2Retain) indicates the maximum time
    allowed to reassign commands after the initial wait (in
    Parameter2).

    If the initiator does not attempt to reconnect and/or reassign
    the outstanding commands within the time specified by
    Parameter3, or if Parameter3 is 0, the target will terminate
    all outstanding commands on this connection.  In this case, no
    other responses should be expected from the target for the
    outstanding commands on this connection.

    A value of 0 for Parameter2 indicates that reconnect can be
    attempted immediately.

</pre>
 */
	TargetDropConn((byte) 0x02),

/**
<pre>
3 - target indicates it will drop all the connections of this
    session.

    Parameter1 field is reserved.

    The Parameter2 field (Time2Wait) indicates, in seconds, the
    minimum time to wait before attempting to reconnect.  The
    Parameter3 field (Time2Retain) indicates the maximum time
    allowed to reassign commands after the initial wait (in
    Parameter2).

    If the initiator does not attempt to reconnect and/or reassign
    the outstanding commands within the time specified by
    Parameter3, or if Parameter3 is 0, the session is terminated.

    In this case, the target will terminate all outstanding
    commands in this session; no other responses should be
    expected from the target for the outstanding commands in this
    session.  A value of 0 for Parameter2 indicates that reconnect
    can be attempted immediately.

</pre>
 */
		TargetDropAllConn((byte) 0x03),

/**
<pre>
4 - target requests parameter negotiation on this connection.  The
    initiator MUST honor this request by issuing a Text Request
    (that can be empty) on the same connection as early as
    possible, but no later than Parameter3 seconds, unless a Text
    Request is already pending on the connection, or by issuing a
    Logout Request.  If the initiator does not issue a Text
    Request the target may reissue the Asynchronous Message
    requesting parameter negotiation.

</pre>
 */
		TargetReqParamNeg((byte) 0x04),

/**
<pre>
255 - vendor specific iSCSI Event.  
      The AsyncVCode details the vendor code, 
      and data MAY accompany the report.

</pre>
 */
		VendorEvent((byte) 0xff);
		
		
	    private final byte value;
	    private static Map<Byte , AsyncEvent> mapping;
	    static {
	        AsyncEvent.mapping = new HashMap<Byte , AsyncEvent>();
	        for (AsyncEvent s : values()) {
	            AsyncEvent.mapping.put(s.value, s);
	        }
	    }
	    private AsyncEvent (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final AsyncEvent valueOf (final byte value) {
	        return AsyncEvent.mapping.get(value);
	    }
	}
	
	private byte op = 0x32;
	private boolean isFinal = true;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN = new byte[8];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte asyncEvent;
	private byte AsyncVCode;
	private byte[] Parameter1 = new byte[2];
	private byte[] Parameter2 = new byte[2];
	private byte[] Parameter3 = new byte[2];
	private byte[] SenseData = new byte[0];
	public AsynMsg(){}
	public AsynMsg(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		asyncEvent = BHS[36];
		AsyncVCode = BHS[37];
		System.arraycopy(BHS, 38, Parameter1, 0, Parameter1.length);
		System.arraycopy(BHS, 40, Parameter2, 0, Parameter2.length);
		System.arraycopy(BHS, 42, Parameter3, 0, Parameter3.length);
		SenseData = DataSegment;
	}
	
	public Opcode getOpcode() {
		return Opcode.ASYNC_MESSAGE;
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
	public AsyncEvent getAsyncEvent() {
		return AsyncEvent.valueOf(this.asyncEvent);
	}
	public void setAsyncEvent(AsyncEvent asyncEvent) {
		this.asyncEvent = asyncEvent.value;
	}
	public byte getAsyncVCode() {
		return AsyncVCode;
	}
	public void setAsyncVCode(byte asyncVCode) {
		AsyncVCode = asyncVCode;
	}
	public byte[] getParameter1() {
		return Parameter1;
	}
	public void setParameter1(byte[] Parameter1) {
		System.arraycopy(Parameter1, 0, this.Parameter1, 0, Math.min(Parameter1.length, this.Parameter1.length));
	}
	public byte[] getParameter2() {
		return Parameter2;
	}
	public void setParameter2(byte[] Parameter2) {
		System.arraycopy(Parameter2, 0, this.Parameter2, 0, Math.min(Parameter2.length, this.Parameter2.length));
	}
	public byte[] getParameter3() {
		return Parameter3;
	}
	public void setParameter3(byte[] Parameter3) {
		System.arraycopy(Parameter3, 0, this.Parameter3, 0, Math.min(Parameter3.length, this.Parameter3.length));
	}
	public byte[] getSenseData() {
		return SenseData;
	}
	public void setSenseData(byte[] senseData) {
		SenseData = senseData;
		DataSegmentLength[0] = (byte) ((senseData.length & 0x00ff0000) >> 16);
		DataSegmentLength[1] = (byte) ((senseData.length & 0x0000ff00) >> 8);
		DataSegmentLength[2] = (byte) ((senseData.length & 0x000000ff) >> 0);
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(op));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		build.append(System.getProperty("line.separator")+" AsyncEvent : "+AsyncEvent.valueOf(this.asyncEvent));
		build.append(System.getProperty("line.separator")+" AsyncVCode : "+AsyncVCode);
		build.append(System.getProperty("line.separator")+" Parameter1 : "+ByteUtil.toHex(this.Parameter1));
		build.append(System.getProperty("line.separator")+" Parameter2 : "+ByteUtil.toHex(this.Parameter2));
		build.append(System.getProperty("line.separator")+" Parameter3 : "+ByteUtil.toHex(this.Parameter3));
		build.append(System.getProperty("line.separator")+" Sense Data  : "+ByteUtil.toHex(this.SenseData));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x32); //Opcode
			dos.writeByte(0x80);
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.writeInt(-1);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.writeByte(this.asyncEvent);
			dos.writeByte(this.AsyncVCode);
			dos.write(this.Parameter1);
			dos.write(this.Parameter2);
			dos.write(this.Parameter3);
			dos.write(this.SenseData);
			int count = this.SenseData.length % 4 == 0?0:(4-this.SenseData.length % 4);
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
		AsynMsg original = new AsynMsg();
		original.setLUN(new byte[]{1});
		original.setStatSN(1);
		original.setExpCmdSN(2);
		original.setMaxCmdSN(3);
		original.setAsyncEvent(AsyncEvent.TargetDropAllConn);
		original.setAsyncVCode((byte)5);
		original.setParameter1(new byte[]{(byte)0xf0});
		original.setParameter2(new byte[]{(byte)0x1f});
		original.setParameter3(new byte[]{(byte)0x0e});
		original.setSenseData("你想干什么？？？".getBytes());
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		AsynMsg after = new AsynMsg(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
