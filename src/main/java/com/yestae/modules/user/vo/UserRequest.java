package com.yestae.modules.user.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.alibaba.fastjson.annotation.JSONField;
import com.yestae.common.validator.group.*;

import lombok.Data;

@Data
public class UserRequest implements Serializable{

	/** 
	 */
	private static final long serialVersionUID = 6276540325078083290L;
	/**
	 * 用户id
	 */
	@NotBlank(message="用户id不能为空", groups={UpdatePwdGroup.class, UpdateMobileGroup.class, ServiceListGroup.class, UserCoinQueryGroup.class})
	private String uid;
	/**
	 * 用户手机号
	 */
	@NotBlank(message="手机号不能为空", groups={AddUserGroup.class, UpdateGroup.class, UpdateMobileGroup.class,SmsGroup.class})
	@Pattern(message="手机号格式不正确", regexp="^1[0-9]{10}$", groups={AddGroup.class, UpdateGroup.class, UpdateMobileGroup.class,SmsGroup.class})
	private String mobile;
	/**
	 * 用户名称
	 */
	private String name;
	/**
	 * 用户性别：0-保密1-男2-女
	 */
	private Integer gender;
	/**
	 * 用户生日，格式:yyyy-MM-dd
	 */
	@JSONField(format = "yyyy-MM-dd")
	private Date birthday;
	/**
	 * 短信验证码
	 */
	private String smsCode;
	/**
	 * 新密码1
	 */
	@NotBlank(message="新密码不能为空", groups=UpdatePwdGroup.class)
	private String newPassword;
	/**
	 * 原登录密码
	 */
//	@NotBlank(message="原密码不能为空", groups=UpdatePwdGroup.class)
	private String oldPassword;
	
	
	/**
	 * 接口调用凭证
	 */
	@NotBlank(message="接口调用凭证不能为空", groups={UpdateMobileGroup.class,UpdatePwdGroup.class})
	private String accessToken;
	
	
	@NotEmpty(message="用户id集合不能为空", groups={GetUserBatchGroup.class})
	private List<String> uids;

	/**
	 * 	商户应用id
	 */
	private String appid;

	/**
	 * 	商户id
	 */
	private String mchid;

	/**
	 * 增值服务id
	 */
	private String addedServiceId;
	
}
