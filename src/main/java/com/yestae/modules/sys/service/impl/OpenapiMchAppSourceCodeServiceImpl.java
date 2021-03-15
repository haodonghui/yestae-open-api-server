package com.yestae.modules.sys.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.yestae.common.utils.RedisKeys;
import com.yestae.common.utils.RedisUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yestae.common.datasource.service.ServiceImpl;
import com.yestae.common.utils.PageUtils;

import com.yestae.modules.sys.dao.OpenapiMchAppSourceCodeDao;
import com.yestae.modules.sys.entity.OpenapiMchAppSourceCodeEntity;
import com.yestae.modules.sys.service.OpenapiMchAppSourceCodeService;

/**
 * 商户应用与用户中心来源映射
 * 根据数据源手动配置 @DataSource("slave1")并继承对应的ServiceImpl* 比如数据源1：继承ServiceImplSava1  据源2：继承ServiceImplSava2
 * 默认数据源无需配置
 * @date 2020-02-20 10:52:51
 */
@Service("openapiMchAppSourceCodeService")
//@DataSource("slave1")
public class OpenapiMchAppSourceCodeServiceImpl extends ServiceImpl<OpenapiMchAppSourceCodeDao, OpenapiMchAppSourceCodeEntity> implements OpenapiMchAppSourceCodeService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Override
	public OpenapiMchAppSourceCodeEntity getEntityByAppCode(String appCode) {

		OpenapiMchAppSourceCodeEntity entity = null;
		try{
			Object obj = redisTemplate.opsForHash().get(RedisKeys.MCH_APP_MAPPING_HASH,appCode);
			if(obj!=null){
				entity = JSONObject.parseObject(obj.toString(),OpenapiMchAppSourceCodeEntity.class);
			}
		}catch (Exception e){}
		if(entity == null ){
			QueryWrapper<OpenapiMchAppSourceCodeEntity> sourceCodequeryWrapper = new QueryWrapper<>();
			sourceCodequeryWrapper.eq("app_code", appCode);
			List<OpenapiMchAppSourceCodeEntity> appSourceList = super.list(sourceCodequeryWrapper);
			if (CollectionUtils.isNotEmpty(appSourceList)) {
				entity =  appSourceList.get(0);
			}
		}

		return entity;
	}
}