package com.pxe.iscsi.cdb16;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;




import com.pxe.iscsi.cdb16.Inquiry.PageCode;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralDeviceType;
import com.pxe.iscsi.cdb16.InquiryStandardData.PeripheralQualifer;

/**
<pre>
4.4.12 Unit Serial Number page (80h)
   This page provides a product serial number for the target or logical unit. See Table 350 following.
   

                     Table 350 — Unit Serial Number page (80h)
    /-----------------------------------------------------------------------\
   |Byte/Bit |   7   |   6   |   5   |   4   |   3   |   2   |   1   |   0   |
   |-------------------------------------------------------------------------|
   |    0    | PERIPHERAL QUALIFIER  |      PERIPHERAL DEVICE TYPE           |
   |-------------------------------------------------------------------------|
   |    1    |                  PAGE CODE (80h)                              |
   |-------------------------------------------------------------------------|
   |    2    |                  Reserved                                     |
   |-------------------------------------------------------------------------|   
   |    3    |                  PAGE LENGTH                                  |
   |-------------------------------------------------------------------------|  
   |   4-n   |                  Product Serial Number                        |
   |-------------------------------------------------------------------------|
   
   PERIPHERAL QUALIFIER field and the PERIPHERAL DEVICE TYPE field
   The PERIPHERAL QUALIFIER field and the PERIPHERAL DEVICE TYPE field are defined in STANDARD INQUIRY DATA (see 3.6.2).
   
   PAGE CODE field
   PAGE CODE field
   
   PAGE LENGTH field
   The PAGE LENGTH field specifies the length in bytes of the product serial number page. Older products that only support 
   the Product Serial Number parameter will have a page length of 08h, while newer products that support both parameters 
   will have a page length of 14h. If the ALLOCATION LENGTH is too small to transfer all of the page, the page length shall 
   not be adjusted to reflect the truncation.
   
   Product Serial Number field
   The Product Serial Number field contains ASCII data that is vendor-assigned serial number. The least significant ASCII 
   character of the serial number shall appear as the last byte in the Data-In Buffer. If the product serial number is not avail-
   able, the target shall return ASCII spaces (20h) in this field.	   
   
   
</pre>
 * 
 *
 */
public class InquiryUnitSerialNum {

	  
	private byte peripheralQulifer;
	private byte peripheralType;
	private byte PageLength=1;
	private String ProductSerialNumber = " ";
	public InquiryUnitSerialNum(){}
	public InquiryUnitSerialNum(byte[] CDB) throws Exception{
		peripheralQulifer = (byte)((CDB[0] & 0xE0) >> 5);
		peripheralType = (byte)(CDB[0] & 0x1F);
		PageLength = CDB[3];
		ProductSerialNumber = new String(CDB,4,PageLength,"US-ASCII");
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
		return PageCode.UnitSerialNumber;
	}
	public byte getPageLength() {
		return PageLength;
	}
	public String getProductSerialNumber() {
		return ProductSerialNumber;
	}
	public void setProductSerialNumber(String productSerialNumber) {
		ProductSerialNumber = productSerialNumber;
		try{
			PageLength = (byte)(productSerialNumber.getBytes("US-ASCII").length);
		}catch(Exception ex){}
	}
	public String toString(){
		StringBuilder build = new StringBuilder();
		build.append(System.getProperty("line.separator")+" PERIPHERAL QUALIFIER : "+PeripheralQualifer.valueOf(this.peripheralQulifer));
		build.append(System.getProperty("line.separator")+" PERIPHERAL DEVICE TYPE   : "+PeripheralDeviceType.valueOf(this.peripheralType));
		build.append(System.getProperty("line.separator")+" pageCode : "+this.getPageCode());
		build.append(System.getProperty("line.separator")+" PAGE LENGTH : "+this.PageLength);
		build.append(System.getProperty("line.separator")+" ProductSerialNumber : "+this.ProductSerialNumber);
		return build.toString();
	}
	
	public byte[] toByte(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			byte b0 = (byte)(this.peripheralQulifer << 5);
			b0 = (byte)(b0 | this.peripheralType);
			dos.writeByte(b0);
			dos.writeByte(0x80);
			dos.writeByte(0);   //Reserved 
			byte[] b4 = this.ProductSerialNumber.getBytes("US-ASCII");
			dos.writeByte(b4.length);   //Reserved
			dos.write(b4);  
			byte[] result = bos.toByteArray();
			dos.close();
			bos.close();
			return result;
		} catch (IOException e) {e.printStackTrace();} 
		return new byte[0];
		
	}
	
	public static void main(String[] args) throws Exception{
		InquiryUnitSerialNum original = new InquiryUnitSerialNum();
		original.setPeripheralQulifer(PeripheralQualifer.notSupported);
		original.setPeripheralType(PeripheralDeviceType.CDorDVDDevice);
		original.setProductSerialNumber("wwerewrew aaaaa @@@@  dafdsaqqq11111111 # 1");
		System.out.println(original);
		byte[] data = original.toByte();
		System.out.println("data size === "+data.length);
		InquiryUnitSerialNum after = new InquiryUnitSerialNum(data);
		System.out.println(after);
		System.out.println(data.length);
		
	}
 	
	
	 
}
