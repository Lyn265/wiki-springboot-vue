package com.lyn.wiki.util;

import org.springframework.stereotype.Component;

@Component
public class RequestContext {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static String getIp() {
        return threadLocal.get();
    }

    public static void setIp(String  ip) {
        RequestContext.threadLocal.set(ip);
    }
}
