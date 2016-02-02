package com.pxe.iscsi.pdu;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.Opcode;

/**
<pre>
10.2.1.  Basic Header Segment (BHS)

   The BHS is 48 bytes long.  The Opcode and DataSegmentLength fields
   appear in all iSCSI PDUs.  In addition, when used, the Initiator Task
   Tag and Logical Unit Number always appear in the same location in the
   header.
   The format of the BHS is: 

   Byte/     0       |       1       |       2       |       3       |
      /              |               |               |               |
     |0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|0 1 2 3 4 5 6 7|
     +---------------+---------------+---------------+---------------+
    0|.|I| Opcode    |F|  Opcode-specific fields                     |
     +---------------+---------------+---------------+---------------+
    4|TotalAHSLength | DataSegmentLength                             |
     +---------------+---------------+---------------+---------------+
    8| LUN or Opcode-specific fields                                 |
     +                                                               +
   12|                                                               |
     +---------------+---------------+---------------+---------------+
   16| Initiator Task Tag                                            |
     +---------------+---------------+---------------+---------------+
   20/ Opcode-specific fields                                        /
    +/                                                               /
     +---------------+---------------+---------------+---------------+
   48
   
10.2.1.1  I

   For request PDUs, the I bit set to 1 is an immediate delivery marker.

10.2.1.2.  Opcode

   The Opcode indicates the type of iSCSI PDU the header encapsulates.

   The Opcodes are divided into two categories: initiator opcodes and
   target opcodes.  Initiator opcodes are in PDUs sent by the initiator
   (request PDUs).  Target opcodes are in PDUs sent by the target
   (response PDUs).

   Initiators MUST NOT use target opcodes and targets MUST NOT use
   initiator opcodes.

   Initiator opcodes defined in this specification are:

     0x00 NOP-Out
     0x01 SCSI Command (encapsulates a SCSI Command Descriptor Block)
     0x02 SCSI Task Management function request
     0x03 Login Request
     0x04 Text Request
     0x05 SCSI Data-Out (for WRITE operations)
     0x06 Logout Request
     0x10 SNACK Request
     0x1c-0x1e Vendor specific codes
Target opcodes are:

     0x20 NOP-In
     0x21 SCSI Response - contains SCSI status and possibly sense
      information or other response information.
     0x22 SCSI Task Management function response
     0x23 Login Response
     0x24 Text Response
     0x25 SCSI Data-In - for READ operations.
     0x26 Logout Response
     0x31 Ready To Transfer (R2T) - sent by target when it is ready
      to receive data.
     0x32 Asynchronous Message - sent by target to indicate certain
      special conditions.
     0x3c-0x3e Vendor specific codes
     0x3f Reject

   All other opcodes are reserved.

10.2.1.3.  Final (F) bit

   When set to 1 it indicates the final (or only) PDU of a sequence.

10.2.1.4.  Opcode-specific Fields

   These fields have different meanings for different opcode types.

10.2.1.5.  TotalAHSLength

   Total length of all AHS header segments in units of four byte words
   including padding, if any.

   The TotalAHSLength is only used in PDUs that have an AHS and MUST be
   0 in all other PDUs.

10.2.1.6.  DataSegmentLength

   This is the data segment payload length in bytes (excluding padding).
   The DataSegmentLength MUST be 0 whenever the PDU has no data segment.

10.2.1.7.  LUN

   Some opcodes operate on a specific Logical Unit.  The Logical Unit
   Number (LUN) field identifies which Logical Unit.  If the opcode does
   not relate to a Logical Unit, this field is either ignored or may be
   used in an opcode specific way.  The LUN field is 64-bits and should
   be formatted in accordance with [SAM2].  For example, LUN[0] from
   [SAM2] is BHS byte 8 and so on up to LUN[7] from [SAM2], which is BHS
   byte 15.

10.2.1.8.  Initiator Task Tag

   The initiator assigns a Task Tag to each iSCSI task it issues.  While
   a task exists, this tag MUST uniquely identify the task session-wide.
   SCSI may also use the initiator task tag as part of the SCSI task
   identifier when the timespan during which an iSCSI initiator task tag
   must be unique extends over the timespan during which a SCSI task tag
   must be unique.  However, the iSCSI Initiator Task Tag must exist and
   be unique even for untagged SCSI commands.

   
   
</pre>
 * 
 *
 */
public class BasicHeaderSegment {
	
	private boolean isImmediate;
	private byte opcode;
	private boolean isFinal;
	private byte[] OpcodeSpecificFields1 = new byte[3];
	private byte TotalAHSLength;
	private byte[] DataSegmentLength = new byte[3];
	private byte[] LUNorOpcodeSpecificFields = new byte[8];
	private byte[] InitiatorTaskTag = new byte[4];
	private byte[] OpcodeSpecificFields2 = new byte[28];
	public BasicHeaderSegment(byte[] BHS) throws Exception{
		if(BHS.length!=48)throw new Exception("illegic Basic Header Segment Size , the proper length is 48");
		isImmediate = (ByteUtil.getBit(BHS[0], 1)==1);
		opcode = (byte)(BHS[0] & 0x3f);
		isFinal = (ByteUtil.getBit(BHS[1], 0)==1);
		System.arraycopy(BHS, 1, OpcodeSpecificFields1, 0, OpcodeSpecificFields1.length);
		TotalAHSLength = BHS[4];
		System.arraycopy(BHS, 5, DataSegmentLength, 0, DataSegmentLength.length);
		System.arraycopy(BHS, 8, LUNorOpcodeSpecificFields, 0, LUNorOpcodeSpecificFields.length);
		System.arraycopy(BHS, 16, InitiatorTaskTag, 0, InitiatorTaskTag.length);
		System.arraycopy(BHS, 20, OpcodeSpecificFields2, 0, OpcodeSpecificFields2.length);
		
	}
	
	
	public int getTotalAHSLength() {
		return TotalAHSLength;
	}
	
	public int getDataSegmentLength() { 
		return ByteUtil.byteArrayToInt(DataSegmentLength);
	}

	public Opcode getOpcode() {
		return Opcode.valueOf(opcode);
	}

	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" isImmediate : "+isImmediate);
		build.append(System.getProperty("line.separator")+" Opcode : 0x"+ByteUtil.toHex(new byte[]{opcode}));
		build.append(System.getProperty("line.separator")+" isFinal : "+isFinal);
		build.append(System.getProperty("line.separator")+" OpcodeSpecificFields1 :  0x"+ByteUtil.toHex(OpcodeSpecificFields1));
		build.append(System.getProperty("line.separator")+" TotalAHSLength : "+(short)TotalAHSLength);
		build.append(System.getProperty("line.separator")+" DataSegmentLength : "+ByteUtil.byteArrayToInt(DataSegmentLength));
		build.append(System.getProperty("line.separator")+" LUNorOpcodeSpecificFields : 0x"+ByteUtil.toHex(LUNorOpcodeSpecificFields));
		build.append(System.getProperty("line.separator")+" InitiatorTaskTag : "+ByteUtil.byteArrayToInt(InitiatorTaskTag));
		build.append(System.getProperty("line.separator")+" OpcodeSpecificFields2 : 0x"+ByteUtil.toHex(OpcodeSpecificFields2));
		return build.toString();
	}
 	
	
	 
}
