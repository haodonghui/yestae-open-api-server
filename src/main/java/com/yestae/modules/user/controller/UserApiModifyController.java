package com.yestae.modules.user.controller;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.common.validator.group.AddUserGroup;
import com.yestae.common.validator.group.UpdateMobileGroup;
import com.yestae.common.validator.group.UpdatePwdGroup;
import com.yestae.modules.commom.request.CommonRequest;
import com.yestae.modules.user.service.AuthorizeService;
import com.yestae.modules.user.service.UserApiService;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.modules.user.vo.UserResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * 用户新增修改控制器
 *
 * @Package com.yestae.modules.userapi 
 * @ClassName UserApiModifyController 
 * @author wangpeng
 * @date 2019年5月16日 下午6:25:52 
 *
 */
@RestController
@RequestMapping("user")
public class UserApiModifyController{
	
	@Autowired
	private UserApiService userApiService;

	@Autowired
	private AuthorizeService authorizeService;

	/**
	 * 
	 * 新增用户
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/add")
	@RequiresPermissions("openapi:user:add")
	@ApiOperation(value = "新增用户", notes = "新增用户", produces = "application/json",hidden=false)
	@ApiImplicitParams({
   	 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"name\":\"BeJson2\",\"mobile\":\"18311112223\",\"gender\":1,\"birthday\":\"2019-01-01\"}",required=true,paramType="form")
	})
	public R add(@Valid CommonRequest baseRequest){
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, AddUserGroup.class);
		userRequestVO.setAppid(baseRequest.getAppid());
		UserResponse responceDTO = userApiService.addUser(userRequestVO);
		
		return R.ok(responceDTO);
	}
	
	
	/**
	 * 
	 * 修改会员手机号
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/updateMobile")
	@RequiresPermissions("openapi:user:updateMobile")
	@ApiOperation(value = "修改会员手机号", notes = "修改会员手机号", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"mobile\":\"18311112226\",\"accessToken\":\"123\"}",required=true,paramType="form")
	})
	public R updateMobile(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, UpdateMobileGroup.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		//会员id
		String uid = userRequestVO.getUid();
		
		String accessToken = userRequestVO.getAccessToken();
		
		authorizeService.validateToken(uid, baseRequest.getAppid(), accessToken);
		
		userApiService.updateMobile(uid, mobile);
		
		return R.ok();
	}
	
	@ApiLog()
	@PostMapping("/updatePassword")
	@RequiresPermissions("openapi:user:updatePassword")
	@ApiOperation(value = "修改会员登录密码", notes = "修改会员登录密码", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"newPassword\":\"qaz123\",\"accessToken\":\"2484cd8f2b541383ecd735cd577a3c9e\"}",required=true,paramType="form")
	})
	public R updatePassword(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		
		ValidatorUtils.validateEntity(userRequestVO, UpdatePwdGroup.class);
		
		String uid = userRequestVO.getUid();
		String accessToken = userRequestVO.getAccessToken();
		
		authorizeService.validateToken(uid, baseRequest.getAppid(), accessToken);
		
		userApiService.updatePassword(userRequestVO);
		return R.ok();
	}
	
}
