package com.lyn.wiki.handler;

import com.lyn.wiki.resp.CommonResp;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BindException.class)
    public CommonResp globalHandler(BindException e){
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
}
