package com.yestae.modules.sys.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yestae.datasource.annotation.DataSource;
import com.yestae.modules.sys.dao.OpenapiMchDao;
import com.yestae.modules.sys.entity.OpenapiMchEntity;
import com.yestae.modules.sys.service.OpenapiMchService;

/**
 * 商户信息 
 * 根据数据源手动配置 @DataSource("slave1")并继承对应的ServiceImpl* 比如数据源1：继承ServiceImplSava1  据源2：继承ServiceImplSava2
 * 默认数据源无需配置
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Service("openapiMchService")
//@DataSource("slave1")
public class OpenapiMchServiceImpl extends ServiceImpl<OpenapiMchDao, OpenapiMchEntity> implements OpenapiMchService {


}