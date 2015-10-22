package com.pxe.myiscsi.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import com.pxe.myiscsi.ENUM.Stage;
import com.pxe.myiscsi.cdb16.Inquiry;
import com.pxe.myiscsi.cdb16.InquiryStandardData;
import com.pxe.myiscsi.cdb16.InquirySupportedVPD;
import com.pxe.myiscsi.cdb16.ReportLUN;
import com.pxe.myiscsi.cdb16.ReportLUN.SelectReport;
import com.pxe.myiscsi.cdb16.ReportLUNParam;
import com.pxe.myiscsi.pdu.LoginRequest;
import com.pxe.myiscsi.pdu.LoginResponse;
import com.pxe.myiscsi.pdu.SCSICommand;
import com.pxe.myiscsi.pdu.SCSICommand.Attr;
import com.pxe.myiscsi.pdu.SCSIDataIn;

public class Test {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress("192.168.43.50",3260));
		InputStream   readStream = socket.getInputStream();
		OutputStream writeStream = socket.getOutputStream();
		byte[] buf = new byte[1024];
		byte[] ISID = new byte[]{0x40,0x00,0x01,0x37,0x00,0x00};
		LoginRequest loginReq1 = new LoginRequest();
		loginReq1.setTransit(true);
		loginReq1.setCSG(Stage.SecurityNegotiation);
		loginReq1.setNSG(Stage.LoginOperationalNegotiation);
		loginReq1.setISID(ISID);
		loginReq1.setInitiatorTaskTag(3);
		loginReq1.setCID((short)1);
		loginReq1.setCmdSN(1);
		loginReq1.setExpStatSN(1);
		loginReq1.setParameter("InitiatorName", "iqn.1991-05.com.microsoft:ahone-outer-pc");
		loginReq1.setParameter("SessionType", "Normal");
		loginReq1.setParameter("TargetName", "iqn.2007-08.name.dns.target.my:iscsiboot3260");
		loginReq1.setParameter("AuthMethod", "None");
		System.out.println(loginReq1);
		writeStream.write(loginReq1.toByte());
		writeStream.flush();
		
		//login Resposne 
		
		int size = readStream.read(buf);
		byte[] BHS = new byte[48];
		byte[] DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		LoginResponse response = new LoginResponse(BHS,DataSegment);
		System.out.println(response);
		
		LoginRequest loginReq2 = new LoginRequest();
		loginReq2.setTransit(true);
		loginReq2.setCSG(Stage.LoginOperationalNegotiation);
		loginReq2.setNSG(Stage.FullFeaturePhase);
		loginReq2.setISID(ISID);
		loginReq2.setTSIH(response.getTSIH());
		loginReq2.setInitiatorTaskTag(3);
		loginReq2.setCID((short)1);
		loginReq2.setCmdSN(response.getExpCmdSN());
		loginReq2.setExpStatSN(2);
		loginReq2.setParameter("HeaderDigest", "None,CRC32C");
		loginReq2.setParameter("DataDigest", "None,CRC32C");
		loginReq2.setParameter("ErrorRecoveryLevel", "2");
		loginReq2.setParameter("InitialR2T", "No");
		loginReq2.setParameter("ImmediateData", "Yes");
		loginReq2.setParameter("MaxRecvDataSegmentLength", "65536");
		loginReq2.setParameter("MaxBurstLength", "262144");
		loginReq2.setParameter("FirstBurstLength", "65536");
		loginReq2.setParameter("MaxConnections", "32");
		loginReq2.setParameter("DataPDUInOrder", "Yes");
		loginReq2.setParameter("DataSequenceInOrder", "Yes");
		loginReq2.setParameter("DefaultTime2Retain", "60");
		loginReq2.setParameter("MaxOutstandingR2T", "16");
		loginReq2.setParameter("SessionType", "Normal");
		System.out.println(loginReq2);
		writeStream.write(loginReq2.toByte());
		writeStream.flush();
		
		//login Resposne 
		
		size = readStream.read(buf);
		DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		LoginResponse response2 = new LoginResponse(BHS,DataSegment);
		System.out.println(response2);
		
		
		SCSICommand cmmdReq= new SCSICommand();
		cmmdReq.setImmediate(false);
		cmmdReq.setFinal(true);
		cmmdReq.setRead(true);
		cmmdReq.setWrite(false);
		cmmdReq.setATTR(Attr.Untagged);
		cmmdReq.setInitiatorTaskTag(1);
		cmmdReq.setExpDataTransferLen(36);
		cmmdReq.setCmdSN(1);
		cmmdReq.setExpStatSN(2);
		Inquiry cdb = new Inquiry();
		cmmdReq.setCDB(cdb.toByte());
		System.out.println(cmmdReq);
		writeStream.write(cmmdReq.toByte());
		writeStream.flush();
		
		//scsi Resposne 
		
		size = readStream.read(buf);
		DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		System.out.println("scsi size : "+size+" opcode : "+BHS[0]+"  is DataIn  --> "+(BHS[0]==0x25)+" dataSegSize --> "+DataSegment.length);
		
		SCSIDataIn in = new SCSIDataIn(BHS,DataSegment);
		InquiryStandardData param = new InquiryStandardData(in.getDataSegment());
		System.out.println(in);
		System.out.println(param);
		
		SCSICommand cmmdReq2= new SCSICommand();
		cmmdReq2.setImmediate(false);
		cmmdReq2.setFinal(true);
		cmmdReq2.setRead(true);
		cmmdReq2.setWrite(false);
		cmmdReq2.setATTR(Attr.Untagged);
		cmmdReq2.setInitiatorTaskTag(1);
		cmmdReq2.setExpDataTransferLen(255);
		cmmdReq2.setCmdSN(2);
		cmmdReq2.setExpStatSN(3);
		Inquiry cdb2 = new Inquiry();
		cdb2.setEVPD(true);
		cdb2.setAllocateLength((byte)255);
		cmmdReq2.setCDB(cdb2.toByte());
		System.out.println(cmmdReq2);
		writeStream.write(cmmdReq2.toByte());
		writeStream.flush();
		
		size = readStream.read(buf);
		DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		System.out.println("scsi size : "+size+" opcode : "+BHS[0]+"  is DataIn  --> "+(BHS[0]==0x25)+" dataSegSize --> "+DataSegment.length);
		in = new SCSIDataIn(BHS,DataSegment);
		InquirySupportedVPD param2 = new InquirySupportedVPD(in.getDataSegment());
		System.out.println(in);
		System.out.println(param2);
		
		socket.close();
		
	}

}
