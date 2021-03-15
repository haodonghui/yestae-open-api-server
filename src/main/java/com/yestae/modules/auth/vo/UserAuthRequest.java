package com.yestae.modules.auth.vo;

import com.yestae.common.validator.group.BankCardVerifyGroup;
import com.yestae.common.validator.group.IdCardVerifyGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangpeng
 * @title: UserAuthRequest
 * @packageName: com.yestae.modules.auth.vo
 * @description: 实名请求实体
 * @date 2020-02-17 14:32
 */
@Data
public class UserAuthRequest implements Serializable {

    private static final long serialVersionUID = -3613712299375339330L;

    @NotBlank(message="uid不能为空", groups = {IdCardVerifyGroup.class, BankCardVerifyGroup.class})
    private String uid;
    @NotBlank(message="realName不能为空", groups = {IdCardVerifyGroup.class, BankCardVerifyGroup.class})
    private String realName;
    @NotBlank(message="idCard不能为空", groups = {IdCardVerifyGroup.class, BankCardVerifyGroup.class})
    private String idCard;
    @NotBlank(message="bankNo不能为空", groups = {BankCardVerifyGroup.class})
    private String bankNo;
    @NotBlank(message="mobile不能为空", groups = {IdCardVerifyGroup.class})
    private String mobile;

}
