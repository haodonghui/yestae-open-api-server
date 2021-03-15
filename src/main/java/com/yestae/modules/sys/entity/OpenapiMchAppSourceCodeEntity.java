package com.yestae.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户应用与用户中心来源映射
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2020-02-20 10:52:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_mch_app_source_code")
public class OpenapiMchAppSourceCodeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type=IdType.INPUT)
	private String id;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 应用编号
	 */
	private String appCode;
	/**
	 * 对应商城用户中心编号
	 */
	private Integer ucCode;

	/**
	 * 对应受益劵服务-产品编号
	 */
	private String productCode;

}
