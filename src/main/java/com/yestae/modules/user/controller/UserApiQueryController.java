package com.yestae.modules.user.controller;

import com.google.common.collect.Maps;
import com.yestae.common.annotation.ApiLog;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.R;
import com.yestae.common.validator.Assert;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.common.validator.group.*;
import com.yestae.modules.commom.config.Constants;
import com.yestae.modules.commom.request.CommonRequest;
import com.yestae.modules.user.service.AuthorizeService;
import com.yestae.modules.user.service.UserApiService;
import com.yestae.modules.user.vo.ServiceDetail;
import com.yestae.modules.user.vo.UserLoginRequest;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.modules.user.vo.UserResponse;
import com.yestae.modules.user.vo.auth.UserAuthResponse;
import com.yestae.modules.user.vo.coin.UserCoinReponse;
import com.yestae.modules.user.vo.coin.UserCoinRequest;
import com.yestae.modules.user.vo.order.OrderDetailVO;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * 用户查询相关信息控制器
 *
 * @Package com.yestae.modules.userapi 
 * @ClassName UserApiQueryController 
 * @author wangpeng
 * @date 2019年5月16日 下午6:41:47 
 *
 */
@RestController
@RequestMapping("user")
public class UserApiQueryController {
	
	@Autowired
	private UserApiService userApiService;
    @Autowired
    private AuthorizeService authorizeService;
	
	/**
	 * 
	 * 查询会员信息
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/getInfo")
	@RequiresPermissions("openapi:user:getInfo")
	@ApiOperation(value = "查询会员信息", notes = "查询会员信息", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"mobile\":\"18311112226\"}",required=true,paramType="form")
	})
	public R getInfo(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		//手机号
		String mobile = userRequestVO.getMobile();
		//会员id
		String uid = userRequestVO.getUid();
		//请求服务获取结果
		return R.ok(userApiService.getUserInfo(uid, mobile));
	}
	/**
	 * 
	 * 批量查询会员信息
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/getInfoBatch")
	@RequiresPermissions("openapi:user:getInfoBatch")
	@ApiOperation(value = "批量查询会员信息", notes = "批量查询会员信息", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
		@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
		@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
		@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
		@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
		@ApiImplicitParam(name="bizcontent",value="{\"uids\":[951375525788782800,951375525788782800]}",required=true,paramType="form")
	})
	public R getInfoBatch(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		//不走校验，参数为空时
//		ValidatorUtils.validateEntity(userRequestVO, GetUserBatchGroup.class);
		List<String> uids = userRequestVO.getUids();
		//请求服务获取结果
		List<UserResponse> list = userApiService.getInfoBatch(uids);
		return R.ok(list);
	}
	
	
	/**
	 * 
	 * 查询会员增值服务
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/serviceList")
	@RequiresPermissions("openapi:user:serviceList")
	@ApiOperation(value = "查询会员增值服务", notes = "查询会员增值服务", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\"}",required=true,paramType="form")
	})
	public R serviceList(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, ServiceListGroup.class);
		//会员id
		String uid = userRequestVO.getUid();

		List<ServiceDetail> serviceDetail = userApiService.searchAddedAllServices(uid);

		UserResponse userInfoResponceDTO = new UserResponse();
		userInfoResponceDTO.setUid(uid);
		userInfoResponceDTO.setServiceDetail(serviceDetail);
		return R.ok(userInfoResponceDTO);
	}

	/**
	 *
	 * 查找用户开通增值服务记录
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/service")
	@RequiresPermissions("openapi:user:service")
	@ApiOperation(value = "查找用户开通增值服务记录", notes = "查找用户开通增值服务记录", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"addedServiceId\":\"1128947923510472705\"}",required=true,paramType="form")
	})
	public R service(@Valid  CommonRequest baseRequest){

		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, ServiceListGroup.class);
		//会员id
		String uid = userRequestVO.getUid();
		// 增值服务id
		String addedServiceId = userRequestVO.getAddedServiceId();
		List<ServiceDetail> serviceDetail = userApiService.searchAddedServices(uid,addedServiceId);

		UserResponse userInfoResponceDTO = new UserResponse();
		userInfoResponceDTO.setUid(uid);
		userInfoResponceDTO.setServiceDetail(serviceDetail);
		return R.ok(userInfoResponceDTO);
	}
	
	/**
	 * 
	 * 查询会员历史订单, 默认最近10条记录
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/orderList")
	@RequiresPermissions("openapi:user:orderList")
	@ApiOperation(value = "查询会员历史订单", notes = "查询会员历史订单", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\"}",required=true,paramType="form")
	})
	public R orderList(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, OrderListGroup.class);
		//会员id
		String uid = userRequestVO.getUid();
		List<OrderDetailVO> listvo = userApiService.orderList(uid);
		return R.ok(listvo);
	}

	/**
	 *
	 * 获取实名认证信息
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/getAuthInfo")
	@RequiresPermissions("openapi:user:getAuthInfo")
	@ApiOperation(value = "查询实名信息", notes = "查询实名信息", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"accessToken\":\"123\"}",required=true,paramType="form")
	})
	public R getAuthInfo(@Valid  CommonRequest baseRequest){

        UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
        String uid = userRequestVO.getUid();
        String accessToken = userRequestVO.getAccessToken();
        Assert.isBlank(uid, "uid不能为空");
        Assert.isBlank(accessToken, "accessToken不能为空");

        //会员id
        userRequestVO.setAppid(baseRequest.getAppid());
        userRequestVO.setMchid(baseRequest.getMchid());
		UserAuthResponse userAuthResponse = userApiService.getAuthInfo4Encrypt(userRequestVO);
		return R.ok(userAuthResponse);
	}



	/**
	 *
	 * 查询会员受益劵余额
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	/*@ApiLog()
	@PostMapping("/getCoinBalance")
	@RequiresPermissions("openapi:user:getCoinBalance")
	@ApiOperation(value = "查询会员受益劵余额", notes = "查询会员受益劵余额", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\"}",required=true,paramType="form")
	})
	public R getUserCoinBalance(@Valid  CommonRequest baseRequest){

		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
		ValidatorUtils.validateEntity(userRequestVO, UserCoinQueryGroup.class);

		//会员id
		String uid = userRequestVO.getUid();
		//请求服务获取结果
		UserResponse userCoinBalance = userApiService.getUserCoinBalance(uid);
		return R.ok(userCoinBalance);
	}*/



	/**
	 *
	 * 发放受益劵
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	/*@ApiLog()
	@PostMapping("/grantCoin")
	@RequiresPermissions("openapi:user:grantCoin")
	@ApiOperation(value = "发放受益劵", notes = "发放受益劵", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"outTradeNo\":\"1128947923510472705\",\"totalFee\":112}",required=true,paramType="form")
	})
	public R grantCoin(@Valid  CommonRequest baseRequest){

		UserCoinRequest request = baseRequest.parseObject(UserCoinRequest.class);

		ValidatorUtils.validateEntity(request, AddGroup.class);

		request.setAppid(baseRequest.getAppid());

		//请求服务获取结果
		UserCoinReponse reponse = userApiService.addUserCoin(request);

		return R.ok(reponse);
	}*/

	/**
	 *
	 * 收回受益劵
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	/*@ApiLog()
	@PostMapping("/recoverCoin")
	@RequiresPermissions("openapi:user:recoverCoin")
	@ApiOperation(value = "收回受益劵", notes = "收回受益劵", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"outTradeNo\":\"1128947923510472705\"}",required=true,paramType="form")
	})
	public R recoverCoin(@Valid  CommonRequest baseRequest){

		UserCoinRequest request = baseRequest.parseObject(UserCoinRequest.class);

		ValidatorUtils.validateEntity(request, UpdateGroup.class);

		request.setAppid(baseRequest.getAppid());

		//请求服务获取结果
		UserCoinReponse reponse = userApiService.recoverCoin(request);

		return R.ok(reponse);
	}*/


	/**
	 * 
	 * 查询会员是否有尊享服务
	 *
	 * @param baseRequest
	 * @return
	 * @throws
	 */
	@ApiLog()
	@PostMapping("/isHonourable")
	@RequiresPermissions("openapi:user:isHonourable")
	@ApiOperation(value = "查询会员是否有尊享服务", notes = "查询会员是否有尊享服务", produces = "application/json",hidden=false)
	@ApiImplicitParams({
		 @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
	   	 @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
   	 @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"mobile\":\"13833333333\"}",required=true,paramType="form")
	})
	public R isHonourable(@Valid  CommonRequest baseRequest){
		
		UserRequest userRequestVO = baseRequest.parseObject(UserRequest.class);
//		ValidatorUtils.validateEntity(userRequestVO, ServiceListGroup.class);
		//会员id
		String uid = userRequestVO.getUid();
		if(StringUtils.isBlank(uid)){
			if(StringUtils.isBlank(userRequestVO.getMobile())){
				return R.error(null, "手机号码不能为空！");
			}
			UserResponse userInfo = userApiService.getUserInfo(null, userRequestVO.getMobile());
			uid = userInfo.getUid();
		}
		
		UserResponse userInfo = null;
		try {
			userInfo = userApiService.getUserInfo(userRequestVO.getUid(), userRequestVO.getMobile());
		} catch (RRException e) {
			return R.error(e.getCode(), e.getMessage());
		}
		if(userInfo == null){
			return R.error(ApiErrorMsgEnums.USER_NOT_EXSIT.getErrCode(), ApiErrorMsgEnums.USER_NOT_EXSIT.getDesc());
		}
			
		
		boolean isHonourable = false;
		List<ServiceDetail> serviceDetail = userApiService.searchAddedAllServices(uid);
		if(serviceDetail != null && serviceDetail.size()>0){
			for (Iterator iterator = serviceDetail.iterator(); iterator.hasNext();) {
				ServiceDetail detail = (ServiceDetail) iterator.next();
//				ServiceDetail(id=af650e3e0a024bed8384ec419f93d1f0, name=大益尊享会员服务, 
//				startTime=1593249081317, endTime=1624785081317, status=2, isOpenOrNo=null, isOpenForTheFirse=null)
				if(StringUtils.equals(detail.getId(), Constants.VAS_ZX)){//e0316f1b70784d26924e09bc18a92fb8
					if(detail.getEndTime()>new Date().getTime()){
						isHonourable = true;
						break;
					}
				}
			}
		}
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		m.put("isHonourable", isHonourable);
		return R.ok(m);
	}

	/**
	 *
	 * 校验用户是否登录
	 *
	 * @param baseRequest	baseRequest
	 * @return R
	 */
	@ApiLog()
	@PostMapping("/checkIsLoginByUidSid")
	@RequiresPermissions("openapi:user:checkIsLoginByUidSid")
	@ApiOperation(value = "校验用户是否登录", notes = "校验用户是否登录", produces = "application/json",hidden=false)
	@ApiImplicitParams({
			@ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
			@ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
			@ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
			@ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
			@ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
			@ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1128947923510472705\",\"sid\":\"123\",\"platform\":\"0\"}",required=true,paramType="form")
	})
	public R checkIsLoginByUidSid(@Valid  CommonRequest baseRequest){

		UserLoginRequest userLoginRequest = baseRequest.parseObject(UserLoginRequest.class);

		String userId = userLoginRequest.getUserId();
		String sid = userLoginRequest.getSid();
		Integer platform = userLoginRequest.getPlatform();
		Assert.isBlank(userId, "userId不能为空");
		Assert.isBlank(sid, "sid不能为空");
		Assert.isBlank("platform", "platform不能为空");

		Boolean flag = userApiService.checkIsLoginByUidSid(userId, sid, platform);
		Map<String, Integer> map = Maps.newHashMap();
		map.put("isLogin", flag ? 1 : 0);
		return R.ok(map);
	}
}
