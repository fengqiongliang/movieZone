package com.moviezone.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Increment implements Serializable{
	private static final long serialVersionUID = -2463361750584994143L;
	private String field;                   
	private long start;   
	private long end;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public synchronized long getStart() {
		return start;
	}
	public synchronized long getNextStart(){
		start = start +1;
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public long getEnd() {
		return end;
	}
	public void setEnd(long end) {
		this.end = end;
	}            
}
