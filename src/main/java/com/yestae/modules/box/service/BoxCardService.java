package com.yestae.modules.box.service;

import com.yestae.modules.box.vo.CardExchangeRequest;
import com.yestae.modules.box.vo.CardInfo;
import com.yestae.modules.box.vo.CardQueryRequest;


/**
 * @author wangpeng
 * @title: CouponService
 * @packageName: com.yestae.modules.coupon.service
 * @description: 茶票service
 * @date 2020-02-18 14:39
 */
public interface BoxCardService {

    /**
     *
     * 根据兑换码查找卡信息
     *
     * @param cardQueryRequest
     *            兑换码
     * @return 卡片信息
     */
    public CardInfo findCardInfoByCardCode(CardQueryRequest cardQueryRequest);

    /**
     *
     * 兑换
     *
     * @param request
     *            宝盒服务兑换请求对象
     * @return 宝盒卡片信息
     *
     */
    public CardInfo exchange(CardExchangeRequest request);
}
