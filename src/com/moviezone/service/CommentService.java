package com.moviezone.service;

import java.util.List;







import com.moviezone.domain.CmmtReply;
import com.moviezone.domain.Comment;
import com.moviezone.domain.Page;
import com.moviezone.domain.Reply;

public interface CommentService {
	public Comment select(long commentid);
	public long selectCommentCount(long movieid);
	public Reply selectReply(long replyid);
	public List<Comment> select(Comment comment,int pageNo,int pageSize);
	public List<Comment> select(long movieid,int pageNo,int pageSize);
	public List<Reply> selectReply(long commentid,int pageNo,int pageSize);
	public Page<Comment> selectPage(Comment comment,int pageNo,int pageSize);
	public long insert(Comment comment);
	public long insertReply(Reply reply);
	public boolean update(Comment comment);
	public boolean delete(Comment comment);
	public boolean delete(long comment);
	public List<CmmtReply> selectCmmtReply(long movieid,int pageNo,int pageSize);
	public List<CmmtReply> selectCmmtReply(long movieid, int pageNo,int pageSize,int replySize);
	
}
