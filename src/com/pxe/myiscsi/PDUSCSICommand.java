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

10.3.  SCSI Command

   The format of the SCSI Command PDU is:

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| 0x01      |F|R|W|. .|ATTR | Reserved                      |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| Logical Unit Number (LUN)                                     |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| Expected Data Transfer Length                                 |
     +---------------+---------------+---------------+---------------+
   24| CmdSN                                                         |
     +---------------+---------------+---------------+---------------+
   28| ExpStatSN                                                     |
     +---------------+---------------+---------------+---------------+
   32/ SCSI Command Descriptor Block (CDB)                           /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48/ AHS (Optional)                                                /
     +---------------+---------------+---------------+---------------+
    x/ Header Digest (Optional)                                      /
     +---------------+---------------+---------------+---------------+
    y/ (DataSegment, Command Data) (Optional)                        /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
    z/ Data Digest (Optional)                                        /
     +---------------+---------------+---------------+---------------+

10.3.1.  Flags and Task Attributes (byte 1)

   The flags for a SCSI Command are:

   bit 0   (F) is set to 1 when no unsolicited SCSI Data-Out PDUs follow
            this PDU.  When F=1 for a write and if Expected Data
            Transfer Length is larger than the DataSegmentLength, the
            target may solicit additional data through R2T.

   bit 1   (R) is set to 1 when the command is expected to input data.

   bit 2   (W) is set to 1 when the command is expected to output data.

   bit 3-4 Reserved.

   bit 5-7 contains Task Attributes.

   Task Attributes (ATTR) have one of the following integer values (see
   [SAM2] for details):

     0 - Untagged
     1 - Simple
     2 - Ordered
     3 - Head of Queue
     4 - ACA
     5-7 - Reserved

   Setting both the W and the F bit to 0 is an error.  Either or both of
   R and W MAY be 1 when either the Expected Data Transfer Length and/or
   Bidirectional Read Expected Data Transfer Length are 0, but they MUST
   NOT both be 0 when the Expected Data Transfer Length and/or
   Bidirectional Read Expected Data Transfer Length are not 0 (i.e.,
   when some data transfer is expected the transfer direction is
   indicated by the R and/or W bit).

10.3.2.  CmdSN - Command Sequence Number

   Enables ordered delivery across multiple connections in a single
   session.

10.3.3.  ExpStatSN

   Command responses up to ExpStatSN-1 (mod 2**32) have been received
   (acknowledges status) on the connection.

10.3.4.  Expected Data Transfer Length

   For unidirectional operations, the Expected Data Transfer Length
   field contains the number of bytes of data involved in this SCSI
   operation.  For a unidirectional write operation (W flag set to 1 and
   R flag set to 0), the initiator uses this field to specify the number
   of bytes of data it expects to transfer for this operation.  For a
   unidirectional read operation (W flag set to 0 and R flag set to 1),
   the initiator uses this field to specify the number of bytes of data
   it expects the target to transfer to the initiator.  It corresponds
   to the SAM2 byte count.

   For bidirectional operations (both R and W flags are set to 1), this
   field contains the number of data bytes involved in the write
   transfer.  For bidirectional operations, an additional header segment
   MUST be present in the header sequence that indicates the
   Bidirectional Read Expected Data Transfer Length.  The Expected Data
   Transfer Length field and the Bidirectional Read Expected Data
   Transfer Length field correspond to the SAM2 byte count

   If the Expected Data Transfer Length for a write and the length of
   the immediate data part that follows the command (if any) are the
   same, then no more data PDUs are expected to follow.  In this case,
   the F bit MUST be set to 1.

   If the Expected Data Transfer Length is higher than the
   FirstBurstLength (the negotiated maximum amount of unsolicited data
   the target will accept), the initiator MUST send the maximum amount
   of unsolicited data OR ONLY the immediate data, if any.

   Upon completion of a data transfer, the target informs the initiator
   (through residual counts) of how many bytes were actually processed
   (sent and/or received) by the target.

10.3.5.  CDB - SCSI Command Descriptor Block

   There are 16 bytes in the CDB field to accommodate the commonly used
   CDBs.  Whenever the CDB is larger than 16 bytes, an Extended CDB AHS
   MUST be used to contain the CDB spillover.

10.3.6.  Data Segment - Command Data

   Some SCSI commands require additional parameter data to accompany the
   SCSI command.  This data may be placed beyond the boundary of the
   iSCSI header in a data segment.  Alternatively, user data (e.g., from
   a WRITE operation) can be placed in the data segment (both cases are
   referred to as immediate data).  These data are governed by the rules
   for solicited vs. unsolicited data outlined in Section 3.2.4.2 Data
   Transfer Overview.

   
</pre>
 * 
 *
 */
public class PDUSCSICommand {
	
	private boolean isImmediate = true;
	private byte Opcode = 0x01;
	private boolean isFinal;
	private boolean isRead;
	private boolean isWrite;
	private byte ATTR;
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN  = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] ExpDataTransferLen = new byte[4];
	private byte[] CmdSN = new byte[4];
	private byte[] ExpStatSN = new byte[4];
	private byte[] CDB = new byte[16];
	public PDUSCSICommand(){}
	public PDUSCSICommand(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		isFinal = (ByteUtil.getBit(BHS[1], 0)==1);
		isRead = (ByteUtil.getBit(BHS[1], 1)==1);
		isWrite = (ByteUtil.getBit(BHS[1], 2)==1);
		ATTR = (byte)(BHS[1] & 0x03);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, ExpDataTransferLen, 0, ExpDataTransferLen.length);
		System.arraycopy(BHS, 24, CmdSN, 0, CmdSN.length);
		System.arraycopy(BHS, 28, ExpStatSN, 0, ExpStatSN.length);
		System.arraycopy(BHS, 32, CDB, 0, CDB.length);
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.SCSI_COMMAND;
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
	public boolean getRead() {
		return isRead;
	}
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}
	public boolean getWrite() {
		return isWrite;
	}
	public void setWrite(boolean isWrite) {
		this.isWrite = isWrite;
	}
	public PDUAttrEnum getATTR() {
		return PDUAttrEnum.valueOf(ATTR);
	}
	public void setATTR(PDUAttrEnum attr) {
		this.ATTR = attr.value();
	}
	public byte getTotalAHSLength() {
		return TotalAHSLength;
	}
	public int getDataSegmentLength() {
		return ByteUtil.byteArrayToInt(this.DataSegmentLength);
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
	public int getExpDataTransferLen() {
		return ByteUtil.byteArrayToInt(ExpDataTransferLen);
	}
	public void setExpDataTransferLen(int expDataTransferLen) {
		ExpDataTransferLen = ByteUtil.intToByteArray(expDataTransferLen);
	}
	public int getCmdSN() {
		return ByteUtil.byteArrayToInt(CmdSN);
	}
	public void setCmdSN(int cmdSN) {
		CmdSN =  ByteUtil.intToByteArray(cmdSN);
	}
	public int getExpStatSN() {
		return ByteUtil.byteArrayToInt(ExpStatSN);
	}
	public void setExpStatSN(int expStatSN) {
		ExpStatSN = ByteUtil.intToByteArray(expStatSN);
	}
	public byte[] getCDB() {
		return CDB;
	}
	public void setCDB(byte[] CDB) {
		System.arraycopy(CDB, 0, this.CDB, 0, Math.min(CDB.length, this.CDB.length));
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+PDUOpcodeEnum.valueOf(Opcode));
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" isRead : "+isRead);
		build.append(System.getProperty("line.separator")+" isWrite : "+isWrite);
		build.append(System.getProperty("line.separator")+" ATTR : "+PDUAttrEnum.valueOf(ATTR));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+this.getDataSegmentLength());
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" ExpDataTransferLen : "+ByteUtil.byteArrayToInt(ExpDataTransferLen));
		build.append(System.getProperty("line.separator")+" CmdSN : 0x"+ByteUtil.toHex(CmdSN));
		build.append(System.getProperty("line.separator")+" ExpStatSN : 0x"+ByteUtil.toHex(ExpStatSN));
		build.append(System.getProperty("line.separator")+" CDB : 0x"+ByteUtil.toHex(CDB));
		return build.toString();
		
	}
 	
	public static void main(String[] args) throws Exception{
		PDUSCSICommand original = new PDUSCSICommand();
		original.setImmediate(true);
		original.setFinal(false);
		original.setRead(true);
		original.setWrite(true);
		byte[] lun = new byte[128];
		for(int i=0;i<lun.length;i++){
			lun[i] = (byte)0xff;
			if(i==7)lun[i] = (byte)0x3e;
		}
		original.setLUN(lun);
		original.setCDB(new byte[]{1});
		original.setATTR(PDUAttrEnum.HeadOfQueue);
		original.setInitiatorTaskTag(1);
		original.setExpDataTransferLen(2);
		original.setCmdSN(3);
		original.setExpStatSN(4);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		PDUSCSICommand after = new PDUSCSICommand(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(this.isImmediate?0x41:0x01); //Opcode
			byte b = 0;
			b = (byte)(this.isFinal?(b|0x80):b);
			b = (byte)(this.isRead?(b|0x40):b);
			b = (byte)(this.isWrite?(b|0x20):b);
			b = (byte)(b|this.ATTR);
			dos.writeByte(b); //T|C|.|.|CSG|NSG
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.ExpDataTransferLen);
			dos.write(this.CmdSN);
			dos.write(this.ExpStatSN);
			dos.write(this.CDB);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	 
}
