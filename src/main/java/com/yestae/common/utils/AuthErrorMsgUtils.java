package com.yestae.common.utils;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author wangpeng
 * @title: AuthErrorMsgUtils
 * @packageName: com.yestae.common.utils
 * @description: 实名认证错误工具
 * @date 2020-02-25 12:01
 */
public class AuthErrorMsgUtils {

    public static Map<String, String> authMsgMap = Maps.newHashMap();

    static {
        authMsgMap.put("user.auth.mobile.empty", "手机号为空");
        authMsgMap.put("user.auth.mobile.illegal", "手机号书写错误");
        authMsgMap.put("user.auth.bankno.empty", "银行卡号为空");
        authMsgMap.put("user.auth.name.empty", "真实姓名为空");
        authMsgMap.put("user.auth.idno.empty", "身份证号为空");
        authMsgMap.put("user.auth.bankno.illegal", "银行卡号不正确");
        authMsgMap.put("user.auth.name.illegal", "真实姓名含特殊字符");
        authMsgMap.put("user.auth.idno.illegal", "身份证号不正确");
        authMsgMap.put("user.auth.times.over", "查询次数超过限制");
        authMsgMap.put("user.auth.bankcard.no.info", "银行卡认证：没有信息，系统无记录");
        authMsgMap.put("user.auth.mobile.no.info", "手机号认证：没有信息，系统无记录");
        authMsgMap.put("user.auth.mobile.no.support", "手暂不支持该号段手机用户实名");
        authMsgMap.put("user.auth.bankcard.info.disaccord", "提交信息不一致");
        authMsgMap.put("user.auth.mobile.info.disaccord", "提交信息不一致");
        authMsgMap.put("user.auth.error", "认证操作失败");
    }
}
