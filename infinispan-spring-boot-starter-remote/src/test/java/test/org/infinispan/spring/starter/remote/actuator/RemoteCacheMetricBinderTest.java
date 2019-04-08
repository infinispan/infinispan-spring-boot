package test.org.infinispan.spring.starter.remote.actuator;

import static java.util.Collections.emptyList;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.spring.common.provider.SpringCache;
import org.infinispan.spring.starter.remote.actuator.RemoteInfinispanCacheMeterBinderProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import io.micrometer.core.instrument.binder.cache.CacheMeterBinder;
import io.micrometer.core.instrument.binder.cache.CacheMeterBinderCompatibilityKit;
import test.org.infinispan.spring.starter.remote.container.InfinispanContainer;

@Testcontainers
public class RemoteCacheMetricBinderTest extends CacheMeterBinderCompatibilityKit {
   @Container
   private final static InfinispanContainer INFINISPAN_SERVER = new InfinispanContainer().withCache("mycache");

   private static RemoteCacheManager cacheManager;
   private RemoteCache<String, String> cache;

   @AfterAll
   public static void cleanup() {
      INFINISPAN_SERVER.stop();
   }

   @AfterEach
   public void cleanCache() {
      cache.clientStatistics().resetStatistics();
   }

   @Override
   public CacheMeterBinder binder() {
      cacheManager = INFINISPAN_SERVER.getCacheManager();
      cache = cacheManager.getCache("mycache");
      RemoteInfinispanCacheMeterBinderProvider remoteInfinispanCacheMeterBinderProvider = new RemoteInfinispanCacheMeterBinderProvider();
      return (CacheMeterBinder) remoteInfinispanCacheMeterBinderProvider.getMeterBinder(new SpringCache(cache), emptyList());
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
