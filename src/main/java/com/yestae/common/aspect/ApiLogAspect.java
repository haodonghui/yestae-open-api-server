package com.yestae.common.aspect;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yestae.common.utils.HttpContextUtils;
import com.yestae.common.utils.IPUtils;
import com.yestae.modules.commom.request.CommonRequest;

/**
 * 
 * api请求切面日志
 *
 * @Package com.yestae.common.aspect
 * @ClassName OpenapiLosAspect
 * @author wangpeng
 * @date 2019年5月14日 上午10:30:52
 *
 */
@Aspect
@Component
public class ApiLogAspect {

	@Autowired
	private ApiLogService apiLogService;

	@Pointcut("@annotation(com.yestae.common.annotation.ApiLog)")
	public void apiPointcut() {

	}

	@Around("apiPointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		// 执行方法
		Object result = point.proceed();
		// 保存日志
		saveApiLog(point.getArgs(), result);

		return result;
	}

	@AfterThrowing(pointcut = "apiPointcut()", throwing = "e")
	public void handleThrowing(JoinPoint joinPoint, Exception e) {
		saveApiLog(joinPoint.getArgs(), e.getMessage());
	}

	private void saveApiLog(Object[] params, Object result) {
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();

		ApiLogEntity apiLogEntity = new ApiLogEntity();
		
		
		// 密码参数隐藏
		apiLogEntity.setParams(JSON.toJSONString(handPrivateInfo(params)));
		// 设置请求结果
		apiLogEntity.setResult(JSON.toJSONString(result));
		apiLogEntity.setReqTime(new Date());
		// 设置IP地址
		apiLogEntity.setIp(IPUtils.getIpAddr(request));
		apiLogEntity.setApiUrl(request.getServletPath());

		apiLogService.save(apiLogEntity);

	}

	/**
	 * 
	 * 隐私信息处理
	 *
	 * @param params
	 * @return
	 * @throws
	 */
	private List<Object> handPrivateInfo(Object[] params) {
		List<Object> paramList = Lists.newArrayList();
		
		if(ArrayUtils.isEmpty(params)){
			return paramList;
		}
		
		for (Object param : params) {
			if(param instanceof CommonRequest){
				CommonRequest commonRequest = (CommonRequest) param;
				//新建请求参数
				CommonRequest logRequestInfo = new CommonRequest();
				BeanUtils.copyProperties(commonRequest, logRequestInfo);
				
				JSONObject bizContentJsonObj = JSON.parseObject(logRequestInfo.getBizcontent());
				if(bizContentJsonObj != null){
					if(StringUtils.isNotBlank(bizContentJsonObj.getString("newPassword"))){
						bizContentJsonObj.put("newPassword", "******");
					}
					if(StringUtils.isNotBlank(bizContentJsonObj.getString("oldPassword"))){
						bizContentJsonObj.put("oldPassword", "******");
					}
					if(StringUtils.isNotBlank(bizContentJsonObj.getString("password"))){
						bizContentJsonObj.put("password", "******");
					}
				}
				logRequestInfo.setBizcontent(JSON.toJSONString(bizContentJsonObj));
				paramList.add(logRequestInfo);
			}else{
				paramList.add(param);
			}
		}
		return paramList;
	}

}
