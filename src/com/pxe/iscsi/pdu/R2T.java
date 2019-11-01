package com.pxe.iscsi.pdu;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.Opcode;

/**
<pre>

10.8.  Ready To Transfer (R2T)

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|.| 0x31      |1| Reserved                                    |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN                                                           |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20| Target Transfer Tag                                           |
     +---------------+---------------+---------------+---------------+
   24| StatSN                                                        |
     +---------------+---------------+---------------+---------------+
   28| ExpCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   32| MaxCmdSN                                                      |
     +---------------+---------------+---------------+---------------+
   36| R2TSN                                                         |
     +---------------+---------------+---------------+---------------+
   40| Buffer Offset                                                 |
     +---------------+---------------+---------------+---------------+
   44| Desired Data Transfer Length                                  |
     +---------------------------------------------------------------+
   48| Header-Digest (Optional)                                      |
     +---------------+---------------+---------------+---------------+

   When an initiator has submitted a SCSI Command with data that passes
   from the initiator to the target (WRITE), the target may specify
   which blocks of data it is ready to receive.  The target may request
   that the data blocks be delivered in whichever order is convenient
   for the target at that particular instant.  This information is
   passed from the target to the initiator in the Ready To Transfer
   (R2T) PDU.

   In order to allow write operations without an explicit initial R2T,
   the initiator and target MUST have negotiated the key InitialR2T to
   No during Login.

   An R2T MAY be answered with one or more SCSI Data-Out PDUs with a
   matching Target Transfer Tag.  If an R2T is answered with a single
   Data-Out PDU, the Buffer Offset in the Data PDU MUST be the same as

   the one specified by the R2T, and the data length of the Data PDU
   MUST be the same as the Desired Data Transfer Length specified in the
   R2T.  If the R2T is answered with a sequence of Data PDUs, the Buffer
   Offset and Length MUST be within the range of those specified by R2T,
   and the last PDU MUST have the F bit set to 1.  If the last PDU
   (marked with the F bit) is received before the Desired Data Transfer
   Length is transferred, a target MAY choose to Reject that

   PDU with "Protocol error" reason code.  DataPDUInOrder governs the
   Data-Out PDU ordering.  If DataPDUInOrder is set to Yes, the Buffer
   Offsets and Lengths for consecutive PDUs MUST form a continuous
   non-overlapping range and the PDUs MUST be sent in increasing offset
   order.

   The target may send several R2T PDUs.  It, therefore, can have a
   number of pending data transfers.  The number of outstanding R2T PDUs
   are limited by the value of the negotiated key MaxOutstandingR2T.
   Within a connection, outstanding R2Ts MUST be fulfilled by the
   initiator in the order in which they were received.

   R2T PDUs MAY also be used to recover Data Out PDUs.  Such an R2T
   (Recovery-R2T) is generated by a target upon detecting the loss of
   one or more Data-Out PDUs due to:

     - Digest error
     - Sequence error
     - Sequence reception timeout

   A Recovery-R2T carries the next unused R2TSN, but requests part of or
   the entire data burst that an earlier R2T (with a lower R2TSN) had
   already requested.

   DataSequenceInOrder governs the buffer offset ordering in consecutive
   R2Ts.  If DataSequenceInOrder is Yes, then consecutive R2Ts MUST
   refer to continuous non-overlapping ranges except for Recovery-R2Ts.

10.8.1.  TotalAHSLength and DataSegmentLength

   For this PDU TotalAHSLength and DataSegmentLength MUST be 0.

10.8.2.  R2TSN

   R2TSN is the R2T PDU input PDU number within the command identified
   by the Initiator Task Tag.

   For bidirectional commands R2T and Data-In PDUs share the input PDU
   numbering sequence (see Section 3.2.2.3 Data Sequencing).

10.8.3.  StatSN

   The StatSN field will contain the next StatSN.  The StatSN for this
   connection is not advanced after this PDU is sent.

10.8.4.  Desired Data Transfer Length and Buffer Offset

   The target specifies how many bytes it wants the initiator to send
   because of this R2T PDU.  The target may request the data from the
   initiator in several chunks, not necessarily in the original order of
   the data.  The target, therefore, also specifies a Buffer Offset that
   indicates the point at which the data transfer should begin, relative
   to the beginning of the total data transfer.  The Desired Data
   Transfer Length MUST NOT be 0 and MUST not exceed MaxBurstLength.

10.8.5.  Target Transfer Tag

   The target assigns its own tag to each R2T request that it sends to
   the initiator.  This tag can be used by the target to easily identify
   the data it receives.  The Target Transfer Tag and LUN are copied in
   the outgoing data PDUs and are only used by the target.  There is no
   protocol rule about the Target Transfer Tag except that the value
   0xffffffff is reserved and MUST NOT be sent by a target in an R2T.





   
   
</pre>
 * 
 *
 */
public class R2T {
	
	private byte opcode = 0x31;
	private boolean isFinal = true;
	private byte TotalAHSLength = 0;
	private byte[] DataSegmentLength = new byte[]{0,0,0};
	private byte[] LUN = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] TargetTransferTag = new byte[4];
	private byte[] StatSN = new byte[4];
	private byte[] ExpCmdSN = new byte[4];
	private byte[] MaxCmdSN = new byte[4];
	private byte[] R2TSN = new byte[4];
	private byte[] BufferOffset = new byte[4];
	private byte[] DesiredDataLength = new byte[4];
	public R2T(){}
	public R2T(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		System.arraycopy(BHS, 8, LUN, 0, LUN.length); 
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, TargetTransferTag, 0, TargetTransferTag.length);
		System.arraycopy(BHS, 24, StatSN, 0, StatSN.length);
		System.arraycopy(BHS, 28, ExpCmdSN, 0, ExpCmdSN.length);
		System.arraycopy(BHS, 32, MaxCmdSN, 0, MaxCmdSN.length);
		System.arraycopy(BHS, 36, R2TSN, 0, R2TSN.length);
		System.arraycopy(BHS, 40, BufferOffset, 0, BufferOffset.length);
		System.arraycopy(BHS, 44, DesiredDataLength, 0, DesiredDataLength.length);
	}
	
	public Opcode getOpcode() {
		return Opcode.R2T;
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
	public int getR2TSN() {
		return ByteUtil.byteArrayToInt(R2TSN);
	}
	public void setR2TSN(int R2TSN) {
		this.R2TSN = ByteUtil.intToByteArray(R2TSN);
	}
	public int getBufferOffset() {
		return ByteUtil.byteArrayToInt(BufferOffset);
	}
	public void setBufferOffset(int BufferOffset) {
		this.BufferOffset = ByteUtil.intToByteArray(BufferOffset);
	}
	public int getDesiredDataLength() {
		return ByteUtil.byteArrayToInt(DesiredDataLength);
	}
	public void setDesiredDataLength(int DesiredDataLength) {
		this.DesiredDataLength = ByteUtil.intToByteArray(DesiredDataLength);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+Opcode.valueOf(opcode));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUN : 0x"+ByteUtil.toHex(LUN));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" TargetTransferTag : "+ByteUtil.byteArrayToInt(this.TargetTransferTag));
		build.append(System.getProperty("line.separator")+" StatSN : "+ByteUtil.byteArrayToInt(this.StatSN));
		build.append(System.getProperty("line.separator")+" ExpCmdSN : "+ByteUtil.byteArrayToInt(this.ExpCmdSN));
		build.append(System.getProperty("line.separator")+" MaxCmdSN : "+ByteUtil.byteArrayToInt(this.MaxCmdSN));
		build.append(System.getProperty("line.separator")+" R2TSN : "+ByteUtil.byteArrayToInt(this.R2TSN));
		build.append(System.getProperty("line.separator")+" BufferOffset : "+ByteUtil.byteArrayToInt(this.BufferOffset));
		build.append(System.getProperty("line.separator")+" DesiredDataLength : "+ByteUtil.byteArrayToInt(this.DesiredDataLength));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x31); //Opcode
			dos.writeByte(0x80);
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.TotalAHSLength);
			dos.write(this.DataSegmentLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(this.LUN);
			dos.write(this.InitiatorTaskTag);
			dos.write(this.TargetTransferTag);
			dos.write(this.StatSN);
			dos.write(this.ExpCmdSN);
			dos.write(this.MaxCmdSN);
			dos.write(this.R2TSN);
			dos.write(this.BufferOffset);
			dos.write(this.DesiredDataLength);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		R2T original = new R2T();
		original.setLUN(new byte[]{1});
		original.setInitiatorTaskTag(1);
		original.setTargetTransferTag(2);
		original.setStatSN(3);
		original.setExpCmdSN(4);
		original.setMaxCmdSN(5);
		original.setR2TSN(6);
		original.setBufferOffset(7);
		original.setDesiredDataLength(8);
		System.out.println(original);
		byte[] data = original.toByte();
		byte[] BHS   = new byte[48];
		System.arraycopy(data, 0, BHS, 0, BHS.length);
		R2T after = new R2T(BHS);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}