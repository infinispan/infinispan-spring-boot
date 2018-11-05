package org.infinispan.spring.starter.embedded.actuator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.MeterBinder;

/**
 * When actuate dependency is found in the classpath, this component links Infinispan cache metrics with Actuator
 *
 * @author Katia Aresti, karesti@redtat.com
 * @since 2.1
 */
@Component
@Qualifier(InfinispanCacheMeterBinderProvider.NAME)
@ConditionalOnClass(name = "org.springframework.boot.actuate.metrics.cache.CacheMeterBinderProvider")
public class InfinispanCacheMeterBinderProvider implements CacheMeterBinderProvider<Cache> {

   public static final String NAME = "infinispanCacheMeterBinderProvider";

   @Override
   public MeterBinder getMeterBinder(Cache cache, Iterable<Tag> tags) {
      return new InfinispanCacheMeterBinder((org.infinispan.Cache) cache.getNativeCache(), tags);
   }
}
