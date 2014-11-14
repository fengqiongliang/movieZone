package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.IP;


public interface StatDao {
	public List<IP> select();
	public boolean update(IP ip);
}
