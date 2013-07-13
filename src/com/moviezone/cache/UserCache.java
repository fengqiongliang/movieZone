package com.moviezone.cache;

import org.apache.commons.lang.StringUtils;

import com.moviezone.domain.User;

public class UserCache {
	private CacheClient client;
	
	public long getNewId(){
		return client.hIncByOne(CacheKey.Id, "user");
	}
	
	public User getById(long id){
		if(id<0)return null;
		String idKey = getIdKey(id);
		return client.getObject(idKey,User.class);
	}
	
	public User getByNickName(String nickname){
		if(StringUtils.isBlank(nickname))return null;
		String nickNameKey = this.getNickNameKey(nickname);
		return client.getObject(nickNameKey,User.class);
	}
	public User getByEmail(String email){
		if(StringUtils.isBlank(email))return null;
		String emailKey = getEmailKey(email);
		return client.getObject(emailKey, User.class);
	}
	public User getByMobile(long mobile){
		if(mobile<0)return null;
		String mobileKey = getMobileKey(mobile);
		return client.getObject(mobileKey, User.class);
	}
	public void putUser(User user){
		if(user==null||user.getId()<1)return;
		long id = user.getId();
		String nickname = user.getNickname();
		String email = user.getEmail();
		long mobile = user.getMobile();
		
		String IdKey = getIdKey(id);
		String NickNameKey = nickname==null?null:getNickNameKey(nickname);
		String emailKey = email==null?null:getEmailKey(email);
		String mobileKey = mobile<1?null:getMobileKey(mobile);
		if(IdKey!=null)client.putObject(IdKey, user);
		if(NickNameKey!=null)client.putObject(NickNameKey, user);
		if(emailKey!=null)client.putObject(emailKey, user);
		if(mobileKey!=null)client.putObject(mobileKey, user);
	}
	public void delUser(User user){
		if(user==null||user.getId()<1)return;
		long id = user.getId();
		String nickname = user.getNickname();
		String email = user.getEmail();
		long mobile = user.getMobile();
		
		String IdKey = getIdKey(id);
		String NickNameKey = nickname==null?null:getNickNameKey(nickname);
		String emailKey = email==null?null:getEmailKey(email);
		String mobileKey = mobile<1?null:getMobileKey(mobile);
		if(IdKey!=null)client.remove(IdKey);
		if(NickNameKey!=null)client.remove(NickNameKey);
		if(emailKey!=null)client.remove(emailKey);
		if(mobileKey!=null)client.remove(mobileKey);
	}

	public void setClient(CacheClient client) {
		this.client = client;
	}

	private String getIdKey(long id){
		return CacheKey.User_Id+"_"+id;
	}
	private String getNickNameKey(String nickname){
		return CacheKey.User_NickName+"_"+nickname;
	}
	private String getEmailKey(String email){
		return CacheKey.User_Email+"_"+email;
	}
	private String getMobileKey(long mobile){
		return CacheKey.User_Mobile+"_"+mobile;
	}
	public void test(){
		System.out.println(getByEmail("nickname550"));
	}
}
