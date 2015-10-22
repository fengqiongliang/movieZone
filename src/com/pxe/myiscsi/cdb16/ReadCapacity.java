package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.CDBOpcode;
 
/**
<pre>
3.25 READ CAPACITY (10) command

3.25.1 READ CAPACITY (10) overview

   The READ CAPACITY (10) command (see table 108) requests that the device server transfer 8 bytes of 
   parameter data describing the capacity and medium format of the direct-access block device to the data-in 
   buffer. This command may be processed as if it has a HEAD OF QUEUE task attribute. If the logical unit 
   supports protection information, the application client should use the READ CAPACITY (16) command instead
   of the READ CAPACITY (10) command.


                     Table 108 —  READ CAPACITY (10) command 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (25h)                         |
   |-------------------------------------------------------------------------|
   |    1    |                  Reserved                             |Obsolet|
   |-------------------------------------------------------------------------|
   |    2    | (MSB) |                                                       |
   |   ...   |                  LOGICAL BLOCK ADDRESS                        |
   |    5    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    6    |                  Reserved                                     |
   |-------------------------------------------------------------------------|   
   |    7    |                  Reserved                                     |
   |-------------------------------------------------------------------------|  
   |    8    |                  Reserved                             |  PMI  |
   |-------------------------------------------------------------------------|     
   |    9    |                  CONTROL                                      |
   |-------------------------------------------------------------------------|
   
   For the definition of the LOGICAL BLOCK ADDRESS field see 2.2.3.
   
   LOGICAL BLOCK ADDRESS field
   The LOGICAL BLOCK ADDRESS field shall be set to zero if the PMI bit is set to zero. 
   If the PMI bit is set to zero and the LOGICAL BLOCK ADDRESS field is not set to zero, 
   the device server shall terminate the command with CHECK CONDITION status with the sense key 
   set to ILLEGAL REQUEST and the additional sense code set to INVALID FIELD IN CDB.

   PMI (Partial Medium Indicator) bit
   
      0 - A partial medium indicator (PMI) bit set to zero specifies that the device server return information 
	      on the last logical block on the direct-access block device. 
		  
      1 - A PMI bit set to one specifies that the device server return information on the last logical block 
          after that specified in the LOGICAL BLOCK ADDRESS field before a substantial vendor-specific delay 
          in data transfer may be encountered. 
      
   This function is intended to assist storage management software in determining whether there is sufficient 
   space starting with the logical block address specified in the CDB to contain a frequently accessed 
   data structure (e.g., a file directory or file index) without incurring an extra delay. 

   3.25.2 READ CAPACITY (10) parameter data
   The READ CAPACITY (10) parameter data is defined in table 109. Any time the READ CAPACITY (10) 
   parameter data changes, the device server should establish a unit attention condition as described in SBC-3.
   
                     Table 109 —  READ CAPACITY (10) parameter data 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | (MSB) |                                                       |
   |   ...   |                  RETURNED LOGICAL BLOCK ADDRESS               |
   |    3    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    4    | (MSB) |                                                       |
   |   ...   |                  BLOCK LENGTH IN BYTES                        |
   |    7    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   
   
   RETURNED LOGICAL BLOCK ADDRESS field
   If the number of logical blocks exceeds the maximum value that is able to  be specified in the RETURNED LOGICAL 
   BLOCK ADDRESS field, the device server shall set the RETURNED LOGICAL BLOCK ADDRESS field to FFFFFFFFh. 
   The application client should then issue a READ CAPACITY (16) command (see 3.27) to retrieve the READ CAPACITY(16) parameter data.

      0 - If the PMI bit is set to zero, the device server shall set the RETURNED LOGICAL BLOCK ADDRESS field to the 
          lower of: 
             a)  the LBA of the last logical block on the direct-access block device; or
             b)  FFFFFFFFh.
		  
      1 - If the PMI bit is set to one, the device server shall set the RETURNED LOGICAL BLOCK ADDRESS field to the lower of:
             a)  the last LBA after that specified in the LOGICAL BLOCK ADDRESS field of the CDB before a substantial
                 vendor-specific delay in data transfer may be encountered; or
             b)  FFFFFFFFh.
		  
   The RETURNED LOGICAL BLOCK ADDRESS shall be greater than or equal to that specified by the LOGICAL BLOCK 
   ADDRESS field in the CDB.
   
   BLOCK LENGTH IN BYTES field 
   The BLOCK LENGTH IN BYTES field contains the number of  bytes of user data in the logical block indicated by the 
   RETURNED LOGICAL BLOCK ADDRESS field. This value does not include protection information or additional 
   information (e.g., ECC bytes) recorded on the medium.

   
   
</pre>
 * 
 *
 */
public class ReadCapacity {
	private byte op = (byte)0x25;
	private byte[] LBA = new byte[4];
	private boolean PMI;
	private byte Control;
	public ReadCapacity(){}
	public ReadCapacity(byte[] CDB) throws Exception{
		System.arraycopy(CDB, 2, LBA, 0, LBA.length);
		PMI = (ByteUtil.getBit(CDB[8], 7) == 1);
		Control = CDB[9];
	}
	
	public CDBOpcode getOpcode() {
		return CDBOpcode.ReadCapacity10;
	}
	public boolean getPMI() {
		return this.PMI;
	}
	public void setPMI(boolean PMI) {
		this.PMI = PMI;
	}
	public int getLBA() { 
		return ByteUtil.byteArrayToInt(this.LBA);
	}
	public void setLBA(int LBA) {
		this.LBA = ByteUtil.intToByteArray(LBA);
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
		build.append(System.getProperty("line.separator")+" PMI     : "+this.PMI  );
		build.append(System.getProperty("line.separator")+" LOGICAL BLOCK ADDRESS : "+ByteUtil.byteArrayToInt(this.LBA));
		build.append(System.getProperty("line.separator")+" Control : "+this.Control);
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x25); //Opcode
			dos.write(new byte[]{0}); //Reserved
			dos.write(this.LBA);
			dos.write(new byte[]{0,0}); //Reserved
			dos.writeByte(this.PMI?0x01:0x00); 
			dos.writeByte(this.Control); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		ReadCapacity original = new ReadCapacity();
		original.setPMI(true);
		original.setLBA(153200);
		original.setControl((byte)3);
		System.out.println(original);
		byte[] data = original.toByte();
		ReadCapacity after = new ReadCapacity(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
