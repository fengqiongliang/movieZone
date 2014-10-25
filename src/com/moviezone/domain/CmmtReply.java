package com.moviezone.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviezone.service.MovieService;

public class CmmtReply implements Serializable{
	private static final long serialVersionUID = -2322806030681038809L;
	private Comment cmmt;
	private List<Reply> replys = new ArrayList<Reply>();
	public Comment getCmmt() {
		return cmmt;
	}
	public void setCmmt(Comment cmmt) {
		this.cmmt = cmmt;
	}
	public List<Reply> getReplys() {
		return replys;
	}
	public void setReplys(List<Reply> replys) {
		this.replys = replys;
	}
}
