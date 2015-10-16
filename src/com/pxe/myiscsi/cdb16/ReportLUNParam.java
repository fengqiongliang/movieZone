package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.moviezone.util.ByteUtil;
import com.pxe.myiscsi.ENUM.CDBOpcode;
 
/**
<pre>
6.32 REPORT LUNS command

    The REPORT LUNS command (see table 272) requests that the peripheral device logical unit inventory accessible
    to the I_T nexus be sent to the application client. 
    The logical unit inventory is a list that shall include the logical unit numbers of all logical units 
    having a PERIPHERAL QUALIFIER value of 000b (see 6.6.2). Logical unit numbers for
    logical units with PERIPHERAL QUALIFIER values other than 000b and 011b may be included in the logical unit
    inventory. Logical unit numbers for logical units with a PERIPHERAL QUALIFIER value of 011b shall not be included in
    the logical unit inventory.

                     Table 272 — REPORT LUNS command
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (A0h)                         |
   |-------------------------------------------------------------------------|
   |    1    |                  Reserved                                     |
   |-------------------------------------------------------------------------|
   |    2    |                  SELECT REPORT                                |
   |-------------------------------------------------------------------------|
   |    3    |                                                               |
   |   ...   |                  Reserved                                     |
   |    5    |                                                               |
   |-------------------------------------------------------------------------|
   |    6    | (MSB) |                                                       |
   |   ...   |                  ALLOCATION LENGTH                            |
   |    9    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    10   |                  Reserved                                     |
   |-------------------------------------------------------------------------|
   |    11    |                 CONTROL                                      |
   |-------------------------------------------------------------------------|
   
   The OPERATION CODE field is defined in 4.3.5.1 and shall be set as shown in table 272 for the REPORT LUNS
   command.
   
   The SELECT REPORT field (see table 273) specifies the types of logical unit addresses that shall be reported
   
     00h - The list shall contain the logical units accessible to the I_T nexus with the following addressing methods (see SAM-4):
              a) logical unit addressing method,
              b) peripheral device addressing method;
              c) flat space addressing method; and
              d) extended logical unit addressing method.
           If there are no logical units, the LUN LIST LENGTH field shall be zero.
		   
     01h - The list shall contain only well known logical units, if any. 
	       If there are no well known logical units, the LUN LIST LENGTH field shall be zero
          
     02h - The list shall contain all logical units accessible to the I_T nexus.
	       
     F8h to FFh - Vendor specific
	 
   The ALLOCATION LENGTH field is defined in 4.3.5.6.
   
   The CONTROL field is defined in SAM-5.
   
   The REPORT LUNS command shall return CHECK CONDITION status only when the device server is unable to
   return the requested report of the logical unit inventory.
   
   If a REPORT LUNS command is received from an I_T nexus with a pending unit attention condition (i.e., before the
   device server reports CHECK CONDITION status), the device server shall perform the REPORT LUNS command
   (see SAM-4).
   
   The REPORT LUNS parameter data should be returned even though the device server is not ready for other
   commands. The report of the logical unit inventory should be available without incurring any media access delays.
   If the device server is not ready with the logical unit inventory or if the inventory list is null for the requesting I_T
   nexus and the SELECT REPORT field set to 02h, then the device server shall provide a default logical unit inventory
   that contains at least LUN 0 or the REPORT LUNS well known logical unit (see 8.2). A non-empty peripheral
   device logical unit inventory that does not contain either LUN 0 or the REPORT LUNS well known logical unit is
   valid.
   
   If a REPORT LUNS command is received for a logical unit that the SCSI target device does not support and the
   device server is not capable of returning the logical unit inventory, then the command shall be terminated with
   CHECK CONDITION status, with the sense key set to ILLEGAL REQUEST, and the additional sense code set to
   LOGICAL UNIT NOT SUPPORTED.
   
   The device server shall report those devices in the logical unit inventory using the format shown in table 274.

                     Table 274 — REPORT LUNS parameter data format
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | (MSB) |                                                       |
   |   ...   |                  LUN LIST LENGTH (n-7)                        |
   |    3    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |    4    | (MSB) |                                                       |
   |   ...   |                  Reserved                                     |
   |    7    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |                            LUN list                                     |
   |-------------------------------------------------------------------------|
   |    8    |                                                               |
   |   ...   |                  LUN [first]                                  |
   |   15    |                                                               |
   |-------------------------------------------------------------------------|
   |         |                     ...                                       |
   |-------------------------------------------------------------------------|
   |   n-7   |                                                               |
   |   ...   |                  LUN [last]                                   |
   |    n    |                                                               |
   |-------------------------------------------------------------------------|
   
   The LUN LIST LENGTH field shall contain the length in bytes of the LUN list that is available to be transferred. 
   The LUN list length is the number of logical unit numbers in the logical unit inventory multiplied by eight. 
   The relationship between the LUN LIST LENGTH field and the CDB ALLOCATION LENGTH field is defined in 4.3.5.6.
   



   
   
</pre>
 * 
 *
 */
public class ReportLUNParam {
	
	private byte[] LunListLength = new byte[]{0x00,0x00,0x00,0x07};
	private List<LUN> LUNS = new ArrayList<LUN>();
	public ReportLUNParam(){}
	public ReportLUNParam(byte[] DataSegment) throws Exception{
		System.arraycopy(DataSegment, 0, LunListLength, 0, LunListLength.length);
		int length = getLunListLength()-7;
		for(int i=1;i<=length;i++){
			byte[] b = new byte[8];
			System.arraycopy(DataSegment, i*b.length, b, 0, b.length);
			LUNS.add(new LUN(ByteUtil.byteArrayToLong(b)));
		}
	}
	
	public int getLunListLength() { 
		return ByteUtil.byteArrayToInt(this.LunListLength);
	}
	public void setLUN(LUN lun){
		this.LUNS.add(lun);
		this.LunListLength = ByteUtil.intToByteArray(this.LUNS.size()+7);
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" LunListLength : "+ByteUtil.byteArrayToInt(LunListLength));
		for(LUN lun:LUNS){
			build.append(System.getProperty("line.separator")+" LUN : "+lun.getId());
		}
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.write(this.LunListLength);
			dos.write(new byte[]{0,0,0,0}); //Reserved
			for(LUN lun:LUNS){
				dos.write(lun.toByte());
			}
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		byte b = 0x07;
		byte r = (byte)(b << 5);
		byte d = (byte)(b | r);
		System.out.println(ByteUtil.toString(b));
		System.out.println(ByteUtil.toString(r));
		System.out.println(ByteUtil.toString(d));
		ReportLUNParam original = new ReportLUNParam();
		original.setLUN(new LUN(1));
		System.out.println(original);
		byte[] data = original.toByte();
		ReportLUNParam after = new ReportLUNParam(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
