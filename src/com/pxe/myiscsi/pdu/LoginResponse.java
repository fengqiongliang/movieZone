package com.pxe.myiscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;





import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.Opcode;
import com.pxe.myiscsi.ENUM.SessionType;
import com.pxe.myiscsi.ENUM.Stage;

/**
<pre>
10.13.  Login Response

   The Login Response indicates the progress and/or end of the Login
   Phase.

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x23      |T|C|.|.|CSG|NSG| Version-max   | Version-active|
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| ISID                                                          |
     +                               +---------------+---------------+
   12|                               | TSIH                          |
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
   36| Status-Class  | Status-Detail | Reserved                      |
     +---------------+---------------+---------------+---------------+
   40/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48/ DataSegment - Login Parameters in Text request Format         /
    +/                                                               /
     +---------------+---------------+---------------+---------------+

10.13.1.  Version-max

   This is the highest version number supported by the target.

   All Login Responses within the Login Phase MUST carry the same
   Version-max.

   The initiator MUST use the value presented as a response to the first
   Login Request.

10.13.2.  Version-active

   Indicates the highest version supported by the target and initiator.
   If the target does not support a version within the range specified
   by the initiator, the target rejects the login and this field
   indicates the lowest version supported by the target.

   All Login Responses within the Login Phase MUST carry the same
   Version-active.

   The initiator MUST use the value presented as a response to the first
   Login Request.

10.13.3.  TSIH

   The TSIH is the target assigned session identifying handle.  Its
   internal format and content are not defined by this protocol except
   for the value 0 that is reserved.  With the exception of the Login
   Final-Response in a new session, this field should be set to the TSIH
   provided by the initiator in the Login Request.  For a new session,
   the target MUST generate a non-zero TSIH and ONLY return it in the
   Login Final-Response (see Section 5.3 Login Phase).

10.13.4.  StatSN

   For the first Login Response (the response to the first Login
   Request), this is the starting status Sequence Number for the
   connection.  The next response of any kind, including the next Login
   Response, if any, in the same Login Phase, will carry this number +
   1.  This field is only valid if the Status-Class is 0.

10.13.5.  Status-Class and Status-Detail

   The Status returned in a Login Response indicates the execution
   status of the Login Phase.  The status includes:

     Status-Class
     Status-Detail

   0 Status-Class indicates success.

   A non-zero Status-Class indicates an exception.  In this case,
   Status-Class is sufficient for a simple initiator to use when
   handling exceptions, without having to look at the Status-Detail.
   The Status-Detail allows finer-grained exception handling for more
   sophisticated initiators and for better information for logging.

   The status classes are as follows:

      0 - Success - indicates that the iSCSI target successfully
          received, understood, and accepted the request.  The numbering
          fields (StatSN, ExpCmdSN, MaxCmdSN) are only valid if
          Status-Class is 0.

      1 - Redirection - indicates that the initiator must take further
          action to complete the request.  This is usually due to the
          target moving to a different address.  All of the redirection
          status class responses MUST return one or more text key
          parameters of the type "TargetAddress", which indicates the
          target's new address.  A redirection response MAY be issued by
          a target prior or after completing a security negotiation if a
          security negotiation is required.  A redirection SHOULD be
          accepted by an initiator even without having the target
          complete a security negotiation if any security negotiation is
          required, and MUST be accepted by the initiator after the
          completion of the security negotiation if any security
          negotiation is required.

      2 - Initiator Error (not a format error) - indicates that the
          initiator most likely caused the error.  This MAY be due to a
          request for a resource for which the initiator does not have
          permission.  The request should not be tried again.

      3 - Target Error - indicates that the target sees no errors in the
          initiator's Login Request, but is currently incapable of
          fulfilling the request.  The initiator may re-try the same
          Login Request later.

   The table below shows all of the currently allocated status codes.
   The codes are in hexadecimal; the first byte is the status class and
   the second byte is the status detail.

   -----------------------------------------------------------------
   Status        | Code | Description
                 |(hex) |
   -----------------------------------------------------------------
   Success       | 0000 | Login is proceeding OK (*1).
   -----------------------------------------------------------------
   Target moved  | 0101 | The requested iSCSI Target Name (ITN)
   temporarily   |      |  has temporarily moved
                 |      |  to the address provided.
   -----------------------------------------------------------------
   Target moved  | 0102 | The requested ITN has permanently moved
   permanently   |      |  to the address provided.
   -----------------------------------------------------------------

   Initiator     | 0200 | Miscellaneous iSCSI initiator
   error         |      | errors.
   ----------------------------------------------------------------
   Authentication| 0201 | The initiator could not be
   failure       |      | successfully authenticated or target
                 |      | authentication is not supported.
   -----------------------------------------------------------------
   Authorization | 0202 | The initiator is not allowed access
   failure       |      | to the given target.
   -----------------------------------------------------------------
   Not found     | 0203 | The requested ITN does not
                 |      | exist at this address.
   -----------------------------------------------------------------
   Target removed| 0204 | The requested ITN has been removed and
                 |      |no forwarding address is provided.
   -----------------------------------------------------------------
   Unsupported   | 0205 | The requested iSCSI version range is
   version       |      | not supported by the target.
   -----------------------------------------------------------------
   Too many      | 0206 | Too many connections on this SSID.
   connections   |      |
   -----------------------------------------------------------------
   Missing       | 0207 | Missing parameters (e.g., iSCSI
   parameter     |      | Initiator and/or Target Name).
   -----------------------------------------------------------------
   Can't include | 0208 | Target does not support session
   in session    |      | spanning to this connection (address).
   -----------------------------------------------------------------
   Session type  | 0209 | Target does not support this type of
   not supported |      | of session or not from this Initiator.
   -----------------------------------------------------------------
   Session does  | 020a | Attempt to add a connection
   not exist     |      | to a non-existent session.
   -----------------------------------------------------------------
   Invalid during| 020b | Invalid Request type during Login.
   login         |      |
   -----------------------------------------------------------------
   Target error  | 0300 | Target hardware or software error.
   -----------------------------------------------------------------
   Service       | 0301 | The iSCSI service or target is not
   unavailable   |      | currently operational.
   -----------------------------------------------------------------
   Out of        | 0302 | The target has insufficient session,
   resources     |      | connection, or other resources.
   -----------------------------------------------------------------

   (*1) If the response T bit is 1 in both the request and the matching
   response, and the NSG is FullFeaturePhase in both the request and the
   matching response, the Login Phase is finished and the initiator may
   proceed to issue SCSI commands.

   If the Status Class is not 0, the initiator and target MUST close the
   TCP connection.

   If the target wishes to reject the Login Request for more than one
   reason, it should return the primary reason for the rejection.

10.13.6.  T (Transit) bit

   The T bit is set to 1 as an indicator of the end of the stage.  If
   the T bit is set to 1 and NSG is FullFeaturePhase, then this is also
   the Final Login Response (see Chapter 5).  A T bit of 0 indicates a
   "partial" response, which means "more negotiation needed".

   A Login Response with a T bit set to 1 MUST NOT contain key=value
   pairs that may require additional answers from the initiator within
   the same stage.

   If the status class is 0, the T bit MUST NOT be set to 1 if the T bit
   in the request was set to 0.

10.13.7.  C (Continue) Bit

   When set to 1,  indicates that the text (set of key=value pairs) in
   this Login Response is not complete (it will be continued on
   subsequent Login Responses); otherwise, it indicates that this Login
   Response ends a set of key=value pairs.  A Login Response with the C
   bit set to 1 MUST have the T bit set to 0.

10.13.8.  Login Parameters

   The target MUST provide some basic parameters in order to enable the
   initiator to determine if it is connected to the correct port and the
   initial text parameters for the security exchange.

   All the rules specified in Section 10.11.6 Text Response Data for
   text responses also hold for Login Responses.  Keys and their
   explanations are listed in Chapter 11 (security negotiation keys) and
   Chapter 12 (operational parameter negotiation keys).  All keys in
   Chapter 12, except for the X extension formats, MUST be supported by
   iSCSI initiators and targets.  Keys in Chapter 11, only need to be
   supported when the function to which they refer is mandatory to
   implement.

   
</pre>
 * 
 *
 */
public class LoginResponse {
	public enum StatusClassDetail {

 /**
<pre>
0000 - Success 
    indicates that the iSCSI target successfully
    received, understood, and accepted the request.  The numbering
    fields (StatSN, ExpCmdSN, MaxCmdSN) are only valid if
    Status-Class is 0.
</pre>
 */
		Success((short) 0x0000),

 /**
<pre>
0101 - Target moved temporarily.  
       The requested iSCSI Target Name (ITN) 
       has temporarily moved to the address provided.

</pre>
 */
	TargetMovedTemp((short) 0x0101),
	
/**
<pre>
0102 - Target moved permanently.
       The requested ITN has permanently 
       moved to the address provided.
</pre>
 */
	TargetMovedPerm((short) 0x0102),

/**
<pre>
0200 - Initiator error. 
       Miscellaneous iSCSI initiator errors.
</pre>
 */
	InitiatorError((short) 0x0200),

/**
<pre>
0201 - Authentication failure.
       The initiator could not be successfully authenticated or target authentication 
       is not supported.

</pre>
 */
		AuthFailure((short) 0x0201),

/**
<pre>
0202 - Initiator Authentication failure.
       The initiator is not allowed access to the given target

</pre>
 */
		InitiatorAuthFailure((short) 0x0202),
		
/**
<pre>
0203 - Not found.
       The requested ITN does not exist at this address.

</pre>
 */
		NotFound((short) 0x0203),
		
/**
<pre>
0204 - Target removed.
       The initiator could not be successfully authenticated or target authentication 
       is not supported.

</pre>
 */
		TargetRemoved((short) 0x0204),
		
/**
<pre>
0205 - Unsupported version.
       The requested iSCSI version range is 
       not supported by the target.

</pre>
 */
		UnsupportedVersion((short) 0x0205),
		
/**
<pre>
0206 - Too many connections.
       Too many connections on this SSID.

</pre>
 */
		TooManyConn((short) 0x0206),

/**
<pre>
0207 - Missing parameter.
       Missing parameters (e.g., iSCSI Initiator and/or Target Name).

</pre>
 */
		MissingParameter((short) 0x0207),

/**
<pre>
0208 - Can't include in session.
       Target does not support session
       spanning to this connection (address).

</pre>
 */
		CannotIncludeInSession((short) 0x0208),

/**
<pre>
0209 - Session type not supported.
       Target does not support this type of
       session or not from this Initiator.

</pre>
 */
		SessionTypeNotSupported((short) 0x0209),

/**
<pre>
020a - Session does not exist.
       Attempt to add a connection to a non-existent session.

</pre>
 */
		SessionNotExist((short) 0x020a),

/**
<pre>
020b - Invalid during login.
       Invalid Request type during Login.

</pre>
 */
		InvalidDuringLogin((short) 0x020b),

/**
<pre>
0300 - Target error.
       Target hardware or software error.

</pre>
 */
		TargetError((short) 0x0300),
		
/**
<pre>
0301 - Service unavailable.
       The iSCSI service or target is not currently operational.

</pre>
 */
		ServiceUnavailable((short) 0x0301),
		
/**
<pre>
0302 - Out of resources.
       The target has insufficient session,connection, or other resources.

</pre>
 */
		OutOfResources((short) 0x0302);
		
		
		
	    private final short value;
	    private static Map<Short , StatusClassDetail> mapping;
	    static {
	    	StatusClassDetail.mapping = new HashMap<Short , StatusClassDetail>();
	        for (StatusClassDetail s : values()) {
	        	StatusClassDetail.mapping.put(s.value, s);
	        }
	    }
	    private StatusClassDetail (final short newValue) {
	        value = newValue;
	    }
	    public final short value () {
	        return value;
	    }
	    public static final StatusClassDetail valueOf (final short value) {
	        return StatusClassDetail.mapping.get(value);
	    }
	}

	private byte opcode = 0x23;
	private boolean isTransit;
	private boolean isContinue;
	private byte CSG;
	private byte NSG;
	private byte VersionMax;
	private byte VersionActive;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] ISID  = new byte[6];
	private byte[] TSIH = new byte[2];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte StatusClass;  
	private byte StatusDetail;
	private Map<String,String> parameter = new LinkedHashMap<String,String>();
	private SessionType sessionType = SessionType.Normal;
	public LoginResponse(){
		//parameter.put("SessionType", sessionType.toString());
	}
	public LoginResponse(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isTransit = (ByteUtil.getBit(BHS[1], 0)==1);
		isContinue = (ByteUtil.getBit(BHS[1], 1)==1);
		CSG = (byte)((BHS[1] & 0x0C) >> 2);
		NSG = (byte)((BHS[1] & 0x03) >> 0);
		VersionMax = BHS[2];
		VersionActive  = BHS[3];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, ISID, 0, ISID.length);
		System.arraycopy(BHS, 14, TSIH, 0, TSIH.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		StatusClass  = BHS[36];
		StatusDetail = BHS[37];
		if(DataSegment.length>0){
			String param = new String(DataSegment,"UTF-8");
			for(String item:param.split(ByteUtil.nullStr)){
				String[] keyValue =item.trim().split("="); 
				this.setParameter(keyValue[0].trim(), keyValue[1].trim());
			}
		}
	}
	
	public Opcode getOpcode() {
		return Opcode.LOGIN_RESPONSE;
	}
	public boolean getTransit() {
		return isTransit;
	}
	public void setTransit(boolean isTransit) {
		this.isTransit = isTransit;
	}
	public boolean getContinue() {
		return isContinue;
	}
	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}
	public Stage getCSG() {
		return Stage.valueOf(CSG);
	}
	public void setCSG(Stage cSG) {
		CSG = cSG.value();
	}
	public Stage getNSG() {
		return Stage.valueOf(NSG);
	}
	public void setNSG(Stage nSG) {
		NSG = nSG.value();
	}
	public int getVersionMax() {
		return VersionMax;
	}
	public void setVersionMax(int versionMax) {
		VersionMax = (byte)versionMax;
	}
	public int getVersionActive() {
		return VersionActive;
	}
	public void setVersionActive(int versionMin) {
		VersionActive = (byte)versionMin;
	}
	public byte getTotalAHSLength() {
		return TotalAHSLength;
	}
	public byte[] getISID() {
		return ISID;
	}
	public void setISID(byte[] ISID) {
		System.arraycopy(ISID, 0, this.ISID, 0, Math.min(ISID.length, this.ISID.length));
	}
	public byte[] getTSIH() {
		return TSIH;
	}
	public void setTSIH(short tSIH) {
		TSIH = ByteUtil.shortToByteArray(tSIH);
	}
	public int getInitiatorTaskTag() {
		return ByteUtil.byteArrayToInt(InitiatorTaskTag);
	}
	public void setInitiatorTaskTag(int initiatorTaskTag) {
		InitiatorTaskTag = ByteUtil.intToByteArray(initiatorTaskTag);
	}
	public byte[] getStatSN() {
		return StatSN;
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
	public StatusClassDetail getStatus() {
		byte[] b = new byte[2];
		b[0] = StatusClass;
		b[1] = StatusDetail;
		short s = ByteUtil.byteArrayToShort(b);
		return StatusClassDetail.valueOf(s);
	}
	public void setStatus(StatusClassDetail statusClass) {
		short s  = statusClass.value();
		byte[] b = ByteUtil.shortToByteArray(s);
		StatusClass = b[0];
		StatusDetail = b[1];
	}
	public SessionType getSessionType() {
		return sessionType;
	}
	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}
	public Map<String,String> getParameter(){
		return parameter;
	}
	public String getParameter(String paramName) {
		return parameter.get(paramName);
	}
	public void setParameter(String parameName,String parameValue) {
		this.parameter.put(parameName, parameValue);
		int length = getDataSegmentLength();
		this.DataSegmentLength[0] = (byte)((length & 0x00ff0000) >> 16);
		this.DataSegmentLength[1] = (byte)((length & 0x0000ff00) >> 8);
		this.DataSegmentLength[2] = (byte)((length & 0x000000ff) >> 0);
	}
	public int getDataSegmentLength() {
		return this.getDataSegment().length;
	}
	
	public byte[] getDataSegment(){
		StringBuilder build = new StringBuilder();
		for(Entry<String,String> entry:parameter.entrySet()){
			build.append(entry.getKey()+"="+entry.getValue()+ByteUtil.nullStr);
		}
		byte[] dataSegment = new byte[0];
		try {
			dataSegment = build.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {}
		return dataSegment;
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isTransit : "+isTransit);
		build.append(System.getProperty("line.separator")+" isContinue : "+isContinue);
		build.append(System.getProperty("line.separator")+" CSG : "+Stage.valueOf(CSG));
		build.append(System.getProperty("line.separator")+" NSG : "+Stage.valueOf(NSG));
		build.append(System.getProperty("line.separator")+" Version-max : "+(short)VersionMax);
		build.append(System.getProperty("line.separator")+" Version-active : "+(short)VersionActive);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" ISID : 0x"+ByteUtil.toHex(ISID));
		build.append(System.getProperty("line.separator")+" TSIH  : 0x"+ByteUtil.toHex(TSIH));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" StatSN : 0x"+ByteUtil.toHex(StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : 0x"+ByteUtil.toHex(ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : 0x"+ByteUtil.toHex(MaxCmdSN));
		build.append(System.getProperty("line.separator")+" Status : "+this.getStatus());
		build.append(System.getProperty("line.separator")+" Login Parameters : ");
		for(Entry<String,String> entry:parameter.entrySet()){
			build.append(System.getProperty("line.separator")+"          "+entry.getKey()+"="+entry.getValue());
		}
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		LoginResponse original = new LoginResponse();
		original.setParameter("haha", "你好啊?你在干什么 韩非械");
		original.setStatus(StatusClassDetail.CannotIncludeInSession);
		original.setVersionMax(43);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		LoginResponse after = new LoginResponse(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x23); //Opcode
			byte b = 0;
			b = (byte)(this.isTransit?(b|0x80):b);
			b = (byte)(this.isContinue?(b|0x40):b);
			b = (byte)(b|((this.CSG & 0x03) << 2));
			b = (byte)(b|((this.NSG & 0x03) << 0));
			dos.writeByte(b); //T|C|.|.|CSG|NSG
			dos.writeByte(this.VersionMax);
			dos.writeByte(this.VersionActive);
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.ISID);
			dos.write(this.TSIH);
			dos.write(this.InitiatorTaskTag);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.writeByte(this.StatusClass);
			dos.writeByte(this.StatusDetail);
			dos.write(new byte[]{0,0});
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
			byte[] dataSegment = this.getDataSegment();
			dos.write(dataSegment);  //DataSegment - Login Parameters in Text request Format(dynamic)
			//write (All PDU segments and digests are padded to the closest integer number of four byte words.)
			int count = dataSegment.length % 4 == 0?0:(4-dataSegment.length % 4);
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
	 
}
