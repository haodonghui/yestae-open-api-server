/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.utils;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数字工具类
 *
 * @author daniel
 */
public class NumberUtils {
	private static Logger logger = LoggerFactory.getLogger(NumberUtils.class);
	public final static String DEFAULT_NUMBER_PATTERN = "##0.00";
	public final static String PATTERN = "###,##0.00";
	public final static String NUMBER_PATTERN = "###,##0";
	public final static String NUMBER_PATTERN_0 = "###,##0.";
	public final static String NUMBER_PATTERN_1 = "###,##0.0";
	
//    public enum Pattern {
//    	NUMBER_PATTERN("###,##0"),
//    	NUMBER_PATTERN_0("###,##0."),
//    	NUMBER_PATTERN_1("###,##0.0"),
//    	NUMBER_PATTERN_2("###,##0.00");
//        private String value;
//
//        Pattern(String value) {
//            this.value = value;
//        }
//        
//        public String getValue() {
//            return value;
//        }
//    }

	// 千分位方法
	public static String fmtMicrometer(Object text) {
		return fmtMicrometer(text+"",DEFAULT_NUMBER_PATTERN);
	}
	
	public static String fmtMicrometer(Object text,String pattern) {
		return fmtMicrometer(text+"",pattern);
	}
	
	public static String fmtMicrometer(String text,String pattern) {
		if(!StringUtils.equals(pattern, DEFAULT_NUMBER_PATTERN) 
				&& !StringUtils.equals(pattern, PATTERN)
				&& !StringUtils.equals(pattern, NUMBER_PATTERN)
				&& !StringUtils.equals(pattern, NUMBER_PATTERN_0)
				&& !StringUtils.equals(pattern, NUMBER_PATTERN_1)
				){
			return text;
		}
		
		DecimalFormat df = new DecimalFormat(pattern);
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			logger.error(e.getMessage());
			number = 0.0;
		}
		return df.format(number);
	}
	
	/**
	 * 判断是否是数值
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(Object obj){
		String str = obj+"";
	   for (int i = str.length();--i>=0;){  
	       if (!Character.isDigit(str.charAt(i))){
	           return false;
	       }
	   }
	   return true;
	}	
}
