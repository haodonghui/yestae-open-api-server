package com.yestae.common.utils;

import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.modules.commom.config.Constants;

/**
 * 
 * 字符串工具类
 *
 * @Package com.yestae.common.utils 
 * @ClassName StrUtils 
 * @author wangpeng
 * @date 2019年5月20日 下午4:02:49 
 *
 */
public class StrUtils {

		
		public static void validPasswordStrict(String password) {
			// 1.密码不能为空；
			if (StringUtils.isBlank(password)) {
				throw new RRException("新密码不能为空", ApiErrorMsgEnums.ERROR_PARAMETER.getErrCode());
			}
			// 2.密码必须是6-16位的数字或字母组合；
			if (!password.matches("^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]{6,16}$")) {
				throw new RRException("密码必须是6-16位的数字或字母组合", ApiErrorMsgEnums.ERROR_PARAMETER.getErrCode());
			}
			// 3.密码过于简单，请重新设置
			if (isOrder(password) ||isSame(password)) {
				throw new RRException("密码过于简单，请重新设置", ApiErrorMsgEnums.ERROR_PARAMETER.getErrCode());
			}
		}
		
		// 判断是否有顺序
		public static boolean isOrder(String str) {
			String orderStr = ""; // 顺序表
			for (int i = 33; i < 127; i++) {
				orderStr += Character.toChars(i)[0];
			}
			String reverseStr = new StringBuilder(str).reverse().toString();
			return orderStr.contains(str) || orderStr.contains(reverseStr);
		}

		// 判断是否相同
		public static boolean isSame(String str) {
			String regex = str.substring(0, 1) + "{" + str.length() + "}";
			return str.matches(regex);
		}
		
		/**
		 * 获取一定长度的随机字符串
		 * 
		 * @param length
		 *            指定字符串长度
		 * @return 一定长度的字符串
		 */
		public static String getRandomStringByLength(int length) {
			String base = "abcdefghijklmnopqrstuvwxyz0123456789";
			Random random = new Random();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < length; i++) {
				int number = random.nextInt(base.length());
				sb.append(base.charAt(number));
			}
			return sb.toString();
		}
		
		/**
		 * 取出一个指定长度大小的随机正整数.
		 * 
		 * @param length
		 *            int 设定所取出随机数的长度。length小于11
		 * @return int 返回生成的随机数。
		 */
		public static int buildRandom(int length) {
			int num = 1;
			double random = Math.random();
			if (random < 0.1) {
				random = random + 0.1;
			}
			for (int i = 0; i < length; i++) {
				num = num * 10;
			}
			return (int) ((random * num));
		}
		
}
