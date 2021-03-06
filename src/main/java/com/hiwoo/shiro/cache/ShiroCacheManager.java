package com.hiwoo.shiro.cache;

import org.apache.shiro.cache.Cache;

/**
 *

 * <p>
 *
 * shiro cache manager 接口
 *
 * <p>
 *
 *
 */
public interface ShiroCacheManager {

    <K, V> Cache<K, V> getCache(String name);

    void destroy();

}

