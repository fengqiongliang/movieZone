package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.cdb16.Inquiry.PageCode;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralDeviceType;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralQualifer;

/**
<pre>
4.4.8 Device Identification VPD page (83h)

   The Device Identification VPD page (see table 337) provides the means to retrieve zero or more identification 
   descriptors applying to the logical unit. Logical units may have more than one identification descriptor 
   (e.g., if several types or associations of identifier are supported). Device identifiers consist of one or 
   more of the following:

      • Logical unit names;
      • SCSI target port identifiers;
      • SCSI target port names;
      • SCSI target device names;
      • Relative target port identifiers;
      • SCSI target port group number; or
      • Logical unit group number.
	  
   Identification descriptors shall be assigned to the peripheral device (e.g., a disc drive) and not to the currently 
   mounted media, in the case of removable media devices. Operating systems are expected to use the identifi-
   cation descriptors during system configuration activities to determine whether alternate paths exist for the 
   same peripheral device.


                     Table 337 — Device Identification VPD page 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | PERIPHERAL QUALIFIER  |      PERIPHERAL DEVICE TYPE           |
   |-------------------------------------------------------------------------|
   |    1    |                  PAGE CODE (83h)                              |
   |-------------------------------------------------------------------------|
   |    2    | (MSB) |                                                       |
   |         |                  PAGE LENGTH (n-3)                            |
   |    3    |                                                       | (LSB) |
   |-------------------------------------------------------------------------|
   |         |                  DENTIFICATION DESCRIPTOR LIST                |
   |-------------------------------------------------------------------------|   
   |    4    |                  IDENTIFICATION DESCRIPTOR (First)            |
   |-------------------------------------------------------------------------|  
   |   ...   |                          ...                                  |
   |   ...   |                          ...                                  |
   |-------------------------------------------------------------------------|     
   |    n    |                  IDENTIFICATION DESCRIPTOR (Last)             |
   |-------------------------------------------------------------------------|
   
   The PERIPHERAL QUALIFIER field and PERIPHERAL DEVICE TYPE field identify the peripheral device connected to the
   logical unit. If the SCSI target device is not capable of supporting a peripheral device connected to this logical unit,
   the device server shall set these fields to 7Fh (i.e., PERIPHERAL QUALIFIER field set to 011b and PERIPHERAL DEVICE
   TYPE field set to 1Fh).

   The peripheral qualifier is defined as :
   
      000b - A peripheral device having the specified peripheral device type is connected to this 
             logical unit. If the device server is unable to determine whether or not a peripheral 
             device is connected, it also shall use this peripheral qualifier. This peripheral qualifier 
             does not mean that the peripheral device connected to the logical unit is ready for 
             access. 
   
      001b - A peripheral device having the specified peripheral device type is not connected to this 
             logical unit. However, the device server is capable of supporting the specified periph-eral 
			 device type on this logical unit. 
      
      010b - Reserved. 

      011b - The device server is not capable of supporting a peripheral device on this logical unit. 
             For this peripheral qualifier the peripheral device type shall be set to 1Fh. All other 
             peripheral device type values are reserved for this peripheral qualifier.
   			 
      100b to 111b - Vendor specific.

   The peripheral device type is defined as : 
   
      00h - Direct access block device (e.g., magnetic disk).
      01h - Sequential-access device (e.g., magnetic tape).
      02h - Printer device.
      03h - Processor device.
      04h - Write-once device (e.g., some optical disks).
      05h - CD/DVD device.
      06h - Scanner device (obsolete).
      07h - Optical memory device (e.g., some optical disks).
      08h - Media changer device (e.g., jukeboxes).
      09h - Communications device (obsolete).
      0Ah to 0Bh - Obsolete.
      0Ch - Storage array controller device (e.g., RAID).
      0Dh - Enclosure services device.
      0Eh - Simplified direct-access device (e.g., magnetic disk).
      0Fh - Optical card reader/writer device.
      10h - Bridge Controller Commands.
      11h - Object-based Storage Device.
      12h - Automation/Drive Interface.	  
      13h - Security manager device.
      14h to 1Dh - Reserved.
      1Eh - Well known logical unit. *
      1Fh - Unknown or no device type.	  
   
   The PAGE LENGTH field indicates the length of the mode page policy descriptor list. The relationship between the PAGE 
   LENGTH field and the CDB ALLOCATION LENGTH field is defined in 2.2.6.
   
   IDENTIFICATION DESCRIPTOR LIST field
   The IDENTIFICATION DESCRIPTOR LIST provides a list of Identification Descriptor information, the format of which is 
   given in Table 338.
   
   IDENTIFICATION DESCRIPTOR field
   Each IDENTIFICATION DESCRIPTOR (see Table 338) contains information identifying the logical unit, physical device, or 
   access path used by the command and returned parameter data.   
   
                     Table 338 — Identification Descriptor 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    |     PROTOCOL IDENTIFIER       |          CODE SET             |
   |-------------------------------------------------------------------------|
   |    1    |  PIV  |Reserve|  ASSOCIATION  |      IDENTIFIER TYPE          |
   |-------------------------------------------------------------------------|
   |    2    |                  Reserved                                     |
   |-------------------------------------------------------------------------|
   |    3    |                  IDENTIFIER LENGTH (n – 3)                    |
   |-------------------------------------------------------------------------| 
   |    4    |                  IDENTIFIER                                   |
   |   ...   |                                                               |
   |    n    |                                                               |
   |-------------------------------------------------------------------------|     

   PROTOCOL IDENTIFIER field
   The PROTOCOL IDENTIFIER field may indicate the SCSI transport protocol to which identification descriptor applies. If the 
   ASSOCIATION field contains a value other than 1h or 2h or the PIV bit is set to zero, then the PROTOCOL IDENTIFIER 
   field should be ignored. If the ASSOCIATION field contains a value of 1h or 2h and the PIV bit is set to one, then the 
   PROTOCOL IDENTIFIER field shall contain one of the values shown in table 238 (see 7.5.1) to indicate the SCSI transport 
   protocol to which identification descriptor applies.   
   
   CODE SET field
   The CODE SET field specifies the code set used for the identifier field, as described in Table 339. 
   This field is intended to be an aid to software that displays the identifier field.	
      0h - Reserved.	  
      1h - The IDENTIFIER field shall contain binary values.	  
      2h - The IDENTIFIER field shall contain ASCII graphic codes (i.e., code values 20h through 7Eh).	  
    3h - Fh - Reserved.	  	  
	
   PIV (Protocol Identifier Valid) bit
      0h - A protocol identifier valid (PIV) bit of zero indicates the PROTOCOL IDENTIFIER field should be ignored. 
	       If the ASSOCIATION field contains a value of 1h or 2h then a PIV bit set to one indicates 
           the PROTOCOL IDENTIFIER field contains a valid protocol identifier selected from the values shown in table 340. 
           If the ASSOCIATION field contains a value other than 1h or 2h then the PIV bit should be ignored.

   Table 340. PROTOCOL IDENTIFIER values
      0h - Fibre Channel.	  
      1h - Parallel SCSI.	  
      2h - SSA.	  
      3h - IEEE 1394.	  
      4h - Remote Direct Memory Access (RDMA).	  
      5h - Internet SCSI.	  
      6h - SAS Serial SCSI Protocol.	  
    7h to Fh - Reserved.	  
      
   ASSOCIATION field
   The ASSOCIATION field indicates  the entity that the  Identification descriptor describes. 
   If a physical or  logical device returns an Identification descriptor with the ASSOCIATION field  set to 0h, 
   it shall return the same descriptor when it is accessed through any other path.
   
   The ASSOCIATION field specifies the entity with which the Identifier field is associated, as described in Table 341.
   Table 341. ASSOCIATION field
      0h - The IDENTIFIER field is associated with the addressed physical or logical device.	  
      1h - The IDENTIFIER field is associated with the port that received the reques.	  
      2h - The IDENTIFIER field is associated with the SCSI target device that contains the addressed logical unit.	  
      3h - Reserved.	     
 
   IDENTIFIER TYPE field
   The IDENTIFIER TYPE field specifies the format and assignment authority for the identifier, 
   as described in tables 342 and 343.
   
   Table 342. IDENTIFIER Type field
      0h - No assignment authority was used and consequently there is no guarantee that the identifier 
	       is globally unique(i.e., the identifier is vendor specific).	  
      1h - The first 8 bytes of the IDENTIFIER field are a Vendor ID (see annex C). 
           The organization associated with the Vendor ID is responsible for ensuring that 
           the remainder of the identifier field is unique. 
           One recommended method of constructing the remainder of the identifier field is to concatenate 
           the product identification field from the standard INQUIRY data field and the product serial number field 
           from the unit serial number page.	  
      2h - The IDENTIFIER field contains a Canonical form IEEE Extended Unique Identifier, 64-bit (EUI-64). 
           In this case, the identifier length field shall be set to 8. 
           Note that the IEEE guide-lines for EUI-64 specify a method for unambiguously encapsulating an IEEE 48-bit 
		   identifier within an EUI-64.	  
      3h - The IDENTIFIER field contains an FC-PH, FC-PH3 or FC-FS Name_Identifier. Any FC-PH, FC-PH3 or FC-FS 
           identifier may be used, including one of the four based on a Canonical form IEEE company_id.	  
      4h - If the ASSOCIATION field contains 1h, the Identifier value contains a four-byte binary number identifying the 
           port relative to other ports in the device using the values shown Table 341. 
		   The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
		   If the ASSOCIATION field does not contain 1h, use of this identifier type is reserved.	  
      5h - If the Association value is 1h, the Identifier value contains a four-byte binary number 
           identifying the port relative to other ports in the device using the values shown Table 341. 
           The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
           If the ASSOCIATION field does not contain 1h, use of this identifier type is reserved.
      6h - If the ASSOCIATION value is 0h, the IDENTIFIER value contains a four-byte binary number identifying the port 
           relative to other ports in the device using the values shown Table 341. 
           The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
           If the ASSOCIATION field does not contain 0h, use of this identifier type is reserved.
      7h - The MD5 logical unit identifier shall not be used if a logical unit provides unique identification using identifier 
           types 2h or 3h. A bridge device may return a MD5 logical unit identifier type for that logical unit that does not 
           support the Device Identification VPD page.		   
    8h to Fh - Reserved.			   
	
    IDENTIFIER LENGTH field
    The IDENTIFIER LENGTH field specifies the length in bytes of the IDENTIFIER field. If the ALLOCATION LENGTH field of 
    the command descriptor block is too small to transfer all of the identifier, 
	the identifier length shall not be adjusted to reflect the truncation.

    IDENTIFIER field
    The IDENTIFIER field contains the identifier as described by the Association, Identifier Type, CODE SET, and IDENTIFIER 
    LENGTH fields. The example described in this clause and shown in Table 343 is not a normative part of this manual. This 
    example of a complete device identification VPD page assumes that the product is a direct-access device with an T10 Ven-
    dor ID of “XYZ_Corp,” a product identification of “Super Turbo Disk,” and a product serial number of “2034589345.” Further-
    more, it is assumed that the manufacturer has been assigned a 24-bit IEEE company_id of 01ABCDh by the IEEE 
    Registration Authority Committee and that the manufacture has assigned a 24-bit extension_identifier of 234567h to this 
    logical unit. The combined 48-bit identifier  is reported in the 64-bit format as defined by the IEEE 64-bit Global Identifier 
    (EUI-64) standard. The data returned in the device identification VPD page for this logical unit is shown in Table 343.
	
    Table 343. Device Identification page example
	Bytes Hexadecimal values ASCII values
 
                     Table 338 — Identification Descriptor 
    /----------------------------------------------------------------------------------\
   |   Byte  |               Hexadecimal values                 |     ASCII values      |
   |------------------------------------------------------------------------------------|
   |  00-15  |  00 83 00 32 02 01 00 22 58 59 5A 5F 43 6F 72 70 | ...2...XYZ_Corp [5]   |
   |  16-31  |  53 75 70 65 72 20 54 75 72 62 6F 20 44 69 73 6B | Super Turbo Disk  PIV |
   |  32-47  |  32 30 33 34 35 38 39 33 34 35 01 02 00 08 01 AB | 2034589345.......     |
   |  48-53  |  CD FF FF 23 45 67                               | ......                |
   |------------------------------------------------------------------------------------| 	
	
   Notes.
       a)  Non-printing ASCII characters are shown as “.”.
       b)  Byte 00 is the beginning of the VPD page (see Table 328).
       c)  Byte 04 is the beginning of the Identification descriptor for the Vendor ID based identifier 
          (Identifier type 1 see Table 342).
       d)  Byte 42 is the beginning of the Identification Descriptor for the EUI-64 identifier 
          (Identifier type 2, see Table 342).
       e)  For Seagate devices, this will say “Seagate.”
	   
	   
	   
   
   
</pre>
 * 
 *
 */
public class InquiryDeviceID {

	  
	private byte peripheralQulifer;
	private byte peripheralType;
	private byte[] PageLength = new byte[2];
	private List<InquiryDeviceIDDescr> IDDescList =new ArrayList<InquiryDeviceIDDescr>();
	public InquiryDeviceID(){}
	public InquiryDeviceID(byte[] CDB) throws Exception{
		peripheralQulifer = (byte)((CDB[0] & 0xE0) >> 5);
		peripheralType = (byte)(CDB[0] & 0x1F);
		System.arraycopy(CDB, 2, PageLength, 0, PageLength.length);
		short length = ByteUtil.byteArrayToShort(this.PageLength);
		for(int i=0;i<length;){
			byte descrLength =CDB[4+i+3]; 
			byte[] descrCDB = new byte[4+descrLength];
			System.arraycopy(CDB, 4+i, descrCDB, 0, descrCDB.length);
			IDDescList.add(new InquiryDeviceIDDescr(descrCDB));
			i=i+descrCDB.length;
		}
	}
	
	public PeripheralQualifer getPeripheralQulifer() {
		return PeripheralQualifer.valueOf(this.peripheralQulifer);
	}
	public void setPeripheralQulifer(PeripheralQualifer peripheralQulifer) {
		this.peripheralQulifer = peripheralQulifer.value();
	}
	public PeripheralDeviceType getPeripheralType() {
		return PeripheralDeviceType.valueOf(this.peripheralType);
	}
	public void setPeripheralType(PeripheralDeviceType peripheralType) {
		this.peripheralType = peripheralType.value();
	}
	public PageCode getPageCode() {
		return PageCode.DeviceID;
	}
	public short getPageLength() {
		short length = 0;
		for(InquiryDeviceIDDescr desc:this.IDDescList){
			length = (short)(length + 4+desc.getIDLength());
		}
		return length;
	}
	public byte[] getIDDescListASByte() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(InquiryDeviceIDDescr desc:this.IDDescList){
			try {
				baos.write(desc.toByte());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return baos.toByteArray();
	}
	public List<InquiryDeviceIDDescr> getIDDescList() {
		return IDDescList;
	}
	public void setIDDescList(InquiryDeviceIDDescr descr) {
		this.IDDescList.add(descr);
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" PERIPHERAL QUALIFIER : "+PeripheralQualifer.valueOf(this.peripheralQulifer));
		build.append(System.getProperty("line.separator")+" PERIPHERAL DEVICE TYPE   : "+PeripheralDeviceType.valueOf(this.peripheralType));
		build.append(System.getProperty("line.separator")+" pageCode : "+this.getPageCode());
		build.append(System.getProperty("line.separator")+" PAGE LENGTH (n-3) : "+ByteUtil.byteArrayToShort(this.PageLength));
		build.append(System.getProperty("line.separator")+" DENTIFICATION DESCRIPTOR LIST : ");
		for(InquiryDeviceIDDescr desc:this.IDDescList){
			build.append(System.getProperty("line.separator")+"       "+desc.toString());
		}
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			byte b0 = (byte)(this.peripheralQulifer << 5);
			b0 = (byte)(b0 | this.peripheralType);
			dos.writeByte(b0);
			dos.writeByte(0x83);
			byte[] descList = getIDDescListASByte();
			dos.writeShort(descList.length);
			dos.write(descList); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		InquiryDeviceID original = new InquiryDeviceID();
		original.setPeripheralQulifer(PeripheralQualifer.notSupported);
		original.setPeripheralType(PeripheralDeviceType.CDorDVDDevice);
		InquiryDeviceIDDescr descr1 = new InquiryDeviceIDDescr();
		InquiryDeviceIDDescr descr2 = new InquiryDeviceIDDescr();
		InquiryDeviceIDDescr descr3 = new InquiryDeviceIDDescr();
		InquiryDeviceIDDescr descr4 = new InquiryDeviceIDDescr();
		descr1.setId("hello,myfjdlsjafldj");
		descr2.setId("======fdjslajfdsa=====fdjsa=jf=dasj=====");
		descr4.setId("!!!!");
		original.setIDDescList(descr1);
		original.setIDDescList(descr2);
		original.setIDDescList(descr3);
		original.setIDDescList(descr4);
		System.out.println(original);
		byte[] data = original.toByte();
		System.out.println("data size === "+data.length);
		InquiryDeviceID after = new InquiryDeviceID(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
