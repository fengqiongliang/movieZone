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

10.14.  Logout Request

   The Logout Request is used to perform a controlled closing of a
   connection.

   An initiator MAY use a Logout Request to remove a connection from a
   session or to close an entire session.

   After sending the Logout Request PDU, an initiator MUST NOT send any
   new iSCSI requests on the closing connection.  If the Logout Request
   is intended to close the session, new iSCSI requests MUST NOT be sent
   on any of the connections participating in the session.

   When receiving a Logout Request with the reason code of "close the
   connection" or "close the session", the target MUST terminate all
   pending commands, whether acknowledged via ExpCmdSN or not, on that
   connection or session respectively.

   When receiving a Logout Request with the reason code "remove
   connection for recovery", the target MUST discard all requests not
   yet acknowledged via ExpCmdSN that were issued on the specified
   connection, and suspend all data/status/R2T transfers on behalf of
   pending commands on the specified connection.

   The target then issues the Logout Response and half-closes the TCP
   connection (sends FIN).  After receiving the Logout Response and
   attempting to receive the FIN (if still possible), the initiator MUST
   completely close the logging-out connection.  For the terminated
   commands, no additional responses should be expected.

   A Logout for a CID may be performed on a different transport
   connection when the TCP connection for the CID has already been
   terminated.  In such a case, only a logical "closing" of the iSCSI
   connection for the CID is implied with a Logout.

   All commands that were not terminated or not completed (with status)
   and acknowledged when the connection is closed completely can be
   reassigned to a new connection if the target supports connection
   recovery.

   If an initiator intends to start recovery for a failing connection,
   it MUST use the Logout Request to "clean-up" the target end of a
   failing connection and enable recovery to start, or the Login Request
   with a non-zero TSIH and the same CID on a new connection for the
   same effect (see Section 10.14.3 CID).  In sessions with a single
   connection, the connection can be closed and then a new connection
   reopened.  A connection reinstatement login can be used for recovery
   (see Section 5.3.4 Connection Reinstatement).

   A successful completion of a Logout Request with the reason code of
   "close the connection" or "remove the connection for recovery"
   results at the target in the discarding of unacknowledged commands
   received on the connection being logged out.  These are commands that
   have arrived on the connection being logged out, but have not been
   delivered to SCSI because one or more commands with a smaller CmdSN
   has not been received by iSCSI.  See Section 3.2.2.1 Command
   Numbering and Acknowledging.  The resulting holes the in command
   sequence numbers will have to be handled by appropriate recovery (see
   Chapter 6) unless the session is also closed.

   The entire logout discussion in this section is also applicable for
   an implicit Logout realized via a connection reinstatement or session
   reinstatement.  When a Login Request performs an implicit Logout, the
   implicit Logout is performed as if having the reason codes specified
   below:

     Reason code        Type of implicit Logout
     -------------------------------------------
         0              session reinstatement
         1              connection reinstatement when
                       the operational ErrorRecoveryLevel < 2
         2              connection reinstatement when
                       the operational ErrorRecoveryLevel = 2

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| 0x06      |1| Reason Code | Reserved                      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------------------------------------------------------+
    8/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| CID or Reserved               | Reserved                      |
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

10.14.1.  Reason Code

   Reason Code indicates the reason for Logout as follows:

      0 - close the session.  All commands associated with the session
          (if any) are terminated.

      1 - close the connection.  All commands associated with connection
          (if any) are terminated.

      2 - remove the connection for recovery.  Connection is closed and
          all commands associated with it, if any, are to be prepared
          for a new allegiance.

   All other values are reserved.


10.14.2.  TotalAHSLength and DataSegmentLength

   For this PDU TotalAHSLength and DataSegmentLength MUST be 0.

10.14.3.  CID

   This is the connection ID of the connection to be closed (including
   closing the TCP stream).  This field is only valid if the reason code
   is not "close the session".

10.14.4.  ExpStatSN

   This is the last ExpStatSN value for the connection to be closed.

10.14.5.  Implicit termination of tasks

   A target implicitly terminates the active tasks due to the iSCSI
   protocol in the following cases:

      a)  When a connection is implicitly or explicitly logged out with
          the reason code of "Close the connection" and there are active
          tasks allegiant to that connection.

      b)  When a connection fails and eventually the connection state
          times out (state transition M1 in Section 7.2.2 State
          Transition Descriptions for Initiators and Targets) and there
          are active tasks allegiant to that connection.

      c)  When a successful recovery Logout is performed while there are
          active tasks allegiant to that connection, and those tasks
          eventually time out after the Time2Wait and Time2Retain
          periods without allegiance reassignment.

      d)  When a connection is implicitly or explicitly logged out with
          the reason code of "Close the session" and there are active
          tasks in that session.

   If the tasks terminated in any of the above cases are SCSI tasks,
   they must be internally terminated as if with CHECK CONDITION status.
   This status is only meaningful for appropriately handling the
   internal SCSI state and SCSI side effects with respect to ordering
   because this status is never communicated back as a terminating
   status to the initiator. However additional actions may have to be
   taken at SCSI level depending on the SCSI context as defined by the
   SCSI standards (e.g., queued commands and ACA, in cases a), b), and
   c), after the tasks are terminated, the target MUST report a Unit
   Attention condition on the next command processed on any connection
   for each affected I_T_L nexus with the status of CHECK CONDITION, and
   the ASC/ASCQ value of 47h/7Fh - "SOME COMMANDS CLEARED BY ISCSI
   PROTOCOL EVENT" - etc. - see [SAM2] and [SPC3]).






   
</pre>
 * 
 *
 */
public class LogoutRequest {
	public enum ReasonCode {

 /**
<pre>
0 - close the session.  All commands associated with 
    the session (if any) are terminated.
</pre>
 */
		CloseSession((byte) 0x00),

 /**
<pre>
1 - close the connection.  All commands associated with 
    the connection (if any) are terminated.

</pre>
 */
		CloseConn((byte) 0x01),
	
/**
<pre>
2 - remove the connection for recovery.  Connection is closed and
    all commands associated with it, if any, are to be prepared
    for a new allegiance.


</pre>
 */
	RemoveConn((byte) 0x02);
		
	    private final byte value;
	    private static Map<Byte , ReasonCode> mapping;
	    static {
	        ReasonCode.mapping = new HashMap<Byte , ReasonCode>();
	        for (ReasonCode s : values()) {
	            ReasonCode.mapping.put(s.value, s);
	        }
	    }
	    private ReasonCode (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final ReasonCode valueOf (final byte value) {
	        return ReasonCode.mapping.get(value);
	    }
	}
	
	private boolean isImmediate;
	private byte opcode = 0x06;
	private boolean isFinal = true;
	private byte reasonCode; 
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] CID = new byte[2];
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	public LogoutRequest(){}
	public LogoutRequest(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		reasonCode = (byte)(BHS[1] & 0x7F);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, CID, 0, CID.length);
		System.arraycopy(BHS, 24, CmdSN, 0, CmdSN.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.LOGOUT_REQUEST;
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
	public ReasonCode getReasonCode() {
		return ReasonCode.valueOf(reasonCode);
	}
	public void setReasonCode(ReasonCode reasonCode) {
		this.reasonCode = reasonCode.value();
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
	public short getCID() {
		return ByteUtil.byteArrayToShort(CID);
	}
	public void setCID(short cid) {
		CID = ByteUtil.shortToByteArray(cid);
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
	
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" ReasonCode : "+ReasonCode.valueOf(this.reasonCode));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" CID : "+ByteUtil.byteArrayToShort(CID));
		build.append(System.getProperty("line.separator")+" CmdSN : 0x"+ByteUtil.toHex(CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : 0x"+ByteUtil.toHex(ExpStatSN));
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		LogoutRequest original = new LogoutRequest();
		original.setImmediate(true);
		original.setReasonCode(ReasonCode.CloseConn);
		original.setInitiatorTaskTag(10);
		original.setCID((short)11);
		original.setCmdSN(1);
		original.setExpStatSN(2);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		LogoutRequest after = new LogoutRequest(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		byte[] b = ByteUtil.intToByteArray(-1);
		System.out.println(ByteUtil.toHex(b));
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(this.isImmediate?0x46:0x06); //Opcode
			dos.writeByte(0x80|this.reasonCode);
			dos.write(new byte[]{0,0});//Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.InitiatorTaskTag);
			dos.write(this.CID);
			dos.write(new byte[]{0,0});//Reserved
			dos.write(this.CmdSN);
			dos.write(this.ExpStatSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
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
	 
}
