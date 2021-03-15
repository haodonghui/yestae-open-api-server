package com.yestae.modules.sys.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户应用信息 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_mch_app")
public class OpenapiMchAppEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 应用名
	 */
	@NotBlank(message="接口名不能为空")
	private String appName;
	/**
	 * 应用编号
	 */
	@NotBlank(message="接口名不能为空")
	private String appCode;
	/**
	 * 应用签名秘钥
	 */
	private String appSecret;
	/**
	 * 商户编号
	 */
	@NotBlank(message="接口名不能为空")
	private String mchCode;
	/**
	 * 应用类型
	 */
	@NotBlank(message="接口名不能为空")
	private String appType;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 应用状态
	 */
	@NotBlank(message="接口名不能为空")
	private Integer appState;
	
}
