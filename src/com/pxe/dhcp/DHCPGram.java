package com.pxe.dhcp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DHCPGram {
	private DHCPSocket socket;
	private DatagramPacket packet;
	public DHCPGram(DHCPSocket socket,DatagramPacket packet){
		this.socket = socket;
		this.packet = packet;
	}
	public DHCPSocket getDHCPSocket() {
		return socket;
	}
	public DatagramSocket getSocket() {
		return socket.getSocket();
	}
	public DatagramPacket getPacket() {
		return packet;
	}
	
}
