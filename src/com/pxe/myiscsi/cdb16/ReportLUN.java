package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
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
public class ReportLUN {
	public enum SelectReport {

 /**
<pre>
00h - The list shall contain the logical units accessible to the I_T nexus with the following addressing methods (see SAM-4):
              a) logical unit addressing method,
              b) peripheral device addressing method;
              c) flat space addressing method; and
              d) extended logical unit addressing method.
           If there are no logical units, the LUN LIST LENGTH field shall be zero.
</pre>
 */
		accessLUN((byte) 0x00),

 /** 
<pre>
01h - The list shall contain only well known logical units, if any. 
      If there are no well known logical units, the LUN LIST LENGTH field shall be zero

</pre>
 */
	wellKnownLUN((byte) 0x01),
	
/**
<pre>
02h - The list shall contain all logical units accessible to the I_T nexus.
</pre>
 */
	AllLUN((byte) 0x02),

/**
<pre>
F8h to FFh - Vendor specific
</pre>
 */
	Vendor((byte) 0xf8);
	    private final byte value;
	    private static Map<Byte , SelectReport> mapping;
	    static {
	    	SelectReport.mapping = new HashMap<Byte , SelectReport>();
	        for (SelectReport s : values()) {
	        	SelectReport.mapping.put(s.value, s);
	        }
	    }
	    private SelectReport (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final SelectReport valueOf (final byte value) {
	        return SelectReport.mapping.get(value);
	    }
	}
	
	private byte op = (byte)0xa0;
	private byte selectReport;
	/**
     * The ALLOCATION LENGTH field specifies the maximum number of bytes that an application client has allocated in the
     * Data-In Buffer. An allocation length of zero specifies that no data shall be transferred. This condition shall
     * not be considered as an error. The device server shall terminate transfers to the Data-In Buffer when the number
     * of bytes specified by the ALLOCATION LENGTH field have been transferred or when all available data have been
     * transferred, whichever is less.
     * <p>
     * The allocation length is used to limit the maximum amount of variable length data (e.g., mode data, log data,
     * diagnostic data) returned to an application client. If the information being transferred to the Data-In Buffer
     * includes fields containing counts of the number of bytes in some or all of the data, then the contents of these
     * fields shall not be altered to reflect the truncation, if any, that results from an insufficient ALLOCATION
     * LENGTH value, unless the standard that describes the Data-In Buffer format states otherwise.
     * <p>
     * If the amount of information to be transferred exceeds the maximum value that the ALLOCATION LENGTH field is
     * capable of specifying, the device server shall transfer no data and terminate the command with CHECK CONDITION
     * status, with the sense key set to ILLEGAL REQUEST, and the additional sense code set to INVALID FIELD IN CDB.
     * <p>
     * If EVPD is set to zero, the allocation length should be at least five, so that the ADDITIONAL LENGTH field in the
     * parameter data is returned. If EVPD is set to one, the allocation length should be should be at least four, so
     * that the PAGE LENGTH field in the parameter data is returned.
     */
	private byte[] allocateLength = new byte[4];
	private byte Control;
	public ReportLUN(){}
	public ReportLUN(byte[] CDB) throws Exception{
		if(CDB.length!=16)throw new Exception("illegic CDB Size , the proper length is 16");
		selectReport = CDB[2];
		System.arraycopy(CDB, 6, allocateLength, 0, allocateLength.length); 
		Control = CDB[11];
	}
	
	public CDBOpcode getOpcode() {
		return CDBOpcode.ReportLUN;
	}
	public SelectReport getSelectReport() {
		return SelectReport.valueOf(this.selectReport);
	}
	public void setSelectReport(SelectReport selectReport) {
		this.selectReport = selectReport.value;
	}
	public int getAllocateLength() { 
		return ByteUtil.byteArrayToInt(this.allocateLength);
	}
	public void setAllocateLength(int allocateLength) {
		this.allocateLength = ByteUtil.intToByteArray(allocateLength);
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
		build.append(System.getProperty("line.separator")+" selectReport : "+SelectReport.valueOf(this.selectReport));
		build.append(System.getProperty("line.separator")+" allocateLength : "+ByteUtil.byteArrayToInt(this.allocateLength));
		build.append(System.getProperty("line.separator")+" Control : "+this.Control);
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0xa0); //Opcode
			dos.write(new byte[]{0}); //Reserved
			dos.writeByte(this.selectReport);
			dos.write(new byte[]{0,0,0}); //Reserved
			dos.write(this.allocateLength); //This is the data segment payload length in bytes (excluding padding).
			dos.write(new byte[]{0}); //Reserved
			dos.writeByte(this.Control); 
			dos.write(new byte[]{0,0,0,0}); //Reserved
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		ReportLUN original = new ReportLUN();
		original.setSelectReport(SelectReport.wellKnownLUN);
		original.setAllocateLength(1);
		original.setControl((byte)2);
		System.out.println(original);
		byte[] data = original.toByte();
		ReportLUN after = new ReportLUN(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
