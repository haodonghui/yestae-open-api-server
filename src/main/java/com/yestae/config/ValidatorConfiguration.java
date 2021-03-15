package com.yestae.config;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * 请求参数校验
 *
 * @Package com.yestae.config 
 * @ClassName ValidatorConfiguration 
 * @author wangpeng
 * @date 2019年5月14日 下午6:36:55 
 *
 */
@Configuration
public class ValidatorConfiguration {
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //第一个参数校验不通过直接失败，默认参数全部交验完之后统一抛出异常
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        return validator;
    }
}