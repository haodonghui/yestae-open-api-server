package com.yestae.modules.box.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 
 * 宝盒服务兑换业务请求对象
 *
 * @Package com.yestae.box.dto
 * @ClassName BindRequest
 * @author young
 * @date 2018年4月26日 下午3:43:15
 *
 */
@Data
public class CardExchangeRequest{
	/**
	 * 兑换码
	 */
	@NotBlank(message="兑换码不能为空")
	private String cardCode;
	/**
	 * 用户id
	 */
	private String memberId;
	/**
	 * 用户手机号
	 */
	private String memberMobile;
	/**
	 * 操作用户ip
	 */
	private String ip;
	/**
	 * 浏览器ua
	 */
	private String ua;
	/**
	 * 此次调用的id标识
	 */
	private String transationId;

}
