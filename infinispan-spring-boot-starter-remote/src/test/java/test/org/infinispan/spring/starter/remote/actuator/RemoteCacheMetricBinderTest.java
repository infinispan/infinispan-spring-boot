package test.org.infinispan.spring.starter.remote.actuator;

import static java.util.Collections.emptyList;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.spring.starter.remote.actuator.RemoteInfinispanCacheMeterBinder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinderCompatibilityKit;

// TODO: start infinispan server to build this remote test. Look at hotrod tests
@ExtendWith(SpringExtension.class)
@Disabled
public class RemoteCacheMetricBinderTest extends CacheMeterBinderCompatibilityKit {

   private static RemoteCacheManager cacheManager;
   private RemoteCache<String, String> cache;

   @AfterAll
   public static void cleanup() {
      cacheManager.stop();
   }

   @Override
   public CacheMeterBinder binder() {
      cacheManager = new RemoteCacheManager(new ConfigurationBuilder()
            .clientIntelligence(ClientIntelligence.BASIC)
            .statistics().enable().build());
      cache = cacheManager.administration().getOrCreateCache("mycache", "default");
      return new RemoteInfinispanCacheMeterBinder(cache, emptyList());
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
