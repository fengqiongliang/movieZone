package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.CommentDao;
import com.moviezone.domain.Comment;
import com.moviezone.domain.Page;
import com.moviezone.domain.Reply;

public class CommentDaoImpl implements CommentDao{
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
	
	private SqlSession session;

	@Override
	public long insert(Comment comment) {
		return session.insert("insertComment", comment)>0?comment.getCommentid():0;
	}
	@Override
	public long insertReply(Reply reply) {
		return session.insert("insertReply", reply)>0?reply.getReplyid():0;
	}

	@Override
	public Comment select(long commentid) {
		if(commentid<1)return null;
		Comment comment = new Comment();
		comment.setCommentid(commentid);
		List<Comment> comments = select(comment,1,1);
		return comments.size()!=1?null:comments.get(0);
	}
	
	@Override
	public Reply selectReply(long replyid) {
		if(replyid<1)return null;
		Reply reply = new Reply();
		reply.setReplyid(replyid);
		List<Reply> replys = selectReply(reply,1,1);
		return replys.size()!=1?null:replys.get(0);
	}
	
	@Override
	public List<Comment> select(Comment comment,int pageNo,int pageSize) {
		if(comment == null)return new ArrayList<Comment>();
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("commentid", comment.getCommentid());
		param.put("userid", comment.getUserid());
		param.put("movieid", comment.getMovieid());
		param.put("content", comment.getContent());
		param.put("createarea", comment.getCreatearea());
		param.put("createtime", comment.getCreatetime());
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectComment", param);
	}
	
	@Override
	public List<Reply> selectReply(Reply reply, int pageNo, int pageSize) {
		if(reply == null)return new ArrayList<Reply>();
		Map<String,Object> param = new  HashMap<String,Object>();
		param.put("replyid", reply.getReplyid());
		param.put("commentid", reply.getCommentid());
		param.put("userid", reply.getUserid());
		param.put("content", reply.getContent());
		param.put("createarea", reply.getCreatearea());
		param.put("createtime", reply.getCreatetime());
		param.put("start", (pageNo-1)*pageSize);
		param.put("size", pageSize);
		return session.selectList("selectReply", param);
	}	
	
	@Override
	public Page<Comment> selectPage(Comment comment, int pageNo, int pageSize) {
		Page<Comment> page = new Page<Comment>();
		if(comment == null)return page;
		Map<String,Object>  result = session.selectOne("selectCmmtCount",comment);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(select(comment,pageNo,pageSize));
		return page;
	}
	
	@Override
	public Page<Reply> selectReplyPage(Reply reply, int pageNo, int pageSize) {
		Page<Reply> page = new Page<Reply>();
		if(reply == null)return page;
		Map<String,Object>  result = session.selectOne("selectReplyCount",reply);
		page.setTotal((Long)result.get("total"));
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setData(selectReply(reply,pageNo,pageSize));
		return page;
	}
	
	@Override
	public boolean update(Comment comment) {
		if(comment.getCommentid()<1)return false;
		return session.update("updateComment", comment)>0;
	}
	
	@Override
	public boolean updateReply(Reply reply) {
		if(reply.getReplyid()<1)return false;
		return session.update("updateReply", reply)>0;
	}
	
	@Override
	public boolean delete(Comment comment) {
		if(comment.getCommentid() <1)return false;
		return session.delete("deleteComment", comment)>0;
	}
	
	@Override
	public boolean delete(long commentid) {
		if(commentid<1)return false;
		Comment comment = new Comment();
		comment.setMovieid(commentid);
		return delete(comment);
	}
	
	@Override
	public boolean deleteReply(Reply reply) {
		if(reply.getCommentid() <1)return false;
		return session.delete("deleteReply", reply)>0;
	}

	@Override
	public boolean deleteReply(long replyid) {
		if(replyid<1)return false;
		Reply reply = new Reply();
		reply.setReplyid(replyid);
		return deleteReply(reply);
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	

}
