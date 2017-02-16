package test.infinispan.autoconfigure.testconfiguration;

import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import infinispan.autoconfigure.remote.InfinispanRemoteConfigurer;

@Configuration
public class InfinispanCacheTestConfiguration {

   public static final int PORT = 11555;

   @Bean
   public InfinispanRemoteConfigurer infinispanRemoteConfigurer() {
      return () -> new ConfigurationBuilder().addServer().host("127.0.0.1").port(PORT).build();
   }
}
