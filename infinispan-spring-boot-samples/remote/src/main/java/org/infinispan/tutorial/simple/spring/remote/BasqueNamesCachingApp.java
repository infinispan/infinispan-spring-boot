package org.infinispan.tutorial.simple.spring.remote;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class BasqueNamesCachingApp implements CommandLineRunner {

   @Autowired
   private RemoteCacheManager cacheManager;

   @Override
   public void run(String... args) {
      // Reset for the example, don't do this in production!!
      RemoteCache<Object, Object> cache = cacheManager.administration().getOrCreateCache(Data.BASQUE_NAMES_CACHE, "default");
      cache.clear();
   }

   public static void main(String... args) {
      new SpringApplicationBuilder().sources(BasqueNamesCachingApp.class).run(args);
   }
}
