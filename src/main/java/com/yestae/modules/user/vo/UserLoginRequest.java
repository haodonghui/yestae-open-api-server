package com.yestae.modules.user.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.yestae.common.validator.group.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserLoginRequest implements Serializable{

	/**
	 */
	private static final long serialVersionUID = 6276540325078083290L;
	/**
	 * 用户id
	 */
	@NotBlank(message="用户userId不能为空")
	private String userId;
	/**
	 * 用户名称
	 */
	@NotBlank(message="用户sid不能为空")
	private String sid;
	/**
	 * 0:未知,1:IOS,2:andriod,3:pc
	 */
	@NotBlank(message="用户id不能为空")
	private Integer platform;

}
