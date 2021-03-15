package com.yestae.modules.box.vo;

import lombok.Data;

/**
 * 
 * 简易的宝盒卡片信息对象
 *
 * @Package com.yestae.box.dto
 * @ClassName SimpleCardInfo
 * @author young
 * @date 2018年4月26日 下午3:43:25
 *
 */
@Data
public class CardInfo {

	/**
	 * 卡号
	 */
	private String cardNumber;
	/**
	 * 有效期时间
	 */
	private Integer useExpiryTime;
	/**
	 * 货号
	 */
	private String productNo;

}
