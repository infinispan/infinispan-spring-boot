package infinispan.testconfiguration;

import infinispan.autoconfigure.InfinispanCacheConfigurer;
import infinispan.autoconfigure.InfinispanGlobalConfigurer;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfinispanCacheTestConfiguration {

    public static final String TEST_CLUSTER = "TEST_CLUSTER";
    public static final String TEST_CACHE_NAME = "test-simple-cache";
    public static final String TEST_GLOBAL_JMX_DOMAIN = "test.spring.infinispan";

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return cacheManager -> {
            final org.infinispan.configuration.cache.Configuration testCache =
                    new ConfigurationBuilder().simpleCache(true)
                                              .eviction().size(1000L).strategy(EvictionStrategy.LRU)
                                              .jmxStatistics().enable()
                                              .build();

            cacheManager.defineConfiguration(TEST_CACHE_NAME, testCache);
        };
    }

    @Bean
    public InfinispanGlobalConfigurer globalConfigurer() {
        return () -> {
            final GlobalConfiguration globalConfiguration = new GlobalConfigurationBuilder()
                    .transport().clusterName(TEST_CLUSTER)
                    .globalJmxStatistics().jmxDomain(TEST_GLOBAL_JMX_DOMAIN).enable()
                    .build();

            return globalConfiguration;
        };
    }
}
