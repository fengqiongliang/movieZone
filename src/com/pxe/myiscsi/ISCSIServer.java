package com.pxe.myiscsi;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.lang.StringUtils;

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
			
			PDUBasicHeaderSegment basicHead = new PDUBasicHeaderSegment(BHS);
			System.out.println(basicHead);
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
			if(basicHead.getOpcode()==PDUOpcodeEnum.LOGIN_REQUEST){
				PDULoginRequest loginRequest = new PDULoginRequest(BHS,DataSegment);
				System.out.println(loginRequest);
				loginPhase.execute(socket, loginRequest);
			}
			if(basicHead.getOpcode()==PDUOpcodeEnum.TEXT_REQUEST){
				PDUTextRequest textRequest = new PDUTextRequest(BHS,DataSegment);
				System.out.println(textRequest);
				featurePhase.discovery(socket, textRequest);
			}
			if(basicHead.getOpcode()==PDUOpcodeEnum.LOGOUT_REQUEST){
				PDULogoutRequest textRequest = new PDULogoutRequest(BHS,DataSegment);
				System.out.println(textRequest);
				featurePhase.logout(socket, textRequest);
			}
			
		}
		
	}
	

}
