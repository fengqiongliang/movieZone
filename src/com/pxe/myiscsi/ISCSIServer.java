package com.pxe.myiscsi;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

import com.pxe.myiscsi.ENUM.Opcode;
import com.pxe.myiscsi.pdu.LoginRequest;
import com.pxe.myiscsi.pdu.LogoutRequest;
import com.pxe.myiscsi.pdu.BasicHeaderSegment;
import com.pxe.myiscsi.pdu.SCSICommand;
import com.pxe.myiscsi.pdu.TextRequest;

public class ISCSIServer {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket();
		serverSocket.bind(new InetSocketAddress("192.168.43.50",3261));
		while(true){
			Socket socket = serverSocket.accept();
			execute(socket);
		}
	}
	
	public static void execute(Socket socket) throws Exception {
		socket.setSoTimeout(0);
		InputStream is = socket.getInputStream();
		byte[] BHS = new byte[48];
		while(true){
			if(socket.isClosed()){
				System.out.println("detact socket is close , return");
				return;
			}
			int size = is.read(BHS);
			if(size != BHS.length){
				//System.out.println("read size : " +size);
				//System.out.println("accept error iscsi packet,continue accept next connection");
				continue;
			}
			
			BasicHeaderSegment basicHead = new BasicHeaderSegment(BHS);
			byte[] AHS = new byte[basicHead.getTotalAHSLength()];
			byte[] HeaderDigest = new byte[0];
			byte[] DataSegment = new byte[basicHead.getDataSegmentLength()];
			byte[] DataDigest = new byte[0];
			
			/*
			 * negotiated in login phase
			boolean hasHeaderDigest = false; 
			if(hasHeaderDigest)HeaderDigest = new byte[HeaderDigestLength];
			boolean hasDataDigest = false;
			if(hasDataDigest)DataDigest = new byte[DataDigestLength];
			*/
			is.read(AHS);
			is.read(HeaderDigest);
			is.read(DataSegment);
			byte[] dataSegmentPadding = new byte[DataSegment.length % 4== 0 ? 0:(4 - DataSegment.length % 4)];
			is.read(dataSegmentPadding);
			is.read(DataDigest);
			PhaseLogin loginPhase = new PhaseLogin();
			PhaseFullFeature featurePhase = new PhaseFullFeature();
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
			}else{
				System.out.println(basicHead);
			}
			
			
		}
		
	}
	

}
