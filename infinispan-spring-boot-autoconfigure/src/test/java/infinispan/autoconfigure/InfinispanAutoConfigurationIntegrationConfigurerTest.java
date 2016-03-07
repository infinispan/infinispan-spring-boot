package infinispan.autoconfigure;

import infinispan.testconfiguration.InfinispanCacheTestConfiguration;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;

import static infinispan.testconfiguration.InfinispanCacheTestConfiguration.TEST_CACHE_NAME;
import static infinispan.testconfiguration.InfinispanCacheTestConfiguration.TEST_CLUSTER;
import static infinispan.testconfiguration.InfinispanCacheTestConfiguration.TEST_GLOBAL_JMX_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { InfinispanAutoConfiguration.class
                                          , InfinispanCacheTestConfiguration.class })
public class InfinispanAutoConfigurationIntegrationConfigurerTest {

    @Autowired
    EmbeddedCacheManager defaultCacheManager;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testWithCacheConfigurer() {
        assertThat(defaultCacheManager.getCacheNames()).isEqualTo(
                Collections.singleton(TEST_CACHE_NAME));

        final Configuration testCacheConfiguration = defaultCacheManager.getCacheConfiguration(TEST_CACHE_NAME);
        assertThat(testCacheConfiguration.jmxStatistics().enabled()).isTrue();
        assertThat(testCacheConfiguration.eviction().size()).isEqualTo(1000L);
        assertThat(testCacheConfiguration.eviction().strategy()).isEqualTo(EvictionStrategy.LRU);
    }

    @Test
    public void testWithGlobalConfigurer() {
        final GlobalConfiguration globalConfiguration = defaultCacheManager.getCacheManagerConfiguration();

        assertThat(globalConfiguration.globalJmxStatistics().enabled()).isTrue();
        assertThat(globalConfiguration.globalJmxStatistics().domain()).isEqualTo(TEST_GLOBAL_JMX_DOMAIN);
        assertThat(globalConfiguration.transport().clusterName()).isEqualTo(TEST_CLUSTER);
    }
}
