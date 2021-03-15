package com.yestae.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.entity.OpenapiMchAppSourceCodeEntity;

import java.util.Map;

/**
 * 商户应用与用户中心来源映射
 *
 * @date 2020-02-20 10:52:51
 */
public interface OpenapiMchAppSourceCodeService extends IService<OpenapiMchAppSourceCodeEntity> {

    OpenapiMchAppSourceCodeEntity getEntityByAppCode(String appCode);
}

