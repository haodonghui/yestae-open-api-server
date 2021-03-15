package com.yestae.modules.sys.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.entity.OpenapiInterfaceLogEntity;

/**
 * 接口日志 
 *
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
public interface OpenapiInterfaceLogService extends IService<OpenapiInterfaceLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

	/**
	 * 修改接口日志 
	 */
	void update(OpenapiInterfaceLogEntity openapiInterfaceLog);
}

