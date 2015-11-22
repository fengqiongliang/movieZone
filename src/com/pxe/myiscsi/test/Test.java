package com.pxe.myiscsi.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

import com.pxe.myiscsi.ENUM.Stage;
import com.pxe.myiscsi.cdb16.Inquiry;
import com.pxe.myiscsi.cdb16.Inquiry.PageCode;
import com.pxe.myiscsi.cdb16.InquiryStandardData;
import com.pxe.myiscsi.cdb16.InquirySupportedVPD;
import com.pxe.myiscsi.cdb16.LUN;
import com.pxe.myiscsi.cdb16.ModeSense6;
import com.pxe.myiscsi.cdb16.ModeSense6.PC;
import com.pxe.myiscsi.cdb16.Read10;
import com.pxe.myiscsi.cdb16.ReadCapacity;
import com.pxe.myiscsi.cdb16.ReadCapacityParam;
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
		SocketAddress LocalIpPort      = new InetSocketAddress("192.168.86.1",3345);
		SocketAddress RemoteIpPort1 = new InetSocketAddress("192.168.86.125",3260);
		SocketAddress RemoteIpPort2 = new InetSocketAddress("192.168.86.126",3260);
		
		Socket socket = new Socket();
		socket.setSoLinger(true, 0); //设置强制关闭
		socket.setReuseAddress(true);
		socket.bind(LocalIpPort);
		socket.connect(RemoteIpPort1);
		
		
		
		
		
		InputStream   readStream = socket.getInputStream();
		OutputStream writeStream = socket.getOutputStream();
		byte[] buf = new byte[1024];
		byte[] ISID = new byte[]{0x40,0x00,0x01,0x37,0x00,0x00};
		LoginRequest loginReq1 = new LoginRequest();
		loginReq1.setTransit(true);
		loginReq1.setCSG(Stage.SecurityNegotiation);
		loginReq1.setNSG(Stage.LoginOperationalNegotiation);
		loginReq1.setISID(ISID);
		loginReq1.setInitiatorTaskTag(1);
		loginReq1.setCID((short)1);
		loginReq1.setCmdSN(1);
		loginReq1.setExpStatSN(1);
		loginReq1.setParameter("InitiatorName", "iqn.1991-05.com.microsoft:ahone-outer-pc");
		loginReq1.setParameter("SessionType", "Normal");
		loginReq1.setParameter("TargetName", "iqn.2007-08.name.dns.target.my:iscsiboot125");
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
		loginReq2.setInitiatorTaskTag(1);
		loginReq2.setCID((short)1);
		loginReq2.setCmdSN(1);
		loginReq2.setExpStatSN(2);
		loginReq2.setParameter("HeaderDigest", "None");
		loginReq2.setParameter("DataDigest", "None");
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
		cmmdReq.setExpDataTransferLen(16);
		cmmdReq.setCmdSN(2);
		cmmdReq.setExpStatSN(3);
		ReportLUN cdb = new ReportLUN();
		cdb.setAllocateLength(16);
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
		ReportLUNParam param = new ReportLUNParam(DataSegment);
		System.out.println(param);
		
		
		SCSIDataIn in = new SCSIDataIn(BHS,DataSegment);
		System.out.println(in);
		
		SCSICommand cmmdReq2= new SCSICommand();
		cmmdReq2.setImmediate(false);
		cmmdReq2.setFinal(true);
		cmmdReq2.setRead(true);
		cmmdReq2.setWrite(false);
		cmmdReq2.setATTR(Attr.Untagged);
		cmmdReq2.setInitiatorTaskTag(2);
		cmmdReq2.setExpDataTransferLen(36);
		cmmdReq2.setCmdSN(3);
		cmmdReq2.setExpStatSN(4);
		
		Inquiry cdb2 = new Inquiry();
		cdb2.setPageCode(PageCode.SupportedVPD);
		cdb2.setAllocateLength((short)36);
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
		InquiryStandardData param2 = new InquiryStandardData(in.getDataSegment());
		System.out.println(in);
		System.out.println(param2);
		
		
		SCSICommand cmmdReq3= new SCSICommand();
		cmmdReq3.setImmediate(false);
		cmmdReq3.setFinal(true);
		cmmdReq3.setRead(true);
		cmmdReq3.setWrite(false);
		cmmdReq3.setATTR(Attr.Untagged);
		cmmdReq3.setInitiatorTaskTag(3);
		cmmdReq3.setExpDataTransferLen(255);
		cmmdReq3.setCmdSN(4);
		cmmdReq3.setExpStatSN(5);
		
		Inquiry cdb3 = new Inquiry();
		cdb3.setPageCode(PageCode.SupportedVPD);
		cdb3.setAllocateLength((short)255);
		cmmdReq3.setCDB(cdb3.toByte());
		System.out.println(cmmdReq3);
		writeStream.write(cmmdReq3.toByte());
		writeStream.flush();
		
		size = readStream.read(buf);
		DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		System.out.println("scsi size : "+size+" opcode : "+BHS[0]+"  is DataIn  --> "+(BHS[0]==0x25)+" dataSegSize --> "+DataSegment.length);
		in = new SCSIDataIn(BHS,DataSegment);
		InquirySupportedVPD param3 = new InquirySupportedVPD(in.getDataSegment());
		System.out.println(in);
		System.out.println(param3);
		
		System.out.println("===========================");
		size = readStream.read(buf);
		System.out.println("-------------------------------------------------");
		DataSegment = new byte[size-48];
		System.arraycopy(buf, 0, BHS, 0, BHS.length);
		System.arraycopy(buf, 48, DataSegment, 0, DataSegment.length);
		System.out.println("scsi size : "+size+" opcode : "+BHS[0]+"  is DataIn  --> "+(BHS[0]==0x25)+" dataSegSize --> "+DataSegment.length);
		
		
		socket.close();
		
		Socket socket2 = new Socket();
		socket2.setReuseAddress(true);
		socket2.setSoLinger(true, 0); //设置强制关闭时
		socket2.bind(LocalIpPort);
		socket2.connect(RemoteIpPort2);
		
		InputStream   readStream2 = socket2.getInputStream();
		OutputStream writeStream2 = socket2.getOutputStream();
		writeStream2.write(150);
		System.out.println("ok");
		
		socket2.close();
		
	}

}
