package com.yestae.modules.auth.service;

import com.yestae.modules.auth.vo.UserAuthRequest;

import java.util.Map;

/**
 * @author wangpeng
 * @title: AuthService
 * @packageName: com.yestae.modules.auth.service
 * @description: 实名service
 * @date 2020-02-17 14:53
 */
public interface AuthService {

    /**
     *  银行卡3要素校验
     * @param userAuthRequest
     * @return
     */
    Map<String, Object> bankCardVerify(UserAuthRequest userAuthRequest);

    /**
     *  实名认证
     * @param userAuthRequest
     * @return
     */
    Map<String, Object> idCardVerify(UserAuthRequest userAuthRequest);
}
