package com.moviezone.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Page<T> implements Serializable{
	private static final long serialVersionUID = 9085705213219380290L;
	private long total = 0;
	private int pageNo = 1;
	private int pageSize = 10;
	private List<T> data = new ArrayList<T>();
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getPageTotal() {
		int pages = (int)(total+pageSize-1)/pageSize;
		return pages==0?1:pages;
	}
	public boolean isFirstPage() {
		return pageNo == 1;
	}
	public boolean isLastPage() {
		return pageNo == getPageTotal();
	}
}
