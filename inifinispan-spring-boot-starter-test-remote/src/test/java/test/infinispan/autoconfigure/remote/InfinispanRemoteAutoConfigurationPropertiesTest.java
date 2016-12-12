package test.infinispan.autoconfigure.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { InfinispanRemoteAutoConfiguration.class})
@TestPropertySource(properties = "infinispan.remote.client-properties=test-hotrod-client.properties")
public class InfinispanRemoteAutoConfigurationPropertiesTest {

    @Autowired
    private RemoteCacheManager remoteCacheManager;

    @Test
    public void testDefaultClient() {
        //when
        int portObtainedFromPropertiesFile = remoteCacheManager.getConfiguration().servers().get(0).port();

        //then
        assertThat(portObtainedFromPropertiesFile).isEqualTo(6667);
    }
}
