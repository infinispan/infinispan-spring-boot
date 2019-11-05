package org.infinispan.spring.starter.embedded.actuator;

import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider")
@ConditionalOnProperty(value = "infinispan.embedded.enabled", havingValue = "true", matchIfMissing = true)
public class InfinispanCacheMeterBinderProviderAutoConfiguration {

    @Bean(InfinispanCacheMeterBinderProvider.NAME)
    @ConditionalOnMissingBean
    CacheMeterBinderProvider<Cache> infinispanCacheMeterBinderProvider() {
        return new InfinispanCacheMeterBinderProvider();
    }
}
