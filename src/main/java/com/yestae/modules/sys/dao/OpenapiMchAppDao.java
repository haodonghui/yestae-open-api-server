package com.yestae.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sys.entity.OpenapiMchAppEntity;

/**
 * 商户应用信息 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Mapper
public interface OpenapiMchAppDao extends BaseMapper<OpenapiMchAppEntity> {
	
}
