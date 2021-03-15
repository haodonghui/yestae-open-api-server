package com.yestae.modules.sms.service.impl;

import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.common.utils.SmsUtils;
import com.yestae.common.utils.StrUtils;
import com.yestae.modules.commom.config.Constants;
import com.yestae.modules.sms.service.SmsApiService;
import com.yestae.modules.user.service.UserApiService;
import com.yestae.sms.enums.SmsSourceEnum;
import com.yestae.sms.enums.SmsTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("smsApiService")
public class SmsApiServiceImpl implements SmsApiService {

	@Autowired
	private UserApiService userApiService;
	
//	@Autowired
//	private ISmsDubboService smsDubboService;
	
	//默认短信失效时间
	private Integer defaultExpires = Constants.DEFAULTEXPIRES;
	//是否开启发送短信
	private boolean isOpen = Boolean.valueOf(Constants.SMSOPEN);
	
	private Integer SMS_SOURCE = SmsSourceEnum.YG_WX_PROGRAM.getValue();
	
	@Override
	public void sendUpdatePasswordCode(String mobile) {
		//校验手机号是否存在
		boolean existMobile = userApiService.isExistMobile(mobile);
		if(!existMobile){
			throw new RRException(ApiErrorMsgEnums.USER_NOT_EXSIT.getDesc(), ApiErrorMsgEnums.USER_NOT_EXSIT.getErrCode());
		}
		
		//发送短信
		this.sendSms(mobile, SMS_SOURCE, SmsTypeEnum.UPDATE_PASSWORD.getValue(), null);
	}

	@Override
	public void sendLoginCode(String mobile) {
		//发送短信
		this.sendSms(mobile, SMS_SOURCE, SmsTypeEnum.LOGIN.getValue(), null);
	}
	
	@Override
	public void sendSms(String mobile, Integer source, Integer type, Integer expires) {
		if(isOpen){
			//校验短信间隔
			if(!isFlag(mobile, type)){
				throw new RRException(ApiErrorMsgEnums.SMS_SEND_FREQUENCY.getDesc(), ApiErrorMsgEnums.SMS_SEND_FREQUENCY.getErrCode());
			}

			if(expires == null) {
				expires = defaultExpires;
			}

            SmsUtils.SmsRequestBIO smsRequestBIO = new SmsUtils.SmsRequestBIO();
            smsRequestBIO.setMobile(mobile).setSource(source).setType(type);
            SmsUtils.sendCode(smsRequestBIO);
//			SmsResult<Boolean> smsResult = smsDubboService.sendCode(mobile, source, type, expires);
//			if (!smsResult.isSucceed()) {
//				throw new RRException(smsResult.getRetMsg(), smsResult.getRetCode());
//			}
		}
	}
	
	@Override
	public boolean validateSmsCode(String mobile, String code, Integer type) {
		if(isOpen){
			//校验
            SmsUtils.checkValidCode(mobile, code, type, true);
//			SmsResult<Boolean> smsResult = smsDubboService.verifyCode(mobile, code, SMS_SOURCE, type);
//			if (smsResult != null && smsResult.isSucceed()) {
//				// 更新验证码状态
//				this.updateSmsStatus(mobile, code);
//				return true;
//			} else {
//				throw new RRException(ApiErrorMsgEnums.SMS_CODE_ERROR.getDesc(),
//						ApiErrorMsgEnums.SMS_CODE_ERROR.getErrCode());
//			}
		}
		
		return true;
	}
	

	/**
	 * 
	 * 查询短信验证码是否频繁操作
	 *
	 * @param mobile
	 * @param type
	 * @return
	 * @throws
	 */
	private boolean isFlag(String mobile, Integer type) {
		return true;
    }

	@Override
	public void sendUpdateMobileCode(String mobile) {
		//校验手机号是否存在
		boolean existMobile = userApiService.isExistMobile(mobile);
		if(existMobile){
			throw new RRException(ApiErrorMsgEnums.USER_MOBILE_EXSIT.getDesc(), ApiErrorMsgEnums.USER_MOBILE_EXSIT.getErrCode());
		}
		
		//发送短信
		this.sendSms(mobile, SMS_SOURCE, SmsTypeEnum.UPDATE_MOBILE_CHECKNEW.getValue(), null);
	}

	@Override
	public String generateCode(String mobile) {

        SmsUtils.SmsRequestBIO smsRequestBIO = new SmsUtils.SmsRequestBIO();
        String code =String.valueOf( StrUtils.buildRandom(6));
        smsRequestBIO.setMobile(mobile).setType(com.yestae.sms.sdk.enums.SmsTypeEnum.LOGIN.getValue()).setCode(code);

        SmsUtils.fictitiousSendCode(smsRequestBIO);

        return code;

//		SmsResult<String> smsResult = smsDubboService.generateCode(mobile, SMS_SOURCE, SmsTypeEnum.LOGIN.getValue(), null);
//		if(smsResult == null){
//			throw new RRException(ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
//					ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
//		}
//		if(!smsResult.isSucceed()){
//			throw new RRException(smsResult.getRetMsg(),
//					smsResult.getRetCode());
//		}
//		return smsResult.getResult();
	}
}
