package com.yestae.modules.coupon.controller;

import com.yestae.business.vo.PageVo;
import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.coupon.api.ActivityTicketDto;
import com.yestae.coupon.api.Coupon;
import com.yestae.modules.commom.request.CommonRequest;
import com.yestae.modules.coupon.service.CouponService;
import com.yestae.modules.coupon.vo.CouponQueryRequestVO;
import com.yestae.modules.coupon.vo.TeaCouponVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangpeng
 * @title: CouponController
 * @packageName: com.yestae.modules.coupon.controller
 * @description: 茶票控制器
 * @date 2020-02-18 14:37
 */
@RestController
@RequestMapping("coupon")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @ApiLog()
    @PostMapping("/getCouponList")
    @RequiresPermissions("openapi:coupon:getCouponList")
    @ApiOperation(value = "分页获取茶票列表", notes = "分页获取茶票列表", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1025275500607488002\",\"couponStatusList\":[1,2],\"curPage\":1,\"pageSize\":20,\"obtainPlatform\":4}",required=true,paramType="form")
    })
    public R getCouponList (@Valid CommonRequest baseRequest) {

        CouponQueryRequestVO couponQueryRequestVO = baseRequest.parseObject(CouponQueryRequestVO.class);
        ValidatorUtils.validateEntity(couponQueryRequestVO);

        PageVo<TeaCouponVo> pageVo = couponService.getCouponList(couponQueryRequestVO);
        return R.ok(pageVo);
    }

    @ApiLog()
    @PostMapping("/getActivityCouponList")
    @RequiresPermissions("openapi:coupon:getActivityCouponList")
    @ApiOperation(value = "获取当前可用的茶票活动", notes = "获取当前可用的茶票活动", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"obtainPlatform\":\"1\"}",required=true,paramType="form")
    })
    public R getActivityCouponList (@Valid CommonRequest baseRequest) {

        CouponQueryRequestVO couponQueryRequestVO = baseRequest.parseObject(CouponQueryRequestVO.class);

        List<ActivityTicketDto> activityTicketDtoList = couponService.getActivityCouponList(couponQueryRequestVO);
        return R.ok(activityTicketDtoList);
    }
}
