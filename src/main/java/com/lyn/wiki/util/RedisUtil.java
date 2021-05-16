package com.lyn.wiki.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    public  boolean validateRepeat(String key,long second){
        if(redisTemplate.hasKey(key)){
            log.info("key已存在:{}",key);
            return false;
        }else{
            redisTemplate.opsForValue().set(key,key,second, TimeUnit.SECONDS);
            return true;
        }
    }
}
