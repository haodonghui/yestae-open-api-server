/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.config.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yestae.common.utils.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.utils.HttpContextUtils;
import com.yestae.common.utils.R;

/**
 * oauth2过滤器
 *
 * @author daniel
 */
@Slf4j
public class OAuth2Filter2 extends AuthenticatingFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求appId应用编码
        String mchid = getRequestMchid((HttpServletRequest) request);
        String appid = getRequestAppid((HttpServletRequest) request);

        if(StringUtils.isBlank(mchid) || StringUtils.isBlank(appid)){
            return null;
        }
        //商户id和appid作为本次调用的token
        return new OAuth2Token2(mchid+","+appid);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String mchid = getRequestMchid((HttpServletRequest) request);
        String appid = getRequestAppid((HttpServletRequest) request);
        if(StringUtils.isBlank(mchid) || StringUtils.isBlank(appid)){
            LogUtil.info(log, "mchid is null or appid is null. param[mchid#{}, appid#{}], ACCESS_FORBIDDEN ", mchid, appid);
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
            httpResponse.setCharacterEncoding("UTF-8");
           String  json = JSONObject.toJSONString(R.error(ApiErrorMsgEnums.ACCESS_FORBIDDEN.getErrCode(), ApiErrorMsgEnums.ACCESS_FORBIDDEN.getDesc()));

            httpResponse.getWriter().print(json);

            return false;
        }

        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtils.getOrigin());
        try {
            //处理登录失败的异常
            Throwable throwable = e.getCause() == null ? e : e.getCause();

            LogUtil.error(log, e,"onLoginFailure, ACCESS_FORBIDDEN ");
            R r = R.error(ApiErrorMsgEnums.ACCESS_FORBIDDEN.getErrCode(), ApiErrorMsgEnums.ACCESS_FORBIDDEN.getDesc());

            String json = JSONObject.toJSONString(r);
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestMchid(HttpServletRequest httpRequest){
        //从header中获取token
        String mchid = httpRequest.getHeader("mchid");

//		if(StringUtils.isBlank(token)){
//			token = "88888888";
//		}

        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(mchid)){
        	mchid = httpRequest.getParameter("mchid");
        }

        return mchid;
    }
    private String getRequestAppid(HttpServletRequest httpRequest){
    	//从header中获取token
    	String mchid = httpRequest.getHeader("appid");
    	
//		if(StringUtils.isBlank(token)){
//			token = "88888888";
//		}
    	
    	//如果header中不存在token，则从参数中获取token
    	if(StringUtils.isBlank(mchid)){
    		mchid = httpRequest.getParameter("appid");
    	}
    	
    	return mchid;
    }


}
