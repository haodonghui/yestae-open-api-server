package com.yestae.modules.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.AuthErrorMsgUtils;
import com.yestae.common.utils.LogUtil;
import com.yestae.common.validator.Assert;
import com.yestae.common.validator.group.BankCardVerifyGroup;
import com.yestae.common.validator.group.IdCardVerifyGroup;
import com.yestae.modules.auth.service.AuthService;
import com.yestae.modules.auth.vo.UserAuthRequest;
import com.yestae.realname.authentication.api.ICardAuthDubboService;
import com.yestae.realname.authentication.api.ICarriersAuthDubboService;
import com.yestae.realname.authentication.api.result.Result;
import com.yestae.third.certification.api.IThirdCertificationDubboService;
import com.yestae.third.certification.api.ThirdCertificationApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @author wangpeng
 * @title: AuthServiceImpl
 * @packageName: com.yestae.modules.auth.service.impl
 * @description: 实名service实现
 * @date 2020-02-17 14:54
 */
@Service("authService")
@Slf4j
public class AuthServiceImpl implements AuthService {

    /**
     *  第三方实名认证dubbo服务
     */
    @Autowired
    private IThirdCertificationDubboService thirdCertificationDubboService;

    /**
     *  创蓝实名认证
     */
    @Autowired
    private ICarriersAuthDubboService carriersAuthDubboService;

    /**
     *  创蓝银行卡认证
     */
    @Autowired
    private ICardAuthDubboService cardAuthDubboService;

    @Override
    public Map<String, Object> bankCardVerify(UserAuthRequest userAuthRequest) {
        String bankNo = userAuthRequest.getBankNo();
        String idCard = userAuthRequest.getIdCard();
        String realName = userAuthRequest.getRealName();
        //参数校验
        Assert.isBlank(bankNo, "bankNo不能为空");
        Assert.isBlank(idCard, "idCard不能为空");
        Assert.isBlank(realName, "realName不能为空");

        //1. 先调用创蓝实名认证，认证不成功在调用老的实名认证
        Result<Boolean> clAuthResult = cardAuthDubboService.cardThreeAuthDetail(realName, idCard, bankNo);
        LogUtil.info(log, " cardAuthDubboService.cardThreeAuthDetail.result#{}",
                JSON.toJSONString(clAuthResult));

        if (clAuthResult != null && clAuthResult.isSucceed() && clAuthResult.getResult()) {
            return null;
        }

        //2. 创蓝实名未通过，继续调用老服务
        ThirdCertificationApiResult<Object> apiResult = thirdCertificationDubboService.bankCardVerify(bankNo, idCard, realName);

        LogUtil.info(log, " thirdCertificationDubboService.bankCardVerify.result#{}",
                JSON.toJSONString(apiResult));

        if (!apiResult.isSucceed()) {
             throw new RRException(AuthErrorMsgUtils.authMsgMap.get(apiResult.getRetCode()), apiResult.getRetCode());
        }
        return null;
    }

    @Override
    public Map<String, Object> idCardVerify(UserAuthRequest userAuthRequest) {
        String realName = userAuthRequest.getRealName();
        String mobile = userAuthRequest.getMobile();
        String idCard = userAuthRequest.getIdCard();

        //参数校验
        Assert.isBlank(mobile, "mobile不能为空");
        Assert.isBlank(idCard, "idCard不能为空");
        Assert.isBlank(realName, "realName不能为空");

        //1. 先调用创蓝实名认证，未通过在调用老的服务
        Result<Boolean> clAuthResult = carriersAuthDubboService.carriersAuthDetai(realName, idCard, mobile);
        LogUtil.info(log, " carriersAuthDubboService.carriersAuthDetai.result#{}",
                JSON.toJSONString(clAuthResult));
        if (clAuthResult != null && clAuthResult.isSucceed() && clAuthResult.getResult()) {
            return null;
        }

        //创蓝实名未通过，继续调用老服务
        ThirdCertificationApiResult<Object> apiResult = thirdCertificationDubboService.telVerify(idCard, realName, mobile);

        LogUtil.info(log, " thirdCertificationDubboService.telVerify.result#{}",
                JSON.toJSONString(apiResult));

        if (!apiResult.isSucceed()) {
            throw new RRException(AuthErrorMsgUtils.authMsgMap.get(apiResult.getRetCode()), apiResult.getRetCode());
        }

        return null;
    }
}
