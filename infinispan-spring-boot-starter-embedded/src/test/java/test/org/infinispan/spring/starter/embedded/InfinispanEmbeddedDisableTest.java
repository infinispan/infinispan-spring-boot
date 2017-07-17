package test.org.infinispan.spring.starter.embedded;

import static org.assertj.core.api.Assertions.assertThat;

import org.infinispan.spring.starter.embedded.InfinispanEmbeddedAutoConfiguration;
import org.infinispan.spring.starter.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        InfinispanEmbeddedAutoConfiguration.class,
        InfinispanEmbeddedCacheManagerAutoConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off",
        "infinispan.embedded.enabled=false",
        "infinispan.embedded.caching.enabled=false"
    }
)
public class InfinispanEmbeddedDisableTest {

   @Autowired
   private ListableBeanFactory beanFactory;

   @Test
   public void testDefaultClient() {
      assertThat(beanFactory.containsBeanDefinition("defaultCacheManager")).isFalse();
   }
}
