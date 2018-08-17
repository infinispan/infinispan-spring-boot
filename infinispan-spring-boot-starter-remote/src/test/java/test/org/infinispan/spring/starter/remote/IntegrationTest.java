package test.org.infinispan.spring.starter.remote;

import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.NearCacheMode;
import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.spring.provider.SpringRemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.org.infinispan.spring.starter.remote.testconfiguration.InfinispanCacheTestConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
      InfinispanRemoteAutoConfiguration.class,
      InfinispanRemoteCacheManagerAutoConfiguration.class,
      InfinispanCacheTestConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off",
        "infinispan.remote.client-properties=test-hotrod-client.properties"
    }
)
public class IntegrationTest {

   @Autowired
   private RemoteCacheManager remoteCacheManager;

   @Autowired
   private SpringRemoteCacheManager springRemoteCacheManager;

   @Test
   public void testConfiguredClient() {
      Configuration configuration = remoteCacheManager.getConfiguration();

      assertThat(configuration.servers().get(0).port()).isEqualTo(InfinispanCacheTestConfiguration.PORT);
      assertThat(configuration.nearCache().mode()).isEqualTo(NearCacheMode.INVALIDATED);
      assertThat(configuration.nearCache().maxEntries()).isEqualTo(InfinispanCacheTestConfiguration.NEAR_CACHE_MAX_ENTRIES);
   }

   @Test
   public void testIfSpringCachingWasCreatedUsingProperEmbeddedCacheManager() throws Exception {
      assertThat(remoteCacheManager).isEqualTo(springRemoteCacheManager.getNativeCacheManager());
   }
}
