package com.yestae.modules.user.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserOauthRequest  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4530534874749181865L;
	
	@NotBlank(message="mobile不能为空")
	private String mobile;
	
	@NotBlank(message="password不能为空")
	private String password;
	
	@NotNull(message="type不能为空")
	private Integer type;

	/**
	 * 	商户应用id
	 */
	private String appid;
}
