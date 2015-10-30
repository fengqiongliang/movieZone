package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.CDBOpcode;
 
/**
<pre>
3.62 WRITE (10) command

   The WRITE (10) command (see table 189) requests that  the device server transfer the specified logical 
   block(s) from the data-out buffer and write them. Each logical block transferred includes user data and may 
   include protection information, based on the WRPROTECT field and the medium format. Each logical block 
   written includes user data and, if the medium is formatted with protection information enabled, protection 
   information. 

                     Table 189 — WRITE (10) command
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (2Ah)                         |
   |-------------------------------------------------------------------------|
   |    1    |      WRDPROTECT       |  DPO  |  FUA  |Reserve|FUA_NV |Obsolet|
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
   
   See the READ (10) command (see 3.20) for the definition of the DPO bit. See 2.2.3  for the definition of the 
   LOGICAL BLOCK ADDRESS field. See 2.2.8 and SPC-4 for the definition of the GROUP NUMBER field.
   
   The device server shall check the protection information transferred from the data-out buffer based on the 
   WRPROTECT field as described in table 190.
   
                       Table 190 — WRPROTECT field
    /-----------------------------------------------------------------------------------------------------------------------------\
   |               |                |                               |                 |                                            |
   |               | Logical unit   |                               |                 |                                            |
   |               | formatted with |                               | Device          |                                            |
   |               | protection     | Field in protection           | server          | If check fails(d)(i),                      |
   |     Code      | information    | information                   | check           | additional sense code                      |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |  Yes(f)(g)(h)  | No protection information received from application client to check                          |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     000b      |     No         | No protection information received from application client to check                          |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     001b(b)   |     Yes(e)     | LOGICAL BLOCK GUARD           |  Shall          | LOGICAL BLOCK GUARD CHECK FAILED           |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     001b(b)   |     Yes(e)     | LOGICAL BLOCK APPLICATION TAG |  May(c)         | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     001b(b)   |     Yes(e)     | LOGICAL BLOCK REFERENCE TAG   |Shall(except type 3)| LOGICAL BLOCK REFERENCE TAG CHECK FAILED|
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     001b(b)   |     No(a)      | No protection information available to check                                                 |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     010b(b)   |     Yes(e)     | LOGICAL BLOCK GUARD           |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     010b(b)   |     Yes(e)     | LOGICAL BLOCK APPLICATION TAG |  May(c)         | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     010b(b)   |     Yes(e)     | LOGICAL BLOCK REFERENCE TAG   |  May(k)         | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     010b(b)   |     No(a)      | No protection information available to check                                                 |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     011b(b)   |     Yes(e)     | LOGICAL BLOCK GUARD           |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     011b(b)   |     Yes(e)     | LOGICAL BLOCK APPLICATION TAG |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     011b(b)   |     Yes(e)     | LOGICAL BLOCK REFERENCE TAG   |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     011b(b)   |     No(a)      | No protection information available to check                                                 |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     100b(b)   |     Yes(e)     | LOGICAL BLOCK GUARD           |  Shall          | LOGICAL BLOCK GUARD CHECK FAILED           |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     100b(b)   |     Yes(e)     | LOGICAL BLOCK APPLICATION TAG |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     100b(b)   |     Yes(e)     | LOGICAL BLOCK REFERENCE TAG   |  Shall not      | No check performed                         |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     100b(b)   |     No(a)      | No protection information available to check                                                 |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     101b(b)   |     Yes(e)     | LOGICAL BLOCK GUARD           |  Shall          | LOGICAL BLOCK GUARD CHECK FAILED           |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     101b(b)   |     Yes(e)     | LOGICAL BLOCK APPLICATION TAG |  May(c)         | LOGICAL BLOCK APPLICATION TAG CHECK FAILED |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     101b(b)   |     Yes(e)     | LOGICAL BLOCK REFERENCE TAG   |  May(j)         | LOGICAL BLOCK REFERENCE TAG CHECK FAILED   |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |     101b(b)   |     No(a)      | No protection information available to check                                                 |
   |-------------------------------------------------------------------------------------------------------------------------------|
   |   110b-111b   |     Reserved                                                                                                  |
   |-------------------------------------------------------------------------------------------------------------------------------|
   
   a - A write operation to a logical unit that supports protection information and has not been formatted with protection 
       information shall be terminated with CHECK CONDITION status with the sense key set to ILLEGAL REQUEST and 
       the additional sense code set to INVALID FIELD IN CDB.
   b - If the logical unit does not support protection information the requested command should be terminated with CHECK 
       CONDITION status with the sense key set to ILLEGAL REQUEST and the additional sense code set to INVALID 
       FIELD IN CDB.
   c - The device server may check the logical block application tag if the ATO bit is set to one in the Control mode page 
       (see SPC-4) and if it has knowledge of the contents of the LOGICAL BLOCK APPLICATION TAG field. If the WRITE (32) 
       command (see 3.65) is used, this knowledge is obtained from the EXPECTED LOGICAL BLOCK APPLICATION 
       TAG field and the LOGICAL BLOCK APPLICATION TAG MASK field in the CDB. Otherwise, this knowledge is 
       obtained by a method not defined by this manual.
   d - If an error is reported, the sense key shall be set to ABORTED COMMAND.
   e - Device server shall preserve the contents of protection information (e.g., write to medium, store in non-volatile memory).
   f - The device server shall write a properly generated CRC into each LOGICAL BLOCK GUARD field.
   g - If type 1 protection is enabled (see SBC-3), the device server shall write the least significant four bytes of each LBA 
       into the LOGICAL BLOCK REFERENCE TAG field of each of the written logical blocks. If the P_TYPE field is not set 
       to 000b, the device server shall write a value of FFFFFFFFh into the logical block reference tag field of each of the 
       written logical blocks.
   h - If the ATO bit is set to one in the Control mode page (see 4.3.8), the device server shall write FFFFh into each 
       LOGICAL BLOCK APPLICATION TAG field. If the ATO bit is set to zero, the device server may write any value into 
       each LOGICAL BLOCK APPLICATION TAG field.
   i - If type 1 protection is enabled, the device server checks the logical block reference tag by comparing it to the lower 4 
       bytes of the LBA associated with the logical block. If type 2 protection or type 3 protection is enabled, the device 
       server checks the logical block reference tag if it has knowledge of the contents of the logical block reference tag 
       field. If type 2 protection is enabled, then this knowledge may be acquired through the expected initial logical block 
       reference tag field in a WRITE (32) command (see 3.65). If type 3 protection is enabled, then the method for 
       acquiring this knowledge is not defined by this manual.   
	   
   The force unit access (FUA) and force unit access non-volatile cache (FUA_NV) bits are defined in table 191.	  
	
   Table 191. Force unit access for write operations
   |------------------------------------------------------------------------------------------------------------------------|
   |  FUA  | FUA_NV | Description                                                                                           |
   |------------------------------------------------------------------------------------------------------------------------|
   |   1   |   0    | The device server shall write the logical blocks to volatile cache, non-volatile cache, and/or the    |
   |       |        | the medium.                                                                                           |
   |------------------------------------------------------------------------------------------------------------------------|
   |   0   |   1    | If the NV_SUP bit is set to one in the Extended INQUIRY Data VPD page (see SPC-4), the device         |
   |       |        | server shall write the logical blocks to non-volatile cache and/or the medium.                        |
   |       |        |                                                                                                       |
   |       |        | If the NV_SUP bit is set to zero in the Extended INQUIRY Data VPD page (see SPC-4), the device        |
   |       |        | server shall write the logical blocks to volatile cache, non-volatile cache, and/or the medium.       |
   |------------------------------------------------------------------------------------------------------------------------|
   |   1   | 0 or 1 | The device server shall write the logical blocks to the medium, and shall not return GOOD status      |
   |       |        | until the logical blocks have actually been written on the medium.                                    |
   |------------------------------------------------------------------------------------------------------------------------|
	
   If logical blocks are transferred directly to a cache, the device server may return GOOD status prior to writing 
   the logical blocks to the medium. Any error that occurs after the GOOD status is returned is a deferred error, 
   and information regarding the error is not reported until a subsequent command.

   TRANSFER LENGTH field
   The TRANSFER LENGTH field specifies the number of contiguous logical blocks of data that shall be transferred 
   from the data-out buffer and written, starting with the logical block specified by the LOGICAL BLOCK 
   ADDRESS field. A TRANSFER LENGTH field set to zero specifies that no logical blocks shall be written. This 
   condition shall not be considered an error. Any other value specifies the number of logical blocks that shall be 
   written. If the logical block address plus the transfer  length exceeds the capacity of the medium, the device 
   server shall terminate the command with CHECK CONDITION status with the sense key set to ILLEGAL REQUEST 
   and the additional sense code set to LOGICAL BLOCK ADDRESS OUT OF RANGE. The TRANSFER LENGTH field 
   is constrained by the MAXIMUM TRANSFER LENGTH field in the Block Limits VPD page.

   Note. For the WRITE (6) command, a TRANSFER LENGTH field set to zero specifies that 256 logical 
   blocks are transferred.
	
   
</pre>
 * 
 *
 */
public class Write10 {
	private byte op = (byte)0x2A;
	private byte WRDPROTECT;
	private boolean DPO;
	private boolean FUA;
	private boolean FUA_NV;
	private byte[] LBA = new byte[4];
	private byte groupNumber;
	private byte[] transferLength = new byte[2];
	private byte Control;
	public Write10(){}
	public Write10(byte[] CDB) throws Exception{
		WRDPROTECT = (byte)((CDB[1] & 0xE0) >> 5);
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
	public byte getWRDPROTECT() {
		return WRDPROTECT;
	}
	public void setWRDPROTECT(byte wRDPROTECT) {
		WRDPROTECT = wRDPROTECT;
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
		build.append(System.getProperty("line.separator")+" WRDPROTECT : "+this.WRDPROTECT);
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
			byte b1 = (byte)(this.WRDPROTECT << 5);
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
		Write10 original = new Write10();
		original.setWRDPROTECT((byte)1);
		original.setDPO(false);
		original.setFUA(true);
		original.setFUA_NV(true);
		original.setLBA(2);
		original.setGroupNumber((byte)3);
		original.setTransferLength((short)4);
		original.setControl((byte)5);
		System.out.println(original);
		byte[] data = original.toByte();
		Write10 after = new Write10(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
