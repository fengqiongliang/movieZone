package com.pxe.myiscsi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.target.connection.SessionType;

/**
<pre>

10.11.  Text Response

   The Text Response PDU contains the target's responses to the
   initiator's Text request.  The format of the Text field matches that
   of the Text request.

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x24      |F|C| Reserved                                  |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN or Reserved                                               |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| Target Transfer Tag or 0xffffffff                             |
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
     / DataSegment (Text)                                            /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

10.11.1.  F (Final) Bit

   When set to 1, in response to a Text Request with the Final bit set
   to 1, the F bit indicates that the target has finished the whole
   operation.  Otherwise, if set to 0 in response to a Text Request with
   the Final Bit set to 1, it indicates that the target has more work to
   do (invites a follow-on text request).  A Text Response with the F
   bit set to 1 in response to a Text Request with the F bit set to 0 is
   a protocol error.

   A Text Response with the F bit set to 1 MUST NOT contain key=value
   pairs that may require additional answers from the initiator.

   A Text Response with the F bit set to 1 MUST have a Target Transfer
   Tag field set to the reserved value of 0xffffffff.

   A Text Response with the F bit set to 0 MUST have a Target Transfer
   Tag field set to a value other than the reserved 0xffffffff.

10.11.2.  C (Continue) Bit

   When set to 1, indicates that the text (set of key=value pairs) in
   this Text Response is not complete (it will be continued on
   subsequent Text Responses); otherwise, it indicates that this Text
   Response ends a set of key=value pairs.  A Text Response with the C
   bit set to 1 MUST have the F bit set to 0.

10.11.3.  Initiator Task Tag

   The Initiator Task Tag matches the tag used in the initial Text
   Request.

10.11.4.  Target Transfer Tag

   When a target has more work to do (e.g., cannot transfer all the
   remaining text data in a single Text Response or has to continue the
   negotiation) and has enough resources to proceed, it MUST set the
   Target Transfer Tag to a value other than the reserved value of
   0xffffffff.  Otherwise, the Target Transfer Tag MUST be set to
   0xffffffff.

   When the Target Transfer Tag is not 0xffffffff, the LUN field may be
   significant.

   The initiator MUST copy the Target Transfer Tag and LUN in its next
   request to indicate that it wants the rest of the data.

   When the target receives a Text Request with the Target Transfer Tag
   set to the reserved value of 0xffffffff, it resets its internal
   information (resets state) associated with the given Initiator Task
   Tag (restarts the negotiation).

   When a target cannot finish the operation in a single Text Response,
   and does not have enough resources to continue, it rejects the Text
   Request with the appropriate Reject code.

   A target may reset its internal state associated with an Initiator
   Task Tag (the current negotiation state), state expressed through the
   Target Transfer Tag if the initiator fails to continue the exchange
   for some time.  The target may reject subsequent Text Requests with
   the Target Transfer Tag set to the "stale" value.

10.11.5.  StatSN

   The target StatSN variable is advanced by each Text Response sent.

10.11.6.  Text Response Data

   The data lengths of a text response MUST NOT exceed the iSCSI
   initiator MaxRecvDataSegmentLength (a per connection and per
   direction negotiated parameter).

   The text in the Text Response Data is governed by the same rules as
   the text in the Text Request Data (see Section 10.10.5 Text).

   Although the initiator is the requesting party and controls the
   request-response initiation and termination, the target can offer
   key=value pairs of its own as part of a sequence and not only in
   response to the initiator.





   
</pre>
 * 
 *
 */
public class PDUTextResponse {
	
	private byte Opcode = 0x24;
	private boolean isFinal;
	private boolean isContinue;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN  = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private Map<String,String> parameter = new LinkedHashMap<String,String>();
	private SessionType sessionType = SessionType.NORMAL;
	public PDUTextResponse(){
		//parameter.put("SessionType", sessionType.toString());
	}
	public PDUTextResponse(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isFinal = (ByteUtil.getBit(BHS[1], 0)==1);
		isContinue = (ByteUtil.getBit(BHS[1], 1)==1);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		if(DataSegment.length>0){
			String param = new String(DataSegment,"UTF-8");
			for(String item:param.split(ByteUtil.nullStr)){
				String[] keyValue =item.trim().split("="); 
				this.setParameter(keyValue[0].trim(), keyValue[1].trim());
			}
		}
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.TEXT_RESPONSE;
	}
	public boolean getFinal() {
		return isFinal;
	}
	public void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	public boolean getContinue() {
		return isContinue;
	}
	public void setContinue(boolean isContinue) {
		this.isContinue = isContinue;
	}
	public byte getTotalAHSLength() {
		return TotalAHSLength;
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
		return  ByteUtil.byteArrayToInt(StatSN);
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
		build.append(System.getProperty("line.separator")+" Opcode : "+PDUOpcodeEnum.valueOf(Opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" isContinue : "+isContinue);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" LUN  : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(TargetTransferTag));
		build.append(System.getProperty("line.separator")+" StatSN : 0x"+ByteUtil.toHex(StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : 0x"+ByteUtil.toHex(ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : 0x"+ByteUtil.toHex(MaxCmdSN));
		build.append(System.getProperty("line.separator")+" Text Parameters : ");
		for(Entry<String,String> entry:parameter.entrySet()){
			build.append(System.getProperty("line.separator")+"          "+entry.getKey()+"="+entry.getValue());
		}
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		PDUTextResponse original = new PDUTextResponse();
		original.setFinal(true);
		original.setContinue(false);
		original.setInitiatorTaskTag(10);
		original.setTargetTransferTag(11);
		original.setStatSN(1);
		original.setExpCmdSN(2);
		original.setMaxCmdSN(3);
		original.setParameter("haha", "你好啊?你在干什么 韩非械");
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		PDUTextResponse after = new PDUTextResponse(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x24); //Opcode
			byte b = 0;
			b = (byte)(this.isFinal?(b|0x80):b);
			b = (byte)(this.isContinue?(b|0x40):b);
			dos.writeByte(b); //T|C|.|.|CSG|NSG
			dos.write(new byte[]{0,0});//Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
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
