/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.config.oauth2;

import java.util.List;
import java.util.Set;

import com.yestae.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.modules.sys.entity.OpenapiMchAppInterfaceEntity;
import com.yestae.modules.sys.entity.OpenapiMchEntity;
import com.yestae.modules.sys.service.OpenapiMchAppInterfaceService;
import com.yestae.modules.sys.service.OpenapiMchService;

/**
 * 认证
 *
 * @author daniel
 */
@Slf4j
@Component
public class OAuth2Realm2 extends AuthorizingRealm {

	 @Autowired
    private OpenapiMchService openapiMchService;
	 
	 @Autowired
    private OpenapiMchAppInterfaceService openapiMchAppInterfaceService;
	 
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token2;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
		//通过appId获取接口授权信息
		OpenapiMchEntity mch = (OpenapiMchEntity) principals.getPrimaryPrincipal();
		//获取该商户所有授权接口权限
		List<OpenapiMchAppInterfaceEntity> list = openapiMchAppInterfaceService
				.list(new QueryWrapper<OpenapiMchAppInterfaceEntity>().eq("mch_code", mch.getMchCode()).eq("app_code", mch.getAppCode()));
		if (CollectionUtils.isEmpty(list)) {
			LogUtil.info(log, "openapiMchAppInterfaceEntity is null. param[mchid#{}, appid#{}], ACCESS_FORBIDDEN ", mch.getMchCode(), mch.getAppCode());
			throw new RRException(ApiErrorMsgEnums.ACCESS_FORBIDDEN.getDesc(),ApiErrorMsgEnums.ACCESS_FORBIDDEN.getErrCode());
		}
		List<String> mchCodeList = Lists.transform(list, new Function<OpenapiMchAppInterfaceEntity, String>() {
			@Override
			public String apply(OpenapiMchAppInterfaceEntity input) {
				return input.getPermCode();
			}
		});
		Set<String> permsSet = Sets.newHashSet(mchCodeList);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
 
		String mchidAndAppid = (String) token.getPrincipal();
        LogUtil.info(log, "doGetAuthenticationInfo. token#{} ", mchidAndAppid);
		String mchid = mchidAndAppid.split(",")[0];
		String appid = mchidAndAppid.split(",")[1];
		OpenapiMchEntity mch = openapiMchService.getOne(new QueryWrapper<OpenapiMchEntity>().eq("mch_code", mchid).eq("mch_state", 0));
		if (mch == null) {
//			return null;
            LogUtil.info(log, "openapiMchEntity is null. param[mchid#{}, appid#{}], ACCESS_FORBIDDEN ", mchid, appid);
			throw new RRException(ApiErrorMsgEnums.ACCESS_FORBIDDEN.getDesc(),ApiErrorMsgEnums.ACCESS_FORBIDDEN.getErrCode());
		}
		mch.setAppCode(appid);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(mch, mchidAndAppid, getName());
		return info;

        
    }
}
