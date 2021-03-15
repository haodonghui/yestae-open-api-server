package com.yestae.modules.sys.service.impl;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yestae.common.utils.PageUtils;
import com.yestae.datasource.annotation.DataSource;
import com.yestae.modules.sys.dao.OpenapiMchAppInterfaceDao;
import com.yestae.modules.sys.entity.OpenapiMchAppInterfaceEntity;
import com.yestae.modules.sys.service.OpenapiMchAppInterfaceService;

/**
 * 商户应用开通接口信息 
 * 根据数据源手动配置 @DataSource("slave1")并继承对应的ServiceImpl* 比如数据源1：继承ServiceImplSava1  据源2：继承ServiceImplSava2
 * 默认数据源无需配置
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Service("openapiMchAppInterfaceService")
//@DataSource("slave1")
public class OpenapiMchAppInterfaceServiceImpl extends ServiceImpl<OpenapiMchAppInterfaceDao, OpenapiMchAppInterfaceEntity> implements OpenapiMchAppInterfaceService {
//	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(OpenapiMchAppInterfaceEntity openapiMchAppInterface) {
		// TODO Auto-generated method stub
		
	}

	

}