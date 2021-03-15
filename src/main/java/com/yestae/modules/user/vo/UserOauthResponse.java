package com.yestae.modules.user.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserOauthResponse  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5331283701743600746L;

	@NotBlank(message="uid不能为空")
	private String uid;
	
	@NotBlank(message="accessToken不能为空")
	private String accessToken;
	
	private Long expiresIn;
	
	@NotBlank(message="refreshToken不能为空")
	private String refreshToken;
	
	//会员名称
	private String name;
}
