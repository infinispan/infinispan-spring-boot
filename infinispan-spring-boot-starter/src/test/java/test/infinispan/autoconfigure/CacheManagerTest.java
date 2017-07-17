package test.infinispan.autoconfigure;

import org.infinispan.spring.starter.embedded.InfinispanEmbeddedAutoConfiguration;
import org.infinispan.spring.starter.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
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
