package com.yestae.modules.user.controller;

import javax.validation.Valid;

import org.apache.oltu.oauth2.common.token.BasicOAuthToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.modules.commom.request.CommonRequest;
import com.yestae.modules.user.service.AuthorizeService;
import com.yestae.modules.user.service.UserApiService;
import com.yestae.modules.user.vo.UserOauthRequest;
import com.yestae.modules.user.vo.UserOauthResponse;
import com.yestae.user.center.dubbo.entity.UserDubbo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("")
public class UserOauthController {

	@Autowired
	private UserApiService userApiService;

	@Autowired
	private AuthorizeService authorizeService;

	/**
	 * 会员认证
	 */
	@ApiLog()
	@PostMapping("/oauth2/authorize")
	@RequiresPermissions("openapi:oauth2:authorize")
	@ApiOperation(value = "会员认证", notes = "会员认证", produces = "application/json", hidden = false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
		 @ApiImplicitParam(name = "bizcontent", value = "{\"password\":\"*****\",\"type\":1,\"mobile\":\"18311112223\"}", required = true, paramType = "form") })
	public R authorize(@Valid CommonRequest commonRequestVO) {
		UserOauthRequest userLoginRequest = commonRequestVO
				.parseObject(UserOauthRequest.class);
		// 表单校验
		ValidatorUtils.validateEntity(userLoginRequest);

		userLoginRequest.setAppid(commonRequestVO.getAppid());
		UserDubbo userDubbo = userApiService.login(userLoginRequest);

		String uid = userDubbo.getUserId();//
		BasicOAuthToken basicOAuthToken = authorizeService.createToken(uid, commonRequestVO.getAppid());
		UserOauthResponse resp = new UserOauthResponse();
		resp.setAccessToken(basicOAuthToken.getAccessToken());
		resp.setExpiresIn(basicOAuthToken.getExpiresIn());
		resp.setRefreshToken(basicOAuthToken.getRefreshToken());
		resp.setUid(uid);
		resp.setName(userDubbo.getName());

		return R.ok(resp);
	}
	
	/**
	 * 刷新accessToken
	 */
	@ApiLog()
	@PostMapping("/oauth2/refreshToken")
	@RequiresPermissions("openapi:oauth2:refreshToken")
	@ApiOperation(value = "刷新accessToken", notes = "刷新accessToken", produces = "application/json", hidden = false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name = "bizcontent", value = "{\"refreshToken\":\"2484cd8f2b541383ecd735cd577a3c9e\",\"uid\":\"22234\",\"accessToken\":\"e9954270086ac0736e642d0d0c6f948d\"}", required = true, paramType = "form") })
	public R refreshToken(@Valid CommonRequest commonRequestVO) {
		UserOauthResponse request = commonRequestVO
				.parseObject(UserOauthResponse.class);
		// 表单校验
		ValidatorUtils.validateEntity(request);

		BasicOAuthToken basicOAuthToken = authorizeService.refreshToken(request.getUid(), commonRequestVO.getAppid(), request.getRefreshToken());
		
		request.setAccessToken(basicOAuthToken.getAccessToken());
		request.setExpiresIn(basicOAuthToken.getExpiresIn());
		request.setRefreshToken(basicOAuthToken.getRefreshToken());

		return R.ok(request);
	}
	
}
