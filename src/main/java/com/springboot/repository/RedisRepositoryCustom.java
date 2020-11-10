package com.springboot.repository;

import java.util.List;

/**
 * 功能：redis interface
 * @author sunpeng
 * @date 2018
 */
public interface RedisRepositoryCustom {

    // 保存，永久
    void save(String key, String value);

    // 保存，单位分钟
    void saveMinutes(String key, String value, long time);

    // 保存，单位天数
    void saveDays(String key, String value, long time);

    // 设置超时时间，单位分钟
    void expireMinutes(String key, long time);

    // 设置超时时间，单位天数
    void expireDays(String key, long time);

    /**
     * 功能：获取对象
     * 备注：要获取的对象，是非list、String对象，以json形式存储到redis中的
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getClassObj(String key, Class<T> clazz);

    String getString(String key);

    void delete(String key);

    void deleteKeys(List<String> keyList);

    /**
     * 功能：获取自增后的数值
     * 备注：注意进行redis 数据丢失的情形的考虑
     *       所以，需要先 get -> 若不存在时 -> setnx 初始化种子 -> get 再判断一次 -> getIncr
     *                           若存在时   -> getIncr
     * @param key redis中需要存在，不存在的话，会先返回0，然后 1，2，3
     * @return
     */
    Long getIncr(String key);

    // 功能：初始化自增开始数值
    //void setIncr(String key, int value);

    /**
     * 功能：list left push
     * @param listName list名字，注意：名称格式最好是：list:xxx
     * @param value
     */
    void leftPush(String listName, String value);

    /**
     * 功能：right pop
     * @param listName
     * @return
     */
    String rightPop(String listName);
}