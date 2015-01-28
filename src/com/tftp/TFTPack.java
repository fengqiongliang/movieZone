package com.tftp;

public final class TFTPack extends TFTPpacket {

    protected TFTPack() {
    }

    public TFTPack(int blockNumber) {
        this.length = 4;
        this.message = new byte[this.length];

        put(0, (short) 4);
        put(2, (short) blockNumber);
    }

    public int blockNumber() {
        return get(2);
    }
}
