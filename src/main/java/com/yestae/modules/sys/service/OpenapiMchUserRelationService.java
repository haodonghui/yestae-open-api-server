package com.yestae.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.entity.OpenapiMchUserRelationEntity;

/**
 * api接口注册关系表
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-18 09:27:44
 */
public interface OpenapiMchUserRelationService extends IService<OpenapiMchUserRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 修改api接口注册关系表
	 */
	void update(OpenapiMchUserRelationEntity openapiMchUserRelation);
	
	/**
	 * 
	 * 保护接口注册用户关系
	 *
	 * @param userId	用户id
	 * @param mobile	用户手机号
	 * @param appCode	应用编码
	 * @param mchCode	商户编码
	 * @throws
	 */
	void saveRelation(String userId, String mobile, String appCode, String mchCode);
}

