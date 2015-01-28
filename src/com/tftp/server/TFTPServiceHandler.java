package com.tftp.server;

import com.tftp.TFTPack;
import com.tftp.TFTPdata;
import com.tftp.TFTPerror;
import com.tftp.TFTPpacket;
import com.tftp.TFTPread;
import com.tftp.TFTPwrite;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TFTPServiceHandler implements Runnable {

    protected DatagramSocket socket;

    protected InetAddress ipAddress;

    protected int port;

    protected FileInputStream inputStream;

    protected FileOutputStream outputStream;

    protected String rootPath = "./";

    private TFTPpacket request;

    public TFTPServiceHandler(TFTPread request, String tftpRoot) {
        try {
            this.rootPath = tftpRoot;
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(300000);
            this.ipAddress = request.getAddress();
            this.port = request.getPort();
            this.request = request;
        } catch (Exception e) {
            e.printStackTrace();
            TFTPerror ePak = new TFTPerror(1, e.getMessage());
            try {
                ePak.send(this.ipAddress, this.port, this.socket);
            } catch (Exception localException1) {
            }
        }
    }

    public TFTPServiceHandler(TFTPwrite request, String tftpRoot) {
        try {
            this.rootPath = tftpRoot;
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(300000);
            this.ipAddress = request.getAddress();
            this.port = request.getPort();
            this.request = request;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("In the constructor:" + e.getMessage());
            TFTPerror ePak = new TFTPerror(1, e.getMessage());
            try {
                ePak.send(this.ipAddress, this.port, this.socket);
            } catch (Exception localException1) {
            }
        }
    }

    @Override
    public void run() {
        initilize();
        service();
    }

    public void initilize() {
        String fileName = null;
        try {
            fileName = this.request.fileName();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        File path = new File(this.rootPath);
        if ((this.request instanceof TFTPread)) {
            try {
                System.out.println("reading file path:"+path.getCanonicalPath());
                if (!path.exists()) {
                    new File("tftpRoot").mkdir();
                    path.mkdir();
                    errorMessage("Folder not found");
                    return;
                }
                File srcFile = new File(this.rootPath + "/" + fileName);
                if ((srcFile.exists()) && (srcFile.isFile())
                        && (srcFile.canRead())) {
                    this.inputStream = new FileInputStream(srcFile);
                    return;
                }
                errorMessage("File not found");
            } catch (Exception e) {
                errorMessage(e);
                e.printStackTrace();
            }
        } else if ((this.request instanceof TFTPwrite))
            try {
                if (!path.exists()) {
                    new File("tftpRoot").mkdir();
                    path.mkdir();
                }
                File srcFile = new File(this.rootPath + "/" + fileName);
                System.out.println("Write file:"+srcFile.getAbsolutePath());
                this.outputStream = new FileOutputStream(srcFile);
            } catch (Exception e) {
                errorMessage(e);
                e.printStackTrace();
            }
    }

    public void service() {
        int bytesRead = TFTPpacket.maxTftpPakLen;
        if ((this.request instanceof TFTPread)) {
            try {
                TFTPdata outPak = new TFTPdata(1, this.inputStream);
                bytesRead = outPak.getLength();
                outPak.send(this.ipAddress, this.port, this.socket);
                for (int blkNum = 2; bytesRead == TFTPpacket.maxTftpPakLen; blkNum++) {
                    TFTPpacket ack = TFTPpacket.receive(this.socket);
                    if ((ack instanceof TFTPerror))
                        break;
                    TFTPack tack = (TFTPack) ack;
                    int ackNumber = tack.blockNumber();
                    if (ackNumber == blkNum - 1) {
                        outPak = new TFTPdata(blkNum, this.inputStream);
                        bytesRead = outPak.getLength();
                        outPak.send(this.ipAddress, this.port, this.socket);
                    } else {
                        outPak.send(this.ipAddress, ack.getPort(), this.socket);
                        blkNum--;
                    }
                }
            } catch (NullPointerException e) {
                errorMessage("File not Found");
                try {
                    this.inputStream.close();
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                errorMessage(e);
                try {
                    this.inputStream.close();
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    this.inputStream.close();
                    this.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                this.inputStream.close();
                this.socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ((this.request instanceof TFTPwrite)) {
            System.out.println("write request service method");
            TFTPdata p = null;
            TFTPpacket inPak = null;
            TFTPack ack = null;
            int count = 0;
            int loop = 3;
            try {
                ack = new TFTPack(0);
                ack.send(this.ipAddress, this.port, this.socket);
                int pakCount = 1;
                for (int bytesOut = 512; bytesOut == 512; pakCount++) {
                    if ((inPak instanceof TFTPerror)) {
                        return;
                    }
                    if ((inPak instanceof TFTPdata)) {
                        p = (TFTPdata) inPak;
                        int blockNum = p.blockNumber();
                        if (blockNum == pakCount) {
                            count = 0;
                            bytesOut = p.write(this.outputStream);
                            ack = new TFTPack(blockNum);
                            ack.send(p.getAddress(), p.getPort(), this.socket);
                        } else {
                            if (count == 3) {
                                return;
                            }
                            count++;
                            pakCount--;
                            ack = new TFTPack(pakCount);
                            ack.send(p.getAddress(), p.getPort(), this.socket);
                        }
                    }

                    loop = 3;
                    while (loop > 0) {
                        loop--;
                        try {
                            inPak = TFTPpacket.receive(this.socket);
                            loop = -1;
                        } catch (Exception e) {
                            ack.send(p.getAddress(), p.getPort(), this.socket);
                        }
                    }
                }
            } catch (Exception e) {
                errorMessage(e);
            }
        }
    }

    public void start() {
        Thread serviceExecutor = new Thread(this);
        serviceExecutor.start();
    }

    public void errorMessage(String mesage) {
        TFTPerror ePak = new TFTPerror(1, mesage);
        try {
            ePak.send(this.ipAddress, this.port, this.socket);
        } catch (Exception localException) {
        }
    }

    public void errorMessage(Exception e) {
        e.printStackTrace();
        TFTPerror ePak = new TFTPerror(1, e.getMessage());
        try {
            ePak.send(this.ipAddress, this.port, this.socket);
        } catch (Exception localException) {
        }
    }

    public void stop() {
        this.socket.close();
    }
}