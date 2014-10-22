package com.moviezone.service;

import java.util.List;





import com.moviezone.domain.Comment;
import com.moviezone.domain.Page;

public interface CommentService {
	public Comment select(long commentid);
	public List<Comment> select(Comment comment,int pageNo,int pageSize);
	public List<Comment> select(long movieid,int pageNo,int pageSize);
	public Page<Comment> selectPage(Comment comment,int pageNo,int pageSize);
	public long insert(Comment comment);
	public boolean update(Comment comment);
	public boolean delete(Comment comment);
	public boolean delete(long comment);
}
