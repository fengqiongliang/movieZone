package com.moviezone.dao;

import com.moviezone.domain.User;


public interface UserDao {
	public User selectById(long id);
	public User selectByNickName(String nickname);
	public User selectByMobile(long mobile);
	public User selectByEmail(String email);
	public long insert(User user);
	public boolean update(User user);
	public boolean delete(long id);
	/**
	 * 
	 * @param nickname û�пɴ���null
	 * @param mobile û�пɴ���-1
	 * @param email  û�пɴ���null
	 * @return
	 */
	public boolean contain(String nickname,long mobile,String email);
}
