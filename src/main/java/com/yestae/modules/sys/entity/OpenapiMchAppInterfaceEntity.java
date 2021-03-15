package com.yestae.modules.sys.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户应用开通接口信息 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_mch_app_interface")
public class OpenapiMchAppInterfaceEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 商户应用编号
	 */
	@NotBlank(message="接口名不能为空")
	private String appCode;
	/**
	 * 接口权限编号
	 */
	@NotBlank(message="接口名不能为空")
	private String permCode;
	/**
	 * 商户编号
	 */
	private String mchCode;
	/**
	 * 是否需要签名
	 */
	@NotBlank(message="接口名不能为空")
	private Integer isAuth;

}
