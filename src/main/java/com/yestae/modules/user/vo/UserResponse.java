package com.yestae.modules.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.yestae.user.center.dubbo.entity.UserImageDubbo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * 会员基本信息返回数据
 *
 * @Package com.yestae.modules.userapi.dto 
 * @ClassName UserInfoDTO 
 * @author wangpeng
 * @date 2019年5月16日 下午3:33:14 
 *
 */
@Data
public class UserResponse implements Serializable{

	/** 
	 */
	private static final long serialVersionUID = -7765963661816147906L;
	
	//@JsonInclude(Include.NON_NULL) 当注解下面的该字段为空时，不返回该字段
	
	//用户id
	@JsonInclude(Include.NON_NULL)
	private String uid;
	//用户名称
	@JsonInclude(Include.NON_NULL)
	private String name;
	//用户手机号
	@JsonInclude(Include.NON_NULL)
	private String mobile;
	//注册时间
	@JsonInclude(Include.NON_NULL)
	private Long regTime;
	//修改时间
	@JsonInclude(Include.NON_NULL)
	private Long modifyTime;
	//性别
	@JsonInclude(Include.NON_NULL)
	private Integer gender;
	//生日
	@JsonInclude(Include.NON_NULL)
	private String birthday;
	//用户等级1-普卡2-金卡
	@JsonInclude(Include.NON_NULL)
	private Integer userType;
	//升级时间
	@JsonInclude(Include.NON_NULL)
	private Long upgradeTime;
	//用户开通的增值服务列表
	@JsonInclude(Include.NON_NULL)
	private List<ServiceDetail> serviceDetail;
	//受益劵余额
	@JsonInclude(Include.NON_NULL)
	private BigDecimal coinBalance;
	//来源 0:未知,1:益友会APP,2:益友会微信公众号,3:益友会WEB,4:总裁茶室APP,5:益购APP,6:益购WEB,9:益购微信小程序,12:小程序开发平台,13:益原素,14:走进大益小程序,15:益享联盟小程序
	@JsonInclude(Include.NON_NULL)
	private Integer source;
	@JsonInclude(Include.NON_NULL)
	private UserImageDubbo userImageDubbo;

}
