package test.org.infinispan.spring.starter.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteCacheManagerAutoConfiguration;
import org.infinispan.spring.starter.remote.actuator.RemoteInfinispanCacheMeterBinderProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
      classes = {
            InfinispanRemoteAutoConfiguration.class,
            InfinispanRemoteCacheManagerAutoConfiguration.class},
      properties = {
            "spring.main.banner-mode=off",
            "infinispan.remote.client-properties=test-hotrod-client.properties",
            "infinispan.remote.enabled=false"
      }
)
public class DisablingTest {

   @Autowired
   private ListableBeanFactory beanFactory;

   @Test
   public void testDefaultClient() {
      assertThat(beanFactory.containsBeanDefinition(InfinispanRemoteAutoConfiguration.REMOTE_CACHE_MANAGER_BEAN_QUALIFIER)).isFalse();
      assertThat(beanFactory.containsBeanDefinition(RemoteInfinispanCacheMeterBinderProvider.NAME)).isFalse();
   }
}
