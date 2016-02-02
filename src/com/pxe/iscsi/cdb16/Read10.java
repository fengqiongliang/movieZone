package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
 
/**
<pre>
3.20 READ (10) command

   The READ (10) command (see table 90) requests that the device server read the specified logical block(s) and 
   transfer them to the data-in buffer. Each logical block read includes user data and, if the medium is formatted 
   with protection information enabled, protection information. Each logical block transferred includes user data 
   and may include protection information, based on the RDPROTECT field and the medium format. The most 
   recent data value written in the addressed logical block shall be returned.

                     Table 90 — READ (10) command
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (28h)                         |
   |-------------------------------------------------------------------------|
   |    1    |      RDPROTECT        |  DPO  |  FUA  |Reserve|FUA_NV |Obsolet|
   |-------------------------------------------------------------------------|
   |    2    | (MSB) |                                                       |
   |   ...   |                  LOGICAL BLOCK ADDRESS                        |
   |    5    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    6    |      Reserved         |           GROUP NUMBER                |
   |-------------------------------------------------------------------------|
   |    7    | (MSB) |                                                       |
   |         |                  TRANSFER LENGTH                              |
   |    8    |                                                       | (LSB) | 
   |-------------------------------------------------------------------------|   
   |    9    |                 CONTROL                                       |
   |-------------------------------------------------------------------------|
   
   RDPROTECT field
   The device server shall check the protection information read from the medium before returning status for the command 
   based on the RDPROTECT field as described in table 91.
   
                       Table 90 — READ (10) command
    /----------------------------------------------------------------------------------------------------------------------------------------------\
   |               |                | Shall device   |                               |                 |                                            |
   |               | Logical unit   | server         |                               | Extended        |                                            |
   |               | formatted with | transmit       |                               | INQUIRY Data    |                                            |
   |               | protection     | protection     | Field in protection           | VPD page bit    | If check fails(d)(f),                      |
   |     Code      | information    | information?   | information(h)                | value(g)        | additional sense code                      |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK GUARD           |  GRD_CHK = 1    | LOGICAL BLOCK GUARD CHECK FAILED           |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK GUARD           |  GRD_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 1(c) | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 1(i) | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     Yes        |       No       | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     No         |       No       | No protection information available to check                                                 |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  GRD_CHK = 1    | LOGICAL BLOCK GUARD CHECK FAILED           |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  GRD_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 1(c) | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 1(i) | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   | 001bOr101b(b) |     No(a)      |       No protection information available to transmit to the data-in buffer or for checking                   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 1(c) | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 1(i) | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    010b(b)    |     No(a)      |       No protection information available to transmit to the data-in buffer or for checking                   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  NO CHECK PERFORMED                                          |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    011b(b)    |     No(a)      |       No protection information available to transmit to the data-in buffer or for checking                   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  GRD_CHK = 1    | LOGICAL BLOCK GUARD CHECK FAILED           |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK GUARD           |  GRD_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 1(c) | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK APPLICATION TAG |  APP_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 1(i) | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     Yes        |       Yes(e)   | LOGICAL BLOCK REFERENCE TAG   |  REF_CHK = 0    | NO CHECK PERFORMED                         |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |    100b(b)    |     No(a)      |       No protection information available to transmit to the data-in buffer or for checking                   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   |   101b-111b   |     Reserved                                                                                                                   |
   |------------------------------------------------------------------------------------------------------------------------------------------------|
   
   a - A read operation to a logical unit that supports protection information and has not been formatted with protection 
       information shall be terminated with CHECK CONDITION status with the sense key set to ILLEGAL REQUEST and 
       the additional sense code set to INVALID FIELD IN CDB.      
   b - If the logical unit does not support protection information the requested command should be terminated with CHECK
       CONDITION status with the sense key set to ILLEGAL REQUEST and the additional sense code set to INVALID 
       FIELD IN CDB.
   c - The device server shall check the logical block application tag if it has knowledge of the contents of the LOGICAL 
       BLOCK APPLICATION TAG field. If the READ (32) command (see 3.23) is used and the ATO bit is set to one in the 
       Control mode page (see SPC-4), this knowledge is acquired from the EXPECTED LOGICAL BLOCK APPLICATION 
       TAG field and the LOGICAL BLOCK APPLICATION TAG MASK field in the CDB. Otherwise, this knowledge may be 
       acquired by a method not defined by this manual.
   d - If an error is reported, the sense key shall be set to ABORTED COMMAND.
   e - Transmit protection information to the data-in buffer.
   f - If multiple errors occur, the selection of which error to report is not defined by this manual.
   g - See the Extended INQUIRY Data VPD page (see SPC-4) for the definitions of the GRD_CHK bit, the APP_CHK bit, 
       and the REF_CHK bit.	   
   h - If the device server detects: 
          a) a LOGICAL BLOCK APPLICATION TAG field set to FFFFh and type 1 protection (See SBC-3) or type 2 
             protection (See SBC-3) is enabled; or
          b) a LOGICAL BLOCK APPLICATION TAG field set to FFFFh, LOGICAL BLOCK REFERENCE TAG field set to 
             FFFF FFFFh, and type 3 protection (See SBC-3) is enabled, 
       then the device server shall not check any protection information in the associated protection information interval.   
   i - If type 1 protection is enabled, the device server checks the logical block reference tag by comparing it to 
       the lower 4 bytes of the LBA associated with the logical block. If type 2 protection or type 3 protection is 
       enabled, the device server checks the LOGICAL BLOCK REFERENCE TAG if it has knowledge of the 
       contents of the logical block reference tag field. If type 2 protection is enabled, then this knowledge may 
       be acquired through the expected INITIAL LOGICAL BLOCK REFERENCE TAG field in a READ (32) 
       command (see 3.23). If type 3 protection is enabled, then the method for acquiring this knowledge is not 
       defined by this manual.
	   
   DPO (Disable Page Out) bit: 	   
   
     0 - SA Disable Page Out (DPO) bit set to zero specifies that the retention priority shall be determined by the 
         RETENTION PRIORITY fields in the Caching mode page (see 4.3.7).
     1 - A DPO bit set to one specifies that the device server shall assign the logical blocks accessed by this command the 
         lowest retention priority for being fetched into or retained by the cache. A DPO bit set to one overrides any retention 
         priority specified in the Caching mode page. All other aspects of the algorithm implementing the cache replacement 
         strategy are not defined by this manual.
		 
   Note. The DPO bit is used to control replacement of logical blocks in the cache when the application client 
         has information on the future usage of the logical blocks. If the DPO bit is set to one, the application 
         client is specifying that the logical blocks accessed by the command are not likely to be accessed again 
         in the near future and should not be put in the cache nor retained by the cache. 
         If the DPO bit is set to zero, the application client is specifying that the logical blocks accessed by this 
         command are likely to be accessed again in the near future.		 
		 
   The force unit access (FUA) and force unit access non-volatile cache (FUA_NV) bits are defined in table 92.	 
	
   Table 92. Force unit access for read operations
   |------------------------------------------------------------------------------------------------------------------------|
   |  FUA  | FUA_NV | Description                                                                                           |
   |------------------------------------------------------------------------------------------------------------------------|
   |   1   |   0    | The device server may read the logical blocks from volatile cache, non-volatile cache, and/or         |
   |       |        | the medium.                                                                                           |
   |------------------------------------------------------------------------------------------------------------------------|
   |   0   |   1    | If the NV_SUP bit is set to one in the Extended INQUIRY Data VPD page (see 4.4.6), the device         |
   |       |        | server shall read the logical blocks from non-volatile cache or the medium. If a nonvolatile cache    |
   |       |        | is present and a volatile cache contains a more recent version of a logical block, the device server  |
   |       |        | shall write the logical block to:                                                                     |
   |       |        |      (a) non-volatile cache; and/or                                                                   |
   |       |        |      (b) the medium,                                                                                  |
   |       |        | before reading it.                                                                                    |
   |       |        | If the NV_SUP bit is set to zero in the Extended INQUIRY Data VPD page (see 4.4.6), the device server |
   |       |        | may read the logical blocks from volatile cache, non-volatile cache, and/or the medium.               |
   |------------------------------------------------------------------------------------------------------------------------|
   |   1   | 0 or 1 | The device server shall read the logical blocks from the medium. If a cache contains a more           |
   |       |        | recent version of a logical block, the device server shall write the logical block to the medium      |
   |       |        | before reading it                                                                                     |
   |------------------------------------------------------------------------------------------------------------------------|
	
   LOGICAL BLOCK ADDRESS field
   The LOGICAL BLOCK ADDRESS field specifies the first logical block accessed by  this command. If  the logical block 
   address exceeds the capacity of the medium the device server shall terminate the command with CHECK CONDITION 
   status with the sense key set to ILLEGAL REQUEST and the additional sense code set to LOGICAL BLOCK ADDRESS OUT 
   OF RANGE.
   
   GROUP NUMBER field
   The GROUP NUMBER field specifies the group into which attributes associated with the command should be collected. A 
   GROUP NUMBER field set to zero specifies that any attributes associated with the command shall not be collected into any 
   group.
   
   TRANSFER LENGTH field
   The TRANSFER LENGTH field specifies the number of contiguous logical blocks of data that shall be read and transferred 
   to the data-in buffer, starting with the logical block specified by the LOGICAL BLOCK ADDRESS field. A TRANSFER 
   LENGTH field set to zero specifies that no logical blocks shall be read. This condition shall not be considered an error. Any 
   other value specifies  the number of logical blocks that shall be read. If the logical block address plus the transfer length 
   exceeds the capacity of the medium, the device server shall terminate the command with CHECK CONDITION status with 
   the sense key set to ILLEGAL REQUEST and the additional sense code set to LOGICAL BLOCK ADDRESS OUT OF RANGE. 
   The TRANSFER LENGTH field is constrained by the MAXIMUM TRANSFER LENGTH field in the Block Limits VPD page.
	
   
</pre>
 * 
 *
 */
public class Read10 {
	private byte op = (byte)0x28;
	private byte RDPROTECT;
	private boolean DPO;
	private boolean FUA;
	private boolean FUA_NV;
	private byte[] LBA = new byte[4];
	private byte groupNumber;
	private byte[] transferLength = new byte[2];
	private byte Control;
	public Read10(){}
	public Read10(byte[] CDB) throws Exception{
		RDPROTECT = (byte)((CDB[1] & 0xE0) >> 5);
		DPO = (ByteUtil.getBit(CDB[1], 3) == 1);
		FUA = (ByteUtil.getBit(CDB[1], 4) == 1);
		FUA_NV = (ByteUtil.getBit(CDB[1], 6) == 1);
		System.arraycopy(CDB, 2, LBA, 0, LBA.length);
		groupNumber = (byte)(CDB[6] & 0x1F);
		System.arraycopy(CDB, 7, transferLength, 0, transferLength.length);
		Control = CDB[9];
	}
	
	public CDBOpcode getOpcode() {
		return CDBOpcode.ReadCapacity10;
	}
	public byte getRDPROTECT() {
		return RDPROTECT;
	}
	public void setRDPROTECT(byte rDPROTECT) {
		RDPROTECT = rDPROTECT;
	}
	public boolean getDPO() {
		return DPO;
	}
	public void setDPO(boolean dPO) {
		DPO = dPO;
	}
	public boolean getFUA() {
		return FUA;
	}
	public void setFUA(boolean fUA) {
		FUA = fUA;
	}
	public boolean getFUA_NV() {
		return FUA_NV;
	}
	public void setFUA_NV(boolean fUA_NV) {
		FUA_NV = fUA_NV;
	}
	public int getLBA() { 
		return ByteUtil.byteArrayToInt(this.LBA);
	}
	public void setLBA(int LBA) {
		this.LBA = ByteUtil.intToByteArray(LBA);
	}
	public byte getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(byte groupNumber) {
		this.groupNumber = (byte)(groupNumber & 0x1F);
	}
	public short getTransferLength() {
		return ByteUtil.byteArrayToShort(transferLength);
	}
	public void setTransferLength(short transferLength) {
		this.transferLength = ByteUtil.shortToByteArray(transferLength);
	}
	public byte getControl() {
		return this.Control;
	}
	public void setControl(byte control) {
		this.Control = control;
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" Opcode : "+CDBOpcode.valueOf(op));
		build.append(System.getProperty("line.separator")+" RDPROTECT : "+this.RDPROTECT);
		build.append(System.getProperty("line.separator")+" DPO      : "+this.DPO);
		build.append(System.getProperty("line.separator")+" FUA       : "+this.FUA);
		build.append(System.getProperty("line.separator")+" FUA_NV    : "+this.FUA_NV);
		build.append(System.getProperty("line.separator")+" LOGICAL BLOCK ADDRESS : "+ByteUtil.byteArrayToInt(this.LBA));
		build.append(System.getProperty("line.separator")+" GROUP NUMBER   : "+this.groupNumber);
		build.append(System.getProperty("line.separator")+" TRANSFER LENGTH  : "+ByteUtil.byteArrayToShort(this.transferLength));
		build.append(System.getProperty("line.separator")+" Control : "+this.Control);
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x28); //Opcode
			byte b1 = (byte)(this.RDPROTECT << 5);
			b1 = (byte)(this.DPO?b1|0x10:b1);
			b1 = (byte)(this.FUA?b1|0x08:b1);
			b1 = (byte)(this.FUA_NV?b1|0x02:b1);
			dos.writeByte(b1);
			dos.write(this.LBA);
			dos.writeByte(this.groupNumber);
			dos.write(this.transferLength); 
			dos.writeByte(this.Control); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		Read10 original = new Read10();
		original.setRDPROTECT((byte)1);
		original.setDPO(false);
		original.setFUA(true);
		original.setFUA_NV(true);
		original.setLBA(2);
		original.setGroupNumber((byte)3);
		original.setTransferLength((short)4);
		original.setControl((byte)5);
		System.out.println(original);
		byte[] data = original.toByte();
		Read10 after = new Read10(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
