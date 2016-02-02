package com.pxe.iscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;




import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.Opcode;
import com.pxe.iscsi.ENUM.SessionType;

/**
<pre>

10.10.  Text Request

   The Text Request is provided to allow for the exchange of information
   and for future extensions.  It permits the initiator to inform a
   target of its capabilities or to request some special operations.

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| 0x04      |F|C| Reserved                                  |
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
   24| CmdSN                                                         |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN                                                     |
     +---------------+---------------+---------------+---------------+
   32/ Reserved                                                      /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / DataSegment (Text)                                            /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   An initiator MUST have at most one outstanding Text Request on a
   connection at any given time.

   On a connection failure, an initiator must either explicitly abort
   any active allegiant text negotiation task or must cause such a task
   to be implicitly terminated by the target.

10.10.1.  F (Final) Bit

   When set to 1,  indicates that this is the last or only text request
   in a sequence of Text Requests; otherwise, it indicates that more
   Text Requests will follow.

10.10.2.  C (Continue) Bit

   When set to 1, indicates that the text (set of key=value pairs) in
   this Text Request is not complete (it will be continued on subsequent
   Text Requests); otherwise, it indicates that this Text Request ends a
   set of key=value pairs.  A Text Request with the C bit set to 1 MUST
   have the F bit set to 0.

10.10.3.  Initiator Task Tag

   The initiator assigned identifier for this Text Request.  If the
   command is sent as part of a sequence of text requests and responses,
   the Initiator Task Tag MUST be the same for all the requests within
   the sequence (similar to linked SCSI commands).  The I bit for all
   requests in a sequence also MUST be the same.

10.10.4.  Target Transfer Tag

   When the Target Transfer Tag is set to the reserved value 0xffffffff,
   it tells the target that this is a new request and the target resets
   any internal state associated with the Initiator Task Tag (resets the
   current negotiation state).

   The target sets the Target Transfer Tag in a text response to a value
   other than the reserved value 0xffffffff whenever it indicates that
   it has more data to send or more operations to perform that are
   associated with the specified Initiator Task Tag.  It MUST do so
   whenever it sets the F bit to 0 in the response.  By copying the
   Target Transfer Tag from the response to the next Text Request, the
   initiator tells the target to continue the operation for the specific
   Initiator Task Tag.  The initiator MUST ignore the Target Transfer
   Tag in the Text Response when the F bit is set to 1.

   This mechanism allows the initiator and target to transfer a large
   amount of textual data over a sequence of text-command/text-response
   exchanges, or to perform extended negotiation sequences.

   If the Target Transfer Tag is not 0xffffffff, the LUN field MUST be
   sent by the target in the Text Response.

   A target MAY reset its internal negotiation state if an exchange is
   stalled by the initiator for a long time or if it is running out of
   resources.

   Long text responses are handled as in the following example:

     I->T Text SendTargets=All (F=1,TTT=0xffffffff)
     T->I Text <part 1> (F=0,TTT=0x12345678)
     I->T Text <empty> (F=1, TTT=0x12345678)
     T->I Text <part 2> (F=0, TTT=0x12345678)
     I->T Text <empty> (F=1, TTT=0x12345678)
     ...
     T->I Text <part n> (F=1, TTT=0xffffffff)

10.10.5.  Text

   The data lengths of a text request MUST NOT exceed the iSCSI target
   MaxRecvDataSegmentLength (a per connection and per direction
   negotiated parameter).  The text format is specified in Section 5.2
   Text Mode Negotiation.

   Chapter 11 and Chapter 12 list some basic Text key=value pairs, some
   of which can be used in Login Request/Response and some in Text
   Request/Response.

   A key=value pair can span Text request or response boundaries.  A
   key=value pair can start in one PDU and continue on the next.  In
   other words the end of a PDU does not necessarily signal the end of a
   key=value pair.

   The target responds by sending its response back to the initiator.
   The response text format is similar to the request text format.  The
   text response MAY refer to key=value pairs presented in an earlier
   text request and the text in the request may refer to earlier
   responses.

   Chapter 5 details the rules for the Text Requests and Responses.

   Text operations are usually meant for parameter setting/
   negotiations, but can also be used to perform some long lasting
   operations.

   Text operations that take a long time should be placed in their own
   Text request.




   
</pre>
 * 
 *
 */
public class TextRequest {
	
	private boolean isImmediate;
	private byte opcode = 0x04;
	private boolean isFinal;
	private boolean isContinue;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN  = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private Map<String,String> parameter = new LinkedHashMap<String,String>();
	private SessionType sessionType = SessionType.Normal;
	public TextRequest(){
		//parameter.put("SessionType", sessionType.toString());
	}
	public TextRequest(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		isFinal = (ByteUtil.getBit(BHS[1], 0)==1);
		isContinue = (ByteUtil.getBit(BHS[1], 1)==1);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
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
		return Opcode.TEXT_REQUEST;
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
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" isContinue : "+isContinue);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" LUN  : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(TargetTransferTag));
		build.append(System.getProperty("line.separator")+" CmdSN : 0x"+ByteUtil.toHex(CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : 0x"+ByteUtil.toHex(ExpStatSN));
		build.append(System.getProperty("line.separator")+" Text Parameters : ");
		for(Entry<String,String> entry:parameter.entrySet()){
			build.append(System.getProperty("line.separator")+"          "+entry.getKey()+"="+entry.getValue());
		}
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		TextRequest original = new TextRequest();
		original.setFinal(false);
		original.setContinue(true);
		original.setInitiatorTaskTag(10);
		original.setTargetTransferTag(11);
		original.setCmdSN(1);
		original.setExpStatSN(2);
		original.setParameter("haha", "你好啊?你在干什么 韩非械");
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		TextRequest after = new TextRequest(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		byte[] b = ByteUtil.intToByteArray(-1);
		System.out.println(ByteUtil.toHex(b));
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(this.isImmediate?0x44:0x04); //Opcode
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
