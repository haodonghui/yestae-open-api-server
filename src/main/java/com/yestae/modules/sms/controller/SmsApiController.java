package com.yestae.modules.sms.controller;

import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.common.validator.group.SmsGroup;
import com.yestae.modules.commom.request.CommonRequest;
import com.yestae.modules.sms.service.SmsApiService;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.sms.enums.SmsTypeEnum;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("sms")
public class SmsApiController {
	
	@Autowired
	private SmsApiService smsApiService;
	
	/**
	 * 
	 * 发送忘记密码验证码
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/sendForgetPwdCode")
	@RequiresPermissions("openapi:user:sendForgetPwdCode")
	@ApiOperation(value = "发送忘记密码短信验证码", notes = "发送忘记密码短信验证码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="bizcontent",value="{\"mobile\":\"18311112223\"}",required=true,paramType="form")
	})
	public R sendUpdatePasswordCode(@Valid  CommonRequest baseRequest) {
		//将请求参数做转换
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		
		ValidatorUtils.validateEntity(userRequestVO, SmsGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		smsApiService.sendUpdatePasswordCode(mobile);
		return R.ok();
	}
	
	/**
	 * 
	 * 发送登录验证码
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/sendAuthCode")
	@RequiresPermissions("openapi:user:sendAuthCode")
	@ApiOperation(value = "发送登录验证码", notes = "发送登录验证码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="bizcontent",value="{\"mobile\":\"18311112223\"}",required=true,paramType="form")
	})
	public R sendLoginCode(@Valid  CommonRequest baseRequest) {
		//将请求参数做转换
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		
		ValidatorUtils.validateEntity(userRequestVO, SmsGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		
		smsApiService.sendLoginCode(mobile);
		return R.ok();
	}
	
	/**
	 * 
	 * 发送修改手机号验证码
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/sendUpdateMobileCode")
	@RequiresPermissions("openapi:user:sendUpdateMobileCode")
	@ApiOperation(value = "发送修改手机号验证码", notes = "发送修改手机号验证码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="bizcontent",value="{\"mobile\":\"18311112223\"}",required=true,paramType="form")
	})
	public R sendUpdateMobileCode(@Valid  CommonRequest baseRequest) {
		//将请求参数做转换
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, SmsGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		smsApiService.sendUpdateMobileCode(mobile);
		return R.ok();
	}
	
	/**
	 * 
	 * 生成微信登录验证码
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/generateCode")
	@RequiresPermissions("openapi:user:generateCode")
	@ApiOperation(value = "生成微信登录验证码", notes = "生成微信登录验证码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
		@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
		@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
		@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
		@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
		@ApiImplicitParam(name="bizcontent",value="{\"mobile\":\"18311112223\"}",required=true,paramType="form")
	})
	public R generateCode(@Valid  CommonRequest baseRequest) {
		//将请求参数做转换
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, SmsGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		String code = smsApiService.generateCode(mobile);
		
		Map<String,Object> map = Maps.newHashMap();
		map.put("smsCode", code);
		return R.ok(map);
	}
	/**
	 * 
	 * 校验短信验证码
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/validatorSmsCode")
	@RequiresPermissions("openapi:user:validatorSmsCode")
	@ApiOperation(value = "校验短信验证码", notes = "校验短信验证码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
		@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
		@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
		@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
		@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
		@ApiImplicitParam(name="bizcontent",value="{\"mobile\":\"18311112223\",\"smsCode\":\"1234\"}",required=true,paramType="form")
	})
	public R validatorSmsCode(@Valid  CommonRequest baseRequest) {
		//将请求参数做转换
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, SmsGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		String smsCode = userRequestVO.getSmsCode();
		int type = SmsTypeEnum.UPDATE_PASSWORD.getValue();
		smsApiService.validateSmsCode(mobile,smsCode,type);
		return R.ok();
	}

}
