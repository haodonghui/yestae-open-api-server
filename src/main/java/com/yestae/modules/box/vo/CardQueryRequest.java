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
public class CardQueryRequest {
	/**
	 * 兑换码
	 */
	@NotBlank(message="兑换码不能为空")
	private String cardCode;

}
