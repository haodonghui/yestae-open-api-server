package com.yestae.modules.coupon.vo;

import com.yestae.coupon.api.Coupon;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 茶票对象
 * 
 * @author haodonghui
 *
 * @time 2017年9月15日
 */
public class TeaCouponVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5253065733762920684L;
	private String couponId; //茶票id
	private String id;// 茶票兑换id
	private String couponName;// 茶票名称
	private Integer expiryDays;// 剩余有效天数
	private Integer couponType;// 茶票类型
	private String couponTypeName;// 茶票类型名称
	private BigDecimal couponValue ;// 代金券类型金额
	private BigDecimal discount  ;// 折扣值
	private String originProductCode;// 资格券-平台商品货号
	private String couponRule;// 使用规则描述
	private String NoCanwhy;//不可用原因描述
	private Long useTime;// 使用时间
	private Integer usableOrigin;// 平台是否可用，1-可用，0-不可用
	private Integer scope;// 使用范围,当使用范围为全场通用时，跳转商城首页
	private String goodsId;// 当使用范围为指定商品时，若商品ID为空（多商品），则跳转商城首页;若不为空(单商品)，则跳转商品详情页
	private String productId;// 当使用范围为指定商品时，若商品ID为空（多商品），则跳转商城首页;若不为空(单商品)，则跳转商品详情页
	private String categoryId;// 当使用范围为指定商品分类时，则跳转对应商品分类列表页（多分类时任选一个）
	private Long invalidTime; // 失效时间：2018-10-31旧版本使用数据
	private Long bindTime; // 绑定时间：2018-10-31旧版本使用数据
	private Integer[] usePlatform;// 可使用平台：2018-10-31旧版本使用数据

    private Long expiryBeginTime;   //有效期开始
    private Long expiryEndTime;     //有效期结束
    private String  memberId;     //会员id
    private Integer  status;     //茶票状态
    private Integer  ruleOfAmount;     //满减

    public String getCouponId() {
        return couponId;
    }

    public TeaCouponVo setCouponId(String couponId) {
        this.couponId = couponId;
        return this;
    }

    public String getId() {
        return id;
    }

    public TeaCouponVo setId(String id) {
        this.id = id;
        return this;
    }

    public String getCouponName() {
        return couponName;
    }

    public TeaCouponVo setCouponName(String couponName) {
        this.couponName = couponName;
        return this;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public TeaCouponVo setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
        return this;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public TeaCouponVo setCouponType(Integer couponType) {
        this.couponType = couponType;
        return this;
    }

    public BigDecimal getCouponValue() {
        return couponValue;
    }

    public TeaCouponVo setCouponValue(BigDecimal couponValue) {
        this.couponValue = couponValue;
        return this;
    }

    public String getOriginProductCode() {
        return originProductCode;
    }

    public TeaCouponVo setOriginProductCode(String originProductCode) {
        this.originProductCode = originProductCode;
        return this;
    }

    public String getCouponRule() {
        return couponRule;
    }

    public TeaCouponVo setCouponRule(String couponRule) {
        this.couponRule = couponRule;
        return this;
    }

    public String getNoCanwhy() {
        return NoCanwhy;
    }

    public TeaCouponVo setNoCanwhy(String noCanwhy) {
        NoCanwhy = noCanwhy;
        return this;
    }

    public Long getUseTime() {
        return useTime;
    }

    public TeaCouponVo setUseTime(Long useTime) {
        this.useTime = useTime;
        return this;
    }

    public Integer getUsableOrigin() {
        return usableOrigin;
    }

    public TeaCouponVo setUsableOrigin(Integer usableOrigin) {
        this.usableOrigin = usableOrigin;
        return this;
    }

    public Integer getScope() {
        return scope;
    }

    public TeaCouponVo setScope(Integer scope) {
        this.scope = scope;
        return this;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public TeaCouponVo setGoodsId(String goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public String getProductId() {
        return productId;
    }

    public TeaCouponVo setProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public TeaCouponVo setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Long getInvalidTime() {
        return invalidTime;
    }

    public TeaCouponVo setInvalidTime(Long invalidTime) {
        this.invalidTime = invalidTime;
        return this;
    }

    public Long getBindTime() {
        return bindTime;
    }

    public TeaCouponVo setBindTime(Long bindTime) {
        this.bindTime = bindTime;
        return this;
    }

    public Integer[] getUsePlatform() {
        return usePlatform;
    }

    public TeaCouponVo setUsePlatform(Integer[] usePlatform) {
        this.usePlatform = usePlatform;
        return this;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public TeaCouponVo setDiscount(BigDecimal discount) {
        this.discount = discount;
        return this;
    }

    public String getCouponTypeName() {
        return couponTypeName;
    }

    public TeaCouponVo setCouponTypeName(String couponTypeName) {
        this.couponTypeName = couponTypeName;
        return this;
    }

    public Long getExpiryBeginTime() {
        return expiryBeginTime;
    }

    public TeaCouponVo setExpiryBeginTime(Long expiryBeginTime) {
        this.expiryBeginTime = expiryBeginTime;
        return this;
    }

    public Long getExpiryEndTime() {
        return expiryEndTime;
    }

    public TeaCouponVo setExpiryEndTime(Long expiryEndTime) {
        this.expiryEndTime = expiryEndTime;
        return this;
    }

    public String getMemberId() {
        return memberId;
    }

    public TeaCouponVo setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TeaCouponVo setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getRuleOfAmount() {
        return ruleOfAmount;
    }

    public TeaCouponVo setRuleOfAmount(Integer ruleOfAmount) {
        this.ruleOfAmount = ruleOfAmount;
        return this;
    }

    public TeaCouponVo(Coupon coupon) {
		if (coupon != null) {
			this.setCouponId(coupon.getCouponId());
			this.setId(coupon.getId());
			this.setCouponName(coupon.getCouponName());
			this.setCouponType(coupon.getCouponType());
			this.setCouponValue(coupon.getCouponValue());
			this.setOriginProductCode(coupon.getOriginProductCode());
			this.setCouponRule(coupon.getCouponRule());
			Long useTime = coupon.getUseTime();
			if (useTime != null) {
				this.setUseTime(new Long(useTime));
			}
			this.scope = coupon.getUseArea();
			this.goodsId = coupon.getGoodsIdList().size() == 1 ? coupon.getGoodsIdList().get(0) : "";
			this.productId = coupon.getProductIdList().size() == 1 ? coupon.getProductIdList().get(0) : "";
			this.usableOrigin = coupon.getUsableOrigin();
			this.expiryDays = coupon.getExpiryDays();
			this.categoryId = coupon.getCategoryIdList().size() == 1 ? coupon.getCategoryIdList().get(0) : "";
			this.invalidTime = coupon.getExpiryEndTime();
			this.usePlatform = coupon.getUsePlatform();
			this.NoCanwhy = coupon.getNoCanwhy();
			this.bindTime = coupon.getBindTime();
			this.discount = coupon.getDiscount();
			this.couponTypeName = coupon.getCouponTypeName();
			this.expiryBeginTime = coupon.getExpiryBeginTime();
			this.expiryEndTime = coupon.getExpiryEndTime();
			this.memberId = coupon.getMemberId();
			this.status = coupon.getStatus();
			this.ruleOfAmount = coupon.getRuleOfAmount();
		}
	}
}