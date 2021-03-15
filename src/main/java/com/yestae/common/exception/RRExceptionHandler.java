/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.exception;

import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.utils.R;

/**
 * 异常处理器
 *
 * @author daniel
 */
@RestControllerAdvice
public class RRExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());
//
	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public R handleRRException(RRException e){
		String code = e.getCode().replaceAll("\\.", "_").toUpperCase();
		String desc = ApiErrorMsgEnums.getDescByCode(code);
		return R.error(code, desc==null?e.getMessage():desc);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public R handlerNoFoundException(Exception e) {
		logger.error(e.getMessage(), e);
		return R.error(ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode(), ApiErrorMsgEnums.SYSTEM_ERROR.getDesc());
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error(ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode(), ApiErrorMsgEnums.SYSTEM_ERROR.getDesc());
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error(ApiErrorMsgEnums.ACCESS_FORBIDDEN.getErrCode(), ApiErrorMsgEnums.ACCESS_FORBIDDEN.getDesc());
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error(ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode(), ApiErrorMsgEnums.SYSTEM_ERROR.getDesc());
	}
	
	/**
	 * 
	 * 参数校验异常
	 *
	 * @param e
	 * @return
	 * @throws
	 */
	@ExceptionHandler(BindException.class)
	public R handleException(BindException e){
		logger.error(e.getMessage(), e);
		List<ObjectError> allErrors = e.getAllErrors();
		
		List<String> msgList = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(allErrors)){
			allErrors.forEach(item -> {
				msgList.add(item.getDefaultMessage());
			});
		}
		
		//没有异常时返回系统默认异常
		String msg = CollectionUtils.isNotEmpty(msgList) ? Joiner.on(", ").join(msgList) : ApiErrorMsgEnums.ERROR_PARAMETER.getDesc();
		return R.error(ApiErrorMsgEnums.ERROR_PARAMETER.getErrCode(), msg);
	}
	
	@ExceptionHandler(ValidationException.class)
	public R handle(ValidationException e) {
		logger.error(e.getMessage(), e);
    	return R.error(ApiErrorMsgEnums.MISSING_PARAMETER.getErrCode(), e.getMessage());
    }
}
