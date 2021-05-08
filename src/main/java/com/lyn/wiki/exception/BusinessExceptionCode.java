package com.lyn.wiki.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionCode {


    USER_LOGIN_NAME_EXIST("登陆名已存在。"),
        ;

    private String desc;

}
