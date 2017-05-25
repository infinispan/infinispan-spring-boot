package test.infinispan.autoconfigure.embedded;

import infinispan.autoconfigure.embedded.InfinispanEmbeddedAutoConfiguration;
import infinispan.autoconfigure.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static infinispan.autoconfigure.embedded.InfinispanEmbeddedAutoConfiguration.DEFAULT_JMX_DOMAIN;
import static org.assertj.core.api.Assertions.assertThat;


@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {InfinispanEmbeddedAutoConfiguration.class, InfinispanEmbeddedCacheManagerAutoConfiguration.class})
public class InfinispanEmbeddedAutoConfigurationIntegrationTest {

   @Autowired
   EmbeddedCacheManager defaultCacheManager;

   @Autowired
   SpringEmbeddedCacheManager springEmbeddedCacheManager;

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

   @Test
   public void testIfSpringCachingWasCreatedUsingProperEmbeddedCacheManager() throws Exception {
      assertThat(defaultCacheManager).isEqualTo(springEmbeddedCacheManager.getNativeCacheManager());
   }
}