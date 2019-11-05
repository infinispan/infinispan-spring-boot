package test.org.infinispan.spring.starter.embedded.actuator;

import org.infinispan.spring.starter.embedded.InfinispanEmbeddedAutoConfiguration;
import org.infinispan.spring.starter.embedded.InfinispanEmbeddedCacheManagerAutoConfiguration;
import org.infinispan.spring.starter.embedded.actuator.InfinispanCacheMeterBinderProvider;
import org.infinispan.spring.starter.embedded.actuator.InfinispanCacheMeterBinderProviderAutoConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.infinispan.spring.starter.embedded.InfinispanEmbeddedAutoConfiguration.DEFAULT_CACHE_MANAGER_QUALIFIER;

@SpringBootTest(
        classes = {
                InfinispanEmbeddedAutoConfiguration.class,
                InfinispanEmbeddedCacheManagerAutoConfiguration.class,
                InfinispanCacheMeterBinderProviderAutoConfiguration.class,
                CustomCacheMeterBinderProviderConfiguration.class
        },
        properties = {
                "spring.main.banner-mode=off",
                "infinispan.embedded.enabled=true",
                "infinispan.embedded.caching.enabled=true"
        }
)
public class InfinispanCustomCacheMeterBinderTest {

    @Autowired
    private ListableBeanFactory beanFactory;

    @Test
    public void testDefaultClient() {
        assertThat(beanFactory.containsBeanDefinition(DEFAULT_CACHE_MANAGER_QUALIFIER)).isTrue();
        assertThat(beanFactory.containsBeanDefinition(InfinispanCacheMeterBinderProvider.NAME)).isTrue();
        assertThat(beanFactory.getBean(InfinispanCacheMeterBinderProvider.NAME))
                .isInstanceOf(CustomCacheMeterBinderProviderConfiguration.CustomMeterBinder.class);
    }
}
