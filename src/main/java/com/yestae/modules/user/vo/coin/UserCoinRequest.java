package com.yestae.modules.user.vo.coin;

import com.alibaba.fastjson.annotation.JSONField;
import com.yestae.common.validator.group.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 受益劵
 */
@Data
public class UserCoinRequest implements Serializable{


	/**
	 * 用户id
	 */
	@NotBlank(message="用户id不能为空", groups={AddGroup.class,UpdateGroup.class})
	private String uid;

	/**
	 * 商户订单号
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母且在同一个商户号下唯一
	 */
	@NotBlank(message="商户订单号不能为空", groups={AddGroup.class,UpdateGroup.class})
	@Pattern(message="商户订单号格式不正确", regexp="^[a-z0-9A-Z]{1,32}$", groups={AddGroup.class,UpdateGroup.class})
	private String outTradeNo;

	/**
	 * 商品描述
	 */
	private String body;
	/**
	 * 订单总金额，单位为分
	 */
	@NotNull(message="订单总金额不能为空", groups={AddGroup.class})
//	@Pattern(message="订单总金额格式不正确", regexp="^[0-9]$", groups={AddGroup.class})
	private Integer totalFee;

	/**
	 * 	商户应用id
	 */
	private String appid;

	/**
	 * 	商户id
	 */
	private String mchid;
	
}
