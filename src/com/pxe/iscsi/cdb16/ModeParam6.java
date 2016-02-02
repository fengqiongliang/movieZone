package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
import com.pxe.iscsi.cdb16.ReportLUN.SelectReport;
 
/**
<pre>
4.3 Mode parameters

4.3.1 Mode parameters overview

   This subclause describes the mode parameter headers, block descriptors, and mode pages used with MODE 
   SELECT command (see 3.12 and 3.13) and MODE SENSE command (see 3.14 and 3.15) that are applicable 
   to all SCSI devices. Subpages are identical to mode pages except that they include a SUBPAGE CODE field 
   that further differentiates the mode page contents. Mode pages specific to each device type are described in 
   the command standard that applies to that device type.

   Note. Many of the mode parameters in the following pages are changeable. A MODE SENSE com-
   mand with the PC bit set to one will return a mask indicating the mode parameters that may 
   be changed by a SCSI initiator port. Seagate disc drive product manuals indicate which 
   pages a drive supports, what the default mode values are, and what mode parameters may 
   be changed.

4.3.2 Mode parameter list format
   The mode parameter list shown in table 261 contains a header, followed by zero or more block descriptors, fol-
   lowed by zero or more variable-length mode pages. Parameter lists are defined for each device type.
   
                     Table 261 — Mode parameter list
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |                            MODE PARAMETER HEADER                        |
   |-------------------------------------------------------------------------|
   |                            BLOCK DESCRIPTOR(S)                          |
   |-------------------------------------------------------------------------|
   |      MODE PAGE(S) OR VENDOR SPECIFIC (E.G., PAGE CODE SET TO ZERO)      |
   |-------------------------------------------------------------------------|
 
4.3.3 Mode parameter header formats

                     Table 262 — Mode parameter header(6)
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                 MODE DATA LENGTH                              |
   |-------------------------------------------------------------------------|
   |    1    |                 MEDIUM TYPE                                   |
   |-------------------------------------------------------------------------|
   |    2    |   WP  |    Reserved   |DPOFUA |          Reserved             |
   |-------------------------------------------------------------------------|
   |    3    |                 BLOCK DESCRIPTOR LENGTH                       |
   |-------------------------------------------------------------------------|
  
   The mode parameter header that is used by the MODE SELECT(10) command (see 3.12) and the MODE 
   SENSE(10) command (see 3.14) is defined in table 263.
   
                     Table 263 —  Mode parameter header(10)
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | (MSB) |                                                       |
   |         |                 MODE DATA LENGTH                              |
   |    1    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    2    |                 MEDIUM TYPE                                   |
   |-------------------------------------------------------------------------|
   |    3    |   WP  |    Reserved   |DPOFUA |          Reserved             |
   |-------------------------------------------------------------------------|
   |    4    |                 Reserved                              |LONGLBA|
   |-------------------------------------------------------------------------|
   |    5    |                 Reserved                                      |
   |-------------------------------------------------------------------------|
   |    6    | (MSB) |                                                       |
   |         |                 BLOCK DESCRIPTOR LENGTH                       |
   |    7    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   
   MODE DATA LENGTH field
   When using the MODE SENSE command, the MODE DATA LENGTH field indicates the length in bytes of the following 
   data that is available to be transferred. The mode data length does not include the number of bytes in the MODE DATA 
   LENGTH field. When using the MODE SELECT command, this field is reserved.
   
   Note. Logical units that support more than 256 bytes of block descriptors and mode pages may need to 
         implement ten-byte mode commands. The mode data length field in the six-byte CDB header limits 
         the returned data to 256 bytes.
   
   MEDIUM TYPE field
   The contents of the MEDIUM TYPE field are unique for each device type. Refer to the mode parameters subclause of the 
   specific device type command standard for definition of these values. Some device types reserve this field.
   
   WP (Write Protect) bit
     1 A WP bit set to one indicates that the medium is write-protected. The medium may be write protected when the soft-
       ware write protect (SWP) bit in the Control mode page (see 4.3.8) is set to one or if another vendor specific mecha-
       nism causes the medium to be write protected. 
     0 A WP bit set to zero indicates that the medium is not write-protected.
	 
   DPOFUA (DPO and FUA support) bit
     0 A DPOFUA bit set to zero indicates that the device server does not support the DPO and FUA bits. 
     1 When used with the MODE SENSE command, a DPOFUA bit set to one indicates that the device server supports 
       the DPO and FUA bits (see 3.20)
	   
   LONGLBA (Long LBA) bit
     0 If the Long LBA (LONGLBA) bit is set to zero, the mode parameter block descriptor(s), if any, are each eight bytes long.
     1 If the LONGLBA bit is set to one, the mode parameter block descriptor(s), if any, are each sixteen bytes long.
  
   BLOCK DESCRIPTOR LENGTH field
   The BLOCK DESCRIPTOR LENGTH field contains the length in bytes of all the block descriptors. It is equal to the number 
   of block descriptors times eight if the LONGLBA bit is set to zero or times sixteen if the LONGLBA bit is set to one, and 
   does not include mode pages or vendor specific parameters (e.g., page code set to zero), if any, that may follow the last 
   block descriptor. A block descriptor length of zero indicates that no block descriptors are included in the mode parameter 
   list. This condition shall not be considered an error.
   
   4.3.4 Mode parameter block descriptors
   4.3.4.1 Mode block descriptors overview
   If the device server returns a mode parameter block descriptor, it shall return a short LBA mode parameter 
   block descriptor (see 4.3.4.2) in the mode parameter data in response to:
      a) a MODE SENSE (6) command; or
      b) a MODE SENSE (10) command with the LLBAA bit set to zero.
	  
   If the device server returns a mode parameter block descriptor and the number of logical blocks is greater than 
   FFFFFFFFh, it may return a long LBA mode parameter block descriptor (see 4.3.4.3) in the mode parameter 
   data in response to a MODE SENSE (10) command with the LLBAA bit set to one.
   
   If the application client sends a mode parameter block descriptor in the mode parameter list, it shall send a 
   short LBA mode parameter block descriptor (see 4.3.4.2) for a MODE SELECT (6) command.
   
   If the application client sends a mode parameter block descriptor in the mode parameter list, it may send a long 
   LBA mode parameter block descriptor (see 4.3.4.3) for a MODE SELECT (10) command.
   
   Support for the mode parameter block descriptors is optional. The device server shall establish a unit attention 
   condition with the additional sense code of MODE PARAMETERS CHANGED (see SPC-4 and SAM-4) when 
   the block descriptor values are changed.
   
   4.3.4.2 Short LBA mode parameter block descriptor
   Table x defines the block descriptor for direct-access block devices used:
   a. with the MODE SELECT (6) and MODE SENSE (6) commands, and
   b. with the MODE SELECT (10) and MODE SENSE (10) commands when the LONGLBA bit is set to zero in 
      the mode parameter header (see 4.3.3))
   Table 264. Short LBA mode parameter block descriptor  

               Table 264 —  Short LBA mode parameter block descriptor
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | (MSB) |                                                       |
   |         |                 NUMBER OF LOGICAL BLOCKS                      |
   |    3    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    4    |                 Reserved                                      |
   |-------------------------------------------------------------------------|
   |    5    | (MSB) |                                                       |
   |         |                 LOGICAL BLOCK LENGTH                          |
   |    7    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|

   A device server shall respond to a MODE SENSE command (see SPC-4) by reporting the number of logical 
   blocks specified in the NUMBER OF LOGICAL BLOCKS field sent in the last MODE SELECT command that 
   contained a mode parameter block descriptor. If no MODE SELECT command with a mode parameter block 
   descriptor has been received then the current number of logical blocks shall be returned. To determine the 
   number of logical blocks at which the logical unit is  currently formatted, the application client shall use the 
   READ CAPACITY command (see 3.24) rather than the MODE SENSE command.
   
   On a MODE SENSE command, the device server may return a value of zero indicating that it does not report 
   the number of logical blocks in the short LBA mode parameter block descriptor.
   
   On a MODE SENSE command, if the number of logical blocks on the medium exceeds the maximum value 
   that is able to be specified in the NUMBER OF LOGICAL BLOCKS field, the device server shall return a value 
   of FFFFFFFh.
   
   If the logical unit does not support changing its capacity by changing the NUMBER OF LOGICAL BLOCKS 
   field using the MODE SELECT command (see 3.11  and 3.12), the value in the NUMBER OF LOGICAL 
   BLOCKS field is ignored.
   
   a. If the NUMBER OF LOGICAL BLOCKS field is set to zero, the logical unit shall retain its current capacity if 
      the logical block length has not changed. If the NUMBER OF LOGICAL BLOCKS field is set to zero and the 
      content of the LOGICAL BLOCK LENGTH field (i.e., new logical block length) is different than the current 
      logical block length, the logical unit shall be set to its maximum capacity when the new logical block length 
      takes effect (i.e., after a successful FORMAT UNIT command);
	  
   b. If the NUMBER OF LOGICAL BLOCKS field is greater than zero and less than or equal to its maximum 
      capacity, the logical unit shall be set to that number of logical blocks. If the content of the LOGICAL BLOCK 
      LENGTH field is the same as the current logical block length, the logical unit shall not become format cor-
      rupt. This capacity setting shall be retained through power cycles, hard resets, logical unit resets, and I_T 
      nexus losses. If the content of the LOGICAL BLOCK LENGTH field is the same as the current logical block 
      length this capacity setting shall take effect on successful completion of the MODE SELECT command. If 
      the content of the LOGICAL BLOCK LENGTH field (i.e., new logical block length) is different than the cur-
      rent logical block length this capacity setting shall take effect when the new logical block length takes effect 
      (i.e., after a successful FORMAT UNIT command);
	  
   c. If the NUMBER OF LOGICAL BLOCKS field is set to a value greater than the maximum capacity of the 
      device and less than FFFFFFFFh, then the MODE SELECT command shall be terminated with CHECK 
      CONDITION status with the sense key set to ILLEGAL REQUEST and the additional sense code set to 
      INVALID FIELD IN PARAMETER LIST. The logical unit shall retain its previous logical block descriptor set-
      tings; or
	  
   d. If the NUMBER OF LOGICAL BLOCKS field is set to FFFFFFFFh, the logical unit shall be set to its maxi-
      mum capacity. If the content of the LOGICAL BLOCK LENGTH field is the same as the current logical block 
      length, the logical unit shall not become format corrupt. This capacity setting shall be retained through 
      power cycles, hard resets, logical unit resets, and I_T nexus losses. If the content of the LOGICAL BLOCK 
      LENGTH field is the same as the current logical block length this capacity setting shall take effect of suc-
      cessful completion of the MODE SELECT command. If the content of the LOGICAL BLOCK LENGTH field 
      (i.e., new logical block length) is different than the current logical block length this capacity setting shall take 
      effect when the new logical block length takes effect (i.e., after a successful FORMAT UNIT command).
	  
   The LOGICAL BLOCK LENGTH field specifies the length in bytes of each logical block. No change shall be 
   made to any logical blocks on the medium until a format operation is initiated by an application client.

   A device server shall respond to a MODE SENSE command (see 3.13 and 3.14) by reporting the length of the 
   logical blocks as specified in the LOGICAL BLOCK LENGTH field sent in the last MODE SELECT command 
   that contained a mode parameter block descriptor. If no MODE SELECT command with a block descriptor has 
   been received then the current logical block length shall be returned (e.g., if the logical block length is 512 
   bytes and a MODE SELECT command occurs with the LOGICAL BLOCK LENGTH field set to 520 bytes, any 
   MODE SENSE commands would return 520 in the LOGICAL BLOCK LENGTH field). To determine the logical 
   block length at which the logical unit is currently formatted, the application client shall use the READ CAPAC-
   ITY command rather than the MODE SELECT command.
   
   4.3.4.3 Long LBA mode parameter block descriptor
   Table 265 defines the block descriptor for direct-access block devices used with the MODE SELECT (10) com-
   mand and MODE SENSE (10) command when the LONGLBA bit is set to one in the mode parameter header (see 4.3.3).    
   
   Table 265. Long LBA mode parameter block descriptor

               Table 265 —  Long LBA mode parameter block descriptor
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | (MSB) |                                                       |
   |         |                 NUMBER OF LOGICAL BLOCKS                      |
   |    7    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    8    |                                                               |
   |         |                 Reserved                                      |
   |    11   |                                                               |
   |-------------------------------------------------------------------------|
   |    12   | (MSB) |                                                       |
   |         |                 LOGICAL BLOCK LENGTH                          |
   |    15   |                                                       | (LSB) |
   |-------------------------------------------------------------------------|

   A device server shall respond to a MODE SENSE command (see 3.13 and 3.14) by reporting the number of 
   logical blocks specified in the NUMBER OF LOGICAL BLOCKS field sent in the last MODE SELECT com-
   mand that contained a mode parameter block descriptor. If no MODE SELECT command with a mode param-
   eter block descriptor has been received then the current number of logical blocks shall be returned. To 
   determine the number of logical blocks at which the logical unit is currently formatted, the application client 
   shall use the READ CAPACITY command rather than the MODE SENSE command.
   
   On a MODE SENSE command, the device server may return a value of zero indicating that it does not report 
   the number of logical blocks in the long LBA mode parameter block descriptor.

   If the logical unit does not support changing its capacity by changing the NUMBER OF LOGICAL BLOCKS 
   field using the MODE SELECT command (see SPC-4), the value in the NUMBER OF LOGICAL BLOCKS field 
   is ignored. If the device supports changing its capacity by changing the NUMBER OF LOGICAL BLOCKS field, 
   then the NUMBER OF LOGICAL BLOCKS field is interpreted as follows:

   a. If the NUMBER OF LOGICAL BLOCKS field is set to zero, the logical unit shall retain its current capacity if 
      the logical block length has not changed. If the NUMBER OF LOGICAL BLOCKS field is set to zero and the 
      content of the LOGICAL BLOCK LENGTH field (i.e., new logical block length) is different than the current 
      logical block length, the logical unit shall be set to its maximum capacity when the new logical block length 
      takes effect (i.e., after a successful FORMAT UNIT command).
	  
   b. If the NUMBER OF LOGICAL BLOCKS field is greater than zero and less than or equal to its maximum 
      capacity, the logical unit shall be set to that number of logical blocks. If the content of the LOGICAL BLOCK 
      LENGTH field is the same as the current logical block length, the logical unit shall not become format cor-
      rupt. This capacity setting shall be retained through power cycles, hard resets, logical unit resets, and I_T 
      nexus losses. If the content of the LOGICAL BLOCK LENGTH field is the same as the current logical block 
      length this capacity setting shall take effect on successful completion of the MODE SELECT command. If 
      the content of the LOGICAL BLOCK LENGTH field (i.e., new logical block length) is different than the cur-
      rent logical block length, this capacity setting shall take effect when the new logical block length takes effect 
      (i.e., after a successful FORMAT UNIT command);
	  
   c. If the NUMBER OF LOGICAL BLOCKS field is set to a value greater than the maximum capacity of the 
      device and less than FFFFFFFF FFFFFFFFh, then the device server shall terminate the MODE SELECT 
      command with CHECK CONDITION status with the sense key set to ILLEGAL REQUEST and the addi-
      tional sense code set to INVALID FIELD IN PARAMETER LIST. The logical unit shall retain its previous 
      block descriptor settings; or
	  
   d. If the NUMBER OF LOGICAL BLOCKS field is set to FFFFFFFF FFFFFFFFh, the logical unit shall be set to 
      its maximum capacity. If the content of the LOGICAL BLOCK LENGTH field is the same as the current logi-
      cal block length, the logical unit shall not become format corrupt. This capacity setting shall be retained 
      through power cycles, hard resets, logical unit resets, and I_T nexus losses. If the content of the LOGICAL 
      BLOCK LENGTH field is the same as the current logical block length this capacity setting shall take effect 
      on successful completion of the MODE SELECT  command. If the content of the LOGICAL BLOCK 
      LENGTH field (i.e., new logical block length) is different than the current logical block length this capacity 
      setting shall take effect when the new logical block length takes effect (i.e., after a successful FORMAT 
      UNIT command).   
 
   The LOGICAL BLOCK LENGTH field specifies the length in bytes of each logical block. No change shall be 
   made to any logical blocks on the medium until a format operation is initiated by an application client.
   
   A device server shall respond to a MODE SENSE command (see 3.13 and 3.14) by reporting the length of the 
   logical blocks as specified in the LOGICAL BLOCK LENGTH field sent in the last MODE SELECT command 
   that contained a mode parameter block descriptor. If no MODE SELECT command with a block descriptor has 
   been received then the current logical block length shall be returned (e.g., if the logical block length is 512 
   bytes and a MODE SELECT command occurs with the LOGICAL BLOCK LENGTH field set to 520 bytes, any 
   MODE SENSE command would return 520 in the LOGICAL BLOCK LENGTH field). To determine the logical 
   block length at which the logical unit is currently formatted, the application client shall use the READ CAPAC-
   ITY command rather than the MODE SELECT command.

   4.3.5 Mode page and subpage formats and page codes
   The page_0 mode page format is defined in table 266.

                     Table 266 —  Page_0 mode page format
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |   PS  |SPF(0B)|                 PAGE CODE                     |
   |-------------------------------------------------------------------------|
   |    1    |                 PAGE LENGTH (n-1)                             |
   |-------------------------------------------------------------------------|
   |    2    |                 MODE PARAMETERS                               |
   |-------------------------------------------------------------------------|
   |    n    |                                                               |
   |-------------------------------------------------------------------------|
   
   The SUB_PAGE mode page format is defined in table 267.
                     Table 267 —  SUB_PAGE mode page format
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |   PS  |SPF(1b)|                 PAGE CODE                     |
   |-------------------------------------------------------------------------|
   |    1    |                 SUBPAGE CODE                                  |
   |-------------------------------------------------------------------------|
   |    2    | (MSB) |                                                       |
   |         |                 PAGE LENGTH (N-3)                             |
   |    3    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    4    |                 MODE PARAMETERS                               |
   |-------------------------------------------------------------------------|
   |    n    |                                                               |
   |-------------------------------------------------------------------------|   

   Each mode page contains a PS bit, an SPF bit, a PAGE CODE field, a PAGE LENGTH field, and a set of mode 
   parameters. The page codes are defined in this subclause and in the mode parameter subclauses in the com-
   mand standard for the specific device type. Each mode page with a SPF bit set to one contains a SUBPAGE 
   CODE field.
   SPF (SubPage Format) bit
     0 A SubPage Format (SPF) bit set to zero indicates that the page_0 mode page format is being used. 
     1 A SPF bit set to one indicates that the SUB_PAGE mode page format is being used.
   PS (parameters saveable) bit
     1 When using the MODE SENSE command, a parameters saveable (PS) bit set to one indicates that the mode page 
       may be saved by the logical unit in a nonvolatile, vendor specific location. 
     0 A PS bit set to zero indicates that the device server is not able to save the supported parameters. When using the  
   MODE SELECT command, the PS bit is reserved.
   
   PAGE CODE and SUBPAGE CODE fields
   The PAGE CODE and SUBPAGE CODE fields identify the format and parameters defined for that mode page. Some page 
   codes are defined as applying to all device types and other page codes are defined for the specific device type. The page 
   codes that apply to a specific device type are defined in the command standard for that device type. The applicability of 
   each subpage code matches that of the page code with which it is associated.

   When using the MODE SENSE command, if page code 00h (vendor specific mode page) is implemented, the 
   device server shall return that mode page last in response to a request to return all mode pages (page code 
   3Fh). When using the MODE SELECT command, this mode page should be sent last.
   
   PAGE LENGTH field
   The PAGE LENGTH field specifies the length in bytes of the mode parameters that follow. If the application client does not 
   set this value to the value that is returned for the mode page by the MODE SENSE command, the command shall be termi-
   nated with CHECK CONDITION status, with the sense key set to ILLEGAL REQUEST, and the additional sense code set to 
   INVALID FIELD IN PARAMETER LIST. The logical unit may implement a mode page that is less than the full mode page 
   length defined, provided no field is truncated and the PAGE LENGTH field correctly specifies the actual length imple-
   mented.
   
   The mode parameters for each mode page are defined in the following subclauses, or in the mode parameters 
   subclause in the command standard for the specific device type. Mode parameters not implemented by the 
   logical unit shall be set to zero.

   Table 268 defines the mode pages that are applicable to all device types that implement the MODE SELECT 
   and MODE SENSE commands.
   
                   Table 268 —  Mode page codes and subpage codes
    /-----------------------------------------------------------------------------------------\
   | Page code  |  Subpage code  |                Mode Page Name                   | Reference |  
   |-------------------------------------------------------------------------------------------|
   |    0Ah     |      00h       | Control                                         |   4.3.8   |
   |-------------------------------------------------------------------------------------------|
   |    0Ah     |      01h       | Control Extension                               |   4.3.9   |
   |-------------------------------------------------------------------------------------------|
   |    02h     |      00h       | Disconnect-Reconnect                            |   4.3.10  |
   |-------------------------------------------------------------------------------------------|
   |    15h     |      00h       | Extended                                        |           |
   |-------------------------------------------------------------------------------------------|
   |    16h     |      00h       | Extended Device-Type Specific                   |           |
   |-------------------------------------------------------------------------------------------|
   |    03h     |      00h       | Format Device mode page (Obsolete)              |   4.3.11  |
   |-------------------------------------------------------------------------------------------|
   |    1Ch     |      00h       | Informational Exceptions Control                |   4.3.12  |
   |-------------------------------------------------------------------------------------------|
   |    09h     |      00h       | Obsolete                                        |           |
   |-------------------------------------------------------------------------------------------|
   |    0Ch     |      00h       | Notch (Obsolete)                                |   4.3.13  |
   |-------------------------------------------------------------------------------------------|
   |    1Ah     |      00h       | Power Condition                                 |   4.3.14  |
   |-------------------------------------------------------------------------------------------|
   |    18h     |      00h       | Protocol Specific LUN                           |   4.3.16  |
   |-------------------------------------------------------------------------------------------|
   |    04h     |      00h       | Rigid Drive Geometry Parameters page (Obsolete) |   4.3.18  |
   |-------------------------------------------------------------------------------------------|
   |    18h     |   01h - FEh    | (See specific SCSI transport protocol)          |           |
   |-------------------------------------------------------------------------------------------|
   |    19h     |      00h       | Protocol Specific Port                          |   4.3.17  |
   |-------------------------------------------------------------------------------------------|
   |    19h     |   01h - FEh    | (See specific SCSI transport protocol)          |           |
   |-------------------------------------------------------------------------------------------|
   |    01h     |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   | 04h - 08h  |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   | 0Bh - 14h  |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   |    1Bh     |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   | 1Dh - 1Fh  |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   | 20h - 3Eh  |   00h - FEh    | (See specific device type)                      |           |
   |-------------------------------------------------------------------------------------------|
   |    00h     | not applicable | Vendor specific (does not require page format)  |           |
   |-------------------------------------------------------------------------------------------|
   |    3Fh     |      00h       | Return all pages(a)                             |           |
   |-------------------------------------------------------------------------------------------|
   |    3Fh     |      FFh       | Return all pages and subpages(a)                |           |
   |-------------------------------------------------------------------------------------------|
   | 00h - 3Eh  |      FFh       | Return all subpages(a)                          |           |
   |-------------------------------------------------------------------------------------------|
   |    All page code and subpage code combinations not shown in this table are reserved.      |
   |-------------------------------------------------------------------------------------------|
   |    a - Valid only for the MODE SENSE command                                              |
   |-------------------------------------------------------------------------------------------|


   

   
   
</pre>
 * 
 *
 */
public class ModeParam6 {
	
	public class ModeParamHead{
		private byte ModeDataLengh = 3;
		private byte MediuType;
		private boolean WP;
		private boolean DPOFUA;
		private byte BlockDescriptorLength;
		ModeParamHead(){}
		public ModeParamHead(byte[] CDB) throws Exception{
			ModeDataLengh = CDB[0];
			MediuType = CDB[1];
			WP = (ByteUtil.getBit(CDB[2], 0) == 1);
			DPOFUA = (ByteUtil.getBit(CDB[2], 3) == 1);
			BlockDescriptorLength = CDB[3];
		}
		public byte getModeDataLengh() {
			return ModeDataLengh;
		}
		public void setModeDataLengh(byte modeDataLengh) {
			ModeDataLengh = modeDataLengh;
		}
		public byte getMediuType() {
			return MediuType;
		}
		public void setMediuType(byte mediuType) {
			MediuType = mediuType;
		}
		public boolean isWP() {
			return WP;
		}
		public void setWP(boolean wP) {
			WP = wP;
		}
		public boolean isDPOFUA() {
			return DPOFUA;
		}
		public void setDPOFUA(boolean dPOFUA) {
			DPOFUA = dPOFUA;
		}
		public byte getBlockDescriptorLength() {
			return BlockDescriptorLength;
		}
		public void setBlockDescriptorLength(byte blockDescriptorLength) {
			BlockDescriptorLength = blockDescriptorLength;
		}
		public String toString(){
			StringBuilder build = new StringBuilder();
			build.append(System.getProperty("line.separator")+" MODE DATA LENGTH : "+this.ModeDataLengh);
			build.append(System.getProperty("line.separator")+" MEDIUM TYPE     : "+this.MediuType);
			build.append(System.getProperty("line.separator")+" WP : "+this.WP);
			build.append(System.getProperty("line.separator")+" DPOFUA  : "+this.DPOFUA);
			build.append(System.getProperty("line.separator")+" BLOCK DESCRIPTOR LENGTH : "+this.BlockDescriptorLength);
			return build.toString();
		}
		public byte[] toByte(){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			try {
				dos.writeByte(this.ModeDataLengh);
				dos.writeByte(this.MediuType);
				byte b2 = 0x00;
				b2 = (byte)(b2|(this.WP?0x80:0x00));
				b2 = (byte)(b2|(this.DPOFUA?0x10:0x00));
				dos.writeByte(b2);
				dos.writeByte(this.BlockDescriptorLength);
				byte[] result = bos.toByteArray();
				dos.close();
				bos.close();
				return result;
			} catch (IOException e) {e.printStackTrace();} 
			return new byte[0];
		}
	}
	
	public class ShortBlockDescr{
		private byte[] NumOfLogicalBlock = new byte[4];
		private byte[] LogicalBlockLength = new byte[3];
		public ShortBlockDescr(){}
		public ShortBlockDescr(byte[] CDB) throws Exception{
			System.arraycopy(CDB, 0, NumOfLogicalBlock, 0, NumOfLogicalBlock.length);
			System.arraycopy(CDB, 5, LogicalBlockLength, 0, LogicalBlockLength.length);
		}
		public String toString(){
			StringBuilder build = new StringBuilder();
			build.append(System.getProperty("line.separator")+" NUMBER OF LOGICAL BLOCKS : "+ByteUtil.byteArrayToInt(this.NumOfLogicalBlock));
			build.append(System.getProperty("line.separator")+" LOGICAL BLOCK LENGTH  : "+ByteUtil.byteArrayToInt(this.LogicalBlockLength));
			return build.toString();
		}
		public int getNumOfLogicalBlock() {
			return ByteUtil.byteArrayToInt(NumOfLogicalBlock);
		}
		public void setNumOfLogicalBlock(int numOfLogicalBlock) {
			NumOfLogicalBlock = ByteUtil.intToByteArray(numOfLogicalBlock);
		}
		public int getLogicalBlockLength() {
			return ByteUtil.byteArrayToInt(LogicalBlockLength);
		}
		public void setLogicalBlockLength(int logicalBlockLength) {
			byte[] b = ByteUtil.intToByteArray(logicalBlockLength);
			LogicalBlockLength[0] = b[1];
			LogicalBlockLength[1] = b[2];
			LogicalBlockLength[2] = b[3];
		}
		public byte[] toByte(){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			try {
				dos.write(this.NumOfLogicalBlock);
				dos.writeByte(0);
				dos.write(this.LogicalBlockLength);
				byte[] result = bos.toByteArray();
				dos.close();
				bos.close();
				return result;
			} catch (IOException e) {e.printStackTrace();} 
			return new byte[0];
		}
	}
	
	public abstract class ModePage {
		public abstract byte[] toByte();
	}
	
	public class InfoExceptionControl extends ModePage{
		private boolean PS;
		private boolean SPF = false;
		private byte PageCode = 0x1c;
		private byte PageLength = 0x0a;
		private boolean PERF;
		private boolean EBF;
		private boolean EWASC;
		private boolean DEXCPT;
		private boolean TEST;
		private boolean EBACKERR;
		private boolean LOGERR;
		private byte MRIE;
		private byte[] INTERVAL_TIMER = new byte[4];
		private byte[] REPORT_COUNT = new byte[4]; 
		public InfoExceptionControl(){}
		public InfoExceptionControl(byte[] CDB) throws Exception{
			PS = (ByteUtil.getBit(CDB[0], 0)==1);
			PERF = (ByteUtil.getBit(CDB[2], 0)==1);
			EBF = (ByteUtil.getBit(CDB[2], 2)==1);
			EWASC = (ByteUtil.getBit(CDB[2], 3)==1);
			DEXCPT = (ByteUtil.getBit(CDB[2], 4)==1);
			TEST = (ByteUtil.getBit(CDB[2], 5)==1);
			EBACKERR = (ByteUtil.getBit(CDB[2], 6)==1);
			LOGERR = (ByteUtil.getBit(CDB[2], 7)==1);
			MRIE = (byte)(CDB[3]&0x0f);
			System.arraycopy(CDB, 4, INTERVAL_TIMER, 0, INTERVAL_TIMER.length);
			System.arraycopy(CDB, 8, REPORT_COUNT, 0, REPORT_COUNT.length);
		}
		public String toString(){
			StringBuilder build = new StringBuilder();
			build.append(System.getProperty("line.separator")+" PS : "+PS);
			build.append(System.getProperty("line.separator")+" PERF : "+PERF);
			build.append(System.getProperty("line.separator")+" EBF : "+EBF);
			build.append(System.getProperty("line.separator")+" EWASC : "+EWASC);
			build.append(System.getProperty("line.separator")+" DEXCPT : "+DEXCPT);
			build.append(System.getProperty("line.separator")+" TEST : "+TEST);
			build.append(System.getProperty("line.separator")+" EBACKERR  : "+EBACKERR);
			build.append(System.getProperty("line.separator")+" LOGERR  : "+LOGERR);
			build.append(System.getProperty("line.separator")+" MRIE  : "+MRIE);
			build.append(System.getProperty("line.separator")+" INTERVAL_TIMER  : "+INTERVAL_TIMER);
			build.append(System.getProperty("line.separator")+" REPORT_COUNT  : "+REPORT_COUNT);
			return build.toString();
		}
		
		public boolean getPS() {
			return PS;
		}
		public void setPS(boolean pS) {
			PS = pS;
		}
		public boolean getSPF() {
			return SPF;
		}
		public byte getPageCode() {
			return PageCode;
		}
		public byte getPageLength() {
			return PageLength;
		}
		public boolean getPERF() {
			return PERF;
		}
		public void setPERF(boolean pERF) {
			PERF = pERF;
		}
		public boolean getEBF() {
			return EBF;
		}
		public void setEBF(boolean eBF) {
			EBF = eBF;
		}
		public boolean getEWASC() {
			return EWASC;
		}
		public void setEWASC(boolean eWASC) {
			EWASC = eWASC;
		}
		public boolean getDEXCPT() {
			return DEXCPT;
		}
		public void setDEXCPT(boolean dEXCPT) {
			DEXCPT = dEXCPT;
		}
		public boolean getTEST() {
			return TEST;
		}
		public void setTEST(boolean tEST) {
			TEST = tEST;
		}
		public boolean getEBACKERR() {
			return EBACKERR;
		}
		public void setEBACKERR(boolean eBACKERR) {
			EBACKERR = eBACKERR;
		}
		public boolean getLOGERR() {
			return LOGERR;
		}
		public void setLOGERR(boolean lOGERR) {
			LOGERR = lOGERR;
		}
		public byte getMRIE() {
			return MRIE;
		}
		public void setMRIE(byte mRIE) {
			MRIE = mRIE;
		}
		public int getINTERVAL_TIMER() {
			return ByteUtil.byteArrayToInt(INTERVAL_TIMER);
		}
		public void setINTERVAL_TIMER(int iNTERVAL_TIMER) {
			INTERVAL_TIMER = ByteUtil.intToByteArray(iNTERVAL_TIMER);
		}
		public int getREPORT_COUNT() {
			return ByteUtil.byteArrayToInt(REPORT_COUNT);
		}
		public void setREPORT_COUNT(int rEPORT_COUNT) {
			REPORT_COUNT = ByteUtil.intToByteArray(rEPORT_COUNT);
		}
		public byte[] toByte(){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			try {
				dos.writeByte(this.PS?0x9C:0x1C);
				dos.writeByte(0x0A);
				byte b = 0x00;
				b = (byte)(b|(this.PERF?0x80:0x00));
				b = (byte)(b|(this.EBF?0x20:0x00));
				b = (byte)(b|(this.EWASC?0x10:0x00));
				b = (byte)(b|(this.DEXCPT?0x08:0x00));
				b = (byte)(b|(this.TEST?0x04:0x00));
				b = (byte)(b|(this.EBACKERR?0x02:0x00));
				b = (byte)(b|(this.LOGERR?0x01:0x00));
				dos.writeByte(this.MRIE&0x0f);
				dos.write(this.INTERVAL_TIMER);
				dos.write(this.REPORT_COUNT);
				byte[] result = bos.toByteArray();
				dos.close();
				bos.close();
				return result;
			} catch (IOException e) {e.printStackTrace();} 
			return new byte[0];
		}
	}
	
	public class CachingParam extends ModePage{
		private boolean PS;
		private boolean SPF = false;
		private byte PageCode = 0x08;
		private byte PageLength = 0x12;
		private boolean IC;
		private boolean ABPF;
		private boolean CAP;
		private boolean DISC;
		private boolean SIZE;
		private boolean WCE;
		private boolean MF;
		private boolean RCD;
		private byte DEMAND_READ_RETENTION_PRIORITY;
		private byte WRITE_RETENTION_PRIORITY;
		private byte[] DISABLE_PREFETCH_TRANSFER_LENGTH = new byte[2];
		private byte[] MINIMUM_PREFETCH = new byte[2];
		private byte[] MAXIMUM_PREFETCH  = new byte[2];
		private byte[] MAXIMUM_PREFETCH_CEILING = new byte[2];
		private boolean FSW;
		private boolean LBCSS;
		private boolean DRA;
		private byte vendorSpecific;
		private boolean NV_DIS;
		private byte NUMBER_OF_CACHE_SEGMENTS;
		private byte[] CACHE_SEGMENT_SIZE  = new byte[2];
		public CachingParam(){}
		public CachingParam(byte[] CDB) throws Exception{
			PS = (ByteUtil.getBit(CDB[0], 0)==1);
			IC = (ByteUtil.getBit(CDB[2], 0)==1);
			ABPF = (ByteUtil.getBit(CDB[2], 1)==1);
			CAP = (ByteUtil.getBit(CDB[2], 2)==1);
			DISC = (ByteUtil.getBit(CDB[2], 3)==1);
			SIZE = (ByteUtil.getBit(CDB[2], 4)==1);
			WCE = (ByteUtil.getBit(CDB[2], 5)==1);
			MF = (ByteUtil.getBit(CDB[2], 6)==1);
			RCD = (ByteUtil.getBit(CDB[2], 7)==1);
			DEMAND_READ_RETENTION_PRIORITY = (byte)((CDB[3]&0xf0)>>>4);
			WRITE_RETENTION_PRIORITY = (byte)(CDB[3]&0x0f);
			System.arraycopy(CDB, 4, DISABLE_PREFETCH_TRANSFER_LENGTH, 0, DISABLE_PREFETCH_TRANSFER_LENGTH.length);
			System.arraycopy(CDB, 6, MINIMUM_PREFETCH, 0, MINIMUM_PREFETCH.length);
			System.arraycopy(CDB, 8, MAXIMUM_PREFETCH, 0, MAXIMUM_PREFETCH.length);
			System.arraycopy(CDB, 10, MAXIMUM_PREFETCH_CEILING, 0, MAXIMUM_PREFETCH_CEILING.length);
			FSW = (ByteUtil.getBit(CDB[12], 0)==1);
			LBCSS = (ByteUtil.getBit(CDB[12], 1)==1);
			DRA = (ByteUtil.getBit(CDB[12], 2)==1);
			vendorSpecific = (byte)((CDB[12]&0x18)>>>4);
			NV_DIS = (ByteUtil.getBit(CDB[12], 7)==1);
			NUMBER_OF_CACHE_SEGMENTS = CDB[13];
			if(CDB.length>14)System.arraycopy(CDB, 14, CACHE_SEGMENT_SIZE, 0, CACHE_SEGMENT_SIZE.length);
		}
		public String toString(){
			StringBuilder build = new StringBuilder();
			build.append(System.getProperty("line.separator")+" PS : "+PS);
			build.append(System.getProperty("line.separator")+" IC : "+IC);
			build.append(System.getProperty("line.separator")+" ABPF : "+ABPF);
			build.append(System.getProperty("line.separator")+" CAP : "+CAP);
			build.append(System.getProperty("line.separator")+" DISC : "+DISC);
			build.append(System.getProperty("line.separator")+" SIZE : "+SIZE);
			build.append(System.getProperty("line.separator")+" WCE  : "+WCE);
			build.append(System.getProperty("line.separator")+" MF  : "+MF);
			build.append(System.getProperty("line.separator")+" RCD  : "+RCD);
			build.append(System.getProperty("line.separator")+" DEMAND_READ_RETENTION_PRIORITY  : "+DEMAND_READ_RETENTION_PRIORITY);
			build.append(System.getProperty("line.separator")+" WRITE_RETENTION_PRIORITY  : "+WRITE_RETENTION_PRIORITY);
			build.append(System.getProperty("line.separator")+" DISABLE_PREFETCH_TRANSFER_LENGTH  : "+ByteUtil.byteArrayToShort(DISABLE_PREFETCH_TRANSFER_LENGTH));
			build.append(System.getProperty("line.separator")+" MINIMUM_PREFETCH  : "+ByteUtil.byteArrayToShort(MINIMUM_PREFETCH));
			build.append(System.getProperty("line.separator")+" MAXIMUM_PREFETCH  : "+ByteUtil.byteArrayToShort(MAXIMUM_PREFETCH));
			build.append(System.getProperty("line.separator")+" MAXIMUM_PREFETCH_CEILING  : "+ByteUtil.byteArrayToShort(MAXIMUM_PREFETCH_CEILING));
			build.append(System.getProperty("line.separator")+" FSW  : "+FSW);
			build.append(System.getProperty("line.separator")+" LBCSS  : "+LBCSS);
			build.append(System.getProperty("line.separator")+" DRA  : "+DRA);
			build.append(System.getProperty("line.separator")+" vendorSpecific  : "+vendorSpecific);
			build.append(System.getProperty("line.separator")+" NV_DIS  : "+NV_DIS);
			build.append(System.getProperty("line.separator")+" NUMBER_OF_CACHE_SEGMENTS  : "+NUMBER_OF_CACHE_SEGMENTS);
			build.append(System.getProperty("line.separator")+" CACHE_SEGMENT_SIZE  : "+ByteUtil.byteArrayToShort(CACHE_SEGMENT_SIZE));
			return build.toString();
		}
		
		
		public boolean getPS() {
			return PS;
		}
		public void setPS(boolean pS) {
			PS = pS;
		}
		public boolean getSPF() {
			return SPF;
		}
		public byte getPageCode() {
			return PageCode;
		}
		public byte getPageLength() {
			return PageLength;
		}
		public boolean getIC() {
			return IC;
		}
		public void setIC(boolean iC) {
			IC = iC;
		}
		public boolean getABPF() {
			return ABPF;
		}
		public void setABPF(boolean aBPF) {
			ABPF = aBPF;
		}
		public boolean getCAP() {
			return CAP;
		}
		public void setCAP(boolean cAP) {
			CAP = cAP;
		}
		public boolean getDISC() {
			return DISC;
		}
		public void setDISC(boolean dISC) {
			DISC = dISC;
		}
		public boolean getSIZE() {
			return SIZE;
		}
		public void setSIZE(boolean sIZE) {
			SIZE = sIZE;
		}
		public boolean getWCE() {
			return WCE;
		}
		public void setWCE(boolean wCE) {
			WCE = wCE;
		}
		public boolean getMF() {
			return MF;
		}
		public void setMF(boolean mF) {
			MF = mF;
		}
		public boolean getRCD() {
			return RCD;
		}
		public void setRCD(boolean rCD) {
			RCD = rCD;
		}
		public byte getDEMAND_READ_RETENTION_PRIORITY() {
			return DEMAND_READ_RETENTION_PRIORITY;
		}
		public void setDEMAND_READ_RETENTION_PRIORITY(byte dEMAND_READ_RETENTION_PRIORITY) {
			DEMAND_READ_RETENTION_PRIORITY = dEMAND_READ_RETENTION_PRIORITY;
		}
		public byte getWRITE_RETENTION_PRIORITY() {
			return WRITE_RETENTION_PRIORITY;
		}
		public void setWRITE_RETENTION_PRIORITY(byte wRITE_RETENTION_PRIORITY) {
			WRITE_RETENTION_PRIORITY = wRITE_RETENTION_PRIORITY;
		}
		public short getDISABLE_PREFETCH_TRANSFER_LENGTH() {
			return ByteUtil.byteArrayToShort(DISABLE_PREFETCH_TRANSFER_LENGTH);
		}
		public void setDISABLE_PREFETCH_TRANSFER_LENGTH(short dISABLE_PREFETCH_TRANSFER_LENGTH) {
			DISABLE_PREFETCH_TRANSFER_LENGTH = ByteUtil.shortToByteArray(dISABLE_PREFETCH_TRANSFER_LENGTH);
		}
		public short getMINIMUM_PREFETCH() {
			return ByteUtil.byteArrayToShort(MINIMUM_PREFETCH);
		}
		public void setMINIMUM_PREFETCH(short mINIMUM_PREFETCH) {
			MINIMUM_PREFETCH = ByteUtil.shortToByteArray(mINIMUM_PREFETCH);
		}
		public short getMAXIMUM_PREFETCH() {
			return ByteUtil.byteArrayToShort(MAXIMUM_PREFETCH);
		}
		public void setMAXIMUM_PREFETCH(short mAXIMUM_PREFETCH) {
			MAXIMUM_PREFETCH = ByteUtil.shortToByteArray(mAXIMUM_PREFETCH);
		}
		public short getMAXIMUM_PREFETCH_CEILING() {
			return ByteUtil.byteArrayToShort(MAXIMUM_PREFETCH_CEILING);
		}
		public void setMAXIMUM_PREFETCH_CEILING(short mAXIMUM_PREFETCH_CEILING) {
			MAXIMUM_PREFETCH_CEILING = ByteUtil.shortToByteArray(mAXIMUM_PREFETCH_CEILING);
		}
		public boolean getFSW() {
			return FSW;
		}
		public void setFSW(boolean fSW) {
			FSW = fSW;
		}
		public boolean getLBCSS() {
			return LBCSS;
		}
		public void setLBCSS(boolean lBCSS) {
			LBCSS = lBCSS;
		}
		public boolean getDRA() {
			return DRA;
		}
		public void setDRA(boolean dRA) {
			DRA = dRA;
		}
		public byte getVendorSpecific() {
			return vendorSpecific;
		}
		public void setVendorSpecific(byte vendorSpecific) {
			this.vendorSpecific = vendorSpecific;
		}
		public boolean getNV_DIS() {
			return NV_DIS;
		}
		public void setNV_DIS(boolean nV_DIS) {
			NV_DIS = nV_DIS;
		}
		public byte getNUMBER_OF_CACHE_SEGMENTS() {
			return NUMBER_OF_CACHE_SEGMENTS;
		}
		public void setNUMBER_OF_CACHE_SEGMENTS(byte nUMBER_OF_CACHE_SEGMENTS) {
			NUMBER_OF_CACHE_SEGMENTS = nUMBER_OF_CACHE_SEGMENTS;
		}
		public short getCACHE_SEGMENT_SIZE() {
			return ByteUtil.byteArrayToShort(CACHE_SEGMENT_SIZE);
		}
		public void setCACHE_SEGMENT_SIZE(short cACHE_SEGMENT_SIZE) {
			this.SIZE = true;
			CACHE_SEGMENT_SIZE = ByteUtil.shortToByteArray(cACHE_SEGMENT_SIZE);
		}
		public byte[] toByte(){
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			try {
				dos.writeByte(this.PS?0x88:0x1C);
				dos.writeByte(0x12);
				byte b2 = 0x00;
				b2 = (byte)(b2|(this.IC?0x80:0x00));
				b2 = (byte)(b2|(this.ABPF?0x40:0x00));
				b2 = (byte)(b2|(this.CAP?0x20:0x00));
				b2 = (byte)(b2|(this.DISC?0x10:0x00));
				b2 = (byte)(b2|(this.SIZE?0x08:0x00));
				b2 = (byte)(b2|(this.WCE?0x04:0x00));
				b2 = (byte)(b2|(this.MF?0x02:0x00));
				b2 = (byte)(b2|(this.RCD?0x01:0x00));
				dos.writeByte(b2);
				dos.writeByte((this.DEMAND_READ_RETENTION_PRIORITY<<4 ) | this.WRITE_RETENTION_PRIORITY);
				dos.write(this.DISABLE_PREFETCH_TRANSFER_LENGTH);
				dos.write(this.MINIMUM_PREFETCH);
				dos.write(this.MAXIMUM_PREFETCH);
				dos.write(this.MAXIMUM_PREFETCH_CEILING);
				byte b12 = 0x00;
				b12 = (byte)(b12|(this.FSW?0x80:0x00));
				b12 = (byte)(b12|(this.LBCSS?0x40:0x00));
				b12 = (byte)(b12|(this.DRA?0x20:0x00));
				b12 = (byte)(b12|(this.vendorSpecific << 3));
				b12 = (byte)(b12|(this.NV_DIS?0x01:0x00));
				dos.writeByte(b12);
				dos.writeByte(this.NUMBER_OF_CACHE_SEGMENTS);
				if(this.SIZE)dos.write(this.CACHE_SEGMENT_SIZE);
				byte[] result = bos.toByteArray();
				dos.close();
				bos.close();
				return result;
			} catch (IOException e) {e.printStackTrace();} 
			return new byte[0];
		}
	}
	
	
	private ModeParamHead header = new ModeParamHead();
	private List<ShortBlockDescr> descriptors = new ArrayList<ShortBlockDescr>();
	private List<ModePage> pages = new ArrayList<ModePage>();
	
	public ModeParam6(){}
	public ModeParam6(byte[] CDB) throws Exception{
		if(CDB.length<4)throw new Exception("illegical ModeParam,the length at least is 4 !!");
		byte[] bHeader = new byte[4];
		System.arraycopy(CDB, 0, bHeader, 0, bHeader.length);
		header = new ModeParamHead(bHeader);
		for(int i=0;i<header.BlockDescriptorLength;i = i+8){
			byte[] b = new byte[8];
			System.arraycopy(CDB, 0, b, 0, b.length);
			ShortBlockDescr descr = new ShortBlockDescr(b);
			descriptors.add(descr);
		}
		//mode page
		
	}
	public ModeParamHead getHeader(){
		return this.header;
	}
	public List<ShortBlockDescr> getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(List<ShortBlockDescr> descriptors) {
		this.descriptors = descriptors;
	}
	public void setDescriptors(ShortBlockDescr descriptor) {
		this.descriptors.add(descriptor);
	}
	public List<ModePage> getPages() {
		return pages;
	}
	public void setPages(List<ModePage> pages) {
		this.pages = pages;
	}
	public void setPages(ModePage modePage) {
		this.pages.add(modePage);
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Header : ");
		build.append(System.getProperty("line.separator")+this.header);
		for(int i=0;i<this.descriptors.size();i++){
			Object descriptor = this.descriptors.get(i);
			build.append(System.getProperty("line.separator")+" Descriptor("+i+") : ");
			build.append(System.getProperty("line.separator")+descriptor);
		}
		for(int i=0;i<this.pages.size();i++){
			Object modePage = this.pages.get(i);
			build.append(System.getProperty("line.separator")+" ModePage("+i+") : ");
			build.append(System.getProperty("line.separator")+modePage);
		}
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			this.header.setBlockDescriptorLength((byte)(this.descriptors.size()*8));
			List<byte[]> datas = new ArrayList<byte[]>();
			int blockLength = 0;
			int modeLength = 3;
			for(ShortBlockDescr descriptor:this.descriptors){
				byte[] data = descriptor.toByte();  
				blockLength  = blockLength + data.length;
				modeLength = modeLength + data.length;
				datas.add(data);
			}
			for(ModePage modePage:this.pages){
				byte[] data = modePage.toByte();
				modeLength = modeLength + data.length;
				datas.add(data);
			}
			this.header.setModeDataLengh((byte)modeLength);
			this.header.setBlockDescriptorLength((byte)blockLength);
			dos.write(this.header.toByte());
			for(byte[] data:datas){
				dos.write(data);
			}
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		ModeParam6 original = new ModeParam6();
		ShortBlockDescr blockDescr = original.new ShortBlockDescr();
		original.setDescriptors(blockDescr);
		System.out.println(original);
		byte[] data = original.toByte();
		ModeParam6 after = new ModeParam6(data);
		System.out.println(after);
		System.out.println(data.length);
		
		
	}
 	
	
	 
}
