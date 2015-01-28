package com.tftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class TFTPdata extends TFTPpacket {

    protected TFTPdata() {
    }

    public TFTPdata(int blockNumber, InputStream in) throws Exception {
        System.out.println("Data packet created:"+blockNumber);
        this.message = new byte[maxTftpPakLen];
        if (in == null) {
            put(0, (short) 5); // TODO
            put(2, (short) blockNumber);
            this.length = 4;
        }
        put(0, (short) 3); // TODO
        put(2, (short) blockNumber);

        int len = in.read(this.message, 4, maxTftpData) + 4;

        if (len == 3)
            this.length = 4;
    }

    public int blockNumber() {
        return get(2);
    }

    public void data(byte[] buffer) {
        byte[] buffer1 = buffer; // TODO
        buffer1 = new byte[this.length - 4];

        for (int i = 0; i < this.length - 4; i++)
            buffer1[i] = this.message[(i + 4)];
    }

    public int write(OutputStream out) throws IOException {
        out.write(this.message, 4, this.length - 4);

        return this.length - 4;
    }
}
