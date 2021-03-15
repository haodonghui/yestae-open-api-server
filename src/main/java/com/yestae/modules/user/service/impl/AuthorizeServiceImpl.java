package com.yestae.modules.user.service.impl;

import java.time.Duration;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.token.BasicOAuthToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.LogUtil;
import com.yestae.common.utils.RedisKeys;
import com.yestae.modules.commom.config.Constants;
import com.yestae.modules.user.service.AuthorizeService;

@Service("authorizeService")
public class AuthorizeServiceImpl implements AuthorizeService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;// 操作key-value都是字符串

	private final static Logger log = LoggerFactory
			.getLogger(AuthorizeServiceImpl.class);

	@Override
	public BasicOAuthToken createToken(String uid, String appid) {
		// 生成Access Token
		OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		// 生成token
		String accessToken = null;
		String refreshToken = null;
		try {
			accessToken = oauthIssuerImpl.accessToken();
			refreshToken = oauthIssuerImpl.refreshToken();
		} catch (OAuthSystemException e) {
			LogUtil.error(log, e, "生成Access Token异常");
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		String accessTokenKey = RedisKeys.AUTH_TOKEN_PREFIX + appid + ":"
				+ accessToken;

		String refreshTokenKey = RedisKeys.AUTH_TOKEN_PREFIX + appid + ":"
				+ refreshToken;

		stringRedisTemplate.opsForValue().set(accessTokenKey, uid,
				Duration.ofSeconds(Constants.ACCESSTOKEN_EXPIRESIN));

		JSONObject refreshTokenData = new JSONObject();
		refreshTokenData.put("uid", uid);
		refreshTokenData.put("accessToken", accessToken);

		stringRedisTemplate.opsForValue().set(refreshTokenKey,
				refreshTokenData.toJSONString(),
				Duration.ofSeconds(Constants.REFRESHTOKEN_EXPIRESIN));
		// 构建oauth2授权返回信息

		return new BasicOAuthToken(accessToken,
				Constants.ACCESSTOKEN_EXPIRESIN, refreshToken, null);
	}

	@Override
	public BasicOAuthToken refreshToken(String uid, String appid,
			String refreshToken) {

		String refreshTokenKey = RedisKeys.AUTH_TOKEN_PREFIX + appid + ":"
				+ refreshToken;

		// 获取refreshTokenValue
		String refreshTokenValue = stringRedisTemplate.opsForValue().get(
				refreshTokenKey);
		if (refreshTokenValue == null) {
			throw new RRException(
					ApiErrorMsgEnums.INVALID_REFRESH_TOKEN.getDesc(),
					ApiErrorMsgEnums.INVALID_REFRESH_TOKEN.getErrCode());
		}
		// 校验refreshTokenValue
		JSONObject refreshTokenData = JSONObject.parseObject(refreshTokenValue);
		String cacheUid = refreshTokenData.getString("uid");
		if (!uid.equals(cacheUid)) {
			throw new RRException(
					ApiErrorMsgEnums.ERROR_REFRESH_TOKEN.getDesc(),
					ApiErrorMsgEnums.ERROR_REFRESH_TOKEN.getErrCode());
		}
		// 获取accessTokenVaule
		String accessToken = refreshTokenData.getString("accessToken");
		String accessTokenKey = RedisKeys.AUTH_TOKEN_PREFIX + appid + ":"
				+ accessToken;
		String accessTokenVaule = stringRedisTemplate.opsForValue().get(
				accessTokenKey);

		// accessToken过期
		if (accessTokenVaule == null) {
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(
					new MD5Generator());
			try {
				accessToken = oauthIssuerImpl.accessToken();
			} catch (OAuthSystemException e) {
				LogUtil.error(log, e, "生成Access Token异常");
				throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
						ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
			}

			refreshTokenData.put("accessToken", accessToken);

			stringRedisTemplate.opsForValue().set(refreshTokenKey,
					refreshTokenData.toJSONString());
		}

		stringRedisTemplate.opsForValue().set(accessTokenKey, uid,
				Duration.ofSeconds(Constants.ACCESSTOKEN_EXPIRESIN));

		return new BasicOAuthToken(accessToken,
				Constants.ACCESSTOKEN_EXPIRESIN, refreshToken, null);

	}

	@Override
	public boolean validateToken(String uid, String appid, String accessToken) {

		String accessTokenKey = RedisKeys.AUTH_TOKEN_PREFIX + appid + ":"
				+ accessToken;
		// 获取accessTokenVaule
		String accessTokenVaule = stringRedisTemplate.opsForValue().get(
				accessTokenKey);
		if (accessTokenVaule == null || !accessTokenVaule.equals(uid)) {
			throw new RRException(
					ApiErrorMsgEnums.INVALID_ACCESS_TOKEN.getDesc(),
					ApiErrorMsgEnums.INVALID_ACCESS_TOKEN.getErrCode());
		}

		return true;
	}

}
