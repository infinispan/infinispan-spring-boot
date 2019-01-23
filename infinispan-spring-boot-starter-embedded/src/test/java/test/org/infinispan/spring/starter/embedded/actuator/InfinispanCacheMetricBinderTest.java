package test.org.infinispan.spring.starter.embedded.actuator;

import static java.util.Collections.emptyList;

import org.infinispan.Cache;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.starter.embedded.actuator.InfinispanCacheMeterBinder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinderCompatibilityKit;

@ExtendWith(SpringExtension.class)
public class InfinispanCacheMetricBinderTest extends CacheMeterBinderCompatibilityKit {

   private static EmbeddedCacheManager cacheManager;
   private Cache<String, String> cache;

   @AfterAll
   public static void cleanup() {
      cacheManager.stop();
   }

   @Override
   public CacheMeterBinder binder() {
      cacheManager = new DefaultCacheManager();
      cache = cacheManager.administration().getOrCreateCache("mycache", new ConfigurationBuilder().jmxStatistics().enable().build());
      return new InfinispanCacheMeterBinder(cache, emptyList());
   }

   @Override
   public void put(String key, String value) {
      cache.put(key, value);
   }

   @Override
   public String get(String key) {
      return cache.get(key);
   }
}
