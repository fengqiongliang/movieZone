package com.pxe.iscsi.target.scsi.lun;


import java.nio.ByteBuffer;

import com.pxe.iscsi.target.scsi.ISerializable;
import com.pxe.iscsi.target.util.ReadWrite;


/**
 * A 64-bit identifier for a logical unit.
 * 
 * @author Andreas Ergenzinger
 */
public class LogicalUnitNumber implements ISerializable {

    /**
     * Length of the field in bytes.
     */
    public static final int SIZE = 8;

    /**
     * Byte representation of the object.
     */
    private final byte[] bytes;

    public LogicalUnitNumber (final long logicalUnitNumber) {
        bytes = ReadWrite.longToBytes(logicalUnitNumber);
    }

    public void serialize (ByteBuffer byteBuffer, int index) {
        byteBuffer.position(index);
        for (int i = 0; i < bytes.length; ++i)
            byteBuffer.put(bytes[i]);
    }

    public int size () {
        return SIZE;
    }

    @Override
    public String toString () {
        return java.util.Arrays.toString(bytes);
    }
}
