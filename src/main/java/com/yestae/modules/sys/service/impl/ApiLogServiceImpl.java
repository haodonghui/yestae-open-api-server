package com.yestae.modules.sys.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yestae.common.aspect.ApiLogEntity;
import com.yestae.common.aspect.ApiLogService;
import com.yestae.common.utils.ExecutorsFactory;
import com.yestae.modules.commom.request.RequestContext;
import com.yestae.modules.sys.entity.OpenapiInterfaceEntity;
import com.yestae.modules.sys.entity.OpenapiInterfaceLogEntity;
import com.yestae.modules.sys.entity.OpenapiMchAppEntity;
import com.yestae.modules.sys.service.OpenapiInterfaceLogService;
import com.yestae.modules.sys.service.OpenapiInterfaceService;
import com.yestae.modules.sys.service.OpenapiMchAppService;

@Service("apiLogService")
public class ApiLogServiceImpl implements ApiLogService {

	@Autowired
	private OpenapiInterfaceLogService openapiInterfaceLogService;

	@Autowired
	private OpenapiInterfaceService openapiInterfaceService;
	
	@Autowired
	private OpenapiMchAppService openapiMchAppService;

	@Override
	public void save(ApiLogEntity apiLogEntity) {
		final String appid = RequestContext.get().getAppid();
		ExecutorsFactory.bulid().execute(new Runnable() {
			@Override
			public void run() {
				OpenapiInterfaceLogEntity logEntity = new OpenapiInterfaceLogEntity();
				String url = apiLogEntity.getApiUrl();
				if (url != null && url.startsWith("/")) {
					url = url.substring(1, url.length());
					logEntity.setInterfaceUrl(url);
					OpenapiInterfaceEntity interfaceEntity = openapiInterfaceService.selectByUrl(url);
					if (interfaceEntity != null) {
						logEntity.setInterfaceName(interfaceEntity.getName());
					}
				}
				OpenapiMchAppEntity mchAppEntity = openapiMchAppService.selectByAppCode(appid);
				if(mchAppEntity!=null){
					logEntity.setAppName(mchAppEntity.getAppName());
				}
				logEntity.setAppCode(appid);
				logEntity.setCreatedTime(new Date());
				logEntity.setIp(apiLogEntity.getIp());
				logEntity.setParams(apiLogEntity.getParams());
				logEntity.setReqTime(apiLogEntity.getReqTime());
				logEntity.setResult(apiLogEntity.getResult());
				openapiInterfaceLogService.save(logEntity);
			}

		});
	}

}