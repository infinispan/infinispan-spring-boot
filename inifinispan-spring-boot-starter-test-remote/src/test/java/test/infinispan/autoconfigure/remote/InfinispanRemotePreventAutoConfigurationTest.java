package test.infinispan.autoconfigure.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { InfinispanRemoteAutoConfiguration.class})
public class InfinispanRemotePreventAutoConfigurationTest {

    @Autowired
    private ListableBeanFactory beanFactory;

    @Test
    public void testIfNoDefaultClientWasCreated() {
        assertThat(beanFactory.getBeansOfType(RemoteCacheManager.class)).isEmpty();
    }

    @Test
    public void testIfNoEmbeddedCacheManagerWasCreated() {
        assertThat(beanFactory.containsBeanDefinition("defaultCacheManager")).isFalse();
    }
}
