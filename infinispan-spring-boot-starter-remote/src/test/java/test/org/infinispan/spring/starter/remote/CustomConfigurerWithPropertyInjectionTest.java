package test.org.infinispan.spring.starter.remote;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.spring.starter.remote.InfinispanRemoteAutoConfiguration;
import org.infinispan.spring.starter.remote.InfinispanRemoteConfigurer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
    classes = {
        CustomConfigurerWithPropertyInjectionTest.TestConfiguration.class,
        InfinispanRemoteAutoConfiguration.class
    },
    properties = {
        "spring.main.banner-mode=off",
        "myServerList=localhost:6667"
    }
)
public class CustomConfigurerWithPropertyInjectionTest {
   @Autowired
   private RemoteCacheManager manager;

   @Test
   public void testConfiguredClient() {
      assertThat(manager.getConfiguration().servers().get(0).port()).isEqualTo(6667);
   }

   @Configuration
   static class TestConfiguration {
      @Value("${myServerList}")
      private String serverList;

      @Bean
      public InfinispanRemoteConfigurer configuration() {
         return () -> new ConfigurationBuilder()
             .addServers(serverList)
             .build();
      }
   }
}
