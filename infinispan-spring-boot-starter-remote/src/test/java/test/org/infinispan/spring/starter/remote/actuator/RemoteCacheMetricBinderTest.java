package test.org.infinispan.spring.starter.remote.actuator;

// TODO: start infinispan server to build this remote test. Look at hotrod tests
public class RemoteCacheMetricBinderTest /*extends CacheMeterBinderCompatibilityKit */ {

//   private RemoteCacheManager cacheManager;
//   private RemoteCache<String, String> cache;
//
//   @AfterClass
//   void cleanup() {
//      cacheManager.stop();
//   }
//
//   @Override
//   public CacheMeterBinder binder() {
//      cacheManager = new RemoteCacheManager(new ConfigurationBuilder()
//            .clientIntelligence(ClientIntelligence.BASIC)
//            .statistics().enable().build());
//      cache = cacheManager.administration().getOrCreateCache("mycache", "default");
//      return new RemoteInfinispanCacheMetricBinder(cache, emptyList());
//   }
//
//   @Override
//   public void put(String key, String value) {
//      cache.put(key, value);
//   }
//
//   @Override
//   public String get(String key) {
//      return cache.get(key);
//   }
//
//   @Ignore
//   @Test
//   void size() {
//      // TODO: Implement size metric on RemoteCache
//
//   }
}
