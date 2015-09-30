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
package com.pxe.myiscsi;


import java.util.HashMap;
import java.util.Map;


/**
<pre>

Task Attributes (ATTR) have one of the following integer values 
(see [SAM2] for details):

    0 - Untagged
    1 - Simple
    2 - Ordered
    3 - Head of Queue
    4 - ACA
    5-7 - Reserved


   
</pre>
 * 
 */
public enum PDUAttrEnum {


    /**
     * 0 - Untagged
     */
	Untagged((byte) 0x00),

	 /**
     * 1 - Simple
     */
	Simple((byte) 0x01),
	
	 /**
     * 2 - Ordered
     */
	Ordered((byte) 0x02),
	
	/**
     * 3 - Head of Queue
     */
	HeadOfQueue((byte) 0x03),
	
	/**
     * 4 - ACA
     */
	ACA((byte) 0x04);
	
    private final byte value;

    private static Map<Byte , PDUAttrEnum> mapping;

    static {
        PDUAttrEnum.mapping = new HashMap<Byte , PDUAttrEnum>();
        for (PDUAttrEnum s : values()) {
            PDUAttrEnum.mapping.put(s.value, s);
        }
    }

    private PDUAttrEnum (final byte newValue) {

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
    public static final PDUAttrEnum valueOf (final byte value) {

        return PDUAttrEnum.mapping.get(value);
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

}
