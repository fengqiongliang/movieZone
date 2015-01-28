package com.tftp.server;

import com.tftp.TFTPpacket;
import com.tftp.TFTPread;
import com.tftp.TFTPwrite;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TftpServer implements Runnable {

    private DatagramSocket socket;

    private int port = 69;

    private String tftpRoot;

    public TftpServer(String tftpRoot) {
        this.tftpRoot = tftpRoot;
    }

    public void start() {
        Thread serviceExecutor = new Thread(this);
        serviceExecutor.start();
    }

    public void stop() {
        this.socket.close();
    }

    @Override
    public void run() {
        initilize();
        service();
    }

    public void initilize() {
        try {
            this.socket = new DatagramSocket(this.port,InetAddress.getByName("0.0.0.0"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void service() {
        try {
            while (true) {
                TFTPpacket in = TFTPpacket.receive(this.socket);
                if (in != null) {
                    if ((in instanceof TFTPread)) {
                        System.out.println("Read request is received");
                        TFTPServiceHandler handler = new TFTPServiceHandler((TFTPread) in,this.tftpRoot);
                        handler.start();
                    }
                    if ((in instanceof TFTPwrite)) {
                        System.out.println("Write request is received");
                        TFTPServiceHandler handler = new TFTPServiceHandler((TFTPwrite) in,this.tftpRoot);
                        handler.start();
                        continue;
                    }
                }
                else 
                    System.out.println("null request");
            }
        } catch (Exception localException) {
        }
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:tftpServer tftpRoot path");
            System.exit(1);
        }
        new TftpServer(args[0]).start();
        System.out.println("TFTp Server listening in the port 69 \n tftp root path:"+ args[0]);
    }
}