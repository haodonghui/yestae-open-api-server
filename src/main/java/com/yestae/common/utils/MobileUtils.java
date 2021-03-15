/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 手机号码工具类
 *
 * @author daniel
 */
public class MobileUtils {

//	private static Logger logger = LoggerFactory.getLogger(MobileUtils.class);
	
	/***
	 * 隐藏手机号码中间4位
	 * @param mobile
	 * @return
	 */
	public static String hideMid4(String mobile) {
		return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
	
	/**
	 * 
	 * 手机号校验
	 *
	 * @param mobile
	 * @return
	 * @throws
	 */
	public static boolean checkMobile(String mobile){
		
		if(StringUtils.isBlank(mobile)) return false;
		//格式校验
		Pattern regex  = Pattern.compile("^1[0-9]{10}$");
    	Matcher matcher = regex.matcher(mobile);
    	if(!matcher.matches()){
    		return false;
    	}
		return true;
	}

}
