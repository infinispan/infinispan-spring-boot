package test.infinispan.autoconfigure.embedded;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import infinispan.autoconfigure.embedded.InfinispanEmbeddedAutoConfiguration;
import infinispan.autoconfigure.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {InfinispanEmbeddedAutoConfiguration.class, InfinispanEmbeddedCacheManagerAutoConfiguration.class})
@TestPropertySource(properties = {"infinispan.embedded.enabled=false", "infinispan.embedded.caching.enabled=false"})
public class InfinispanEmbeddedDisableTest {

   @Autowired
   private ListableBeanFactory beanFactory;

   @Test
   public void testDefaultClient() {
      assertThat(beanFactory.containsBeanDefinition("defaultCacheManager")).isFalse();
   }
}
