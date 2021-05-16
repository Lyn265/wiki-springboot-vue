package com.lyn.wiki.config;

import com.lyn.wiki.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                    "/test/**",
                    "/redis/**",
                    "/user/login",
                    "/category/all",
                    "/ebook/list",
                    "/doc/all/**",
                    "/doc/vote/**",
                    "/doc/find-content/**",
                    "/ebook-snapshot/**"
                );
    }
}
