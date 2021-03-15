package com.yestae.common.enums;

public enum SmsTypeEnum {
	/**
	 * 登录
	 */
	LOGIN(0),
	/**
	 * 注册
	 */
	SIGNUP(1),
	/**
	 * 更新密码
	 */
	UPDATE_PASSWORD(2),
	/**
	 * 修改手机号-校验原手机号
	 */
	UPDATE_MOBILE_CHECKOLD(3),
	/**
	 * 修改手机号-校验新手机号
	 */
	UPDATE_MOBILE_CHECKNEW(4),
	/**
	 * 忘记密码
	 */
	FORGET_PASSWORD(5),
	/**
	 * 预存款支付
	 */
	DEPOSIT_PAY(6),
	/**
	 * 取茶身份校验
	 */
	PICK_TEA(7),
	/**
	 * 其他
	 */
	OTHER(9),
	
	;

    private final int value;

    private SmsTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
