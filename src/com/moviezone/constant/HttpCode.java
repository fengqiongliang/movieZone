package com.moviezone.constant;

public enum HttpCode {
	/**
	 * 用户没登陆
	 */
	unLogin(1000),           //没有登陆
	/**
	 * 用户名已存在
	 */
	usernameExit(1001),      //用户名已存在
	/**
	 * 用户名错误
	 */
	usernameError(1002),     //用户错误
	/**
	 * 密码错误
	 */
	passwordError(1003),     //密码错误
	/**
	 * 缺少参数
	 */
	missParam(1004),         //少传参数
	/**
	 * 参数错误，参数转换发生错误
	 */
	paramError(1005),        //参数错误
	/**
	 * 不支持请求方法GET或POST
	 */
	notSupportMethod(1006);  //不支持的get或post方法

	private int code;
	private HttpCode(int code){
		this.code=code;
	}
	public int getCode(){
		return code;
	}
	@Override
	public String toString(){
		return code+"";
	}
}
