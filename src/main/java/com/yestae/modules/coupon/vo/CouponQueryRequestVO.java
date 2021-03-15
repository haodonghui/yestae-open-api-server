package com.yestae.modules.coupon.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * @author wangpeng
 * @title: CouponQueryRequestVO
 * @packageName: com.yestae.modules.coupon.vo
 * @description: 茶票分页查询vo
 * @date 2020-02-18 14:21
 */
@Data
public class CouponQueryRequestVO implements Serializable {
    private static final long serialVersionUID = -3597866672725985601L;

    //*********查询参数**************//
    /**
     *  用户id
     */
    @NotBlank(message="uid不能为空")
    private String uid;

    /**
     * 茶票状态
     * 1-未使用，2-已使用，3-已过期
     */
    private Integer couponStatus;

    /**
     * 茶票状态集合，可组合茶票状态进行查询，获取多种状态的茶票集合
     */
    private List<Integer> couponStatusList;

    /**
     * 适用平台
     * 1-电商APP端，2-电商小程序端，3-电商PC端，4-益原素小程序（选传）
     */
    private Integer originType;

    /**
     * 活动方式：1-注册，2-升级金卡，3-支付成功，4-赠送（选传)
     */
    private Integer activityType;

    /**
     * 领取方式：1-自动领取，2-点击领取，3-扫码领取，4-后台绑定（选传）
     */
    private Integer getType;

    /**
     * 茶票实际使用平台：1-电商APP端，2-电商小程序端，3-电商PC端，4-益原素小程序（选传）
     */
    private Integer platform;

    /**
     * 适用会员等级：1-普卡，2-金卡（选传）
     */
    private Integer enableUser;

    /**
     * 推广人ID（选传）
     */
    private String generalizeUserId;

    /**
     * 获取平台：1-电商APP端，2-电商小程序端，3-电商PC端，4-益原素小程序（选传）
     */
    private Integer obtainPlatform;

    /**
     * 订单ID（选传）
     */
    private String orderId;

    /**
     *  适用平台：1-电商APP端，2-电商小程序端，3-电商PC端，4-益原素小程序（选传）
     */
    private Integer enablePlatform;

    /**
     *  活动id
     */
    private String activityId;

    /**
     *  茶票id
     */
    private String ticketId;

    /**
     *  活动开始时间
     */
    private Long activityStartTimeBegin;

    /**
     *  活动结束时间
     */
    private Long activityStartTimeEnd;

    //***********分页参数***************//
    /**
     *  当前页码
     */
    private Integer curPage;

    /**
     * pageSize
     */
    private Integer pageSize;

}
