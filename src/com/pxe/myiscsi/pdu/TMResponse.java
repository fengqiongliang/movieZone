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

10.6.  Task Management Function Response

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x22      |1| Reserved    | Response      | Reserved      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------------------------------------------------------+
    8/ Reserved                                                      /
     /                                                               /
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
   36/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+

   For the functions ABORT TASK, ABORT TASK SET, CLEAR ACA, CLEAR TASK
   SET, LOGICAL UNIT RESET, TARGET COLD RESET, TARGET WARM RESET and
   TASK REASSIGN, the target performs the requested Task Management
   function and sends a Task Management response back to the initiator.
   For TASK REASSIGN, the new connection allegiance MUST ONLY become
   effective at the target after the target issues the Task Management
   Response.

10.6.1.  Response

   The target provides a Response, which may take on the following
   values:

      a)    0 - Function complete.
      b)    1 - Task does not exist.
      c)    2 - LUN does not exist.
      d)    3 - Task still allegiant.
      e)    4 - Task allegiance reassignment not supported.
      f)    5 - Task management function not supported.
      g)    6 - Function authorization failed.
      h)  255 - Function rejected.

   All other values are reserved.

   For a discussion on usage of response codes 3 and 4, see Section
   6.2.2 Allegiance Reassignment.

   For the TARGET COLD RESET and TARGET WARM RESET functions, the target
   cancels all pending operations across all Logical Units known to the
   issuing initiator.  For the TARGET COLD RESET function, the target
   MUST then close all of its TCP connections to all initiators
   (terminates all sessions).

   The mapping of the response code into a SCSI service response code
   value, if needed, is outside the scope of this document.  However, in
   symbolic terms Response values 0 and 1 map to the SCSI service
   response of FUNCTION COMPLETE.  All other Response values map to the
   SCSI service response of FUNCTION REJECTED.  If a Task Management
   function response PDU does not arrive before the session is
   terminated, the SCSI service response is SERVICE DELIVERY OR TARGET
   FAILURE.

   The response to ABORT TASK SET and CLEAR TASK SET MUST only be issued
   by the target after all of the commands affected have been received
   by the target, the corresponding task management functions have been
   executed by the SCSI target, and the delivery of all responses
   delivered until the task management function completion have been
   confirmed (acknowledged through ExpStatSN) by the initiator on all
   connections of this session.  For the exact timeline of events, refer
   to Section 10.6.2 Task Management Actions on Task Sets.

   For the ABORT TASK function,

      a)  If the Referenced Task Tag identifies a valid task leading to
          a successful termination, then targets must return the
          "Function complete" response.
      b)  If the Referenced Task Tag does not identify an existing task,
          but if the CmdSN indicated by the RefCmdSN field in the Task
          Management function request is within the valid CmdSN window
          and less than the CmdSN of the Task Management function
          request itself, then targets must consider the CmdSN received
          and return the "Function complete" response.
      c)  If the Referenced Task Tag does not identify an existing task
          and if the CmdSN indicated by the RefCmdSN field in the Task
          Management function request is outside the valid CmdSN window,
          then targets must return the "Task does not exist" response.

10.6.2.  Task Management Actions on Task Sets

   The execution of ABORT TASK SET and CLEAR TASK SET Task Management
   function requests consists of the following sequence of events in the
   specified order on each of the entities.

   The initiator:

         a) Issues ABORT TASK SET/CLEAR TASK SET request.
         b) Continues to respond to each target transfer tag received
            for the affected task set.
         c) Receives any responses for the tasks in the affected task
            set (may process them as usual because they are guaranteed
            to be valid).
         d) Receives the task set management response, thus concluding
            all the tasks in the affected task set.

   The target:

         a) Receives the ABORT TASK SET/CLEAR TASK SET request.
         b) Waits for all target transfer tags to be responded to and
            for all affected tasks in the task set to be received.
         c) Propagates the command to and receives the response from the
            target SCSI layer.
         d) Takes note of last-sent StatSN on each of the connections in
            the iSCSI sessions (one or more) sharing the affected task
            set, and waits for acknowledgement of each StatSN (may
            solicit for acknowledgement by way of a NOP-In).  If some
            tasks originate from non-iSCSI I_T_L nexi then the means by
            which the target insures that all affected tasks have
            returned their status to the initiator are defined by the
            specific protocol.

         e) Sends the task set management response to the issuing
            initiator.

10.6.3.  TotalAHSLength and DataSegmentLength

   For this PDU TotalAHSLength and DataSegmentLength MUST be 0.





   
   
</pre>
 * 
 *
 */
public class TMResponse {
	public enum Response {

 /**
<pre>
0 - Function complete.
</pre>
 */
		FunctionComplete((byte) 0x00),

 /**
<pre>
1 - Task does not exist.
</pre>
 */
		TaskNotExist((byte) 0x01),

 /**
<pre>
2 - LUN does not exist.
</pre>
 */
		LUNNotExist((byte) 0x02),
	
 /**
<pre>
3 - Task still allegiant.
</pre>
 */
		TaskStillAllegiant((byte) 0x03),
	
 /**
<pre>
4 - Task allegiance reassignment not supported.
</pre>
 */
		TaskAlleReassignNotSupport((byte) 0x04),
	
 /**
<pre>
5 - Task management function not supported.
</pre>
 */
		TaskFunctionNotSupported((byte) 0x05),
	
 /**
<pre>
6 - Function authorization failed.
</pre>
 */
		FunctionAuthFailed((byte) 0x06),
	
/**
<pre>
255 - Function rejected.
</pre>
 */
		FunctionRejected((byte) 0xff);

		
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
		
	private byte opcode = 0x22;  
	private boolean isFinal = true;
	private byte response;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[]{0,0,0};
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	public TMResponse(){}
	public TMResponse(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		response = BHS[2]; 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
	}
	 
	public Opcode getOpcode() {
		return Opcode.SCSI_TM_RESPONSE;
	}
	public boolean getFinal(){
		return isFinal;
	}
	public Response getResponse() {
		return Response.valueOf(this.response);
	}
	public void setResponse(Response response) {
		this.response = response.value;
	}
	public int getTotalAHSLength() {
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

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" Response : "+Response.valueOf(this.response));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x22); //Opcode
			dos.writeByte(0x80);
			dos.writeByte(this.response);
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
		TMResponse original = new TMResponse();
		original.setResponse(Response.FunctionRejected);
		original.setInitiatorTaskTag(2);
		original.setStatSN(3);
		original.setExpCmdSN(4);
		original.setMaxCmdSN(5);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		TMResponse after = new TMResponse(BHS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
