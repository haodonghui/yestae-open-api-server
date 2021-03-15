package com.yestae.modules.sys.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yestae.common.utils.PageUtils;
import com.yestae.modules.sys.dao.OpenapiMchUserRelationDao;
import com.yestae.modules.sys.entity.OpenapiMchAppEntity;
import com.yestae.modules.sys.entity.OpenapiMchEntity;
import com.yestae.modules.sys.entity.OpenapiMchUserRelationEntity;
import com.yestae.modules.sys.service.OpenapiMchAppService;
import com.yestae.modules.sys.service.OpenapiMchService;
import com.yestae.modules.sys.service.OpenapiMchUserRelationService;

/**
 * api接口注册关系表
 * 根据数据源手动配置 @DataSource("slave1")并继承对应的ServiceImpl* 比如数据源1：继承ServiceImplSava1  据源2：继承ServiceImplSava2
 * 默认数据源无需配置
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-18 09:27:44
 */
@Service("openapiMchUserRelationService")
//@DataSource("slave1")
public class OpenapiMchUserRelationServiceImpl extends ServiceImpl<OpenapiMchUserRelationDao, OpenapiMchUserRelationEntity> implements OpenapiMchUserRelationService {
//	private Logger logger = LoggerFactory.getLogger(getClass());
	//商户service
	@Autowired
	private OpenapiMchService openapiMchService;
	//应用service
	@Autowired
	private OpenapiMchAppService openapiMchAppService;
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		return null;
	}

	@Override
	public void update(OpenapiMchUserRelationEntity openapiMchUserRelation) {
		
	}

	@Override
	public void saveRelation(String userId, String mobile, String appCode, String mchCode) {
		//通过APPCode和mchCode获取应用和商户信息
		OpenapiMchUserRelationEntity relation = new OpenapiMchUserRelationEntity();
		OpenapiMchEntity mch = openapiMchService.getOne(new QueryWrapper<OpenapiMchEntity>().eq("mch_code", mchCode));
		Optional.ofNullable(mch).ifPresent(m -> {
			String machName = m.getMchName();
			relation.setMchName(machName);
		});
		OpenapiMchAppEntity mchApp = openapiMchAppService.getOne(new QueryWrapper<OpenapiMchAppEntity>().eq("app_code", appCode));
		Optional.ofNullable(mchApp).ifPresent(app -> {
			String appName = app.getAppName();
			relation.setAppName(appName);
		});
		relation.setAppCode(appCode);
		relation.setMchCode(mchCode);
		relation.setMobile(mobile);
		relation.setUserId(userId);
		relation.setCreatedBy(relation.getAppName());
		relation.setCreatedTime(new Date());
		this.save(relation);
	}

}