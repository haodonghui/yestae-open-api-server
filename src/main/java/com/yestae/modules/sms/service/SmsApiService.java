package com.yestae.modules.sms.service;

public interface SmsApiService {
	
	/**
	 * 
	 * 发送忘记密码短信验证码
	 *
	 * @param mobile
	 * @throws
	 */
	void sendUpdatePasswordCode(String mobile);
	
	/**
	 * 
	 * 发送无密登录短信验证码
	 *
	 * @param mobile
	 * @throws
	 */
	void sendLoginCode(String mobile);
	
	/**
	 * 
	 * 通用发送短信验证码
	 *
	 * @param mobile
	 * @param source
	 * @param type
	 * @param expires
	 * @throws
	 */
	void sendSms(String mobile, Integer source, Integer type, Integer expires);
	
	/**
	 * 
	 * 验证码校验
	 *
	 * @param mobile
	 * @param code
	 * @param type
	 * @throws
	 */
	boolean validateSmsCode(String mobile, String code, Integer type);

	/**
	 * 
	 * 发送修改手机号验证码
	 *
	 * @param mobile
	 * @throws
	 */
	void sendUpdateMobileCode(String mobile);

	/**
	 * 
	 * 生成短信验证码，不发送给用户，可用作微信授权登录
	 *
	 * @param mobile
	 * @return
	 * @throws
	 */
	String generateCode(String mobile);

}
