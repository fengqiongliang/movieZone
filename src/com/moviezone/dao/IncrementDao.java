package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Increment;


public interface IncrementDao {
	public List<Increment> select();
	public boolean update(Increment incre);
}
