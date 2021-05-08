package com.lyn.wiki.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    public BusinessExceptionCode exceptionCode;

    public BusinessException(BusinessExceptionCode exceptionCode){
        super(exceptionCode.getDesc());
        this.exceptionCode = exceptionCode;
    }

    @Override
    public  Throwable fillInStackTrace() {
        return this;
    }
}
