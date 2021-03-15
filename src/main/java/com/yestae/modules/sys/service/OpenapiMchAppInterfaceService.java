package com.yestae.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.entity.OpenapiMchAppInterfaceEntity;

/**
 * 商户应用开通接口信息 
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
public interface OpenapiMchAppInterfaceService extends IService<OpenapiMchAppInterfaceEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 修改商户应用开通接口信息 
	 */
	void update(OpenapiMchAppInterfaceEntity openapiMchAppInterface);
}

