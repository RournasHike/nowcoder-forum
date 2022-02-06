package com.nowcoder.community.util;

import org.springframework.stereotype.Component;

/**
 * 服务端数据缓存类：缓存用户信息，可以多线程隔离访问，用于替代session
 * @author Alex
 * @version 1.0
 * @date 2022/2/6 11:39
 */
@Component
public class ThreadLocalHolder<T> {
    private ThreadLocal<T> cache = new ThreadLocal<>();

    /**
     * 设置缓存数据
     * @param t
     */
    public void setCache(T t){
        cache.set(t);
    }

    /**
     * 获取缓存数据
     * @return
     */
    public T getCache(){
        return cache.get();
    }

    /**
     * 清除缓存
     */
    public void clear(){
        cache.remove();
    }
}
