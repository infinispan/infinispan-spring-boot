package test.infinispan.autoconfigure.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infinispan.autoconfigure.remote.InfinispanRemoteAutoConfiguration;
import infinispan.autoconfigure.remote.InfinispanRemoteCacheManagerAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {InfinispanRemoteAutoConfiguration.class, InfinispanRemoteCacheManagerAutoConfiguration.class})
@TestPropertySource(properties = {"infinispan.remote.client-properties=test-hotrod-client.properties", "infinispan.remote.enabled=false"})
public class DisablingTest {

   @Autowired
   private ListableBeanFactory beanFactory;

   @Test
   public void testDefaultClient() {
      assertThat(beanFactory.containsBeanDefinition("remoteCacheManager")).isFalse();
   }
}
