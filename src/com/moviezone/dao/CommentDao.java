package com.moviezone.dao;

import java.util.List;
import java.util.Map;

import com.moviezone.domain.Page;
import com.moviezone.domain.Comment;
import com.moviezone.domain.Reply;


public interface CommentDao {
	public long selectCount(Comment comment);
	public long selectCommentCount(long movieid);
	public Comment select(long commentid);
	public List<Comment> select(Comment comment,int pageNo,int pageSize);
	public List<Comment> selectRecmmdCmmt(String type,int pageNo,int pageSize);
	public Page<Comment> selectRecmmdCmmtPage(String type,int pageNo,int pageSize);
	public Page<Comment> selectPage(Comment comment,int pageNo,int pageSize);
	public Page<Comment> selectPage(Long commentid, Long movieid, Long userid,String type,int pageNo,int pageSize);
	public long insert(Comment comment);
	public boolean update(Comment comment);
	public boolean delete(Comment comment);    
	public boolean delete(long commentid);   
	public Reply selectReply(long replyid);
	public List<Reply> selectReply(Reply reply,int pageNo,int pageSize);
	public Page<Reply> selectReplyPage(Reply reply,int pageNo,int pageSize);
	public long insertReply(Reply reply);
	public boolean updateReply(Reply reply);
	public boolean deleteReply(Reply reply);    
	public boolean deleteReply(long replyid);
	public void sceneCmmt(long commentid);
	
	
}
