package com.pxe.iscsi.cdb16;

import com.moviezone.util.ByteUtil;

/**
 * Loginc Unit Number(64-bit)
 * A 64-bit identifier for a logical unit.
 * 
 */
public class LUN {
	private long id;
	public LUN(long id){
		this.id = id;
	}
	public long getId(){
		return this.id;
	}
	public byte[] toByte(){
		return ByteUtil.longToByteArray(this.id);
	}
}
