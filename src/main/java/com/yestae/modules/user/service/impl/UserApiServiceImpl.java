package com.yestae.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yestae.business.enums.SourceEnum;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.enums.CommonEnums;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.*;
import com.yestae.modules.commom.request.RequestContext;
import com.yestae.modules.sys.entity.OpenapiMchAppSourceCodeEntity;
import com.yestae.modules.user.service.UserApiService;
import com.yestae.modules.user.vo.ServiceDetail;
import com.yestae.modules.user.vo.UserOauthRequest;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.modules.user.vo.UserResponse;
import com.yestae.modules.user.vo.auth.UserAuthResponse;
import com.yestae.modules.user.vo.order.OrderDetailVO;
import com.yestae.order.api.OrderVo;
import com.yestae.user.center.dubbo.entity.*;
import com.yestae.user.vas.dubbo.common.api.VasResult;
import com.yestae.user.vas.dubbo.vo.VasEquityVoDubbo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Service("userApiService")
public class UserApiServiceImpl extends BaseService implements UserApiService {


	private final static Logger log = LoggerFactory
			.getLogger(UserApiServiceImpl.class);

	/**
	 * 校验手机号是否存在
	 *
	 * @param mobile 手机号
	 * @return
	 */
	public boolean isExistMobile(String mobile) {
		boolean isExist = false;
		// 请求用户中心登录服务
		UserResult<Boolean> userResult = userCenterService
				.validateIsExistMobile(mobile);
		if (userResult == null) {
			LogUtil.error(
					log,
					"用户中心validateIsExistMobile接口异常:param:[mobile:{}],result:[{}]",
					mobile, userResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (userResult.isSucceed()) {
			isExist = userResult.getResult();
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
		return isExist;
	}

	/**
	 * 查询用户是否完成益友会实名
	 *
	 * @param uid
	 * @return
	 * @throws
	 */
	@Override
	public boolean findUserCertificationStatus(String uid) {
		UserResult<Integer> userResult = userCenterService
				.findUserCertificationStatus(uid);

		if (userResult == null) {
			LogUtil.error(
					log,
					"用户中心findUserCertificationStatus接口异常:param:[uid:{}],result:[{}]",
					uid, userResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		if (userResult.isSucceed()) {
			return userResult.getResult() == 1;
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
	}

	/**
	 * 查询新手机号是否实名
	 *
	 * @param uid
	 * @param mobile
	 * @return
	 * @throws
	 */
	@Override
	public boolean findNewMobileCertificationStatus(String uid, String mobile) {
		UserResult<Integer> userAuthResult = userCenterRealNameAuthenticationService
				.findCertificationStatus(Long.valueOf(uid), mobile);

		if (userAuthResult == null) {
			LogUtil.error(
					log,
					"用户中心findCertificationStatus接口异常:param:[uid:{},mobile:{}],result:[{}]",
					uid, mobile, userAuthResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (userAuthResult.isSucceed()) {
			return userAuthResult.getResult() == 1;
		} else {
			throw new RRException(userAuthResult.getRetMsg(),
					userAuthResult.getRetCode());
		}
	}

	/**
	 * 更新手机号
	 *
	 * @param uid
	 * @param mobile
	 * @return
	 * @throws
	 */
	public boolean updateMobile(String uid, String mobile) {
		// 修改手机号
		UserResult<Boolean> userResult = userCenterService.modifyUserMobile(
				uid, uid, mobile, SourceEnum.UNKNOWN.getValue());
		if (userResult == null) {
			LogUtil.error(
					log,
					"用户中心modifyUserMobile接口异常:param:[uid:{},mobile:{}],result:[{}]",
					uid, mobile, userResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (!userResult.isSucceed()) {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public UserResponse addUser(UserRequest userRequestVO) {
		UserRegisterParameter registerParameter = regRequest2UserRegisterParameter(userRequestVO);
		Map<String, Object> mapResult = userCenterService
				.registerUser(registerParameter);
		UserResult<UserDubbo> userResult;
		if (mapResult == null
				|| (userResult = (UserResult<UserDubbo>) mapResult
				.get("userResult")) == null) {
			LogUtil.error(log, "用户中心registerUser接口异常:param:[{}]result:[{}]",
					userRequestVO, JSON.toJSONString(mapResult));
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (!userResult.isSucceed()) {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
		// 处理相应返回数据
		UserDubbo result = userResult.getResult();
		UserResponse userInfoResponceDTO = new UserResponse();
		String uid = result.getUserId();
		String mobile = result.getMobile();
		userInfoResponceDTO.setUid(uid);
		userInfoResponceDTO.setMobile(mobile);
		userInfoResponceDTO.setName(result.getName());
		userInfoResponceDTO.setRegTime(result.getCreateTime());

		saveUserRelation(uid, mobile);

		return userInfoResponceDTO;
	}

	/**
	 * 获取用户基本信息
	 *
	 * @param uid    用户id
	 * @param mobile 用户手机号
	 * @return
	 * @throws
	 */
	public UserResponse getUserInfo(String uid, String mobile) {

		// 参数校验---开始
		if (StringUtils.isBlank(uid) && StringUtils.isBlank(mobile)) {
			throw new RRException("用户id和手机号不能同时为空",
					ApiErrorMsgEnums.MISSING_PARAMETER.getErrCode());
		}
		// 参数校验---结束
		// 获取查询用户的优先条件 uid > mobile
		// 调用dubbo注册接口
		UserResult<UserDubbo> userResult;
		if (StringUtils.isNotBlank(uid)) {
			userResult = userCenterService.findUser(uid);
		} else {
			userResult = userCenterService.findUserByMobile(mobile);
		}

		if (userResult == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		// 结果判断赋值
		UserDubbo result;
		if (userResult.isSucceed() && (result = userResult.getResult()) != null) {
			List<UserDubbo> userDubboList = Lists.newArrayList(result);
			return packUserInfo(userDubboList).get(0);
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
	}

	@Override
	public List<String> findUserAdd(String uid) {
		// 查询用户开通服务
		UserResult<List<String>> userResult = userCenterAddedService
				.searchAddedServices(uid);
		if (userResult == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		if (userResult.isSucceed()) {
			return userResult.getResult();
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
	}

	@Override
	public UserResponse getUserAddInfo(String uid, List<String> addIdList) {
		if (StringUtils.isBlank(uid)) {
			throw new RRException(ApiErrorMsgEnums.MISSING_PARAMETER.getDesc(),
					ApiErrorMsgEnums.MISSING_PARAMETER.getErrCode());
		}

		UserResponse userInfoResponceDTO = new UserResponse();
		List<ServiceDetail> serviceDetailList = Lists.newArrayList();
		userInfoResponceDTO.setServiceDetail(serviceDetailList); // 增值服务
		userInfoResponceDTO.setUid(uid); // 用户id
		// 未开通增值服务，返回空集合
		if (CollectionUtils.isEmpty(addIdList)) {
			return userInfoResponceDTO;
		}
		// 查询增值服务信息
		List<VasEquityVoDubbo> vasEquityVoList = findVasDubbo(addIdList);
		Optional.ofNullable(vasEquityVoList)
				.ifPresent(
						vasEquityVos -> {
							vasEquityVos
									.forEach(vasEquityV -> {
										ServiceDetail serviceDetail = new ServiceDetail();
										serviceDetail.setId(vasEquityV.getId());
										serviceDetail.setName(vasEquityV
												.getVasName());

										// 查询出增值服务开通时间
										AddedServiceResultDubbo addedServiceStatusResult = findAddedServiceStatus(
												uid, vasEquityV.getId());
										Optional.ofNullable(
												addedServiceStatusResult)
												.ifPresent(
														addedServiceStatus -> {
															serviceDetail
																	.setEndTime(addedServiceStatus
																			.getEndTime());
															serviceDetail
																	.setStartTime(addedServiceStatus
																			.getBeginTime());
														});

										serviceDetailList.add(serviceDetail);
									});
						});
		return userInfoResponceDTO;
	}

	@Override
	public List<VasEquityVoDubbo> findVasDubbo(List<String> vasIds) {
		VasResult<List<VasEquityVoDubbo>> vasResult = vasDubboService
				.findVasEquityVoByVasIdList(vasIds);
		if (vasResult == null) {
			LogUtil.error(
					log,
					"用户中心findVasEquityVoByVasIdList接口异常:param:[vasIds:{}],result:[{}]",
					vasIds, vasResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (vasResult.isSucceed()) {
			return vasResult.getResult();
		} else {
			throw new RRException(vasResult.getRetMsg(), vasResult.getRetCode());
		}
	}

	@Override
	public AddedServiceResultDubbo findAddedServiceStatus(String uid,
														  String vasId) {
		UserResult<AddedServiceResultDubbo> userResult = userCenterAddedService
				.findAddedServiceStatus(uid, vasId);
		if (userResult == null) {
			LogUtil.error(
					log,
					"用户中心findVasEquityVoByVasIdList接口异常:param:[uid:{},vasId:{}],result:[{}]",
					uid, vasId, userResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if (userResult.isSucceed()) {
			return userResult.getResult();
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
	}

	@Override
	public void updatePassword(UserRequest userRequestVO) {
		// 手机号校验
		String uid = userRequestVO.getUid();
		// 原登录密码
//		String oldPassword = userRequestVO.getOldPassword();
		// 新密码1
		String newPasswordo = userRequestVO.getNewPassword();
		StrUtils.validPasswordStrict(newPasswordo);
		// 成功，修改密码
		UserResult<Boolean> userResult = userCenterService.modifyUserPassword(uid, newPasswordo, SourceEnum.UNKNOWN.getValue());
		if (userResult == null) {
			LogUtil.error(log,
					"用户中心modifyUserPassword接口异常:param:[uid:{}],result:[{}]",
					uid, userResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		if (!userResult.isSucceed()) {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}

		if (!userResult.getResult()) {
			throw new RRException(ApiErrorMsgEnums.UPDATE_PWD_ERROR.getDesc(),
					ApiErrorMsgEnums.UPDATE_PWD_ERROR.getErrCode());
		}

	}

	public UserDubbo getUserDubbo(String uid) {
		if (StringUtils.isNotEmpty(uid)) {
			UserResult<UserDubbo> userResult = userCenterService.findUser(uid);
			if (userResult.isSucceed() && userResult.getResult() != null) {
				return userResult.getResult();
			} else {
				throw new RRException(userResult.getRetMsg(), userResult.getRetCode());
			}
		}
		return null;
	}

	private void saveUserRelation(String uid, String mobile) {
		RequestContext requestContext = RequestContext.get();
		final String appid = requestContext.getAppid();
		final String mchid = requestContext.getMchid();
		ExecutorsFactory.bulid().execute(new Runnable() {
			@Override
			public void run() {
				// 保存注册关系
				openapiMchUserRelationService.saveRelation(uid, mobile, appid,
						mchid);
			}
		});
	}

	@Override
	public List<OrderDetailVO> orderList(String uid) {
		// 默认查询一个月的订单
		List<OrderVo> orderList = findMemberOrderByDate(uid, 1);

		List<OrderDetailVO> listvo = Lists.newArrayList();
		if (orderList.size() == 0) {
			return listvo;
		}
		orderList.forEach(order -> {
			OrderDetailVO orderDetailVO = new OrderDetailVO().transDubboVO2ApiVO(order);
			listvo.add(orderDetailVO);
		});
		return listvo;
	}

	@Override
	public UserDubbo login(UserOauthRequest userLoginRequest) {
		Integer type = userLoginRequest.getType();
		String mobile = userLoginRequest.getMobile();
		String password = userLoginRequest.getPassword();
		String appid = userLoginRequest.getAppid();
		int source = SourceEnum.UNKNOWN.getValue();

		if (StringUtils.isNotBlank(appid)) {
			OpenapiMchAppSourceCodeEntity entity = openapiMchAppSourceCodeService.getEntityByAppCode(appid);
			source = entity!=null?entity.getUcCode():SourceEnum.UNKNOWN.getValue();
		}

		Map<String, Object> userMap = null;
		if (type == null || type == 1) { // 密码登录
			UserLoginParameter user = new UserLoginParameter();
			user.setMobile(mobile);
			user.setPassword(password);
			user.setSource(source);
			try {
				userMap = userCenterService.login(user);
			} catch (Exception e) {
				LogUtil.error(log, e, "用户服务异常");
			}
		} else if (type == 2) {// 验证码登录
			Boolean isExist = isExistMobile(mobile);
			if (!isExist) {
				UserRequest userRequestVO = new UserRequest();
				userRequestVO.setMobile(mobile);
				userRequestVO.setAppid(userLoginRequest.getAppid());
				addUser(userRequestVO);
			}
			UserLoginNoPasswordParameter user = new UserLoginNoPasswordParameter();
			user.setMobile(mobile);
			user.setCode(password);
			user.setSource(source);
			try {
				userMap = userCenterService.loginNoPassword(user);
			} catch (Exception e) {
				LogUtil.error(log, e, "用户服务异常");
			}
		}

		if (userMap == null || userMap.get("userResult") == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		UserResult userResult = (UserResult) userMap.get("userResult");
		if (!userResult.isSucceed()) {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}

		UserDubbo userDubbo = (UserDubbo) userResult.getResult();
		if (userDubbo == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		return userDubbo;
	}

	@Override
	public List<UserResponse> getInfoBatch(List<String> uids) {
		//参数校验
		if (CollectionUtils.isEmpty(uids)) {
			throw new RRException("用户id为空",
					ApiErrorMsgEnums.MISSING_PARAMETER.getErrCode());
		}
		UserResult<List<UserDubbo>> userResult = userCenterService.searchUsers(uids);
		if (userResult == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		// 结果判断赋值
		if (userResult.isSucceed()) {
			return packUserInfo(userResult.getResult());
		} else {
			throw new RRException(userResult.getRetMsg(),
					userResult.getRetCode());
		}
	}

	@Override
	public UserAuthResponse getAuthInfo4Encrypt(UserRequest userRequestVO) {
		String uid = userRequestVO.getUid();

		//查询用户实名的银行卡及身份证信息
		UserResult<UserRealNameCertificationDubbo> certificationInfoResult = userCenterRealNameAuthenticationService
				.findCertificationInfo(Long.valueOf(uid));
		LogUtil.info(log, "userCenterRealNameAuthenticationService.findCertificationInfo(uid)[result#{}]",
				JSON.toJSONString(certificationInfoResult));

		//返回值
		UserAuthResponse userAuthResponse = new UserAuthResponse();

		if (certificationInfoResult == null) {
			userAuthResponse.setAuthStatus(CommonEnums.AUTH_STATUS_NO.getValue());
			return userAuthResponse;
		}

		if (!certificationInfoResult.isSucceed()) {
			throw new RRException(certificationInfoResult.getRetMsg(), certificationInfoResult.getRetCode());
		}

		UserRealNameCertificationDubbo result = certificationInfoResult.getResult();
		if (result == null) {
			userAuthResponse.setAuthStatus(CommonEnums.AUTH_STATUS_NO.getValue());
			return userAuthResponse;
		}

		String bankCardNo = result.getBankCardNo();
		String idNo = result.getIdNo();

		//加密key:appid前16位
		String key = userRequestVO.getAppid().substring(0, 16);
		userAuthResponse.setAuthStatus(CommonEnums.AUTH_STATUS_YES.getValue());
		userAuthResponse.setIdCard(AESUtil.encrypt2Base64(idNo, key));
		userAuthResponse.setBankNo(AESUtil.encrypt2Base64(bankCardNo, key));
		userAuthResponse.setRealName(result.getRealName());
		userAuthResponse.setUid(uid);
		return userAuthResponse;
	}

	/*@Override
	public UserResponse getUserCoinBalance(String uid) {

		CoinResult<YestaeCoinDto> yestaeCoinDtoCoinResult = coinDubboService.readYestaeCoin(uid);
		if (yestaeCoinDtoCoinResult == null) {
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		if (!yestaeCoinDtoCoinResult.isSucceed()) {
			throw new RRException(yestaeCoinDtoCoinResult.getRetMsg(), yestaeCoinDtoCoinResult.getRetCode());
		}

		UserResponse response = new UserResponse();
		BigDecimal coinBalance = BigDecimal.ZERO;
		YestaeCoinDto yestaeCoinDto = yestaeCoinDtoCoinResult.getResult();
		if(yestaeCoinDto!=null){
			coinBalance = yestaeCoinDto.getAmount();
		}
		response.setCoinBalance(coinBalance);
		response.setUid(uid);

		return response;
	}*/

	/*@Override
	public UserCoinReponse addUserCoin(UserCoinRequest userCoinRequest) {

		String uid = userCoinRequest.getUid();
		UserDubbo userDubbo = getUserDubbo(uid);
		if(userDubbo == null){
			throw new RRException(ApiErrorMsgEnums.USER_NOT_EXSIT.getDesc(),
					ApiErrorMsgEnums.USER_NOT_EXSIT.getErrCode());
		}
		//会员等级
		Integer type = userDubbo.getType();

		//是否开通尊享
		Boolean isPlus = isVasPlus(uid);
		//应用与用户中心、受益劵映射关系
		OpenapiMchAppSourceCodeEntity entity = openapiMchAppSourceCodeService.getEntityByAppCode(userCoinRequest.getAppid());

		CoinDetailVo coinDetailVo = new CoinDetailVo();

		coinDetailVo.setAmount(new BigDecimal(userCoinRequest.getTotalFee())
				.divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));
		coinDetailVo.setIsVas(isPlus ? 1: 0);
		coinDetailVo.setOrderNo(userCoinRequest.getOutTradeNo());
		coinDetailVo.setProduct(entity!=null?Integer.valueOf(entity.getProductCode()):1);
		coinDetailVo.setRemark(userCoinRequest.getBody());
		coinDetailVo.setSource(Integer.valueOf(com.yestae.coin.api.enums.SourceEnum.MINIAPP.getValue()));
		coinDetailVo.setUserId(Long.valueOf(uid));
		coinDetailVo.setUserType(type);
		coinDetailVo.setBussinessType("1");
		coinDetailVo.setType(YestaeCoinTypeEnum.DEAL_DONE.getValue());

		CoinResult<YestaeCoinDto> yestaeCoinDtoCoinResult = coinDubboService.produce(coinDetailVo);
		if(yestaeCoinDtoCoinResult == null){
			LogUtil.error(
					log,
					"coinDubboService.produce()异常:param:[coinDetailVo:{}]",
					coinDetailVo);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if(!yestaeCoinDtoCoinResult.isSucceed()){
			throw new RRException(yestaeCoinDtoCoinResult.getRetMsg(),yestaeCoinDtoCoinResult.getRetCode());
		}
		YestaeCoinDto yestaeCoinDto = yestaeCoinDtoCoinResult.getResult();
		if(yestaeCoinDto==null){
			LogUtil.error(
					log,
					"coinDubboService.produce()异常:param:[coinDetailVo:{}],result:[{}]",
					coinDetailVo, yestaeCoinDtoCoinResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		UserCoinReponse reponse = new UserCoinReponse();
		reponse.setTradeAmount(yestaeCoinDto.getChangeAmount());
		reponse.setCoinBalance(yestaeCoinDto.getAmount());
		reponse.setOutTradeNo(userCoinRequest.getOutTradeNo());
		reponse.setTradeId(yestaeCoinDto.getFlowNo());
		reponse.setUid(uid);
		return reponse;

	}*/
	@Override
	public Boolean isVasPlus(String uid) {
		if(StringUtils.isBlank(uid)){
			return Boolean.FALSE;
		}
		//查询用户开通服务
		UserResult<List<String>> userResult = userCenterAddedService.searchAddedServices(uid);
		if(userResult != null && userResult.isSucceed()){
			List<String> userVasIdList = userResult.getResult();
			if(org.springframework.util.CollectionUtils.isEmpty(userVasIdList)){
				return Boolean.FALSE;
			}else{
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	/*@Override
	public UserCoinReponse recoverCoin(UserCoinRequest userCoinRequest) {
		String uid = userCoinRequest.getUid();
		String outTradeNo = userCoinRequest.getOutTradeNo();

		//应用与用户中心、受益劵映射关系
		OpenapiMchAppSourceCodeEntity entity = openapiMchAppSourceCodeService.getEntityByAppCode(userCoinRequest.getAppid());
		String product = entity!=null?entity.getProductCode():"1";

		CoinResult<YestaeCoinDto> yestaeCoinDtoCoinResult = coinDubboService.refund(uid,outTradeNo,product);
		if(yestaeCoinDtoCoinResult == null){
			LogUtil.error(
					log,
					"coinDubboService.refund()异常:param:[uid:{},outTradeNo:{},product:{}]",
					uid,outTradeNo,product);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}
		if(!yestaeCoinDtoCoinResult.isSucceed()){
			throw new RRException(yestaeCoinDtoCoinResult.getRetMsg(),yestaeCoinDtoCoinResult.getRetCode());
		}
		YestaeCoinDto yestaeCoinDto = yestaeCoinDtoCoinResult.getResult();

		if(yestaeCoinDto == null){
			LogUtil.error(
					log,
					"coinDubboService.refund()异常:param:[uid:{},outTradeNo:{},product:{}],result:[{}]",
					uid,outTradeNo,product, yestaeCoinDtoCoinResult);
			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
		}

		UserCoinReponse reponse = new UserCoinReponse();
		reponse.setTradeAmount(yestaeCoinDto.getChangeAmount());
		reponse.setCoinBalance(yestaeCoinDto.getAmount());
		reponse.setOutTradeNo(outTradeNo);
		reponse.setTradeId(yestaeCoinDto.getFlowNo());
		reponse.setUid(uid);
		return reponse;

	}*/


		/**
         * 组装会员信息
         *
         * @param
         * @return
         * @throws
         */
	private List<UserResponse> packUserInfo(List<UserDubbo> userDubboList) {
		List<UserResponse> responseList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(userDubboList)) {
			return responseList;
		}
		userDubboList.forEach(item -> {
			UserResponse userInfoResponceDTO = new UserResponse();
			userInfoResponceDTO.setUid(item.getUserId());
			userInfoResponceDTO.setName(item.getName());
			userInfoResponceDTO.setMobile(item.getMobile());
			userInfoResponceDTO.setGender(item.getGender());
			userInfoResponceDTO.setBirthday(item.getBirthday() == null ? null
					: DateUtils.formatDateByLong(item.getBirthday(),
					DateUtils.YYYY_MM_DD));
			userInfoResponceDTO.setUserType(item.getType());
			userInfoResponceDTO.setUpgradeTime(item.getUpgradeTime());
			userInfoResponceDTO.setRegTime(item.getCreateTime());
            userInfoResponceDTO.setSource(item.getSource());
			userInfoResponceDTO.setUserImageDubbo(item.getUserImageDubbo());
			responseList.add(userInfoResponceDTO);
		});
		return responseList;
	}

	@Override
	public List<ServiceDetail> searchAddedAllServices(String uid){
		List<ServiceDetail> serviceDetailList = Lists.newArrayList();
		if(StringUtils.isBlank(uid)){
			return serviceDetailList;
		}
		UserResult<List<AddedServiceResultDubbo>>  result = userCenterAddedService.searchAddedAllServices(uid);
		if(result!=null && result.isSucceed()){
			List<AddedServiceResultDubbo> addedServiceList = result.getResult();
			Optional.ofNullable(addedServiceList)
					.ifPresent(
							new Consumer<List<AddedServiceResultDubbo>>() {
								@Override
								public void accept(List<AddedServiceResultDubbo> serviceList) {
									addedServiceList
											.forEach(addedService -> {
												ServiceDetail serviceDetail = new ServiceDetail();
												serviceDetail.setId(addedService.getAddedServiceId());
												serviceDetail.setName(addedService.getVasName());
												serviceDetail.setStartTime(addedService.getBeginTime());
												serviceDetail.setEndTime(addedService.getEndTime());
												serviceDetail.setStatus(addedService.getStatus());
												serviceDetailList.add(serviceDetail);
											});
								}
							});
		}

		return serviceDetailList;
	}

	@Override
	public List<ServiceDetail> searchAddedServices(String uid, String addedServiceId) {
        List<ServiceDetail> serviceDetailList = Lists.newArrayList();
		if(StringUtils.isBlank(uid) || StringUtils.isBlank(addedServiceId)){
			return serviceDetailList;
		}
		UserResult<AddedServiceResultDubbo> result = userCenterAddedService.findAddedServiceStatus(uid, addedServiceId);
		if(result!=null && result.isSucceed()){
			AddedServiceResultDubbo resultDubbo = result.getResult();
			Optional.ofNullable(resultDubbo)
					.ifPresent(
							(AddedServiceResultDubbo addedServiceResultDubbo) -> {
                                ServiceDetail serviceDetail = new ServiceDetail();
								serviceDetail.setId(addedServiceResultDubbo.getAddedServiceId());
								serviceDetail.setName(addedServiceResultDubbo.getVasName());
								serviceDetail.setStartTime(addedServiceResultDubbo.getBeginTime());
								serviceDetail.setEndTime(addedServiceResultDubbo.getEndTime());
								serviceDetail.setStatus(addedServiceResultDubbo.getStatus());
                                serviceDetail.setIsOpenOrNo(addedServiceResultDubbo.getIsOpenOrNo());
                                serviceDetail.setIsOpenForTheFirse(addedServiceResultDubbo.getIsOpenForTheFirse());
                                serviceDetailList.add(serviceDetail);
							});
		}
		return serviceDetailList;
	}

	@Override
	public Boolean checkIsLoginByUidSid(String userId, String sid, Integer platform) {
		if (StringUtils.isBlank(userId) || StringUtils.isBlank(sid)) {
			return false;
		}
		boolean flag = false;
		UserResult<Boolean> result = userCenterService.validateLoginStatus(userId,
				sid, platform);
		if (result != null && result.isSucceed()) {
			Boolean checkResult = result.getResult();
			if (checkResult) {
				flag = true;
			}
		}
		return flag;
	}
}
