package com.posppay.newpay.common.utils;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author wwa
 */
@Component
@Slf4j
public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private ValueOperations<String, String> valueOperations;
    @Autowired
    private HashOperations<String, String, Object> hashOperations;
    @Autowired
    private ListOperations<String, Object> listOperations;
    @Autowired
    private SetOperations<String, Object> setOperations;
    @Autowired
    private ZSetOperations<String, Object> zSetOperations;
    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;
    private final static Gson GSON = new Gson();

    public void set(String key, Object value, long expire) {
        try {
            valueOperations.set(key, toJson(value));
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        try {
            String value = valueOperations.get(key);
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            return value == null ? null : fromJson(value, clazz);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        try {
            String value = valueOperations.get(key);
            if (expire != NOT_EXPIRE) {
                redisTemplate.expire(key, expire, TimeUnit.SECONDS);
            }
            return value;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return GSON.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
