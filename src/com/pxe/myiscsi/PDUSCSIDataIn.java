package com.pxe.myiscsi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;

/**
<pre>

   The SCSI Data-In PDU for READ operations has the following format:

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x25      |F|A|0 0 0|O|U|S| Reserved      |Status or Rsvd |
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
   24| StatSN or Reserved                                            |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36| DataSN                                                        |
     +---------------+---------------+---------------+---------------+
   40| Buffer Offset                                                 |
     +---------------+---------------+---------------+---------------+
   44| Residual Count                                                |
     +---------------+---------------+---------------+---------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+
     / DataSegment                                                   /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
     | Data-Digest (Optional)                                        |
     +---------------+---------------+---------------+---------------+

   Status can accompany the last Data-In PDU if the command did not end
   with an exception (i.e., the status is "good status" - GOOD,
   CONDITION MET or INTERMEDIATE CONDITION MET).  The presence of status
   (and of a residual count) is signaled though the S flag bit.
   Although targets MAY choose to send even non-exception status in
   separate responses, initiators MUST support non-exception status in
   Data-In PDUs.
   
10.7.1.  F (Final) Bit

   For outgoing data, this bit is 1 for the last PDU of unsolicited data
   or the last PDU of a sequence that answers an R2T.

   For incoming data, this bit is 1 for the last input (read) data PDU
   of a sequence.  Input can be split into several sequences, each
   having its own F bit.  Splitting the data stream into sequences does
   not affect DataSN counting on Data-In PDUs.  It MAY be used as a
   "change direction" indication for Bidirectional operations that need
   such a change.

   DataSegmentLength MUST not exceed MaxRecvDataSegmentLength for the
   direction it is sent and the total of all the DataSegmentLength of
   all PDUs in a sequence MUST not exceed MaxBurstLength (or
   FirstBurstLength for unsolicited data).  However the number of
   individual PDUs in a sequence (or in total) may be higher than the
   MaxBurstLength (or FirstBurstLength) to MaxRecvDataSegmentLength
   ratio (as PDUs may be limited in length by the sender capabilities).
   Using DataSegmentLength of 0 may increase beyond what is reasonable
   for the number of PDUs and should therefore be avoided.

   For Bidirectional operations, the F bit is 1 for both the end of the
   input sequences and the end of the output sequences.

10.7.2.  A (Acknowledge) Bit

   For sessions with ErrorRecoveryLevel 1 or higher, the target sets
   this bit to 1 to indicate that it requests a positive acknowledgement
   from the initiator for the data received.  The target should use the
   A bit moderately; it MAY only set the A bit to 1 once every
   MaxBurstLength bytes, or on the last Data-In PDU that concludes the
   entire requested read data transfer for the task from the target's
   perspective, and it MUST NOT do so more frequently.  The target MUST
   NOT set to 1 the A bit for sessions with ErrorRecoveryLevel=0.  The
   initiator MUST ignore the A bit set to 1 for sessions with
   ErrorRecoveryLevel=0.

   On receiving a Data-In PDU with the A bit set to 1 on a session with
   ErrorRecoveryLevel greater than 0, if there are no holes in the read
   data until that Data-In PDU, the initiator MUST issue a SNACK of type
   DataACK except when it is able to acknowledge the status for the task
   immediately via ExpStatSN on other outbound PDUs if the status for
   the task is also received.  In the latter case (acknowledgement
   through ExpStatSN), sending a SNACK of type DataACK in response to
   the A bit is OPTIONAL, but if it is done, it must not be sent after
   the status acknowledgement through ExpStatSN.  If the initiator has
   detected holes in the read data prior to that Data-In PDU, it MUST

   postpone issuing the SNACK of type DataACK until the holes are
   filled.  An initiator also MUST NOT acknowledge the status for the
   task before those holes are filled.  A status acknowledgement for a
   task that generated the Data-In PDUs is considered by the target as
   an implicit acknowledgement of the Data-In PDUs if such an
   acknowledgement was requested by the target.

10.7.3.  Flags (byte 1)

   The last SCSI Data packet sent from a target to an initiator for a
   SCSI command that completed successfully (with a status of GOOD,
   CONDITION MET, INTERMEDIATE or INTERMEDIATE CONDITION MET) may also
   optionally contain the Status for the data transfer.  As Sense Data
   cannot be sent together with the Command Status, if the command is
   completed with an error, then the response and sense data MUST be
   sent in a SCSI Response PDU (i.e., MUST NOT be sent in a SCSI Data
   packet).  If Status is sent with the data, then a SCSI Response PDU
   MUST NOT be sent as this would violate SCSI rules (a single status).
   For Bidirectional commands, the status MUST be sent in a SCSI
   Response PDU.

      bit 2-4 - Reserved.

      bit 5-6 - used the same as in a SCSI Response.  These bits are
                only valid when S is set to 1.  For details see Section
                10.4.1 Flags (byte 1).

      bit 7 S (status)- set to indicate that the Command Status field
                contains status.  If this bit is set to 1, the F bit
                MUST also be set to 1.

   The fields StatSN, Status, and Residual Count only have meaningful
   content if the S bit is set to 1 and their values are defined in
   Section 10.4 SCSI Response.

10.7.4.  Target Transfer Tag and LUN

   On outgoing data, the Target Transfer Tag is provided to the target
   if the transfer is honoring an R2T.  In this case, the Target
   Transfer Tag field is a replica of the Target Transfer Tag provided
   with the R2T.

   On incoming data, the Target Transfer Tag and LUN MUST be provided by
   the target if the A bit is set to 1; otherwise they are reserved.
   The Target Transfer Tag and LUN are copied by the initiator into the
   SNACK  of type DataACK that it issues as a result of receiving a SCSI
   Data-In PDU with the A bit set to 1.

   The Target Transfer Tag values are not specified by this protocol
   except that the value 0xffffffff is reserved and means that the
   Target Transfer Tag is not supplied.  If the Target Transfer Tag is
   provided, then the LUN field MUST hold a valid value and be
   consistent with whatever was specified with the command; otherwise,
   the LUN field is reserved.

10.7.5.  DataSN

   For input (read) or bidirectional Data-In PDUs, the DataSN is the
   input PDU number within the data transfer for the command identified
   by the Initiator Task Tag.

   R2T and Data-In PDUs, in the context of bidirectional commands, share
   the numbering sequence (see Section 3.2.2.3 Data Sequencing).

   For output (write) data PDUs, the DataSN is the Data-Out PDU number
   within the current output sequence.  The current output sequence is
   either identified by the Initiator Task Tag (for unsolicited data) or
   is a data sequence generated for one R2T (for data solicited through
   R2T).

10.7.6.  Buffer Offset

   The Buffer Offset field contains the offset of this PDU payload data
   within the complete data transfer.  The sum of the buffer offset and
   length should not exceed the expected transfer length for the
   command.

   The order of data PDUs within a sequence is determined by
   DataPDUInOrder.  When set to Yes, it means that PDUs have to be in
   increasing Buffer Offset order and overlays are forbidden.

   The ordering between sequences is determined by DataSequenceInOrder.
   When set to Yes, it means that sequences have to be in increasing
   Buffer Offset order and overlays are forbidden.

10.7.7.  DataSegmentLength

   This is the data payload length of a SCSI Data-In or SCSI Data-Out
   PDU.  The sending of 0 length data segments should be avoided, but
   initiators and targets MUST be able to properly receive 0 length data
   segments.

   The Data Segments of Data-In and Data-Out PDUs SHOULD be filled to
   the integer number of 4 byte words (real payload) unless the F bit is
   set to 1.





   
   
</pre>
 * 
 *
 */
public class PDUSCSIDataIn {
	
	private byte Opcode = 0x25;
	private boolean isFinal;
	private boolean isAck;
	private boolean isResidualOverflow;
	private boolean isResidualUnderflow;
	private boolean isStatus;
	private byte StatusorRsvd; 
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUN = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] DataSN = new byte[4];
	private byte[] BufferOffset = new byte[4];
	private byte[] ResidualCount = new byte[4];
	private byte[] DataSegment = new byte[0];
	public PDUSCSIDataIn(){}
	public PDUSCSIDataIn(byte[] BHS,byte[] DataSegment) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isFinal = (ByteUtil.getBit(BHS[1], 0)==1);
		isAck = (ByteUtil.getBit(BHS[1], 1)==1);
		isResidualOverflow = (ByteUtil.getBit(BHS[1], 5)==1);
		isResidualUnderflow = (ByteUtil.getBit(BHS[1], 6)==1);
		isStatus = (ByteUtil.getBit(BHS[1], 7)==1);
		StatusorRsvd = BHS[3];
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 36, DataSN, 0, DataSN.length);
		System.arraycopy(BHS, 40, BufferOffset, 0, BufferOffset.length);
		System.arraycopy(BHS, 44, ResidualCount, 0, ResidualCount.length);
		this.DataSegment = DataSegment;
	}
	
	public PDUOpcodeEnum getOpcode() {
		return PDUOpcodeEnum.SCSI_DATA_IN;
	}
	public boolean getFinal(){
		return isFinal;
	}
	public void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}
	public boolean getAck() {
		return isAck;
	}
	public void setAck(boolean isAck) {
		this.isAck = isAck;
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
	public boolean getStatus() {
		return isStatus;
	}
	public void setStatus(boolean isStatus) {
		this.isStatus = isStatus;
	}
	public byte getStatusorRsvd() {
		return StatusorRsvd;
	}
	public void setStatusorRsvd(byte statusorRsvd) {
		StatusorRsvd = statusorRsvd;
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
	public int getDataSN() {
		return ByteUtil.byteArrayToInt(DataSN);
	}
	public void setDataSN(int DataSN) {
		this.DataSN = ByteUtil.intToByteArray(DataSN);
	}
	public int getBufferOffset() {
		return ByteUtil.byteArrayToInt(BufferOffset);
	}
	public void setBufferOffset(int BufferOffset) {
		this.BufferOffset = ByteUtil.intToByteArray(BufferOffset);
	}
	public int getResidualCount() {
		return ByteUtil.byteArrayToInt(ResidualCount);
	}
	public void setResidualCount(int ResidualCount) {
		this.ResidualCount = ByteUtil.intToByteArray(ResidualCount);
	}
	public byte[] getDataSegment() {
		return DataSegment;
	}
	public void setDataSegment(byte[] dataSegment) {
		DataSegment = dataSegment;
		DataSegmentLength[0] = (byte) ((dataSegment.length & 0x00ff0000) >> 16);
		DataSegmentLength[1] = (byte) ((dataSegment.length & 0x0000ff00) >> 8);
		DataSegmentLength[2] = (byte) ((dataSegment.length & 0x000000ff) >> 0);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+PDUOpcodeEnum.valueOf(Opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" isAck : "+this.isAck);
		build.append(System.getProperty("line.separator")+" isResidualOverflow : "+this.isResidualOverflow);
		build.append(System.getProperty("line.separator")+" isResidualUnderflow : "+this.isResidualUnderflow);
		build.append(System.getProperty("line.separator")+" isStatus : "+this.isStatus);
		build.append(System.getProperty("line.separator")+" StatusorRsvd : "+this.StatusorRsvd);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(this.TargetTransferTag));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		build.append(System.getProperty("line.separator")+" DataSN : "+ByteUtil.byteArrayToInt(this.DataSN));
		build.append(System.getProperty("line.separator")+" BufferOffset : "+ByteUtil.byteArrayToInt(this.BufferOffset));
		build.append(System.getProperty("line.separator")+" ResidualCount : "+ByteUtil.byteArrayToInt(this.ResidualCount));
		build.append(System.getProperty("line.separator")+" DataSegment : 0x"+ByteUtil.toHex(this.DataSegment));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x25); //Opcode
			byte b = 0;
			b = (byte)(this.isFinal?(b|0x80):b);
			b = (byte)(this.isAck?(b|0x40):b);
			b = (byte)(this.isResidualOverflow?(b|0x04):b);
			b = (byte)(this.isResidualUnderflow?(b|0x02):b);
			b = (byte)(this.isStatus?(b|0x01):b);
			dos.writeByte(b);
			dos.write(new byte[]{0}); //Reserved
			dos.writeByte(this.StatusorRsvd);
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.write(this.DataSN);
			dos.write(this.BufferOffset);
			dos.write(this.ResidualCount);
			dos.write(this.DataSegment);
			//write (All PDU segments and digests are padded to the closest integer number of four byte words.)
			int count = this.DataSegment.length % 4 == 0?0:(4-this.DataSegment.length % 4);
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
		PDUSCSIDataIn original = new PDUSCSIDataIn();
		original.setFinal(true);
		original.setAck(false);
		original.setResidualOverflow(false);
		original.setResidualUnderflow(true);
		original.setStatus(true);
		original.setStatusorRsvd((byte)100);
		original.setLUN(new byte[]{1});
		original.setInitiatorTaskTag(1);
		original.setTargetTransferTag(2);
		original.setStatSN(3);
		original.setExpCmdSN(4);
		original.setMaxCmdSN(5);
		original.setDataSN(6);
		original.setBufferOffset(7);
		original.setResidualCount(8);
		original.setDataSegment("hfdsakfdlsajfldsa".getBytes());
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		byte[] dataS = new byte[data.length-BHS.length];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		System.arraycopy(data, 48, dataS, 0, dataS.length);
		PDUSCSIDataIn after = new PDUSCSIDataIn(BHS,dataS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
