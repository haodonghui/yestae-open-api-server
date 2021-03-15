package com.yestae.modules.user.service;

import org.apache.oltu.oauth2.common.token.BasicOAuthToken;

public interface AuthorizeService {
	/**
	 * 生成接口凭证
	 * 
	 * @param uid
	 *            用户
	 * @param appid
	 *            应用id
	 * @return BasicOAuthToken
	 */
	BasicOAuthToken createToken(String uid, String appid);

	/**
	 * 刷新access_token有效期
	 * access_token是调用授权关系接口的调用凭证，由于access_token有效期（目前为2个小时）较短，
	 * 当access_token超时后，可以使用refresh_token进行刷新，access_token刷新结果有两种： 1.
	 * 若access_token已超时，那么进行refresh_token会获取一个新的access_token，新的超时时间； 2.
	 * 若access_token未超时
	 * ，那么进行refresh_token不会改变access_token，但超时时间会刷新，相当于续期access_token。
	 * refresh_token拥有较长的有效期（30天），当refresh_token失效的后，需要用户重新授权。
	 * 
	 * @param access_token
	 *            接口凭证
	 * @param refresh_token
	 *            更新凭证
	 * @return BasicOAuthToken
	 */
	BasicOAuthToken refreshToken(String uid, String appid, String refreshToken);

	/**
	 * 校验接口访问凭证
	 * 
	 * @param uid
	 *            用户id
	 * @param appid
	 *            应用id
	 * @param accessToken
	 *            接口凭证
	 * @return boolean
	 */
	boolean validateToken(String uid, String appid, String accessToken);
}
