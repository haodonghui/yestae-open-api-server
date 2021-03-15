package com.yestae.modules.sys.entity;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * api接口信息 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_interface")
public class OpenapiInterfaceEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 接口名称
	 */
	@NotBlank(message="接口名不能为空")
	private String name;
	/**
	 * 接口地址
	 */
	@NotBlank(message="接口请求url不能为空")
	private String url;
	/**
	 * 权限编号
	 */
	private String permCode;

}
