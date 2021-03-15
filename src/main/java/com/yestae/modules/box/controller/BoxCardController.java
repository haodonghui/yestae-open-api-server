package com.yestae.modules.box.controller;


import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.modules.box.service.BoxCardService;
import com.yestae.modules.box.vo.CardExchangeRequest;
import com.yestae.modules.box.vo.CardInfo;
import com.yestae.modules.box.vo.CardQueryRequest;
import com.yestae.modules.commom.request.CommonRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * @author wangpeng
 * @title: CouponController
 * @packageName: com.yestae.modules.coupon.controller
 * @description: 宝盒卡控制器
 * @date 2020-02-18 14:37
 */
@RestController
@RequestMapping("boxcard")
public class BoxCardController {

    @Autowired
    private BoxCardService boxCardService;

    @ApiLog()
    @PostMapping("/findCardInfoByCardCode")
    @RequiresPermissions("openapi:boxcard:findCardInfoByCardCode")
    @ApiOperation(value = "根据兑换码获取卡信息", notes = "根据兑换码获取卡信息", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"cardCode\":\"1025275500607488002\"}",required=true,paramType="form")
    })
    public R findCardInfoByCardCode (@Valid CommonRequest baseRequest) {
        CardQueryRequest cardQueryRequest = baseRequest.parseObject(CardQueryRequest.class);
        ValidatorUtils.validateEntity(cardQueryRequest);

        CardInfo cardInfo = boxCardService.findCardInfoByCardCode(cardQueryRequest);
        return R.ok(cardInfo);
    }

    @ApiLog()
    @PostMapping("/exchange")
    @RequiresPermissions("openapi:boxcard:exchange")
    @ApiOperation(value = "兑换", notes = "兑换", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"cardCode\":\"1025275500607488002\",\"memberId\":\"1025275500607488002\"}",required=true,paramType="form")
    })
    public R exchange (@Valid CommonRequest baseRequest) {

        CardExchangeRequest cardExchangeRequest = baseRequest.parseObject(CardExchangeRequest.class);
        ValidatorUtils.validateEntity(cardExchangeRequest);

        CardInfo cardInfo = boxCardService.exchange(cardExchangeRequest);

        return R.ok(cardInfo);
    }
}
