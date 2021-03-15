package com.yestae.modules.auth.controller;

import com.yestae.common.annotation.ApiLog;
import com.yestae.common.utils.R;
import com.yestae.common.validator.ValidatorUtils;
import com.yestae.common.validator.group.BankCardVerifyGroup;
import com.yestae.common.validator.group.IdCardVerifyGroup;
import com.yestae.modules.auth.service.AuthService;
import com.yestae.modules.auth.vo.UserAuthRequest;
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
import java.util.Map;

/**
 * @author wangpeng
 * @title: AuthController
 * @packageName: com.yestae.modules.auth.controller
 * @description: 实名认证
 * @date 2020-02-17 14:38
 */
@RestController
@RequestMapping("auth")
public class AuthController {

    /**
     *  实名
     */
    @Autowired
    private AuthService authService;

    @ApiLog()
    @PostMapping("/bankCardVerify")
    @RequiresPermissions("openapi:auth:bankCardVerify")
    @ApiOperation(value = "银行卡3要素校验", notes = "银行卡3要素校验", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1025275500607488002\",\"realName\":\"张三丰\",\"idCard\":\"110000201904251111\",\"bankNo\":\"622611111111111111111\"}",required=true,paramType="form")
    })
    public R bankCardVerify (@Valid CommonRequest baseRequest) {
        UserAuthRequest userAuthRequest = baseRequest.parseObject(UserAuthRequest.class);
        ValidatorUtils.validateEntity(userAuthRequest, BankCardVerifyGroup.class);

        Map<String, Object> data = authService.bankCardVerify(userAuthRequest);
        return R.ok();
    }

    @ApiLog()
    @PostMapping("/idCardVerify")
    @RequiresPermissions("openapi:auth:idCardVerify")
    @ApiOperation(value = "实名认证", notes = "实名认证", produces = "application/json",hidden=false)
    @ApiImplicitParams({
            @ApiImplicitParam(name="appid",value="应用编号",required=true,paramType="form"),
            @ApiImplicitParam(name="mchid",value="商户编号",required=true,paramType="form"),
            @ApiImplicitParam(name="timestamp",value="请求时间戳",required=true,paramType="form"),
            @ApiImplicitParam(name="version",value="版本号",required=true,paramType="form"),
            @ApiImplicitParam(name="sign",value="签名",required=true,paramType="form"),
            @ApiImplicitParam(name="bizcontent",value="{\"uid\":\"1025275500607488002\",\"realName\":\"张三丰\",\"idCard\":\"110000201904251111\",\"mobile\":\"18311111111\"}",required=true,paramType="form")
    })
    public R idCardVerify (@Valid CommonRequest baseRequest) {
        UserAuthRequest userAuthRequest = baseRequest.parseObject(UserAuthRequest.class);
        ValidatorUtils.validateEntity(userAuthRequest, IdCardVerifyGroup.class);
        Map<String, Object> data = authService.idCardVerify(userAuthRequest);
        return R.ok();
    }
}
;