package com.yestae.modules.user.service;

import com.yestae.modules.user.vo.ServiceDetail;
import com.yestae.modules.user.vo.UserOauthRequest;
import com.yestae.modules.user.vo.UserRequest;
import com.yestae.modules.user.vo.UserResponse;
import com.yestae.modules.user.vo.auth.UserAuthResponse;
import com.yestae.modules.user.vo.coin.UserCoinReponse;
import com.yestae.modules.user.vo.coin.UserCoinRequest;
import com.yestae.modules.user.vo.order.OrderDetailVO;
import com.yestae.user.center.dubbo.entity.AddedServiceResultDubbo;
import com.yestae.user.center.dubbo.entity.UserDubbo;
import com.yestae.user.vas.dubbo.vo.VasEquityVoDubbo;

import java.util.List;

public interface UserApiService {
	/**
	 * 
	 * 更新手机号
	 *
	 * @param uid
	 *            用户id
	 * @param mobile
	 *            新手机号
	 * @return boolean
	 */
	boolean updateMobile(String uid, String mobile);

	/**
	 * 
	 * 新增用户信息
	 *
	 * @param userRequestVO
	 * @return UserInfoResponceDTO
	 * @throws
	 */
	UserResponse addUser(UserRequest userRequestVO);

	/**
	 * 
	 * 获取用户基本信息
	 *
	 * @param uid
	 * @param mobile
	 * @return
	 * @throws
	 */
	UserResponse getUserInfo(String uid, String mobile);

	/**
	 * 
	 * 查询用户新手机号是否实名
	 *
	 * @param uid
	 * @param mobile
	 * @return
	 * @throws
	 */
	boolean findNewMobileCertificationStatus(String uid, String mobile);

	/**
	 * 
	 * 获取用户在益友会实名状态
	 *
	 * @param uid
	 * @return
	 * @throws
	 */
	boolean findUserCertificationStatus(String uid);

	/**
	 * 
	 * 判断手机号是否存在
	 *
	 * @param mobile
	 * @return
	 * @throws
	 */
	boolean isExistMobile(String mobile);

	/**
	 * 
	 * 获取用户开通的增值服务
	 *
	 * @param uid
	 * @return
	 * @throws
	 */
	List<String> findUserAdd(String uid);

	/**
	 * 
	 * 查询用户开通的增值服务基础信息，有效期，增值服务名称
	 *
	 * @param uid
	 * @param addIdList
	 * @return
	 * @throws
	 */
	UserResponse getUserAddInfo(String uid, List<String> addIdList);

	/**
	 * 
	 * 通过增值服务id获取后台维护的增值服务信息
	 *
	 * @param vasIds
	 * @return
	 * @throws
	 */
	List<VasEquityVoDubbo> findVasDubbo(List<String> vasIds);

	/**
	 * 
	 * 查询用户中心用户开通增值服务情况
	 *
	 * @param uid
	 * @param vasId
	 * @return
	 * @throws
	 */
	AddedServiceResultDubbo findAddedServiceStatus(String uid, String vasId);

	/**
	 * 
	 * 更新用户密码
	 *
	 * @throws
	 */
	void updatePassword(UserRequest userRequestVO);

	/**
	 * 
	 * 查询用户历史订单
	 *
	 * @param uid
	 * @return
	 * @throws
	 */
	List<OrderDetailVO> orderList(String uid);
	
	/**用户登录
	 * 
	 * @param userLoginRequest
	 * @return
	 */
	UserDubbo login(UserOauthRequest userLoginRequest);

	/**
	 * 
	 * 批量获取用户信息
	 *
	 * @param uids
	 * @return
	 * @throws
	 */
	List<UserResponse> getInfoBatch(List<String> uids);

	/**
	 * 	获取用户实名信息
	 * 	获取到的信息身份证和银行卡通过aes加密并base64编码。
	 * @param userRequestVO
	 * @return
	 */
    UserAuthResponse getAuthInfo4Encrypt(UserRequest userRequestVO);

	/**
	 *
	 * 通过用户id获取受益劵余额
	 *
	 * @param uid 用户id
	 * @return UserResponse
	 * @throws
	 */
//	UserResponse getUserCoinBalance(String uid);

	/**
	 *
	 * 增加受益劵
	 *
	 * @param  userCoinRequest
	 * @return UserCoinReponse
	 * @throws
	 */
//	UserCoinReponse addUserCoin(UserCoinRequest userCoinRequest);

	/**
	 *
	 * 用户是否开通尊享
	 *
	 * @param  uid
	 * @return  Boolean
	 * @throws
	 */
	Boolean isVasPlus(String uid);

	/**
	 *
	 * 收回受益劵
	 *
	 * @param  userCoinRequest
	 * @return UserCoinReponse
	 * @throws
	 */
//	UserCoinReponse recoverCoin(UserCoinRequest userCoinRequest);

	/**
	 * 查找用户开通尊享记录
	 * @param uid
	 * @return
	 */
	List<ServiceDetail> searchAddedAllServices(String uid);

	/**
	 * 查找用户开通尊享记录
	 * @param uid 用户userId
	 * @param addedServiceId 增值服务Id
	 * @return com.yestae.modules.user.vo.ServiceDetail
	 */
	List<ServiceDetail> searchAddedServices(String uid, String addedServiceId);

	/**
	 * 校验用户是否登录
	 *
	 * @param userId userId
	 * @param sid	token
	 * @return
	 */
	Boolean checkIsLoginByUidSid(String userId, String sid, Integer platform);


}
