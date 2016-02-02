package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
import com.pxe.iscsi.cdb16.ReportLUN.SelectReport;
 
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
public class ReadCapacityParam {
	private byte[] returnLBA = new byte[4];
	private byte[] blockLength = new byte[4];
	public ReadCapacityParam(){}
	public ReadCapacityParam(byte[] CDB) throws Exception{
		System.arraycopy(CDB, 0, returnLBA, 0, returnLBA.length);
		System.arraycopy(CDB, 4, blockLength, 0, blockLength.length);
	}
	
	public int getReturnLBA() { 
		return ByteUtil.byteArrayToInt(this.returnLBA);
	}
	public void setReturnLBA(int returnLBA) {
		this.returnLBA = ByteUtil.intToByteArray(returnLBA);
	}
	public int getBlockLength() { 
		return ByteUtil.byteArrayToInt(this.blockLength);
	}
	public void setBlockLength(int blockLength) {
		this.blockLength = ByteUtil.intToByteArray(blockLength);
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" RETURNED LOGICAL BLOCK ADDRESS     : "+ByteUtil.byteArrayToInt(this.returnLBA));
		build.append(System.getProperty("line.separator")+" BLOCK LENGTH IN BYTES : "+ByteUtil.byteArrayToInt(this.blockLength));
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.write(this.returnLBA);
			dos.write(this.blockLength);
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		ReadCapacityParam original = new ReadCapacityParam();
		original.setReturnLBA(1);
		original.setBlockLength(2);
		System.out.println(original);
		byte[] data = original.toByte();
		ReadCapacityParam after = new ReadCapacityParam(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
