package com.yestae.modules.sys.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商户信息 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_mch")
public class OpenapiMchEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 商户名称
	 */
	@NotBlank(message="接口名不能为空")
	private String mchName;
	/**
	 * 商户编号 自动生成
	 */
	private String mchCode;
	/**
	 * 商户地址
	 */
	@NotBlank(message="接口名不能为空")
	private String mchAddress;
	/**
	 * 联系人
	 */
	@NotBlank(message="接口名不能为空")
	private String linkMan;
	/**
	 * 联系电话
	 */
	@NotBlank(message="接口名不能为空")
	private String linkPhone;
	/**
	 * 联系人邮箱
	 */
	@NotBlank(message="接口名不能为空")
	private String linkEmail;
	/**
	 * 商户状态
	 */
	@NotBlank(message="接口名不能为空")
	private Integer mchState;
	
	/**
	 * 应用编号，用于授权验证时使用
	 */
	@TableField(exist = false)
	private String appCode;
}
