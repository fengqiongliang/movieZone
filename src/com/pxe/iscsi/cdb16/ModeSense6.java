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
3.13 MODE SENSE(6) command

3.13.1 MODE SENSE(6) command introduction

   The MODE SENSE(6) command (see table 65) provides a means for a device server to report parameters to 
   an application client. It is a complementary command to the MODE SELECT(6) command. Device servers that 
   implement the MODE SENSE(6) command shall also implement the MODE SELECT(6) command.

                     Table 65 — MODE SENSE(6) command
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |                  OPERATION CODE (1Ah)                         |
   |-------------------------------------------------------------------------|
   |    1    |        Reserved               |  DBD  |  Reserved             |
   |-------------------------------------------------------------------------|
   |    2    |       PC      |                PAGE CODE                      |
   |-------------------------------------------------------------------------|
   |    3    |                 SUBPAGE CODE                                  |
   |-------------------------------------------------------------------------|
   |    4    |                 ALLOCATION LENGTH                             |
   |-------------------------------------------------------------------------|   
   |    5    |                 CONTROL                                       |
   |-------------------------------------------------------------------------|

   DBD (disable block descriptors) bit
     0 A disable block descriptors (DBD) bit set  to zero specifies that the device server may return zero or more block
       descriptors in the returned MODE SENSE data. 
     1 A DBD bit set to one specifies that the device server shall not return any block descriptors in the returned MODE
       SENSE data.
   
   PC (Page Control) field
   The page control (PC) field specifies the type of mode parameter values to be returned in the mode pages. The PC field is
   defined in table 66.
   
   Table 66 — Page control (PC) field
       00b Current values 
       01b Changeable values 
       10b Default values 
       11b Saved values 
	   
   The PC field only affects the mode parameters within the mode pages, however the PS bit, SPF bit, PAGE CODE field, 
   SUBPAGE CODE field, and PAGE LENGTH field should return current values (i.e., as if PC is set to 00b). The mode 
   parameter header and mode parameter block descriptor should return current values.

   Some SCSI target devices may not distinguish between current and saved mode parameters and report identical values in 
   response to a PC field of either 00b or 11b. See also the description of the save pages (SP) bit in the MODE SELECT command.
   
   PAGE CODE and SUBPAGE CODE fields
   The PAGE CODE and SUBPAGE CODE fields specify which mode pages and subpages to return (see table 67).
   

	               Table 67 —  Mode page code usage for all devices
    /-------------------------------------------------------------------------------------\
   |   Page Code   |  Subpage Code  |              Description                             |
   |---------------------------------------------------------------------------------------|
   |     00h       |vendor specific | Vendor specific (does not require page format)       |
   |---------------------------------------------------------------------------------------|
   |     01h - 1Fh |     00h        | See specific device types (page_0 format)            |
   |---------------------------------------------------------------------------------------|
   |     01h - 1Fh |     01h - DFh  | See specific device types (sub_page format)          |
   |---------------------------------------------------------------------------------------|
   |     01h - 1Fh |     E0h - FEh  | Vendor specific (sub_page format)                    |
   |---------------------------------------------------------------------------------------|
   |     01h - 1Fh |     FFh        | Return all subpages for the specified device         |
   |               |                | specific mode page in the page_0 format              |
   |               |                | for subpage 00h and in the sub_page format           |
   |               |                | for 01h - FEh                                        |
   |---------------------------------------------------------------------------------------|
   |     20h - 3Eh |     00h        | Vendor specific (page_0 format required)             |
   |---------------------------------------------------------------------------------------|
   |     20h - 3Eh |     01h - FEh  | Vendor specific (sub_page format required)           |
   |---------------------------------------------------------------------------------------|
   |     20h - 3Eh |     FFh        | Return all subpages for the specified vendor         |
   |               |                | specific mode page in the page_0 format              |
   |               |                | for subpage 00h and in the sub_page format           |
   |               |                | for 01h - FEh                                        |
   |---------------------------------------------------------------------------------------|
   |     3Fh       |     00h        | Return all subpage 00h mode pages in page_0 format   |
   |---------------------------------------------------------------------------------------|
   |     3Fh       |     01h - FEh  | Reserved                                             |
   |---------------------------------------------------------------------------------------|
   |     3Fh       |     FFh        | Return all subpages for all mode pages in the        |
   |               |                | page_0 format for subpage 00h and in the             |
   |               |                | sub_page format for 01h - FEh                        |
   |---------------------------------------------------------------------------------------|
 
   ALLOCATION LENGTH field
   The ALLOCATION LENGTH field is defined in 2.2.6.
   
   An application client may request any one or all of the supported mode pages from the device server. If an 
   application client issues a MODE SENSE command with a page code or subpage code value not implemented 
   by the logical unit, the command shall be terminated with CHECK CONDITION status, with the sense key set 
   to ILLEGAL REQUEST, and the additional sense code set to INVALID FIELD IN CDB.
   
   If an application client requests all supported mode pages, the device server shall return the supported pages 
   in ascending page code order beginning with mode page 01h. If mode page 00h is implemented, the device 
   server shall return mode page 00h after all other mode pages have been returned.
   
   If the PC field and the PAGE CODE field are both set to zero, the device server should return a mode parame-
   ter header and block descriptor, if applicable.
   
   The mode parameter list for all device types for MODE SELECT and MODE SENSE is defined in 3.13. Parts of 
   the mode parameter list are specifically defined for each device type. Definitions for the parts of each mode 
   parameter list that are unique for each device-type may be found in the applicable command standards.
 
   3.13.1.1 Current values
   A PC field value of 00b requests that the device server return the current values of the mode parameters. The 
   current values returned are:
        a)  The current values of the mode parameters established by the last successful MODE SELECT command;
        b)  The saved values of the mode parameters if a MODE SELECT command has not successfully 
            completed since the mode parameters were restored to their saved values (see 3.9); or
        c)  The default values of the mode parameters if a MODE SELECT command has not successfully 
            completed since the mode parameters were restored to their default values (see 3.9).
   
   3.13.1.2 Changeable values
   A PC field value of 01b requests that the device server return a mask denoting those mode parameters that are 
   changeable. In the mask, the bits in the fields of the mode parameters that are changeable all shall be set to 
   one and the bits in the fields of the mode parameters that are non-changeable (i.e., defined by the logical unit) 
   all shall be set to zero.
   
   If the logical unit does not implement changeable parameters mode pages and the device server receives a 
   MODE SENSE command with 01b in the PC field, then the command shall be terminated with CHECK CONDITION status, 
   with the sense key set to ILLEGAL REQUEST, and the additional sense code set to INVALID FIELD IN CDB.
   
   An attempt to change a non-changeable mode parameter using the MODE SELECT command shall result in 
   an error condition (see 3.9).
   
   The application client should issue a MODE SENSE command with the PC field set to 01b and the PAGE 
   CODE field set to 3Fh to determine which mode pages are supported, which mode parameters within the 
   mode pages are changeable, and the supported length of each mode page prior to issuing any MODE 
   SELECT commands.
   
   3.13.1.3 Default values
   A PC field value of 10b requests that the device server return the default values of the mode parameters. 
   Unsupported parameters shall be set to zero. Default values should be accessible even if the logical unit is not 
   ready.
   
   3.13.1.4 Saved values
   A PC field value of 11b requests that the device server return the saved values of the mode parameters. Mode 
   parameters not supported by the logical unit shall be set to zero. If saved values are not implemented, the 
   command shall be terminated with CHECK CONDITION status, with the sense key set to ILLEGAL REQUEST, 
   and the additional sense code set to SAVING PARAMETERS NOT SUPPORTED.
   
   The method of saving parameters is vendor specific. The parameters are preserved in such a manner that they 
   are retained when the device is powered down. All saveable mode pages should be considered saved when a 
   MODE SELECT command issued with the SP bit set to one has returned a GOOD status or after the successful 
   completion of a FORMAT UNIT command.

   3.13.1.5 Initial responses
   After a logical unit reset, the device server shall respond in the following manner:
       a)  If default values are requested, report the default values;
       b)  If saved values are requested, report valid restored mode parameters, or restore the mode parameters 
           and report them. If the saved values of the mode parameters are not able to be accessed from the 
           nonvolatile vendor specific location, the command shall be terminated with CHECK CONDITION status, 
           with the sense key set to NOT READY. If saved parameters are not implemented, respond as defined in 3.13.1.4; or
       c)  If current values are requested and the current values have been sent by the application client via a 
           MODE SELECT command, the current values shall be returned. If the current values have not been 
           sent, the device server shall return:
               a)  The saved values, if saving is implemented and saved values are available; or
               b)  The default values.  	
   	
	
	

   
   
</pre>
 * 
 *
 */
public class ModeSense6 {
	public enum PC {

/**
<pre>
00b -  Current values  
</pre>
 */
	CurrentValues((byte) 0x00),
	
 /**
<pre>
01b - Changeable values  
</pre>
 */
	ChangeableValues((byte) 0x01),

/**
<pre>
10b - Default values 
</pre>
 */
	DefaultValues ((byte) 0x02),
	
 /** 
<pre>
11b - Saved values
</pre>
 */
	SavedValues((byte) 0x03);
	    private final byte value;
	    private static Map<Byte , PC> mapping;
	    static {
	    	PC.mapping = new HashMap<Byte , PC>();
	        for (PC s : values()) {
	        	PC.mapping.put(s.value, s);
	        }
	    }
	    private PC (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final PC valueOf (final byte value) {
	        return PC.mapping.get(value);
	    }
	}
		
	private byte op = (byte)0x1a;
	private boolean DBD;
	private byte pc;
	private byte pageCode;
	private byte subPageCode;
	private byte allocateLength;
	private byte Control;
	public ModeSense6(){}
	public ModeSense6(byte[] CDB) throws Exception{
		DBD = (ByteUtil.getBit(CDB[1], 4) == 1);
		pc = (byte)((CDB[2] & 0xC0) >> 6);
		pageCode = (byte)(CDB[2] & 0x3F);
		subPageCode = CDB[3];
		allocateLength = CDB[4];
		Control = CDB[5];
	}
	
	public CDBOpcode getOpcode() {
		return CDBOpcode.ModeSense6;
	}
	public boolean getDBD() {
		return DBD;
	}
	public void setDBD(boolean dBD) {
		DBD = dBD;
	}
	public PC getPC() {
		return PC.valueOf(this.pc);
	}
	public void setPC(PC pc) {
		this.pc = pc.value;
	}
	public byte getPageCode() {
		return pageCode;
	}
	public void setPageCode(byte pageCode) {
		this.pageCode = pageCode;
	}
	public byte getSubPageCode() {
		return subPageCode;
	}
	public void setSubPageCode(byte subPageCode) {
		this.subPageCode = subPageCode;
	}
	public byte getAllocateLength() {
		return allocateLength;
	}
	public void setAllocateLength(byte allocateLength) {
		this.allocateLength = allocateLength;
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
		build.append(System.getProperty("line.separator")+" DBD     : "+this.DBD  );
		build.append(System.getProperty("line.separator")+" PC : "+PC.valueOf(this.pc));
		build.append(System.getProperty("line.separator")+" PAGE CODE : "+this.pageCode);
		build.append(System.getProperty("line.separator")+" SUBPAGE CODE : "+this.subPageCode);
		build.append(System.getProperty("line.separator")+" allocateLength : "+this.allocateLength);
		build.append(System.getProperty("line.separator")+" Control : "+this.Control);
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeByte(0x1a); //Opcode
			dos.writeByte(this.DBD?0x08:0x00);
			dos.writeByte((this.pc << 6)|this.pageCode);
			dos.writeByte(this.subPageCode);
			dos.writeByte(this.allocateLength);
			dos.writeByte(this.Control); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		ModeSense6 original = new ModeSense6();
		original.setDBD(true);
		original.setPC(PC.SavedValues);
		original.setPageCode((byte)1);
		original.setSubPageCode((byte)2);
		original.setAllocateLength((byte)3);
		original.setControl((byte)4);
		System.out.println(original);
		byte[] data = original.toByte();
		ModeSense6 after = new ModeSense6(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
