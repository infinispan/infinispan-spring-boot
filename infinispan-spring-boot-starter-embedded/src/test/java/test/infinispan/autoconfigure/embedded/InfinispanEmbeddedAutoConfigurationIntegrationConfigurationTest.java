package test.infinispan.autoconfigure.embedded;

import infinispan.autoconfigure.embedded.InfinispanEmbeddedAutoConfiguration;
import infinispan.autoconfigure.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.infinispan.eviction.EvictionType;
import org.infinispan.manager.EmbeddedCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.infinispan.autoconfigure.testconfiguration.InfinispanCacheConfigurationBaseTestConfiguration;
import test.infinispan.autoconfigure.testconfiguration.InfinispanCacheConfigurationTestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
   classes = {
      InfinispanEmbeddedAutoConfiguration.class,
      InfinispanEmbeddedCacheManagerAutoConfiguration.class,
      InfinispanCacheConfigurationBaseTestConfiguration.class,
      InfinispanCacheConfigurationTestConfiguration.class
   },
   properties = {
      "spring.main.banner-mode=off"
   }
)
public class InfinispanEmbeddedAutoConfigurationIntegrationConfigurationTest {
   @Autowired
   EmbeddedCacheManager manager;

   @Test
   public void testConfiguration() {
      assertThat(manager.getCacheNames()).contains("base-cache", "small-cache", "large-cache");
      assertThat(manager.getCacheConfiguration("base-cache").memory().size()).isEqualTo(500L);
      assertThat(manager.getCacheConfiguration("base-cache").memory().evictionType()).isEqualTo(EvictionType.COUNT);
      assertThat(manager.getCacheConfiguration("small-cache").memory().size()).isEqualTo(1000L);
      assertThat(manager.getCacheConfiguration("small-cache").memory().evictionType()).isEqualTo(EvictionType.MEMORY);
      assertThat(manager.getCacheConfiguration("large-cache").memory().size()).isEqualTo(2000L);
      assertThat(manager.getCacheConfiguration("large-cache").memory().evictionType()).isEqualTo(EvictionType.COUNT);
   }
}
