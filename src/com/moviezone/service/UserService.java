package com.moviezone.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.moviezone.domain.Page;
import com.moviezone.domain.User;

public interface UserService {
	public User select(long userid);
	public List<User> select(User user);
	public List<User> select(User user,int pageNo,int pageSize);
	public List<User> selectForbit(User user,int pageNo,int pageSize);
	public boolean isForbitIp(String ip);
	public Page<User> selectPage(User user);
	public Page<User> selectPage(User user,int pageNo,int pageSize);
	public Page<User> selectForbitPage(User user,int pageNo,int pageSize);
	public long insert(User user);
	public boolean update(User user);
	public boolean delete(User user);
	public boolean delete(long userid);
	public boolean delForbit(long userid);
	public Page<User> selectForbitUser(String userid,String nickname,String createarea,String createip,int pageNo);
	public Page<User> selectNormalUser(String userid,String nickname,String createarea,String createip,int pageNo);
	public void permitModify(long userid);
	public void addNormalForbit(long userid);
	public void addSystemForbit(String createip,String createarea);
	public void testTransaction()throws Exception;
}
