package com.yestae.modules.commom.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class CommonRequest implements Serializable{

	/** 
	 */
	private static final long serialVersionUID = -6889637321206758840L;
	//app编码
	@NotBlank(message="应用编号（appid）不能为空")
	private String appid;
	//商户编码
	@NotBlank(message="商户编号（mchid）不能为空")
	private String mchid;
	//请求时间戳
	@NotNull(message="时间戳（timestamp）不能为空")
	private Long timestamp;
	//具体业务参数json
	@NotBlank(message="业务参数（bizcontent）不能为空")
	private String bizcontent;
	//版本号
	@NotBlank(message="版本号（version）不能为空")
	private String version;
	//签名字符串
	@NotBlank(message="签名（sign）不能为空")
	private String sign;
	
	public <T> T  parseObject(Class<T> clazz){
		if(StringUtils.isNotBlank(bizcontent)){
			return JSONObject.parseObject(bizcontent, clazz);
		}
		return null;
	}
}
