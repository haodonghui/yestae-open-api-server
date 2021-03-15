package com.yestae.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;

@Data
public class BaseEntity implements Serializable{
	/** 
	 */
	private static final long serialVersionUID = 1283335927664841675L;
	/**
	 * 创建人
	 */
	private String createdBy;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 更新人
	 */
	private String updatedBy;
	/**
	 * 更新时间
	 */
	private Date updatedTime;
	
	
}
