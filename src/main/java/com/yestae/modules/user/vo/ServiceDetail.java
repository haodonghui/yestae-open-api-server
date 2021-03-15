package com.yestae.modules.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 增值服务明细
 *
 * @Package com.yestae.modules.userapi.dto 
 * @ClassName ServiceDetail 
 * @author wangpeng
 * @date 2019年5月17日 下午1:48:55 
 *
 */
@Data
public class ServiceDetail implements Serializable {

	/** 
	 */
	private static final long serialVersionUID = 253832345070311127L;
	//增值服务id
	private String id;
	//增值服务名称
	private String name;
	//增值服务有效期开始时间
	private Long startTime;
	//增值服务有效期结束时间
	private Long endTime;
	/**
	 * 2-正常状态 3-即将到期 4-已到期
	 */
	private String status;

	/**
	 * 是否首次开通过 0 否,1 是
	 */
	private Integer isOpenOrNo;

	/**
	 * 是否首次开通 0 否,1 是
	 */
	private Integer isOpenForTheFirse;
}
