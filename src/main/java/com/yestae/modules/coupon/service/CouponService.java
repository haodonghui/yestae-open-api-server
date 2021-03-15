package com.yestae.modules.coupon.service;

import com.yestae.business.vo.PageVo;
import com.yestae.coupon.api.ActivityTicketDto;
import com.yestae.coupon.api.Coupon;
import com.yestae.modules.coupon.vo.CouponQueryRequestVO;
import com.yestae.modules.coupon.vo.TeaCouponVo;

import java.util.List;

/**
 * @author wangpeng
 * @title: CouponService
 * @packageName: com.yestae.modules.coupon.service
 * @description: 茶票service
 * @date 2020-02-18 14:39
 */
public interface CouponService {

    /**
     *  分页查询茶票信息
     * @param couponQueryRequestVO
     * @return
     */
    PageVo<TeaCouponVo> getCouponList(CouponQueryRequestVO couponQueryRequestVO);

    /**
     *  获取当前可用活动集合
     * @param couponQueryRequestVO
     * @return
     */
    List<ActivityTicketDto> getActivityCouponList(CouponQueryRequestVO couponQueryRequestVO);
}
