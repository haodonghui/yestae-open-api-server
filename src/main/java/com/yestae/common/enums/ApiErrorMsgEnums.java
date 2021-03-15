package com.yestae.common.enums;

/**
 * 
 * 系统错误参数枚举
 *
 * @Package com.yestae.common.enums 
 * @ClassName ApiErrorMsgEnums 
 * @author wangpeng
 * @date 2019年5月14日 下午5:17:23 
 *
 */
public enum ApiErrorMsgEnums {
	
	SYSTEM_ERROR("SYSTEM_ERROR", "系统错误"),
	MISSING_PARAMETER("MISSING_PARAMETER", "缺少必要参数"),
	INVALID_PARAMETER("INVALID_PARAMETER", "参数无效"),
	ERROR_PARAMETER("ERROR_PARAMETER", "参数错误"),
	ACCESS_FORBIDDEN("ACCESS_FORBIDDEN", "无权限使用接口"),
	USER_NOT_EXSIT("USER_NOT_EXSIT", "会员不存在"),
	USER_AUTHENTICATED("USER_AUTHENTICATED", "会员已实名"),
	USER_MOBILE_EXSIT("USER_MOBILE_EXSIT", "会员手机号已存在"),
	USER_AUTH_NEW_MOBILE_DISACCORD("USER_AUTH_NEW_MOBILE_DISACCORD", "新手机与您益友实名不一致"),
	SMS_SEND_FREQUENCY("SMS_SEND_FREQUENCY", "短信请求过于频繁，请稍后再试"),
	SMS_CODE_ERROR("SMS_CODE_ERROR", "短信验证码不正确"),
	UPDATE_PWD_ERROR("UPDATE_PWD_ERROR", "修改密码失败"),
	OLD_PWD_ERROR("OLD_PWD_ERROR", "原密码不正确"),
	TWO_PASSWORD_INPUT_DIFFER("TWO_PASSWORD_INPUT_DIFFER", "两次密码输入不正确"),
	ERROR_REFRESH_TOKEN("ERROR_REFRESH_TOKEN", "refreshToken错误"),
	INVALID_REFRESH_TOKEN("INVALID_REFRESH_TOKEN", "refreshToken无效"),
	INVALID_ACCESS_TOKEN("INVALID_ACCESS_TOKEN", "accessToken无效"),
	CARD_NOT_EXSIT("CARD_NOT_EXSIT", "兑换卡不存在"),
	CARD_EXCHANGE_FAIL("CARD_EXCHANGE_FAIL", "兑换失败"),
	;
	
	private String errCode;
	private String desc;
	
	private ApiErrorMsgEnums(String errCode, String desc) {
		this.errCode = errCode;
		this.desc = desc;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static String getDescByCode(String code) {
		for (ApiErrorMsgEnums internationalEnum : ApiErrorMsgEnums.values()) {
			if (internationalEnum.getErrCode().equals(code)) {
				return internationalEnum.getDesc();
			}
		}
		return null;
	}

}
