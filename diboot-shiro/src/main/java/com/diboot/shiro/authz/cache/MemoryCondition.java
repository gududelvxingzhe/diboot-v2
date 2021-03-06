package com.diboot.shiro.authz.cache;

import com.diboot.shiro.authz.config.AuthConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * memory条件类：用与创建memory缓存对象
 * @author : wee
 * @version : v2.0
 * @Date 2019-08-05  14:39
 */
public class MemoryCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        //获取配置信息
        Boolean enableCached = context.getEnvironment().getProperty("diboot.shiro.auth-configuration.cache.permission-caching-enabled", Boolean.class);
        enableCached = enableCached == null ? true : enableCached;
        AuthConfiguration.Cache.CacheWay cacheWay = context.getEnvironment().getProperty("diboot.shiro.auth-configuration.cache.cache-way", AuthConfiguration.Cache.CacheWay.class);
        cacheWay = cacheWay == null ? AuthConfiguration.Cache.CacheWay.MEMORY : cacheWay;
        return enableCached && AuthConfiguration.Cache.CacheWay.MEMORY.equals(cacheWay);
    }
}
