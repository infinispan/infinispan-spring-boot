package test.infinispan.autoconfigure;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.spring.starter.embedded.InfinispanEmbeddedAutoConfiguration;
import org.infinispan.spring.starter.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
      classes = {
            InfinispanRemoteAutoConfiguration.class,
            InfinispanRemoteCacheManagerAutoConfiguration.class,
            InfinispanEmbeddedAutoConfiguration.class,
            InfinispanEmbeddedCacheManagerAutoConfiguration.class
      },
      properties = {
            "infinispan.remote.server-list=127.0.0.1:6667"
      })
public class CacheManagerTest {
   @Autowired
   private ApplicationContext context;

   @Test
   public void testRemoteCacheManager() {
      Assert.assertNotNull(context.getBean(RemoteCacheManager.class));
   }

   @Test
   public void testDefaultCacheManager() {
      Assert.assertNotNull(context.getBean(DefaultCacheManager.class));
   }
}
