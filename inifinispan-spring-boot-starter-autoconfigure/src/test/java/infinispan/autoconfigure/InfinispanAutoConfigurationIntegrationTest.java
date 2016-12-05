package infinispan.autoconfigure;

import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static infinispan.autoconfigure.InfinispanAutoConfiguration.DEFAULT_JMX_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(InfinispanAutoConfiguration.class)
public class InfinispanAutoConfigurationIntegrationTest {

    @Autowired
    EmbeddedCacheManager defaultCacheManager;

    @Test
    public void contextLoads() {

    }

    @Test
    public void testAutowired() {
        assertThat(defaultCacheManager).isNotNull();
    }

    @Test
    public void testDefaultConfigurations() {
        assertThat(defaultCacheManager.getClusterName()).isEqualTo("default-autoconfigure");
        assertThat(defaultCacheManager.getCacheNames()).isEmpty();

        final Configuration defaultCacheConfiguration = defaultCacheManager.getDefaultCacheConfiguration();
        assertThat(defaultCacheConfiguration.jmxStatistics().enabled()).isFalse();

        final GlobalConfiguration globalConfiguration = defaultCacheManager.getCacheManagerConfiguration();
        assertThat(globalConfiguration.globalJmxStatistics().enabled()).isTrue();
        assertThat(globalConfiguration.globalJmxStatistics().domain()).isEqualTo(DEFAULT_JMX_DOMAIN);
    }
}