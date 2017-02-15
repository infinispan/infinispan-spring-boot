package infinispan.autoconfigure.remote;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import infinispan.autoconfigure.common.InfinispanProperties;

@Configuration
@ComponentScan
//Since a jar with configuration might be missing (which would result in TypeNotPresentExceptionProxy), we need to
//use String based methods.
//See https://github.com/spring-projects/spring-boot/issues/1733
@ConditionalOnClass(name = "org.infinispan.client.hotrod.RemoteCacheManager")
@Conditional(InfinispanRemoteFileChecker.class)
@ConditionalOnProperty(value = "infinispan.remote.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(InfinispanProperties.class)
public class InfinispanRemoteAutoConfiguration {

   @Autowired
   private InfinispanProperties infinispanProperties;

   @Autowired(required = false)
   private InfinispanRemoteConfigurer infinispanRemoteConfigurer;

   @Autowired
   private ApplicationContext ctx;

   @Bean
   @Conditional(InfinispanRemoteFileChecker.class)
   public RemoteCacheManager remoteCacheManager() throws IOException {
      org.infinispan.client.hotrod.configuration.Configuration configuration;
      if (infinispanRemoteConfigurer != null) {
         configuration = infinispanRemoteConfigurer.getRemoteConfiguration();
      } else {
         final String remoteClientPropertiesLocation = infinispanProperties.getRemote().getClientProperties();
         Resource hotRodClientPropertiesFile = ctx.getResource(remoteClientPropertiesLocation);
         Properties hotrodClientProperties = new Properties();
         try (InputStream stream = hotRodClientPropertiesFile.getURL().openStream()) {
            hotrodClientProperties.load(stream);
            configuration = new ConfigurationBuilder().withProperties(hotrodClientProperties).build();
         }
      }
      return new RemoteCacheManager(configuration);
   }
}