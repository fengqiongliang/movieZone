package com.moviezone.service.support;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moviezone.dao.CommentDao;
import com.moviezone.domain.Page;
import com.moviezone.domain.Comment;
import com.moviezone.service.CommentService;
import com.moviezone.service.KeyService;

public class CommentServiceImpl implements CommentService{
	private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	private CommentDao commentDao;
	private KeyService keyService;

	@Override
	public Comment select(long commentid) {
		return commentDao.select(commentid);
	}
		
	@Override
	public List<Comment> select(Comment comment, int pageNo, int pageSize) {
		return commentDao.select(comment, pageNo, pageSize);
	}
	
	@Override
	public List<Comment> select(long movieid, int pageNo, int pageSize) {
		if(movieid<1)return new ArrayList<Comment>();
		Comment param = new Comment();
		param.setMovieid(movieid);
		return select(param,pageNo,pageSize);
	}
	
	@Override
	public Page<Comment> selectPage(Comment comment, int pageNo, int pageSize) {
		return commentDao.selectPage(comment, pageNo, pageSize);
	}
	
	@Override
	public long insert(Comment comment) {
		comment.setCommentid(keyService.getCommentid());
		return commentDao.insert(comment);
	}

	@Override
	public boolean update(Comment comment) {
		return commentDao.update(comment);
	}

	@Override
	public boolean delete(Comment comment) {
		return commentDao.delete(comment);
	}

	@Override
	public boolean delete(long commentid) {
		return commentDao.delete(commentid);
	}
	
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public void setKeyService(KeyService keyService) {
		this.keyService = keyService;
	}

	

	

	

}
