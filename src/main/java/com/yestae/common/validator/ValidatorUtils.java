/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.validator;

import com.yestae.common.exception.RRException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

import java.util.Set;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 * @author daniel
 */
public class ValidatorUtils {
    private static Validator validator;

    static {
    	ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //第一个参数校验不通过直接失败，默认参数全部交验完之后统一抛出异常
                .addProperty("hibernate.validator.fail_fast", "true")
                .buildValidatorFactory();
         validator = validatorFactory.getValidator();
    }

    /**
     * 校验对象
     * @param object        待校验对象
     * @param groups        待校验的组
     * @throws RRException  校验不通过，则报RRException异常
     */
    public static void validateEntity(Object object, Class<?>... groups)
            throws RRException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            StringBuilder msg = new StringBuilder();
            for(ConstraintViolation<Object> constraint:  constraintViolations){
                msg.append(constraint.getMessage());
            }
            throw new ValidationException(msg.toString());
        }
    }
}
