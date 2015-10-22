package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import com.moviezone.util.ByteUtil;

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
public class InquiryDeviceIDDescr {
	public enum ProtocolID {

 /**
<pre>
0h - Fibre Channel.	
</pre>
 */
		FibreChannel((byte) 0x00),

 /** 
<pre>
1h - Parallel SCSI.
</pre>
 */
	ParallelSCSI((byte) 0x01),
	
/**
<pre>
2h - SSA.	
</pre>
 */
	SSA((byte) 0x02),
	
/**
<pre>
3h - IEEE 1394.	
</pre>
 */
	IEEE1394((byte) 0x03),
	
/**
<pre>
4h - Remote Direct Memory Access (RDMA).	
</pre>
 */
	RDMA((byte) 0x04),
	
/**
<pre>
5h - Internet SCSI.	
</pre>
 */
	ISCSI((byte) 0x05),
	
/**
<pre>
6h - SAS Serial SCSI Protocol.	 
</pre>
 */
	SASSerialSCSI((byte) 0x06);
	
	    private final byte value;
	    private static Map<Byte , ProtocolID> mapping;
	    static {
	    	ProtocolID.mapping = new HashMap<Byte , ProtocolID>();
	        for (ProtocolID s : values()) {
	        	ProtocolID.mapping.put(s.value, s);
	        }
	    }
	    private ProtocolID (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final ProtocolID valueOf (final byte value) {
	        return ProtocolID.mapping.get(value);
	    }
	}

	public enum CodeSet {

 /**
<pre>
1h - The IDENTIFIER field shall contain binary values.
</pre>
 */
		BinaryValue((byte) 0x01),

/**
<pre>
2h - The IDENTIFIER field shall contain ASCII graphic codes (i.e., code values 20h through 7Eh).
</pre>
 */
	AscillGraphicValue((byte) 0x02),

/**
<pre>
3h - The IDENTIFIER field shall contain ISO/IEC 10646-1 (UTF-8) codes:
</pre>
 */
	UTF8((byte) 0x03);
			
	    private final byte value;
	    private static Map<Byte , CodeSet> mapping;
	    static {
	    	CodeSet.mapping = new HashMap<Byte , CodeSet>();
	        for (CodeSet s : values()) {
	        	CodeSet.mapping.put(s.value, s);
	        }
	    }
	    private CodeSet (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final CodeSet valueOf (final byte value) {
	        return CodeSet.mapping.get(value);
	    }
	}
	
	public enum Association {

 /**
<pre>
0h - The IDENTIFIER field is associated with the addressed physical or logical device.
</pre>
 */
		PhysicalOrLogicDevice((byte) 0x00),

 /** 
<pre>
1h - The IDENTIFIER field is associated with the port that received the reques.
</pre>
 */
	Port((byte) 0x01),
	
/**
<pre>
2h - The IDENTIFIER field is associated with the SCSI target device that contains the addressed logical unit.	  
</pre>
 */
	SCSITarget((byte) 0x02);
	
	    private final byte value;
	    private static Map<Byte , Association> mapping;
	    static {
	    	Association.mapping = new HashMap<Byte , Association>();
	        for (Association s : values()) {
	        	Association.mapping.put(s.value, s);
	        }
	    }
	    private Association (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final Association valueOf (final byte value) {
	        return Association.mapping.get(value);
	    }
	}

	public enum IDType {

 /**
<pre>
0h - No assignment authority was used and consequently there is no guarantee that the identifier 
     is globally unique(i.e., the identifier is vendor specific).
</pre>
 */
		NoAuthority((byte) 0x00),

 /** 
<pre>
 1h - The first 8 bytes of the IDENTIFIER field are a Vendor ID (see annex C). 
      The organization associated with the Vendor ID is responsible for ensuring that 
      the remainder of the identifier field is unique. 
      One recommended method of constructing the remainder of the identifier field is to concatenate 
      the product identification field from the standard INQUIRY data field and the product serial number field 
      from the unit serial number page.	  
</pre>
 */
	First8ByteVendorID((byte) 0x01),
	
/**
<pre>
2h - The IDENTIFIER field contains a Canonical form IEEE Extended Unique Identifier, 64-bit (EUI-64). 
      In this case, the identifier length field shall be set to 8. 
      Note that the IEEE guide-lines for EUI-64 specify a method for unambiguously encapsulating an IEEE 48-bit 
      identifier within an EUI-64.
</pre>
 */
	EUI64((byte) 0x02),
	
/**
<pre>
3h - The IDENTIFIER field contains an FC-PH, FC-PH3 or FC-FS Name_Identifier. Any FC-PH, FC-PH3 or FC-FS 
     identifier may be used, including one of the four based on a Canonical form IEEE company_id.
</pre>
 */
	FCNameID((byte) 0x03),
	
/**
<pre>
4h - If the ASSOCIATION field contains 1h, the Identifier value contains a four-byte binary number identifying the 
     port relative to other ports in the device using the values shown Table 341. 
     The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
     If the ASSOCIATION field does not contain 1h, use of this identifier type is reserved.	
</pre>
 */
	BinaryNumber((byte) 0x04),
	
/**
<pre>
5h - If the Association value is 1h, the Identifier value contains a four-byte binary number 
     identifying the port relative to other ports in the device using the values shown Table 341. 
     The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
     If the ASSOCIATION field does not contain 1h, use of this identifier type is reserved.

</pre>
 */
	BinaryNumber2((byte) 0x05),
	
/**
<pre>
 6h - If the ASSOCIATION value is 0h, the IDENTIFIER value contains a four-byte binary number identifying the port 
      relative to other ports in the device using the values shown Table 341. 
      The CODE SET field shall be set to 1h and the IDENTIFIER LENGTH field shall be set to 4h. 
      If the ASSOCIATION field does not contain 0h, use of this identifier type is reserved.

</pre>
 */
	BinaryNumber3((byte) 0x06),

/**
<pre>
7h - The MD5 logical unit identifier shall not be used if a logical unit provides unique identification using identifier 
     types 2h or 3h. A bridge device may return a MD5 logical unit identifier type for that logical unit that does not 
     support the Device Identification VPD page.	

</pre>
 */
	NoMD5ID((byte) 0x07),

/**
<pre>
8h - SCSI_NAME_STRING
</pre>
 */
		ScsiNameString((byte) 0x08);
	
		
	    private final byte value;
	    private static Map<Byte , IDType> mapping;
	    static {
	    	IDType.mapping = new HashMap<Byte , IDType>();
	        for (IDType s : values()) {
	        	IDType.mapping.put(s.value, s);
	        }
	    }
	    private IDType (final byte newValue) {
	        value = newValue;
	    }
	    public final byte value () {
	        return value;
	    }
	    public static final IDType valueOf (final byte value) {
	        return IDType.mapping.get(value);
	    }
	}

	
	private byte protocolID;
	private byte codeSet;
	private boolean PIV;
	private byte association;
	private byte idType;
	private byte idLength;
	private byte[] id = new byte[0];
	public InquiryDeviceIDDescr(){}
	public InquiryDeviceIDDescr(byte[] CDB) throws Exception{
		protocolID = (byte)((CDB[0] & 0xF0) >> 4);
		codeSet = (byte)(CDB[0] & 0x0F);
		PIV = (ByteUtil.getBit(CDB[1], 0) == 1);
		association = (byte)((CDB[1] & 0x30) >> 4);
		idType = (byte)(CDB[1] & 0x0F);
		idLength = CDB[3];
		id = new byte[idLength];
		System.arraycopy(CDB, 4, id, 0, id.length);
	}
	
	public ProtocolID getProtocolID() {
		return ProtocolID.valueOf(this.protocolID);
	}
	public void setProtocolID(ProtocolID protocolID) {
		this.protocolID = protocolID.value();
	}
	public CodeSet getCodeSet() {
		return CodeSet.valueOf(this.codeSet);
	}
	public void setCodeSet(CodeSet codeSet) {
		this.codeSet = codeSet.value();
	}
	public boolean getPIV() {
		return this.PIV;
	}
	public void setPIV(boolean pIV) {
		this.PIV = pIV;
	}
	public Association getAssociation() {
		return Association.valueOf(association);
	}
	public void setAssociation(Association association) {
		this.association = association.value;
	}
	public IDType getIDType() {
		return IDType.valueOf(this.idType);
	}
	public void setIDType(IDType idType) {
		this.idType = idType.value;
	}
	public byte getIDLength(){
		return this.idLength;
	}
	public String getId() {
		if(getCodeSet() == CodeSet.BinaryValue)return ByteUtil.toHex(this.id);
		return getASCIIString(this.id);
	}
	public void setId(byte[] id) {
		this.id = id;
		this.idLength = (byte)id.length;
		this.setCodeSet(CodeSet.BinaryValue);
	}
	public void setId(String id) {
		this.id = getASCII(id);
		this.idLength = (byte)this.id.length;
		this.setCodeSet(CodeSet.AscillGraphicValue);
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" PROTOCOL IDENTIFIER : "+ProtocolID.valueOf(this.protocolID));
		build.append(System.getProperty("line.separator")+" CODE SET   : "+CodeSet.valueOf(this.codeSet));
		build.append(System.getProperty("line.separator")+" PIV   : "+this.PIV);
		build.append(System.getProperty("line.separator")+" ASSOCIATION : "+Association.valueOf(this.association));
		build.append(System.getProperty("line.separator")+" IDENTIFIER TYPE : "+IDType.valueOf(this.idType));
		build.append(System.getProperty("line.separator")+" IDENTIFIER LENGTH (n – 3)   : "+this.idLength);
		build.append(System.getProperty("line.separator")+" IDENTIFIER  : "+getId());
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			byte b0 = (byte)(this.protocolID << 4);
			b0 = (byte)(b0 | this.codeSet);
			dos.writeByte(b0);
			byte b1 = (byte)(this.PIV?0x80:0x00);
			b1 = (byte) (b1 | (this.association << 4));
			b1 = (byte) (b1 | this.idType );
			dos.writeByte(b1);
			dos.writeByte(0x00); //Reserved
			dos.writeByte(this.idLength);
			dos.write(this.id); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		InquiryDeviceIDDescr original = new InquiryDeviceIDDescr();
		original.setProtocolID(ProtocolID.ISCSI);
		original.setCodeSet(CodeSet.AscillGraphicValue);
		original.setPIV(true);
		original.setAssociation(Association.SCSITarget);
		original.setIDType(IDType.FCNameID);
		//original.setId("hello,my little boy!");
		System.out.println(original);
		byte[] data = original.toByte();
		System.out.println("data size === "+data.length);
		InquiryDeviceIDDescr after = new InquiryDeviceIDDescr(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	private byte[] getASCII(String desc){
		byte[] result = new byte[desc.length()];
		for(int i=0;i<desc.length();i++){
			result[i] = (byte)desc.charAt(i);
		}
		return result;
	}
	private String getASCIIString(byte[] src){
		StringBuilder build = new StringBuilder();
		for(int i=0;i<src.length;i++){
			if(src[i]==0)continue;
			build.append((char)src[i]);
		}
		return build.toString();
	}
	 
}
