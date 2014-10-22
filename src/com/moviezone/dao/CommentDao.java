package com.moviezone.dao;

import java.util.List;

import com.moviezone.domain.Page;
import com.moviezone.domain.Comment;


public interface CommentDao {
	public Comment select(long commentid);
	public List<Comment> select(Comment comment,int pageNo,int pageSize);
	public Page<Comment> selectPage(Comment comment,int pageNo,int pageSize);
	public long insert(Comment comment);
	public boolean update(Comment comment);
	public boolean delete(Comment comment);    
	public boolean delete(long commentid);   
}
