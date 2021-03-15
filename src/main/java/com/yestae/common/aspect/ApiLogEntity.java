package com.yestae.common.aspect;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ApiLogEntity  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2787899715387441212L;
	
	/**
	 * 接口名称
	 */
	private String apiName;
	/**
	 * 接口地址
	 */
	private String apiUrl;
	/**
	 * 请求时间
	 */
	private Date reqTime;
	/**
	 * 请求参数
	 */
	private String params;
	/**
	 * 响应结果
	 */
	private String result;
	/**
	 * 调用者ip
	 */
	private String ip;

}
