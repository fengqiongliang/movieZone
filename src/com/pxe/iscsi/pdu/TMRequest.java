package com.pxe.iscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;



import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.Opcode;
import com.pxe.iscsi.ENUM.SessionType;

/**
<pre>


10.5.  Task Management Function Request

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| 0x02      |1| Function    | Reserved                      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| Logical Unit Number (LUN) or Reserved                         |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| Referenced Task Tag or 0xffffffff                             |
     +---------------+---------------+---------------+---------------+
   24| CmdSN                                                         |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN                                                     |
     +---------------+---------------+---------------+---------------+
   32| RefCmdSN or Reserved                                          |
     +---------------+---------------+---------------+---------------+
   36| ExpDataSN or Reserved                                         |
     +---------------+---------------+---------------+---------------+
   40/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+

10.5.1.  Function

   The Task Management functions provide an initiator with a way to
   explicitly control the execution of one or more Tasks (SCSI and iSCSI
   tasks).  The Task Management function codes are listed below.  For a
   more detailed description of SCSI task management, see [SAM2].

   1 -  ABORT TASK - aborts the task identified by the Referenced Task
        Tag field.

   2 -  ABORT TASK SET - aborts all Tasks issued via this session on the
        logical unit.

   3 -  CLEAR ACA - clears the Auto Contingent Allegiance condition.

   4 -  CLEAR TASK SET - aborts all Tasks in the appropriate task set as
        defined by the TST field in the Control mode page (see [SPC3]).

   5 -  LOGICAL UNIT RESET

   6 -  TARGET WARM RESET

   7 -  TARGET COLD RESET

   8 -  TASK REASSIGN - reassigns connection allegiance for the task
        identified by the Referenced Task Tag field to this connection,
        thus resuming the iSCSI exchanges for the task.

   For all these functions, the Task Management function response MUST
   be returned as detailed in Section 10.6 Task Management Function
   Response.  All these functions apply to the referenced tasks
   regardless of whether they are proper SCSI tasks or tagged iSCSI
   operations.  Task management requests must act on all the commands
   from the same session having a CmdSN lower than the task management
   CmdSN.  LOGICAL UNIT RESET, TARGET WARM RESET and TARGET COLD RESET
   may affect commands from other sessions or commands from the same
   session with CmdSN equal or exceeding CmdSN.

   If the task management request is marked for immediate delivery, it
   must be considered immediately for execution, but the operations
   involved (all or part of them) may be postponed to allow the target
   to receive all relevant tasks.  According to [SAM2], for all the
   tasks covered by the Task Management response (i.e., with CmdSN lower
   than the task management command CmdSN) but except the Task
   Management response to a TASK REASSIGN, additional responses MUST NOT
   be delivered to the SCSI layer after the Task Management response.
   The iSCSI initiator MAY deliver to the SCSI layer all responses
   received before the Task Management response (i.e., it is a matter of
   implementation if the SCSI responses, received before the Task
   Management response but after the task management request was issued,
   are delivered to the SCSI layer by the iSCSI layer in the initiator).
   The iSCSI target MUST ensure that no responses for the tasks covered
   by a task management function are delivered to the iSCSI initiator
   after the Task Management response except for a task covered by a
   TASK REASSIGN.

   For ABORT TASK SET and CLEAR TASK SET, the issuing initiator MUST
   continue to respond to all valid target transfer tags (received via
   R2T, Text Response, NOP-In, or SCSI Data-In PDUs) related to the
   affected task set, even after issuing the task management request.
   The issuing initiator SHOULD however terminate (i.e., by setting the
   F-bit to 1) these response sequences as quickly as possible.  The
   target on its part MUST wait for responses on all affected target

   transfer tags before acting on either of these two task management
   requests.  In case all or part of the response sequence is not
   received (due to digest errors) for a valid TTT, the target MAY treat
   it as a case of within-command error recovery class (see Section
   6.1.4.1 Recovery Within-command) if it is supporting
   ErrorRecoveryLevel >= 1, or alternatively may drop the connection to
   complete the requested task set function.

   If an ABORT TASK is issued for a task created by an immediate command
   then RefCmdSN MUST be that of the Task Management request itself
   (i.e., CmdSN and RefCmdSN are equal); otherwise RefCmdSN MUST be set
   to the CmdSN of the task to be aborted (lower than CmdSN).

   If the connection is still active (it is not undergoing an implicit
   or explicit logout), ABORT TASK MUST be issued on the same connection
   to which the task to be aborted is allegiant at the time the Task
   Management Request is issued.  If the connection is implicitly or
   explicitly logged out (i.e., no other request will be issued on the
   failing connection and no other response will be received on the
   failing connection), then an ABORT TASK function request may be
   issued on another connection.  This Task Management request will then
   establish a new allegiance for the command to be aborted as well as
   abort it (i.e., the task to be aborted will not have to be retried or
   reassigned, and its status, if issued but not acknowledged, will be
   reissued followed by the Task Management response).

   At the target an ABORT TASK function MUST NOT be executed on a Task
   Management request; such a request MUST result in Task Management
   response of "Function rejected".

   For the LOGICAL UNIT RESET function, the target MUST behave as
   dictated by the Logical Unit Reset function in [SAM2].

   The implementation of the TARGET WARM RESET function and the TARGET
   COLD RESET function is OPTIONAL and when implemented, should act as
   described below.  The TARGET WARM RESET is also subject to SCSI
   access controls on the requesting initiator as defined in [SPC3].
   When authorization fails at the target, the appropriate response as
   described in Section 10.6 Task Management Function Response MUST be
   returned by the target.  The TARGET COLD RESET function is not
   subject to SCSI access controls, but its execution privileges may be
   managed by iSCSI mechanisms such as login authentication.

   When executing the TARGET WARM RESET and TARGET COLD RESET functions,
   the target cancels all pending operations on all Logical Units known
   by the issuing initiator.  Both functions are equivalent to the
   Target Reset function specified by [SAM2].  They can affect many
   other initiators logged in with the servicing SCSI target port.

   The target MUST treat the TARGET COLD RESET function additionally as
   a power on event, thus terminating all of its TCP connections to all
   initiators (all sessions are terminated).  For this reason, the
   Service Response (defined by [SAM2]) for this SCSI task management
   function may not be reliably delivered to the issuing initiator port.

   For the TASK REASSIGN function, the target should reassign the
   connection allegiance to this new connection (and thus resume iSCSI
   exchanges for the task).  TASK REASSIGN MUST ONLY be received by the
   target after the connection on which the command was previously
   executing has been successfully logged-out.  The Task Management
   response MUST be issued before the reassignment becomes effective.
   For additional usage semantics see Section 6.2 Retry and Reassign in
   Recovery.

   At the target a TASK REASSIGN function request MUST NOT be executed
   to reassign the connection allegiance of a Task Management function
   request, an active text negotiation task, or a Logout task; such a
   request MUST result in Task Management response of "Function
   rejected".

   TASK REASSIGN MUST be issued as an immediate command.

10.5.2.  TotalAHSLength and DataSegmentLength

   For this PDU TotalAHSLength and DataSegmentLength MUST be 0.

10.5.3.  LUN

   This field is required for functions that address a specific LU
   (ABORT TASK, CLEAR TASK SET, ABORT TASK SET, CLEAR ACA, LOGICAL UNIT
   RESET) and is reserved in all others.

10.5.4.  Referenced Task Tag

   The Initiator Task Tag of the task to be aborted for the ABORT TASK
   function or reassigned for the TASK REASSIGN function.  For all the
   other functions this field MUST be set to the reserved value
   0xffffffff.

10.5.5.  RefCmdSN

   If an ABORT TASK is issued for a task created by an immediate command
   then RefCmdSN MUST be that of the Task Management request itself
   (i.e., CmdSN and RefCmdSN are equal).


   For an ABORT TASK of a task created by non-immediate command RefCmdSN
   MUST be set to the CmdSN of the task identified by the Referenced
   Task Tag field.  Targets must use this field as described in section
   10.6.1 when the task identified by the Referenced Task Tag field is
   not with the target.

   Otherwise, this field is reserved.

10.5.6.  ExpDataSN

   For recovery purposes, the iSCSI target and initiator maintain a data
   acknowledgement reference number - the first input DataSN number
   unacknowledged by the initiator.  When issuing a new command, this
   number is set to 0.  If the function is TASK REASSIGN, which
   establishes a new connection allegiance for a previously issued Read
   or Bidirectional command, ExpDataSN will contain  an updated data
   acknowledgement reference number or the value 0; the latter
   indicating that the data acknowledgement reference number is
   unchanged.  The initiator MUST discard any data PDUs from the
   previous execution that it did not acknowledge and the target MUST
   transmit all Data-In PDUs (if any) starting with the data
   acknowledgement reference number.  The number of retransmitted PDUs
   may or may not be the same as the original transmission depending on
   if there was a change in MaxRecvDataSegmentLength in the
   reassignment.  The target MAY also send no more Data-In PDUs if all
   data has been acknowledged.

   The value of ExpDataSN  MUST be 0 or higher than the DataSN of the
   last acknowledged Data-In PDU, but not larger than DataSN+1 of the
   last Data-In PDU sent by the target.  Any other value MUST be ignored
   by the target.

   For other functions this field is reserved.




   
</pre>
 * 
 *
 */
public class TMRequest {
	public enum Function {

 /**
<pre>
1 - ABORT TASK.  aborts the task identified by the Referenced Task
    Tag field.
</pre>
 */
		AbortTask((byte) 0x01),
	
/**
<pre>
2 - ABORT TASK SET. aborts all Tasks issued via this session on the
    logical unit.
</pre>
 */
		AbortTaskSet((byte) 0x02),

/**
<pre>
3 - CLEAR ACA. clears the Auto Contingent Allegiance condition.
</pre>
 */
		ClearAca((byte) 0x03),

/**
<pre>
4 - CLEAR TASK SET. aborts all Tasks in the appropriate task set as
    defined by the TST field in the Control mode page (see [SPC3]).
</pre>
 */
		ClearTaskSet((byte) 0x04),

/**
<pre>
5 - LOGICAL UNIT RESET
</pre>
 */
		LogicalUnitReset((byte) 0x05),

/**
<pre>
6 - TARGET WARM RESET
</pre>
 */
		TargetWarmReset((byte) 0x06),

/**
<pre>
7 - TARGET COLD RESET
</pre>
 */
		TargetColdReset((byte) 0x07),
		
/**
<pre>
8 - TASK REASSIGN. reassigns connection allegiance for the task
    identified by the Referenced Task Tag field to this connection,
    thus resuming the iSCSI exchanges for the task.
</pre>
 */
		TaskReassign((byte) 0x08);
		
	    private final byte value;
	    private static Map<Byte , Function> mapping;
	    static {
	    	Function.mapping = new HashMap<Byte , Function>();
	        for (Function s : values()) {
	        	Function.mapping.put(s.value, s);
	        }
	    }
	    private Function (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Function valueOf (final byte value) {
	        return Function.mapping.get(value);
	    }
	}
	
	private boolean isImmediate;
	private byte opcode = 0x02;
	private boolean isFinal = true;
	private byte function;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN  = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] RefTaskTag = new byte[]{(byte) 0xff,(byte) 0xff,(byte) 0xff,(byte) 0xff};
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private byte[] RefCmdSN = new byte[4];
	private byte[] ExpDataSN = new byte[4];
	private Map<String,String> parameter = new LinkedHashMap<String,String>();
	private SessionType sessionType = SessionType.Normal;
	public TMRequest(){
		//parameter.put("SessionType", sessionType.toString());
	}
	public TMRequest(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		function = (byte)(BHS[1] & 0x7f);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, RefTaskTag, 0, RefTaskTag.length);
		System.arraycopy(BHS, 24, CmdSN, 0, CmdSN.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
		System.arraycopy(BHS, 32, RefCmdSN, 0, RefCmdSN.length);
		System.arraycopy(BHS, 36, ExpDataSN, 0, ExpDataSN.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.SCSI_TM_REQUEST;
	}
	public boolean getImmediate() {
		return isImmediate;
	}
	public void setImmediate(boolean isImmediate) {
		this.isImmediate = isImmediate;
	}
	public boolean getFinal() {
		return isFinal;
	}
	public Function getFunction() {
		return Function.valueOf(this.function);
	}
	public void setFunction(Function function) {
		this.function = function.value;
	}
	public byte getTotalAHSLength() {
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
	public int getRefTaskTag() {
		return ByteUtil.byteArrayToInt(RefTaskTag);
	}
	public void setRefTaskTag(int refTaskTag) {
		RefTaskTag = ByteUtil.intToByteArray(refTaskTag);
	}
	public int getCmdSN() {
		return ByteUtil.byteArrayToInt(CmdSN);
	}
	public void setCmdSN(int cmdSN) {
		CmdSN = ByteUtil.intToByteArray(cmdSN);
	}
	public int getExpStatSN() {
		return ByteUtil.byteArrayToInt(ExpStatSN);
	}
	public void setExpStatSN(int expStatSN) {
		ExpStatSN = ByteUtil.intToByteArray(expStatSN);
	}
	public int getRefCmdSN() {
		return ByteUtil.byteArrayToInt(RefCmdSN);
	}
	public void setRefCmdSN(int refCmdSN) {
		RefCmdSN = ByteUtil.intToByteArray(refCmdSN);
	}
	public int getExpDataSN() {
		return ByteUtil.byteArrayToInt(ExpDataSN);
	}
	public void setExpDataSN(int expDataSN) {
		ExpDataSN = ByteUtil.intToByteArray(expDataSN);
	}
	
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" Function : "+Function.valueOf(this.function));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" LUN  : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" RefTaskTag : "+ByteUtil.byteArrayToInt(this.RefTaskTag));
		build.append(System.getProperty("line.separator")+" CmdSN : 0x"+ByteUtil.toHex(CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : 0x"+ByteUtil.toHex(ExpStatSN));
		build.append(System.getProperty("line.separator")+" RefCmdSN : 0x"+ByteUtil.toHex(RefCmdSN));
		build.append(System.getProperty("line.separator")+" ExpDataSN : 0x"+ByteUtil.toHex(ExpDataSN));
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		TMRequest original = new TMRequest();
		original.setImmediate(true);
		original.setFunction(Function.LogicalUnitReset);
		original.setLUN(new byte[]{(byte)0xe5,(byte)0xa1});
		original.setInitiatorTaskTag(2);
		original.setRefTaskTag(3);
		original.setCmdSN(4);
		original.setExpStatSN(5);
		original.setRefCmdSN(6);
		original.setExpDataSN(7);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		TMRequest after = new TMRequest(BHS);
		System.out.println(after);
		System.out.println(data.length);
		byte[] b = ByteUtil.intToByteArray(-1);
		System.out.println(ByteUtil.toHex(b));
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(this.isImmediate?0x42:0x02); //Opcode
			dos.writeByte(0x80|this.function);
			dos.write(new byte[]{0,0});//Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.RefTaskTag);
			dos.write(this.CmdSN);
			dos.write(this.ExpStatSN);
			dos.write(this.RefCmdSN);
			dos.write(this.ExpDataSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	 
}
