package test.infinispan.autoconfigure.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.spring.provider.SpringRemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import test.infinispan.autoconfigure.testconfiguration.InfinispanCacheTestConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {InfinispanRemoteAutoConfiguration.class, InfinispanRemoteCacheManagerAutoConfiguration.class, InfinispanCacheTestConfiguration.class})
@TestPropertySource(properties = "infinispan.remote.client-properties=test-hotrod-client.properties")
public class IntegrationTest {

   @Autowired
   private RemoteCacheManager remoteCacheManager;

   @Autowired
   private SpringRemoteCacheManager springRemoteCacheManager;

   @Test
   public void testConfiguredClient() {
      //when
      int portObtainedFromPropertiesFile = remoteCacheManager.getConfiguration().servers().get(0).port();

      //then
      assertThat(portObtainedFromPropertiesFile).isEqualTo(InfinispanCacheTestConfiguration.PORT);
   }

   @Test
   public void testIfSpringCachingWasCreatedUsingProperEmbeddedCacheManager() throws Exception {
      assertThat(remoteCacheManager).isEqualTo(springRemoteCacheManager.getNativeCacheManager());
   }
}
