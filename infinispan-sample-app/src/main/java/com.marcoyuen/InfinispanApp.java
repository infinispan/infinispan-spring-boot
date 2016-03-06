package com.marcoyuen;

import infinispan.autoconfigure.InfinispanCacheConfigurer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InfinispanApp implements CommandLineRunner {

    @Autowired
    private EmbeddedCacheManager cacheManager;

    @Bean
    public InfinispanCacheConfigurer cacheConfigurer() {
        return manager -> {
            final Configuration ispnConfig = new ConfigurationBuilder()
                    .clustering()
                    .cacheMode(CacheMode.REPL_SYNC)
                    .build();

            manager.defineConfiguration("repl-sync", ispnConfig);
        };
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("Cluster name: %s\n", cacheManager.getClusterName());
        System.out.printf("Cache names: %s\n", cacheManager.getCacheNames());
        System.out.printf("Default cache conf: %s\n", cacheManager.getDefaultCacheConfiguration());
    }

    public static void main(final String[] args) {
        SpringApplication.run(InfinispanApp.class, args);
    }
}
