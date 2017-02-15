package infinispan.autoconfigure.embedded;

import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.spring.provider.SpringEmbeddedCacheManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
//Since a jar with configuration might be missing (which would result in TypeNotPresentExceptionProxy), we need to
//use String based methods.
//See https://github.com/spring-projects/spring-boot/issues/1733
@ConditionalOnClass(name = "org.infinispan.spring.provider.SpringEmbeddedCacheManager")
@ConditionalOnMissingBean(type = {"org.infinispan.spring.provider.SpringEmbeddedCacheManager", "org.infinispan.spring.provider.SpringEmbeddedCacheManagerFactoryBean"})
@ConditionalOnBean(type = "org.infinispan.manager.EmbeddedCacheManager")
@ConditionalOnProperty(value = "infinispan.embedded.cache.enabled", havingValue = "true", matchIfMissing = true)
public class InfinispanEmbeddedCacheManagerAutoConfiguration {

   @Bean
   public SpringEmbeddedCacheManager springEmbeddedCacheManager(EmbeddedCacheManager embeddedCacheManager) {
      return new SpringEmbeddedCacheManager(embeddedCacheManager);
   }
}