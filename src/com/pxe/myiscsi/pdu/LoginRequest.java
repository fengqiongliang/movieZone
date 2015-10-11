package com.pxe.myiscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;




import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.Opcode;
import com.pxe.myiscsi.ENUM.SessionType;
import com.pxe.myiscsi.ENUM.Stage;

/**
<pre>
10.12.  Login Request

   After establishing a TCP connection between an initiator and a
   target, the initiator MUST start a Login Phase to gain further access
   to the target's resources.

   The Login Phase (see Chapter 5) consists of a sequence of Login
   Requests and Responses that carry the same Initiator Task Tag.

   Login Requests are always considered as immediate.
   
   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|1| 0x03      |T|C|.|.|CSG|NSG| Version-max   | Version-min   |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| ISID                                                          |
     +                               +---------------+---------------+
   12|                               | TSIH                          |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| CID                           | Reserved                      |
     +---------------+---------------+---------------+---------------+
   24| CmdSN                                                         |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN   or   Reserved                                     |
     +---------------+---------------+---------------+---------------+
   32| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   36| Reserved                                                      |
     +---------------+---------------+---------------+---------------+
   40/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48/ DataSegment - Login Parameters in Text request Format         /
    +/                                                               /
     +---------------+---------------+---------------+---------------+

10.12.1.  T (Transit) Bit

   If set to 1, indicates that the initiator is ready to transit to the
   next stage.

   If the T bit is set to 1 and NSG is FullFeaturePhase, then this also
   indicates that the initiator is ready for the Final Login Response
   (see Chapter 5).

10.12.2.  C (Continue) Bit

   When set to 1,  indicates that the text (set of key=value pairs) in
   this Login Request is not complete (it will be continued on
   subsequent Login Requests); otherwise, it indicates that this Login
   Request ends a set of key=value pairs.  A Login Request with the C
   bit set to 1 MUST have the T bit set to 0.

10.12.3.  CSG and NSG

   Through these fields, Current Stage (CSG) and Next Stage (NSG), the
   Login negotiation requests and responses are associated with a
   specific stage in the session (SecurityNegotiation,
   LoginOperationalNegotiation, FullFeaturePhase) and may indicate the
   next stage to which they want to move (see Chapter 5).  The next
   stage value is only valid  when the T bit is 1; otherwise, it is
   reserved.

   The stage codes are:

      - 0 - SecurityNegotiation
      - 1 - LoginOperationalNegotiation
      - 3 - FullFeaturePhase

   All other codes are reserved.

10.12.4.  Version

   The version number of the current draft is 0x00.  As such, all
   devices MUST carry version 0x00 for both Version-min and Version-max.

10.12.4.1.  Version-max

   Maximum Version number supported.

   All Login Requests within the Login Phase MUST carry the same
   Version-max.

   The target MUST use the value presented with the first Login Request.

10.12.4.2.  Version-min

   All Login Requests within the Login Phase MUST carry the same
   Version-min.  The target MUST use the value presented with the first
   Login Request.

10.12.5.  ISID

   This is an initiator-defined component of the session identifier and
   is structured as follows (see [RFC3721] and Section 9.1.1
   Conservative Reuse of ISIDs for details):

    Byte/     0       |       1       |       2       |       3       |
       /              |               |               |               |
      |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
      +---------------+---------------+---------------+---------------+
     8| T |    A      |              B                |      C        |
      +---------------+---------------+---------------+---------------+
    12|               D               |
      +---------------+---------------+

   The T field identifies the format and usage of A, B, C, and D as
   indicated below:

     T

     00b     OUI-Format
             A&B are a 22 bit OUI
             (the I/G & U/L bits are omitted)
             C&D 24 bit qualifier
     01b     EN - Format (IANA Enterprise Number)
             A - Reserved
             B&C EN (IANA Enterprise Number)
             D - Qualifier
     10b     "Random"
             A - Reserved
             B&C Random
             D - Qualifier
     11b     A,B,C&D Reserved

   For the T field values 00b and 01b, a combination of A and B (for
   00b) or B and C (for 01b) identifies the vendor or organization whose
   component (software or hardware) generates this ISID.  A vendor or
   organization with one or more OUIs, or one or more Enterprise
   Numbers, MUST use at least one of these numbers and select the
   appropriate value for the T field when its components generate ISIDs.
   An OUI or EN MUST be set in the corresponding fields in network byte
   order (byte big-endian).

   If the T field is 10b, B and C are set to a random 24-bit unsigned
   integer value in network byte order (byte big-endian).  See [RFC3721]
   for how this affects the principle of "conservative reuse".
   The Qualifier field is a 16 or 24-bit unsigned integer value that
   provides a range of possible values for the ISID within the selected
   namespace.  It may be set to any value within the constraints
   specified in the iSCSI protocol (see Section 3.4.3 Consequences of
   the Model and Section 9.1.1 Conservative Reuse of ISIDs).

   The T field value of 11b is reserved.

   If the ISID is derived from something assigned to a hardware adapter
   or interface by a vendor, as a preset default value, it MUST be
   configurable to a value assigned according to the SCSI port behavior
   desired by the system in which it is installed (see Section 9.1.1
   Conservative Reuse of ISIDs and Section 9.1.2 iSCSI Name, ISID, and
   TPGT Use).  The resultant ISID MUST also be persistent over power
   cycles, reboot, card swap, etc.

10.12.6.  TSIH

   TSIH must be set in the first Login Request.  The reserved value 0
   MUST be used on the first connection for a new session.  Otherwise,
   the TSIH sent by the target at the conclusion of the successful login
   of the first connection for this session MUST be used.  The TSIH
   identifies to the target the associated existing session for this new
   connection.

   All Login Requests within a Login Phase MUST carry the same TSIH.

   The target MUST check the value presented with the first Login
   Request and act as specified in Section 5.3.1 Login Phase Start.

10.12.7.  Connection ID - CID

   A unique ID for this connection within the session.

   All Login Requests within the Login Phase MUST carry the same CID.

   The target MUST use the value presented with the first Login Request.

   A Login Request with a non-zero TSIH and a CID equal to that of an
   existing connection implies a logout of the connection followed by a
   Login (see Section 5.3.4 Connection Reinstatement).  For the details
   of the implicit Logout Request, see Section 10.14 Logout Request.

10.12.8.  CmdSN

   CmdSN is either the initial command sequence number of a session (for
   the first Login Request of a session - the "leading" login), or the
   command sequence number in the command stream if the login is for a
   new connection in an existing session.

   Examples:

      -  Login on a leading connection - if the leading login carries
         the CmdSN 123, all other Login Requests in the same Login Phase
         carry the CmdSN 123 and the first non-immediate command in
         FullFeaturePhase also carries the CmdSN 123.

      -  Login on other than a leading connection - if the current CmdSN
         at the time the first login on the connection is issued is 500,
         then that PDU carries CmdSN=500.  Subsequent Login Requests
         that are needed to complete this Login Phase may carry a CmdSN
         higher than 500 if non-immediate requests that were issued on
         other connections in the same session advance CmdSN.

   If the Login Request is a leading Login Request, the target MUST use
   the value presented in CmdSN as the target value for ExpCmdSN.

10.12.9.  ExpStatSN

   For the first Login Request on a connection this is ExpStatSN for the
   old connection and this field is only valid if the Login Request
   restarts a connection (see Section 5.3.4 Connection Reinstatement).

   For subsequent Login Requests it is used to acknowledge the Login
   Responses with their increasing StatSN values.

10.12.10.  Login Parameters

   The initiator MUST provide some basic parameters in order to enable
   the target to determine if the initiator may use the target's
   resources and the initial text parameters for the security exchange.

   All the rules specified in Section 10.10.5 Text for text requests
   also hold for Login Requests.  Keys and their explanations are listed
   in Chapter 11 (security negotiation keys) and Chapter 12 (operational
   parameter negotiation keys).  All keys in Chapter 12, except for the
   X extension formats, MUST be supported by iSCSI initiators and
   targets.  Keys in Chapter 11 only need to be supported when the
   function to which they refer is mandatory to implement.
   
   
</pre>
 * 
 *
 */
public class LoginRequest {
	
	private boolean isImmediate = true;
	private byte opcode = 0x03;
	private boolean isTransit;
	private boolean isContinue;
	private byte CSG;
	private byte NSG;
	private byte VersionMax;
	private byte VersionMin;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] ISID  = new byte[6];
	private byte[] TSIH = new byte[2];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] CID = new byte[2];
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private Map<String,String> parameter = new LinkedHashMap<String,String>();
	private SessionType sessionType = SessionType.Normal;
	public LoginRequest(){
		parameter.put("SessionType", sessionType.toString());
	}
	public LoginRequest(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isTransit = (ByteUtil.getBit(BHS[1], 0)==1);
		isContinue = (ByteUtil.getBit(BHS[1], 1)==1);
		CSG = (byte)((BHS[1] & 0x0C) >> 2);
		NSG = (byte)((BHS[1] & 0x03) >> 0);
		VersionMax = BHS[2];
		VersionMin  = BHS[3];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, ISID, 0, ISID.length);
		System.arraycopy(BHS, 14, TSIH, 0, TSIH.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, CID, 0, CID.length);
		System.arraycopy(BHS, 24, CmdSN, 0, CmdSN.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
		if(DataSegment.length>0){
			String param = new String(DataSegment,"UTF-8");
			for(String item:param.split(ByteUtil.nullStr)){
				String[] keyValue =item.trim().split("="); 
				this.setParameter(keyValue[0].trim(), keyValue[1].trim());
			}
		}
	}
	
	public Opcode getOpcode() {
		return Opcode.LOGIN_REQUEST;
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
	public int getVersionMin() {
		return VersionMin;
	}
	public void setVersionMin(int versionMin) {
		VersionMin = (byte)versionMin;
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
	public void setTSIH(byte[] TSIH) {
		System.arraycopy(TSIH, 0, this.TSIH, 0, Math.min(TSIH.length, this.TSIH.length));
	}
	public int getInitiatorTaskTag() {
		return ByteUtil.byteArrayToInt(InitiatorTaskTag);
	}
	public void setInitiatorTaskTag(int initiatorTaskTag) {
		InitiatorTaskTag = ByteUtil.intToByteArray(initiatorTaskTag);
	}
	public byte[] getCID() {
		return CID;
	}
	public void setCID(byte[] CID) {
		System.arraycopy(CID, 0, this.CID, 0, Math.min(CID.length, this.CID.length));
	}
	public int getCmdSN() {
		return ByteUtil.byteArrayToInt(CmdSN);
	}
	public void setCmdSN(int CmdSN) {
		this.CmdSN = ByteUtil.intToByteArray(CmdSN);
	}
	public int getExpStatSN() {
		return ByteUtil.byteArrayToInt(ExpStatSN);
	}
	public void setExpStatSN(int ExpStatSN) {
		this.ExpStatSN = ByteUtil.intToByteArray(ExpStatSN);
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
	public boolean getImmediate() {
		return isImmediate;
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
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isTransit : "+isTransit);
		build.append(System.getProperty("line.separator")+" isContinue : "+isContinue);
		build.append(System.getProperty("line.separator")+" CSG : "+Stage.valueOf(CSG));
		build.append(System.getProperty("line.separator")+" NSG : "+Stage.valueOf(NSG));
		build.append(System.getProperty("line.separator")+" Version-max : "+(short)VersionMax);
		build.append(System.getProperty("line.separator")+" Version-min : "+(short)VersionMin);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" ISID : 0x"+ByteUtil.toHex(ISID));
		build.append(System.getProperty("line.separator")+" TSIH  : 0x"+ByteUtil.toHex(TSIH));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" CID : 0x"+ByteUtil.toHex(CID));
		build.append(System.getProperty("line.separator")+" CmdSN : 0x"+ByteUtil.toHex(CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : 0x"+ByteUtil.toHex(ExpStatSN));
		build.append(System.getProperty("line.separator")+" Login Parameters : ");
		for(Entry<String,String> entry:parameter.entrySet()){
			build.append(System.getProperty("line.separator")+"          "+entry.getKey()+"="+entry.getValue());
		}
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		LoginRequest original = new LoginRequest();
		original.setParameter("haha", "你好啊?你在干什么 韩非械");
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		LoginRequest after = new LoginRequest(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x43); //Opcode
			byte b = 0;
			b = (byte)(this.isTransit?(b|0x80):b);
			b = (byte)(this.isContinue?(b|0x40):b);
			b = (byte)(b|((this.CSG & 0x03) << 2));
			b = (byte)(b|((this.NSG & 0x03) << 0));
			dos.writeByte(b); //T|C|.|.|CSG|NSG
			dos.writeByte(this.VersionMax);
			dos.writeByte(this.VersionMin);
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.ISID);
			dos.write(this.TSIH);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.CID);
			dos.write(new byte[]{0,0}); //Reserved 
			dos.write(this.CmdSN);
			dos.write(this.ExpStatSN);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			dos.write(new byte[]{0,0,0,0}); //Reserved
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
