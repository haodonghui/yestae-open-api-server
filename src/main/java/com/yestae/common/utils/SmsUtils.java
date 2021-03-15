package com.yestae.common.utils;

import com.alibaba.fastjson.JSON;
import com.yestae.business.enums.MessageEnum;
import com.yestae.common.exception.RRException;
import com.yestae.modules.commom.config.Constants;
import com.yestae.sms.sdk.request.SmsCheckVerfyCodeRequest;
import com.yestae.sms.sdk.request.SmsVerifyCodeSdkRequest;
import com.yestae.sms.sdk.response.SmsSdkResponse;
import com.yestae.sms.sdk.utils.SmsConstants;
import com.yestae.sms.sdk.utils.SmsSdkUtils;
import com.yestae.tools.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

/**
 * @author wangpeng
 * @title: SmsUtils
 * @packageName: com.yestae.rest.util
 * @description: 短信工具类
 * @date 2020-03-09 9:44
 */
@Slf4j
public class SmsUtils {

    public static final String URL = Constants.SMSURL;

    /**
     * 发送短信验证码,验证码code 可为空，为空时系统自动生成验证码
     *
     * @return
     */
    public static boolean sendCode(SmsRequestBIO smsRequestBIO) {
        String code = smsRequestBIO.getCode();
        Integer expires = smsRequestBIO.getExpires();
        String mobile = smsRequestBIO.getMobile();
        Integer type = smsRequestBIO.getType();

        if (expires == null) {
            expires = Constants.DEFAULTEXPIRES;
        }

        if (StringUtils.isBlank(code)) {
            code = String.valueOf(StrUtils.buildRandom(6));
        }

        String content = SmsConstants.smsContentMap.get(type);
        content = String.format(content,code, expires/60);
        SmsVerifyCodeSdkRequest.Builder smsVerifyCodeSdkRequest = new SmsVerifyCodeSdkRequest.Builder();
        String platform = Constants.SMSPLATFORM;
        SmsVerifyCodeSdkRequest build =
                smsVerifyCodeSdkRequest
                        .bizType(type)
                        .code(code)
                        .content(content)
                        .expires(expires)
                        .mobile(mobile)
                        .platform(platform)
                        .type(0)
                        .build();

        SmsSdkResponse<Boolean> response = SmsSdkUtils.sendVerfyCode(URL + Constants.SMS_SENDVERIFICATIONCODE_URI, build);
        LogUtil.info(log, "SmsSdkUtils.sendVerfyCode[result#{}]", JSON.toJSONString(response));

        if (response != null && !response.isSucceed() && !response.getResult()) {
                throw new RRException(response.getRetMsg(), response.getRetCode());
        }
        return true;
    }

    /**
     *  虚拟发送短信,用于授权登录
     * @param smsRequestBIO
     */
    public static void fictitiousSendCode(SmsRequestBIO smsRequestBIO) {
        String code = smsRequestBIO.getCode();
        Integer expires = smsRequestBIO.getExpires();
        String mobile = smsRequestBIO.getMobile();
        Integer type = smsRequestBIO.getType();

        if (expires == null) {
            expires = Constants.DEFAULTEXPIRES;
        }

        if (StringUtils.isBlank(code)) {
            code = code = String.valueOf(StrUtils.buildRandom(6));
        }

        String content = SmsConstants.smsContentMap.get(type);
        content = String.format(content,code, expires/60);
        SmsVerifyCodeSdkRequest.Builder smsVerifyCodeSdkRequest = new SmsVerifyCodeSdkRequest.Builder();
        String platform = Constants.SMSPLATFORM;
        SmsVerifyCodeSdkRequest build =
                smsVerifyCodeSdkRequest
                        .bizType(type)
                        .code(code)
                        .content(content)
                        .expires(expires)
                        .mobile(mobile)
                        .platform(platform)
                        .type(0)
                        .build();

        SmsSdkResponse<Boolean> response = SmsSdkUtils.fictitiousSendCode(URL + Constants.SMS_FICTITIOUSSENDCODE_URI, build);
        LogUtil.info(log, "SmsSdkUtils.fictitiousSendCode[result#{}]", JSON.toJSONString(response));

        if (response != null && !response.isSucceed() && !response.getResult()) {
            throw new RRException(response.getRetMsg(), response.getRetCode());
        }

    }

    /**
     *  校验短信验证码
     * @param mobile    手机号
     * @param code      验证码
     * @param type      业务类型
     * @return
     */
    public static boolean checkValidCode(String mobile, String code, Integer type, boolean ifChangeStatus) {

        SmsCheckVerfyCodeRequest.Builder SmsCheckVerfyCodeRequest = new SmsCheckVerfyCodeRequest.Builder();
        String platform = Constants.SMSPLATFORM;
        com.yestae.sms.sdk.request.SmsCheckVerfyCodeRequest build = SmsCheckVerfyCodeRequest
                .bizType(type)
                .code(code)
                .mobile(mobile)
                .platform(platform)
                .changeCodeStatus(ifChangeStatus ? 1 : 0)
                .build();
        SmsSdkResponse<Boolean> response = SmsSdkUtils.checkVerfyCode(URL + Constants.SMS_CHECKVALIDCODE_URI, build);
        LogUtil.info(log, "SmsSdkUtils.sendVerfyCode[result#{}]", JSON.toJSONString(response));

        if (response == null) {
            throw new RRException(MessageEnum.SMS_VALIDATE_ERROR.getValue());
        }
        if (!response.isSucceed()) {
            throw new RRException(response.getRetMsg(), response.getRetCode());
        }
        String retMsg = response.getRetMsg();
        String smsOpenResultDto = JSON.parseObject(retMsg).getString("smsOpenResultDto");

        if (!"sms.verify.success".equals(JSON.parseObject(smsOpenResultDto).getString("retCode"))) {
            throw new RRException(JSON.parseObject(smsOpenResultDto).getString("retMsg"), JSON.parseObject(smsOpenResultDto).getString("retCode"));
        }
        return true;

    }


    /**
     * 发送验证码请求实体
     */
    public static class SmsRequestBIO {

        /**
         * 手机号
         */
        private String mobile;
        /**
         * 来源
         */
        private Integer source;
        /**
         * 业务类型
         */
        private Integer type;
        /**
         * 短信失效时间
         */
        private Integer expires;
        /**
         * 验证码
         */
        private String code;

        public String getMobile() {
            return mobile;
        }

        public SmsRequestBIO setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Integer getSource() {
            return source;
        }

        public SmsRequestBIO setSource(Integer source) {
            this.source = source;
            return this;
        }

        public Integer getType() {
            return type;
        }

        public SmsRequestBIO setType(Integer type) {
            this.type = type;
            return this;
        }

        public Integer getExpires() {
            return expires;
        }

        public SmsRequestBIO setExpires(Integer expires) {
            this.expires = expires;
            return this;
        }

        public String getCode() {
            return code;
        }

        public SmsRequestBIO setCode(String code) {
            this.code = code;
            return this;
        }
    }

}