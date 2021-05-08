package com.lyn.wiki.handler;

import com.lyn.wiki.exception.BusinessException;
import com.lyn.wiki.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BindException.class)
    public CommonResp filedExceptionHandler(BindException e){
        CommonResp commonResp = new CommonResp();
        commonResp.setSuccess(false);
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
//        String field = e.getFieldError().getField();
        for (ObjectError allError : allErrors) {
            String message = allError.getDefaultMessage();
            commonResp.setMessage(message);
        }
        return commonResp;
    }

    @ExceptionHandler(value = BusinessException.class)
    public CommonResp BusinessExceptionHandler(BusinessException e){
        CommonResp commonResp = new CommonResp();
        log.warn("业务异常:{}",e.getExceptionCode().getDesc());
        commonResp.setMessage(e.getMessage());
        commonResp.setSuccess(false);
        return commonResp;
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResp validExceptionHandler(Exception e){
        CommonResp commonResp = new CommonResp();
        log.error("系统异常:{}",e);
        commonResp.setMessage("系统异常");
        commonResp.setSuccess(false);
        return commonResp;
    }

}
