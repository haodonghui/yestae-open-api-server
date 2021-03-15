package com.yestae.common.enums;

/**
 * 
 * 业务返回结果标识
 *
 * @Package com.yestae.common.enums 
 * @ClassName ResultCodeEnums 
 * @author wangpeng
 * @date 2019年5月14日 下午2:27:22 
 *
 */
public enum ResultCodeEnums {
	/**
     * 成功
     */
	SUCCESS("SUCCESS"),
    /**
     * 失败
     */
	FAIL("FAIL");

    private String flag;

	private ResultCodeEnums(String flag) {
		this.flag = flag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
