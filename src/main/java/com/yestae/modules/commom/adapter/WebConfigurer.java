package com.yestae.modules.commom.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.yestae.modules.commom.Interceptor.RequestInterceptor;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {
	
    @Autowired
    private RequestInterceptor requestInterceptor;
    
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");
    }

}