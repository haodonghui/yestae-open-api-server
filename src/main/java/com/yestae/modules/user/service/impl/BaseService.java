package com.yestae.modules.user.service.impl;

import com.google.common.collect.Lists;
import com.yestae.business.enums.PlatformEnum;
import com.yestae.business.enums.SourceEnum;
import com.yestae.common.api.MemberVo;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.StrUtils;
import com.yestae.modules.sys.entity.OpenapiMchAppSourceCodeEntity;
import com.yestae.modules.sys.service.OpenapiMchAppSourceCodeService;
import com.yestae.modules.sys.service.OpenapiMchUserRelationService;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.order.api.IOrderDubboService;
import com.yestae.order.api.OrderResult;
import com.yestae.order.api.OrderVo;
import com.yestae.user.center.dubbo.entity.UserDubbo;
import com.yestae.user.center.dubbo.entity.UserRegisterParameter;
import com.yestae.user.center.dubbo.entity.UserResult;
import com.yestae.user.center.dubbo.service.IUserCenterAddedService;
import com.yestae.user.center.dubbo.service.IUserCenterRealNameAuthenticationService;
import com.yestae.user.center.dubbo.service.IUserCenterService;
import com.yestae.user.vas.dubbo.service.IVasDubboService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 * 用户相关基础信息
 *
 * @Package com.yestae.modules.userapi.service.impl 
 * @ClassName UserBaseService 
 * @author wangpeng
 * @date 2019年5月20日 上午11:14:14 
 *
 */
public class BaseService {
	//用户中心
	@Autowired
	protected IUserCenterService userCenterService;
	//实名认证
	@Autowired
	protected IUserCenterRealNameAuthenticationService userCenterRealNameAuthenticationService;
	//用户中心增值服务
	@Autowired
	protected IUserCenterAddedService userCenterAddedService;
	//增值服务管理后台dubbo
	@Autowired
	protected IVasDubboService vasDubboService;
	//短信服务
//	@Autowired
//	protected ISmsApiService smsApiService;
	
	//订单服务
	@Autowired
	protected IOrderDubboService orderDubboService;
	
	@Autowired
	protected OpenapiMchUserRelationService openapiMchUserRelationService;

	/**
	 * 	商户应用
	 */
	@Autowired
	protected OpenapiMchAppSourceCodeService openapiMchAppSourceCodeService;


	/**
	 * 	受益劵
	 */
//	@Autowired
//	protected ICoinDubboService coinDubboService;


	
	/**
	 * 
	 * 获取用户基础信息
	 *
	 * @param uid
	 * @return
	 * @throws
	 */
	protected MemberVo packMemberVo(String uid){
		if(StringUtils.isBlank(uid)){
			throw new RRException(ApiErrorMsgEnums.MISSING_PARAMETER.getDesc(), "用户id不能为空");
		}
		UserResult<UserDubbo> userResult = userCenterService.findUser(uid);
		
		if(userResult == null){
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(), ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if(!userResult.isSucceed()){
			throw new RRException(userResult.getRetMsg(), userResult.getRetCode());
		}
		
		UserDubbo userDubbo;
		if((userDubbo = userResult.getResult()) == null){
			throw new RRException(ApiErrorMsgEnums.USER_NOT_EXSIT.getDesc(), ApiErrorMsgEnums.USER_NOT_EXSIT.getErrCode());
		}
		
		MemberVo vo = new MemberVo();
		vo.setMemberId(userDubbo.getUserId());
		vo.setMemberName(userDubbo.getName());
		vo.setMemberNo(userDubbo.getUserNum() + "");
		vo.setMemberPhone(userDubbo.getMobile());
		vo.setMemberLevel(userDubbo.getType() + "");
		return vo;
	}
	
	//注册信息封装
	//注册信息封装
	protected UserRegisterParameter regRequest2UserRegisterParameter(UserRequest userRequestVO) {
		String name = userRequestVO.getName();
		//昵称为空时随机生成昵称
		if(StringUtils.isBlank(name)){
			name = "T" + StrUtils.buildRandom(8);
		}
		UserRegisterParameter urp = new UserRegisterParameter();
		urp.setPassword(StrUtils.getRandomStringByLength(10));
		urp.setMobile(userRequestVO.getMobile());
		urp.setName(name);
		if(userRequestVO.getBirthday() != null){
			urp.setBirthday(userRequestVO.getBirthday().getTime());
		}
		urp.setGender(userRequestVO.getGender());
		urp.setPlatform(PlatformEnum.UNKNOWN.getValue());

        String appid = userRequestVO.getAppid();

        if (StringUtils.isNotBlank(appid)) {
			OpenapiMchAppSourceCodeEntity entity = openapiMchAppSourceCodeService.getEntityByAppCode(appid);
			urp.setSource(entity!=null?entity.getUcCode():SourceEnum.UNKNOWN.getValue());
        }else{
            urp.setSource(SourceEnum.UNKNOWN.getValue());
        }
		return urp;
	}

	
	/**
	 * 
	 * 查询最近几个月的订单
	 *
	 * @param uid
	 * @param months
	 * @return
	 * @throws
	 */
	protected List<OrderVo> findMemberOrderByDate(String uid, Integer months){
		if(StringUtils.isBlank(uid)){
			throw new RRException(ApiErrorMsgEnums.MISSING_PARAMETER.getDesc(), ApiErrorMsgEnums.MISSING_PARAMETER.getErrCode());
		}
		
		MemberVo memberVo = packMemberVo(uid);
		months = months == null ? 1 : months;
		OrderResult<List<OrderVo>> orderResult = orderDubboService.findMemberOrderByDate(memberVo, months);
		if(orderResult == null){
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(), ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if(!orderResult.isSucceed()){
			throw new RRException(orderResult.getRetMsg(), orderResult.getRetCode());
		}
		
		return orderResult.getResult() == null ? Lists.newArrayList() : orderResult.getResult();
	}

}
