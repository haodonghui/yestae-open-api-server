package com.yestae.modules.user.vo.coin;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yestae.common.validator.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 受益劵
 */
@Data
public class UserCoinReponse implements Serializable{

	//用户id
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String uid;

	//此次交易获取的益币数量
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal tradeAmount;

	//商户订单号
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String outTradeNo;

	//受益劵余额
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private BigDecimal coinBalance;

	//交易流水id
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String tradeId;

	
}
