package cc.kevinlu.quartz.web.global;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import cc.kevinlu.quartz.bean.base.BaseResult;
import cc.kevinlu.quartz.common.Constants;
import cc.kevinlu.quartz.common.exception.ConflictException;
import cc.kevinlu.quartz.common.exception.ParamErrorException;
import cc.kevinlu.quartz.error.CommonError;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常控制
 *
 * @author: cc
 **/
@Slf4j
@ControllerAdvice
@Order(1)
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = { ConflictException.class })
    public BaseResult handleConflictException(ConflictException conflictException) {
        log.error(conflictException.getMessage(), conflictException);
        return BaseResult.fail(conflictException.getCode(), conflictException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { ParamErrorException.class })
    public BaseResult handleParamErrorException(ParamErrorException paramErrorException) {
        log.error(paramErrorException.getMessage(), paramErrorException);
        return BaseResult.fail(paramErrorException.getCode(), paramErrorException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
    public BaseResult handleMethodNotSupportedException(HttpRequestMethodNotSupportedException notSupportedException) {
        log.error(notSupportedException.getMessage(), notSupportedException);
        return BaseResult.fail(notSupportedException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { InvalidFormatException.class })
    public BaseResult handleInvalidFormatException(InvalidFormatException invalidFormatException) {
        log.error(invalidFormatException.getMessage(), invalidFormatException);
        return BaseResult.fail(invalidFormatException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public BaseResult handleHttpMessageNotReadableException(HttpMessageNotReadableException hnrException) {
        log.error(hnrException.getMessage(), hnrException);
        return BaseResult.fail(hnrException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { MethodArgumentTypeMismatchException.class })
    public BaseResult handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException mArgumentTypeMisMatchException) {
        log.error(mArgumentTypeMisMatchException.getMessage(), mArgumentTypeMisMatchException);
        return BaseResult.fail(mArgumentTypeMisMatchException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public BaseResult handleValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        String errorMessage = CommonError.PARAM_INPUT_ILLEGAL_ERROR.getMessage();
        if (bindingResult != null && bindingResult.getFieldError() != null) {
            errorMessage = bindingResult.getFieldError().getDefaultMessage();
        }
        log.error(errorMessage, methodArgumentNotValidException);

        return BaseResult.fail(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(value = { BindException.class })
    public BaseResult handleBindException(BindException bindException) {

        String errorMessage = CommonError.PARAM_INPUT_ILLEGAL_ERROR.getMessage();
        BindingResult bindingResult = bindException.getBindingResult();
        if (bindingResult != null && bindingResult.getFieldError() != null) {
            errorMessage = bindingResult.getFieldError().getDefaultMessage();
        }
        log.error(errorMessage, bindException);

        return BaseResult.fail(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(value = { DataIntegrityViolationException.class })
    public BaseResult handleOtherException(DataIntegrityViolationException dataIntegrityViolationException) {
        log.error(dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);
        String msg = "";
        if (dataIntegrityViolationException.getMessage().contains(Constants.DATA_TOO_LONG)) {
            msg = "参数超过最大长度限制";
        } else {
            msg = "请求参数异常";
        }
        return BaseResult.fail(CommonError.PARAM_INPUT_ILLEGAL_ERROR);
    }

    @ResponseBody
    @ExceptionHandler(value = { Exception.class })
    public BaseResult handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return BaseResult.fail(CommonError.PARAM_INPUT_ILLEGAL_ERROR);
    }

}
