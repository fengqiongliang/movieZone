package com.pxe.myiscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.pxe.myiscsi.cdb16.Inquiry.PageCode;
import com.pxe.myiscsi.cdb16.InquiryStandardData.PeripheralDeviceType;
import com.pxe.myiscsi.cdb16.InquiryStandardData.PeripheralQualifer;

/**
<pre>
4.4.11 Supported Vital Product Data pages (00h)

   This clause contains a list of the vital product data page codes supported by the target or logical unit 
   (see Table 349). If a target supports any vital product data pages, 
   it also shall support this vital product data page.

                     Table 349 — Supported Vital Product Data pages 
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | PERIPHERAL QUALIFIER  |      PERIPHERAL DEVICE TYPE           |
   |-------------------------------------------------------------------------|
   |    1    |                  PAGE CODE (00h)                              |
   |-------------------------------------------------------------------------|
   |    2    |                  Reserved                                     |
   |-------------------------------------------------------------------------|
   |    3    |                  PAGE LENGTH (n-3)                            |
   |-------------------------------------------------------------------------|
   |    4    |                                                               |
   |   ...   |                  SUPPORTED PAGE LIST                          |
   |    n    |                                                               |
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
   
   The SUPPORTED PAGE LIST field shall contain a list of all vital product data page codes (see clause 4.4.1) implemented 
   for the target or logical unit in ascending order beginning with page code 00h.
   
   
   
</pre>
 * 
 *
 */
public class InquirySupportedVPD {

	  
	private byte peripheralQulifer;
	private byte peripheralType;
	private byte PageLength;
	private byte[] SupportedPageList = new byte[0];
	public InquirySupportedVPD(){}
	public InquirySupportedVPD(byte[] CDB) throws Exception{
		peripheralQulifer = (byte)((CDB[0] & 0xE0) >> 5);
		peripheralType = (byte)(CDB[0] & 0x1F);
		PageLength = CDB[3];
		SupportedPageList = new byte[PageLength];
		for(int i=0;i<PageLength;i++){
			SupportedPageList[i] = CDB[4+i];
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
		return PageCode.SupportedVPD;
	}
	public byte getPageLength() {
		return PageLength;
	}
	public PageCode[] getSupportedPageList() {
		PageCode[] pageCode = new PageCode[this.SupportedPageList.length];
		for(int i=0;i<pageCode.length;i++){
			pageCode[i] = PageCode.valueOf(this.SupportedPageList[i]);
		}
		return pageCode;
	}
	public void setSupportedPageList(PageCode pageCode) {
		byte[] newValue = new byte[this.SupportedPageList.length+1];
		System.arraycopy(this.SupportedPageList, 0, newValue, 0, this.SupportedPageList.length);
		newValue[newValue.length-1] = pageCode.value();
		this.SupportedPageList = newValue;
		this.PageLength = (byte)this.SupportedPageList.length;
	}
	
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" PERIPHERAL QUALIFIER : "+PeripheralQualifer.valueOf(this.peripheralQulifer));
		build.append(System.getProperty("line.separator")+" PERIPHERAL DEVICE TYPE   : "+PeripheralDeviceType.valueOf(this.peripheralType));
		build.append(System.getProperty("line.separator")+" pageCode : "+this.getPageCode().value());
		build.append(System.getProperty("line.separator")+" PAGE LENGTH (n-3) : "+this.PageLength);
		build.append(System.getProperty("line.separator")+" SUPPORTED PAGE LIST : ");
		for(PageCode pageCode:this.getSupportedPageList()){
			build.append(System.getProperty("line.separator")+"       "+pageCode );
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
			dos.writeByte(0x00);
			dos.writeByte(0x00); //Reserved
			dos.writeByte(this.PageLength);
			dos.write(this.SupportedPageList); 
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		InquirySupportedVPD original = new InquirySupportedVPD();
		original.setPeripheralQulifer(PeripheralQualifer.notSupported);
		original.setPeripheralType(PeripheralDeviceType.CDorDVDDevice);
		original.setSupportedPageList(PageCode.BlockLimits);
		original.setSupportedPageList(PageCode.ExtendInquiryData);
		original.setSupportedPageList(PageCode.SupportedVPD);
		original.setSupportedPageList(PageCode.UnitSerialNumber);
		System.out.println(original);
		byte[] data = original.toByte();
		System.out.println("data size === "+data.length);
		InquirySupportedVPD after = new InquirySupportedVPD(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
