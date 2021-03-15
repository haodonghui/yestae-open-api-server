package com.yestae.modules.user.vo.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.collect.Lists;
import com.yestae.order.api.OrderDeliveryVo;
import com.yestae.order.api.OrderGoodsVo;
import com.yestae.order.api.OrderVo;

@Data
public class OrderDetailVO implements Serializable {
	
	//@JsonInclude(Include.NON_NULL) 当注解下面的该字段为空时，不返回该字段
	
	/** 
	 */
	private static final long serialVersionUID = 7495464508001318075L;
	
    /**
     * 平台订单业务编号
     */
	@JsonInclude(Include.NON_NULL)
    private String orderNo;
    /**
     * 实际支付金额
     */
	@JsonInclude(Include.NON_NULL)
    private BigDecimal totalAmount;
    /**
     * 订单当前状态 1 待付款 2 待发货 3 已发货 4 退款/售后 5 退款/售后完成  6 交易完成
     */
	@JsonInclude(Include.NON_NULL)
    private String orderState;
    /**
     * 订单的商品列表
     */
	@JsonInclude(Include.NON_NULL)
    private List<OrderGoodsBaseVO> orderGoods;
    /**
     * 发货单单号，多个以逗号隔开
     */
	@JsonInclude(Include.NON_NULL)
    private String deliveryNo;
    /**
     * 下单时间
     */
	@JsonInclude(Include.NON_NULL)
    private Long orderTime;
    /**
     * 实际支付时间
     */
	@JsonInclude(Include.NON_NULL)
    private Long payTime;
    /**
     * 订单备注留言
     */
	@JsonInclude(Include.NON_NULL)
    private String remark;

	
    /**
     * 
     * 将dubbo服务的实体转成apivo
     *
     * @param orderVO
     * @throws
     */
	public OrderDetailVO transDubboVO2ApiVO(OrderVo orderVO){
		
		this.setOrderNo(orderVO.getOrderNo());
		//状态转换
		this.setOrderState(handlerOrderState(orderVO.getOrderState(), orderVO.getReceiveState()));
		//
		this.setRemark(orderVO.getRemark());
		this.setTotalAmount(orderVO.getPayAmount());
		this.setOrderTime(orderVO.getCreateTime());
		Optional.ofNullable(orderVO.getOrderPaymentVo()).ifPresent(orderPaymentVo -> {
			this.setPayTime(orderPaymentVo.getFinishPayTime());
		});
		
		// 处理商品信息
		List<OrderGoodsVo> orderGoodsVoList = orderVO
				.getOrderGoodsVoList();
		List<OrderGoodsBaseVO> orderGoodsBaseVOList = Lists
				.newArrayList();
		orderGoodsVoList.forEach(goods -> {
			OrderGoodsBaseVO goodsBaseVO = new OrderGoodsBaseVO();
			goodsBaseVO.transDubboVO2ApiVO(goods);
			orderGoodsBaseVOList.add(goodsBaseVO);
		});

		this.setOrderGoods(orderGoodsBaseVOList);

		// 处理发货单号
		List<OrderDeliveryVo> deliveryVoList = orderVO
				.getOrderDeliveryVoList();
		Optional.ofNullable(deliveryVoList).ifPresent(
				noNulldeliveryVoList -> {
					 StringBuilder sb = new StringBuilder();
					noNulldeliveryVoList.forEach(delivery -> {
						if(sb.length() > 0) {
			                sb.append(",");
			            }
			            sb.append(delivery
								.getOrderDeliveryNo());
						
					});
					this.setDeliveryNo(sb.toString());
				});
		
		return this;
	}
	/**
	 * 
	 * 交易完成状态转换
	 *
	 * @param orderState
	 * @param receicedState
	 * @return
	 * @throws
	 */
	private String handlerOrderState(String orderState, String receicedState) {
		if("3".equals(orderState) && "1".equals(receicedState)){
			return "6";
		}
		
		return orderState;
	}

}
