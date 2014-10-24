package com.moviezone.dao.support;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.CommentDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.Comment;

public class CommentDaoImpl implements CommentDao{
	private static final Logger logger = LoggerFactory.getLogger(CommentDaoImpl.class);
	
	private SqlSession session;

	@Override
	public long insert(Comment comment) {
		return session.insert("insertComment", comment)>0?comment.getCommentid():0;
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
	public boolean update(Comment comment) {
		if(comment.getCommentid()<1)return false;
		return session.update("updateComment", comment)>0;
	}

	@Override
	public boolean delete(Comment comment) {
		if(comment==null || comment.getCommentid() <1)return false;
		return session.delete("deleteComment", comment)>0;
	}
	
	@Override
	public boolean delete(long commentid) {
		if(commentid<1)return false;
		Comment comment = new Comment();
		comment.setMovieid(commentid);
		return delete(comment);
	}
	
	public void setSession(SqlSession session) {
		this.session = session;
	}

	

	

	

}
