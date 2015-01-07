package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;

public class UserNick implements Serializable{
	private static final long serialVersionUID = -1359459397842911694L;
	private String newnick; 
	private String oldnick;
	public String getNewnick() {
		return newnick;
	}
	public void setNewnick(String newnick) {
		this.newnick = newnick;
	}
	public String getOldnick() {
		return oldnick;
	}
	public void setOldnick(String oldnick) {
		this.oldnick = oldnick;
	}
	
}
