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

12.21.  SessionType

   Use: LO, Declarative, Any-Stage
   Senders: Initiator
   Scope: SW

   SessionType= <Discovery|Normal>

   Default is Normal.

   The initiator indicates the type of session it wants to create.  The
   target can either accept it or reject it.

   A discovery session indicates to the Target that the only purpose of
   this Session is discovery.  The only requests a target accepts in
   this type of session are a text request with a SendTargets key and a
   logout request with reason "close the session".

   The discovery session implies MaxConnections = 1 and overrides both
   the default and an explicit setting.

   
</pre>
 * 
 */
public enum PDUSessionTypeEnum {


    /**
     * 0 - Normal
     */
	Normal((byte) 0x00),

	 /**
     * 1 - Discovery
     */
	Discovery((byte) 0x01);
	
    private final byte value;

    private static Map<Byte , PDUSessionTypeEnum> mapping;

    static {
        PDUSessionTypeEnum.mapping = new HashMap<Byte , PDUSessionTypeEnum>();
        for (PDUSessionTypeEnum s : values()) {
            PDUSessionTypeEnum.mapping.put(s.value, s);
        }
    }

    private PDUSessionTypeEnum (final byte newValue) {

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
    public static final PDUSessionTypeEnum valueOf (final byte value) {

        return PDUSessionTypeEnum.mapping.get(value);
    }

    // --------------------------------------------------------------------------
    // --------------------------------------------------------------------------

}
