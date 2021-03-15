package com.yestae.modules.commom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * openapi常量类
 *
 * @Package com.yestae.modules.commom.config 
 * @ClassName OpenApiConstant 
 * @author wangpeng
 * @date 2019年5月20日 下午4:06:00 
 *
 */
@Configuration
public class Constants {

	/**
	 * 	发送短信验证码uri
	 */
	public static final String SMS_SENDVERIFICATIONCODE_URI = "/sms/open/send/sendVerificationCode";

	/**
	 * 	发送虚拟短信验证码uri
	 */
	public static final String SMS_FICTITIOUSSENDCODE_URI = "/sms/open/send/generateCode";

	/**
	 * 	 校验短信验证码uri
	 */
	public static final String SMS_CHECKVALIDCODE_URI = "/sms/open/send/checkValidCode";

    /**
	 * accessToken过期时间，单位秒
	 */
	public static long ACCESSTOKEN_EXPIRESIN = 7200;
	
	/**
	 * refreshToken过期时间，单位秒
	 */
	public static long REFRESHTOKEN_EXPIRESIN = 15 * 24 * 60 * 60;
	
	//默认短信时间
	public static Integer DEFAULTEXPIRES;

	@Autowired(required = false)
	public void setDefaultExpires(@Value(value = "${yestae.sms.defaultExpires}") Integer defaultExpires) {
		Constants.DEFAULTEXPIRES = defaultExpires;
	}
	
	//发送短信是否开启
	public static String SMSOPEN;
	@Autowired(required = false)
	public void setSmsOpen(@Value(value = "${yestae.sms.smsOpen}") String smsOpen) {
		Constants.SMSOPEN = smsOpen;
	}

	//短信业务平台
    public static String SMSPLATFORM;
    @Autowired(required = true)
    public void setSmsPlatform(@Value(value = "${yestae.sms.smsPlatform}") String smsPlatform) {
        Constants.SMSPLATFORM = smsPlatform;
    }

    //短信url
    public static String SMSURL;
    @Autowired(required = false)
    public void setSmsUrl(@Value(value = "${yestae.sms.smsUrl}") String smsUrl) {
        Constants.SMSURL = smsUrl;
    }
    
    //增值服务-尊享
    public static String VAS_ZX;
    @Autowired(required = false)
    public void setZxId(@Value(value = "${yestae.vas.zx}") String zx) {
    	Constants.VAS_ZX = zx;
    }
}
