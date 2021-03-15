package com.yestae.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.entity.OpenapiMchAppEntity;

/**
 * 商户应用信息 
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
public interface OpenapiMchAppService extends IService<OpenapiMchAppEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 修改商户应用信息 
	 */
	void update(OpenapiMchAppEntity openapiMchApp);

	OpenapiMchAppEntity selectByAppCode(String appCode);
}

