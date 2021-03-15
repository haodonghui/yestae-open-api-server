package com.yestae.modules.coupon.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yestae.business.vo.PageVo;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.LogUtil;
import com.yestae.coupon.api.*;
import com.yestae.modules.coupon.service.CouponService;
import com.yestae.modules.coupon.vo.CouponQueryRequestVO;
import com.yestae.modules.coupon.vo.TeaCouponVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangpeng
 * @title: CouponServiceImpl
 * @packageName: com.yestae.modules.coupon.service.impl
 * @description: 茶票实现
 * @date 2020-02-18 14:40
 */
@Service("couponService")
@Slf4j
public class CouponServiceImpl implements CouponService {

    @Autowired
    private ITeaCouponDubboService teaCouponDubboService;

    @Override
    public PageVo<TeaCouponVo> getCouponList(CouponQueryRequestVO couponQueryRequestVO) {

        LogUtil.info(log, "getCouponList.param#{}", JSON.toJSONString(couponQueryRequestVO));

        MyCouponSearchVO myCouponSearchVO = new MyCouponSearchVO();

        BeanUtils.copyProperties(couponQueryRequestVO, myCouponSearchVO);
        myCouponSearchVO.setCouponStatusList(couponQueryRequestVO.getCouponStatusList());

        CouponApiResult<PageVo<Coupon>> apiResult = teaCouponDubboService.getMyCouponPage(myCouponSearchVO, couponQueryRequestVO.getCurPage(), couponQueryRequestVO.getPageSize());

        if (!apiResult.isSucceed()) {
            LogUtil.error(log, "teaCouponDubboService.getMyCouponPage[param#{}, result#{}]",
                    JSON.toJSONString(myCouponSearchVO),
                    JSON.toJSONString(apiResult));

            throw new RRException(apiResult.getRetMsg(), apiResult.getRetCode());
        }

        PageVo<TeaCouponVo> pageVo = new PageVo<TeaCouponVo>(couponQueryRequestVO.getCurPage(), couponQueryRequestVO.getPageSize());
        if (apiResult != null) {
            PageVo<Coupon> _pageVo = apiResult.getResult();
            List<Coupon> list = _pageVo.getRecords();
            List<TeaCouponVo> teaCouponVoList = Lists.newArrayList();
            for (Coupon coupon : list) {
                TeaCouponVo teaCouponVo = new TeaCouponVo(coupon);
                teaCouponVoList.add(teaCouponVo);
            }
            pageVo.setCurrent(_pageVo.getCurrent());
            pageVo.setSize(_pageVo.getSize());
            pageVo.setTotal(_pageVo.getTotal());
            pageVo.setRecords(teaCouponVoList);
        }
        return pageVo;

    }

    @Override
    public List<ActivityTicketDto> getActivityCouponList(CouponQueryRequestVO couponQueryRequestVO) {

        ActivityTicketSearchVO activityTicketSearchVO = new ActivityTicketSearchVO();
        BeanUtils.copyProperties(couponQueryRequestVO, activityTicketSearchVO);
        CouponApiResult<List<ActivityTicketDto>> apiResult =
                teaCouponDubboService.getCurrentActivityTicketDtoList(activityTicketSearchVO);

        if (!apiResult.isSucceed()) {
            LogUtil.error(log, "teaCouponDubboService.getCurrentActivityTicketDtoList[param#{}, result#{}]",
                    JSON.toJSONString(activityTicketSearchVO),
                    JSON.toJSONString(apiResult));

            throw new RRException(apiResult.getRetMsg(), apiResult.getRetCode());
        }

        return apiResult.getResult();
    }
}
