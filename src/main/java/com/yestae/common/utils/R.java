/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.utils;

import java.util.HashMap;

import com.yestae.common.enums.ResultCodeEnums;

/**
 * 返回数据
 *
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public static R error(String err_code, String err_code_des) {
		R r = new R();
		r.put("resultCode", ResultCodeEnums.FAIL.getFlag());
		r.put("errCode", err_code);
		r.put("errCodeDes", err_code_des);
		return r;
	}

	public static R ok(Object result) {
		R r = new R();
		r.put("resultCode", ResultCodeEnums.SUCCESS.getFlag());
		r.put("result", result);
		return r;
	}
	
	public static R ok() {
		R r = new R();
		r.put("resultCode", ResultCodeEnums.SUCCESS.getFlag());
		return r;
	}
	
}
