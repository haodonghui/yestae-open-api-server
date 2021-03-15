package com.yestae.modules.user.vo.auth;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wangpeng
 * @title: UserAuthResponse
 * @packageName: com.yestae.modules.user.vo.auth
 * @description: 相关描述
 * @date 2020-02-23 15:39
 */
@Data
public class UserAuthResponse implements Serializable {
    private static final long serialVersionUID = 5963052028688514507L;

    /**
     *  实名状态
     *  0：未实名
     *  1：已实名
     */
    private Integer authStatus;
    /**
     *  用户id
     */
    private String uid;
    /**
     *  手机号
     */
//    private String mobile;
    /**
     *  身份证
     */
    private String idCard;

    /**
     *  银行卡
     */
    private String bankNo;

    /**
     *  真实姓名
     */
    private String realName;
}
