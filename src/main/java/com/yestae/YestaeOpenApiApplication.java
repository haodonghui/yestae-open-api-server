/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;



@SpringBootApplication
@ImportResource(value="classpath:spring-dubbo-client.xml")
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class YestaeOpenApiApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(YestaeOpenApiApplication.class, args);
	}

//	private CorsConfiguration buildConfig() {  
//        CorsConfiguration corsConfiguration = new CorsConfiguration();  
//        corsConfiguration.addAllowedOrigin("*");  
//        corsConfiguration.addAllowedHeader("*");  
//        corsConfiguration.addAllowedMethod("*");  
//        return corsConfiguration;  
//    }  
      
    /** 
     * 跨域过滤器 
     * @return 
     */  
//    @Bean  
//    public CorsFilter corsFilter() {  
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
//        source.registerCorsConfiguration("/**", buildConfig()); // 4  
//        return new CorsFilter(source);  
//    } 	
	
//	@Bean
//	public WebMvcConfigurer corsConfigurer() {
//		return new WebMvcConfigurerAdapter() {
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**")
//						.allowedOrigins("**")
//						// 可访问ip，ip最好从配置文件中获取，
//						.allowedMethods("PUT", "DELETE", "GET", "POST").allowedHeaders("*")
//						.exposedHeaders("access-control-allow-headers", "access-control-allow-methods", "access-control-allow-origin", "access-control-max-age", "X-Frame-Options")
//						.allowCredentials(false).maxAge(3600);
//			}
//		};
//
//	}
}