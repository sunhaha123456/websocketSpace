package com.springboot.repository.impl;

import com.springboot.repository.RedisRepositoryCustom;
import com.springboot.util.JsonUtil;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepositoryCustomImpl implements RedisRepositoryCustom {

    @Inject
    StringRedisTemplate template;

    public void save(String key, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value);
    }

    public void saveMinutes(String key, String value, long time) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, time, TimeUnit.MINUTES);
    }

    public void saveDays(String key, String value, long time) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, time, TimeUnit.DAYS);
    }

    public void expireMinutes(String key, long time) {
        template.expire(key, time, TimeUnit.MINUTES);
    }

    public void expireDays(String key, long time) {
        template.expire(key, time, TimeUnit.DAYS);
    }

    public <T> T getClassObj(String key, Class<T> clazz) {
        String value = getString(key);
        T t = null;
        if (value != null && value.length() > 0) {
            t = JsonUtil.jsonToObject(value, clazz);
        }
        return t;
    }

    public String getString(String key) {
        ValueOperations<String, String> ops = template.opsForValue();
        String value = ops.get(key);
        return value;
    }

    public void delete(String key) {
        template.delete(key);
    }

    public void deleteKeys(List<String> keyList) {
        template.delete(keyList);
    }

    public Long getIncr(String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, template.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        //entityIdCounter.expire(0, TimeUnit.SECONDS);
        return increment;
    }

//    public void setIncr(String key, int value) {
//        RedisAtomicLong counter = new RedisAtomicLong(key, template.getConnectionFactory());
//        counter.set(value);
//        //counter.expire(0, TimeUnit.SECONDS);
//    }

    @Override
    public void leftPush(String listName, String value) {
        ListOperations<String, String> listOps = template.opsForList();
        listOps.leftPush(listName, value);
    }

    @Override
    public String rightPop(String listName) {
        ListOperations<String, String> listOps = template.opsForList();
        return listOps.rightPop(listName);
    }
}