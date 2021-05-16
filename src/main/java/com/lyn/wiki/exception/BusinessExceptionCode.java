package com.lyn.wiki.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BusinessExceptionCode {


    USER_LOGIN_NAME_EXIST("登陆名已存在。"),
    USER_LOGIN_NAME_NOT_EXIST("用户名或密码不正确。"),
    VOTE_REPEAT("你已点赞过。"),

        ;

    private String desc;

}
