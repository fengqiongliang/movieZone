package com.pxe.iscsi;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;

import com.moviezone.util.ByteUtil;
import com.pxe.iscsi.ENUM.CDBOpcode;
import com.pxe.iscsi.ENUM.Opcode;
import com.pxe.iscsi.cdb16.Inquiry;
import com.pxe.iscsi.cdb16.ModeSense6;
import com.pxe.iscsi.cdb16.Read10;
import com.pxe.iscsi.cdb16.ReadCapacity;
import com.pxe.iscsi.cdb16.ReportLUN;
import com.pxe.iscsi.cdb16.Write10;
import com.pxe.iscsi.pdu.BasicHeaderSegment;
import com.pxe.iscsi.pdu.LoginRequest;
import com.pxe.iscsi.pdu.LogoutRequest;
import com.pxe.iscsi.pdu.NopOut;
import com.pxe.iscsi.pdu.SCSICommand;
import com.pxe.iscsi.pdu.TMRequest;
import com.pxe.iscsi.pdu.TextRequest;

class DataHolder{
	byte[] readData = new byte[262144];
	byte[] lastData = new byte[262144];
	int readSize = 0;
	int lastSize   = 0;
	
}
public class ISCSIServer {
	private boolean isDebug = false;
	private static PhaseLogin loginPhase = new PhaseLogin();
	private static PhaseFullFeature featurePhase = new PhaseFullFeature();
	public static void main(String[] args) throws Exception {
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress("192.168.98.1",3260));
		while(true){
			final Socket socket = serverSocket.accept();
			//Thread.currentThread().sleep(2000);
			pool.execute(new Runnable(){
				@Override
				public void run() {
					try{execute(socket);}catch(Exception ex){ex.printStackTrace();}
				}
			});
		}
	}
	
	public static void execute(Socket socket) throws Exception {
		System.out.println("******************************************************************");
		System.out.println(socket.getRemoteSocketAddress());
		System.out.println("******************************************************************");
		socket.setTcpNoDelay(true);
		socket.setSoTimeout(200);
		InputStream is = socket.getInputStream();
		DataHolder holder = new DataHolder();
		
		while(true){
			if(socket.isClosed()){
				System.out.println("detact socket is close , return");
				return;
			}
			
			int size = -1 ; 
			try{
				size = is.read(holder.readData);
				if(size==-1)continue;
			}catch(Exception e){continue;};
			holder.readSize = size;
			receive(socket,holder);
			
			
			
		}
		
		
		
		
	}
	
	
	private static void receive(Socket socket,DataHolder holder) throws Exception{
		byte[] fullData = new byte[holder.lastSize+holder.readSize];
		if(holder.lastSize>0){
			System.arraycopy(holder.lastData, 0, fullData, 0, holder.lastSize);
			holder.lastSize=0;
			System.out.println(" fullData.length =  "+fullData.length);
		}
		System.arraycopy(holder.readData, 0, fullData, fullData.length-holder.readSize, holder.readSize);
		if(fullData.length<48){
			System.out.println("error data size = "+fullData.length+" --> "+ByteUtil.toHex(fullData));
			System.exit(0);
		}
		for(int index=0;index<fullData.length;){
			if(index+48>fullData.length){
				byte[] nextData = new byte[fullData.length-index];
				System.arraycopy(fullData, index, nextData, 0, nextData.length);
				System.out.println("not enough head size ,Save for next "+nextData.length+" --> "+ByteUtil.toHex(nextData));
				holder.lastData=nextData;
				holder.lastSize=nextData.length;
				break;
			}
			byte[] BHS = new byte[48];
			System.arraycopy(fullData, index, BHS, 0, BHS.length);
			BasicHeaderSegment basicHead = new BasicHeaderSegment(BHS);
			byte[] DataSegment = new byte[basicHead.getDataSegmentLength()];
			byte[] dataSegmentPadding = new byte[DataSegment.length % 4== 0 ? 0:(4 - DataSegment.length % 4)];
			if((fullData.length-index-48)!=basicHead.getDataSegmentLength()+dataSegmentPadding.length){
				System.out.println("here result : "+(fullData.length-index-48)+" dataLength : "+basicHead.getDataSegmentLength());
			}
			int residueCount = fullData.length-(index+BHS.length+DataSegment.length+dataSegmentPadding.length);
			if(residueCount<0){
				byte[] nextData = new byte[fullData.length-index];
				System.arraycopy(fullData, index, nextData, 0, nextData.length);
				System.out.println("not enough data size ,Save for next "+nextData.length+" --> "+ByteUtil.toHex(nextData));
				System.out.println(basicHead);
				holder.lastData=nextData;
				holder.lastSize=nextData.length;
				toPrint(BHS,DataSegment,basicHead);
				//DataSegment = new byte[residueCount-48];
				//System.arraycopy(fullData, index+48, DataSegment, 0, DataSegment.length);
				//process(socket,BHS,basicHead,DataSegment);
				break;
			}
			try{
			System.arraycopy(fullData, index+48, DataSegment, 0, DataSegment.length);
			}catch(Exception ex){
				ex.printStackTrace();
				System.out.println("fullData.length = "+fullData.length);
				System.out.println("DataSegment.length = "+DataSegment.length);
				System.out.println("index+48 = "+index+48);
			}
			index += BHS.length+DataSegment.length+dataSegmentPadding.length;
			
			process(socket,BHS,basicHead,DataSegment);
			if(index<fullData.length)System.out.println(" detected second package , size = "+(fullData.length-index));
			
		}
		
		
		
	}
	private static void process(Socket socket,byte[] BHS,BasicHeaderSegment basicHead,byte[] DataSegment ) throws Exception{
		if(basicHead.getOpcode()==Opcode.LOGIN_REQUEST){
			LoginRequest loginRequest = new LoginRequest(BHS,DataSegment);
			System.out.println(loginRequest);
			loginPhase.execute(socket, loginRequest);
		}else if(basicHead.getOpcode()==Opcode.TEXT_REQUEST){
			TextRequest textRequest = new TextRequest(BHS,DataSegment);
			System.out.println(textRequest);
			featurePhase.discovery(socket, textRequest);
		}else if(basicHead.getOpcode()==Opcode.LOGOUT_REQUEST){
			LogoutRequest textRequest = new LogoutRequest(BHS,DataSegment);
			System.out.println(textRequest);
			featurePhase.logout(socket, textRequest);
		}else if(basicHead.getOpcode()==Opcode.SCSI_COMMAND){
			SCSICommand scsiCommand = new SCSICommand(BHS,DataSegment);
			System.out.println(scsiCommand);
			featurePhase.scsiCommand(socket, scsiCommand);
		}else if(basicHead.getOpcode()==Opcode.SCSI_TM_REQUEST){
			TMRequest tmRequest = new TMRequest(BHS);
			System.out.println(tmRequest);
			featurePhase.tmManager(socket, tmRequest);
		}else if(basicHead.getOpcode()==Opcode.NOP_OUT){
			NopOut nopRequest = new NopOut(BHS,DataSegment);
			System.out.println(nopRequest);
			featurePhase.nopOut(socket, nopRequest);
		}else{
			System.out.println(basicHead);
		}
	}
	
	private static void toPrint(byte[] BHS,byte[] DataSegment,BasicHeaderSegment basicHead)throws Exception{
		
		if(basicHead.getOpcode()==Opcode.LOGIN_REQUEST){
			LoginRequest loginRequest = new LoginRequest(BHS,DataSegment);
			System.out.println(loginRequest);
		}else if(basicHead.getOpcode()==Opcode.TEXT_REQUEST){
			TextRequest textRequest = new TextRequest(BHS,DataSegment);
			System.out.println(textRequest);
		}else if(basicHead.getOpcode()==Opcode.LOGOUT_REQUEST){
			LogoutRequest textRequest = new LogoutRequest(BHS,DataSegment);
			System.out.println(textRequest);
		}else if(basicHead.getOpcode()==Opcode.SCSI_COMMAND){
			SCSICommand scsiCommand = new SCSICommand(BHS,DataSegment);
			System.out.println(scsiCommand);
			byte[] CDB = scsiCommand.getCDB();
			if(CDB[0] == CDBOpcode.ReportLUN.value())System.out.println(new ReportLUN(scsiCommand.getCDB()));
			if(CDB[0] == CDBOpcode.Inquiry.value())System.out.println(new Inquiry(scsiCommand.getCDB()));
			if(CDB[0] == CDBOpcode.ReadCapacity10.value())System.out.println(new ReadCapacity(scsiCommand.getCDB()));
			if(CDB[0] == CDBOpcode.Read10.value())System.out.println(new Read10(scsiCommand.getCDB()));
			if(CDB[0] == CDBOpcode.ModeSense6.value())System.out.println(new ModeSense6(scsiCommand.getCDB()));
			if(CDB[0] == CDBOpcode.TestUnitReady.value())System.out.println("Test Unit Ready");
			if(CDB[0] == CDBOpcode.Write10.value())System.out.println(new Write10(scsiCommand.getCDB()));
			
		}else if(basicHead.getOpcode()==Opcode.SCSI_TM_REQUEST){
			TMRequest tmRequest = new TMRequest(BHS);
			System.out.println(tmRequest);
		}else if(basicHead.getOpcode()==Opcode.NOP_OUT){
			NopOut nopRequest = new NopOut(BHS,DataSegment);
			System.out.println(nopRequest);
		}
	}
	
	
	
	
}
