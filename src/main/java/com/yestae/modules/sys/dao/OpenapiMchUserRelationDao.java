package com.yestae.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sys.entity.OpenapiMchUserRelationEntity;

/**
 * api接口注册关系表
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-18 09:27:44
 */
@Mapper
public interface OpenapiMchUserRelationDao extends BaseMapper<OpenapiMchUserRelationEntity> {
	
}
