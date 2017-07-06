package test.infinispan.autoconfigure;

import infinispan.autoconfigure.embedded.InfinispanEmbeddedAutoConfiguration;
import infinispan.autoconfigure.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        CacheAutoConfiguration.class,
        InfinispanRemoteAutoConfiguration.class,
        InfinispanRemoteCacheManagerAutoConfiguration.class,
        InfinispanEmbeddedAutoConfiguration.class,
        InfinispanEmbeddedCacheManagerAutoConfiguration.class
    },
    properties = {
        "spring.cache.type=NONE",
        "infinispan.remote.server-list=127.0.0.1:6667"
    })
public class CacheDisabledTest {
    @Autowired
    private ApplicationContext context;

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testDefaultCacheManager() {
        context.getBean(DefaultCacheManager.class);
        Assert.fail("No bean of type RemoteCacheManager should have been found as spring.cache.type=none");
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void testRemoteCacheManager() {
        context.getBean(RemoteCacheManager.class);
        Assert.fail("No bean of type RemoteCacheManager should have been found as spring.cache.type=none");
    }
}
