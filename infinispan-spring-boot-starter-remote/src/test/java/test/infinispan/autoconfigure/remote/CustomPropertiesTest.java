package test.infinispan.autoconfigure.remote;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
        //when
        int portObtainedFromPropertiesFile = remoteCacheManager.getConfiguration().servers().get(0).port();
        boolean tcpNoDelay = remoteCacheManager.getConfiguration().tcpNoDelay();

        //then
        assertThat(portObtainedFromPropertiesFile).isEqualTo(6667);
        assertThat(tcpNoDelay).isFalse();
    }
}
