package com.yestae.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口日志 
 * 
 * @author daniel
 * @email leihehong@yestae.com
 * @date 2019-05-08 09:46:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("openapi_interface_log")
public class OpenapiInterfaceLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(value="id", type=IdType.UUID)
	private String id;
	/**
	 * 接口名称
	 */
	private String interfaceName;
	/**
	 * 接口地址
	 */
	private String interfaceUrl;
	/**
	 * 商户应用编号
	 */
	private String appCode;
	/**
	 * 商户应用名称
	 */
	private String appName;
	/**
	 * 请求时间
	 */
	private Date reqTime;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 响应结果
	 */
	private String result;
	/**
	 * 调用者ip
	 */
	private String ip;

}
