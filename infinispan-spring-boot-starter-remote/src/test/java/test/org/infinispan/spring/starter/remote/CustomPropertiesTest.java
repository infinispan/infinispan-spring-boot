package test.org.infinispan.spring.starter.remote;

import org.infinispan.client.hotrod.configuration.NearCacheConfiguration;
import org.infinispan.client.hotrod.configuration.NearCacheMode;
import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        InfinispanRemoteAutoConfiguration.class,
        InfinispanRemoteCacheManagerAutoConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off",
        "infinispan.remote.client-properties=test-hotrod-client.properties"
    }
)
public class CustomPropertiesTest {

    @Autowired
    private RemoteCacheManager remoteCacheManager;

    @Test
    public void testDefaultClient() {
        int portObtainedFromPropertiesFile = remoteCacheManager.getConfiguration().servers().get(0).port();
        boolean tcpNoDelay = remoteCacheManager.getConfiguration().tcpNoDelay();

        assertThat(portObtainedFromPropertiesFile).isEqualTo(6667);
        assertThat(tcpNoDelay).isFalse();

        NearCacheConfiguration nearCacheConfiguration = remoteCacheManager.getConfiguration().nearCache();

        assertThat(nearCacheConfiguration.maxEntries()).isEqualTo(1000);
        assertThat(nearCacheConfiguration.mode()).isEqualTo(NearCacheMode.INVALIDATED);
        assertThat(nearCacheConfiguration.cacheNamePattern().toString()).isEqualTo("cus*");
    }
}
