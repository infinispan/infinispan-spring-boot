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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        "spring.main.banner-mode=off",
        "infinispan.remote.server-list=127.0.0.1:6667"
    })
public class ApplicationPropertiesTest {
    @Autowired
    private RemoteCacheManager remoteCacheManager;
    @Autowired
    private DefaultCacheManager embeddedCacheManager;

    @Test
    public void testDefaultClient() {
        Assert.assertNotNull(remoteCacheManager);
        Assert.assertNotNull(embeddedCacheManager);
    }
}
