package test.org.infinispan.spring.starter.embedded.actuator;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.infinispan.spring.starter.embedded.actuator.InfinispanCacheMeterBinderProvider;
import org.infinispan.spring.starter.embedded.actuator.InfinispanCacheMeterBinderProviderAutoConfiguration;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(InfinispanCacheMeterBinderProviderAutoConfiguration.class)
public class CustomCacheMeterBinderProviderConfiguration {


    @Bean(InfinispanCacheMeterBinderProvider.NAME)
    CacheMeterBinderProvider<Cache> customInfinispanCacheMeterBinderProvider() {
        return new CustomMeterBinder();
    }

    public static final class CustomMeterBinder implements CacheMeterBinderProvider<Cache> {

        @Override
        public MeterBinder getMeterBinder(Cache cache, Iterable<Tag> tags) {
            return registry -> {
                //do nothing
            };
        }
    }


}
