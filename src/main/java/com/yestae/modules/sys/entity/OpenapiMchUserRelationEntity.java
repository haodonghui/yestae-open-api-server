package com.yestae.modules.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * api接口注册关系表
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-18 09:27:44
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_mch_user_relation")
public class OpenapiMchUserRelationEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 商户编号
	 */
	private String mchCode;
	/**
	 * 商户名称
	 */
	private String mchName;
	/**
	 * 应用编号
	 */
	private String appCode;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 用户中心用户id
	 */
	private String userId;
	/**
	 * 用户中心用户手机号
	 */
	private String mobile;

}
