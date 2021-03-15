package com.yestae.modules.sys.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yestae.modules.sys.entity.OpenapiInterfaceLogEntity;

/**
 * 接口日志 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Mapper
public interface OpenapiInterfaceLogDao extends BaseMapper<OpenapiInterfaceLogEntity> {
	
}
