/**
 * Copyright (c) 2012, University of Konstanz, Distributed Systems Group All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. * Neither the name of the University of Konstanz nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.pxe.myiscsi.ENUM;


import java.util.HashMap;
import java.util.Map;


/**
<pre>

   
</pre>
 * 
 */
public enum CDBOpcode {
	
	
    /**
     * REPORT LUNS command
     */
    ReportLUN((byte) 0xa0),

    /**
     * REPORT LUNS command
     */
    Inquiry((byte) 0x12),
    
    /**
     * TEST_UNIT_READY command
     */
    TestUnitReady((byte) 0x00),
    
    /**
     * REQUEST_SENSE command
     */
    RequestSense((byte) 0x03),
    
    /**
     * FORMAT_UNIT command
     */
    FormatUnit((byte) 0x04),
    
    /**
     * SEND_DIAGNOSTIC command
     */
    SendDiagnostic((byte) 0x1d),
    
    /**
     * READ_CAPACITY_10 command
     */
    ReadCapacity10((byte) 0x25),
    
    /**
     * READ_CAPACITY_16 command
     */
    ReadCapacity16((byte) 0x9e),
    
    /**
     * READ_6 command
     */
    Read6((byte) 0x08),
    
    /**
     * READ_10 command
     */
    Read10((byte) 0x28),
    
    /**
     * WRITE_6 command
     */
    Write6((byte) 0x0a),
    
    /**
     * WRITE_10 command
     */
    Write10((byte) 0x2a);
    
    private final byte value;

    private static Map<Byte , CDBOpcode> mapping;

    static {
        CDBOpcode.mapping = new HashMap<Byte , CDBOpcode>();
        for (CDBOpcode s : values()) {
            CDBOpcode.mapping.put(s.value, s);
        }
    }

    private CDBOpcode (final byte newValue) {

        value = newValue;
    }

    /**
     * Returns the value of this enumeration.
     * 
     * @return The value of this enumeration.
     */
    public final byte value () {

        return value;
    }

    /**
     * Returns the constant defined for the given <code>value</code>.
     * 
     * @param value The value to search for.
     * @return The constant defined for the given <code>value</code>. Or <code>null</code>, if this value is not defined
     *         by this enumeration.
     */
    public static final CDBOpcode valueOf (final byte value) {

        return CDBOpcode.mapping.get(value);
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

}
