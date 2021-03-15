package com.yestae.modules.user.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yestae.order.api.OrderGoodsVo;

import lombok.Data;

@Data
public class OrderGoodsBaseVO implements Serializable {

	/** 
	 */
	private static final long serialVersionUID = 338483424413487049L;
	/**
	 * 商品编号
	 */
	@JsonInclude(Include.NON_NULL)
	private String goodsNo;
	/**
	 * 商品名称
	 */
	@JsonInclude(Include.NON_NULL)
	private String goodsName;
	/**
	 * 商品销售价
	 */
	@JsonInclude(Include.NON_NULL)
	private BigDecimal salePrice;
	
	/**
	 * 商品数量单位
	 */
	@JsonInclude(Include.NON_NULL)
	private String goodsUnit;
	/**
	 * 商品数量
	 */
	@JsonInclude(Include.NON_NULL)
	private Float goodsNums;
	
	/**
	 * 商品的规格属性
	 */
	@JsonInclude(Include.NON_NULL)
	private String goodsSpec;

	
	/**
	 * 
	 * 数据转换
	 *
	 * @param orderGoodsVo
	 * @throws
	 */
	public void transDubboVO2ApiVO(OrderGoodsVo orderGoodsVo){
		BeanUtils.copyProperties(orderGoodsVo, this);
	}
}
