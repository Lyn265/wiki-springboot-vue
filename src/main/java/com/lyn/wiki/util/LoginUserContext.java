package com.lyn.wiki.util;

import com.lyn.wiki.resp.UserLoginResp;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class LoginUserContext implements Serializable {
    //当前用户线程
    private static ThreadLocal<UserLoginResp> user = new ThreadLocal<>();

    public static UserLoginResp getUser() {
        return user.get();
    }

    public static void setUser(UserLoginResp user) {
        LoginUserContext.user.set(user);
    }
}
