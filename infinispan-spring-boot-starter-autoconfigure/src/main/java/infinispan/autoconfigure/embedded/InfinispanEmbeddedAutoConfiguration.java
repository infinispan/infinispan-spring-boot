package infinispan.autoconfigure.embedded;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import infinispan.autoconfigure.common.InfinispanProperties;

@Configuration
@ComponentScan
//Since a jar with configuration might be missing (which would result in TypeNotPresentExceptionProxy), we need to
//use String based methods.
//See https://github.com/spring-projects/spring-boot/issues/1733
@ConditionalOnClass(name = "org.infinispan.manager.EmbeddedCacheManager")
@ConditionalOnProperty(value = "infinispan.embedded.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(InfinispanProperties.class)
public class InfinispanEmbeddedAutoConfiguration {

   public static final String DEFAULT_JMX_DOMAIN = "infinispan";

   @Autowired
   private InfinispanProperties infinispanProperties;

   @Autowired(required = false)
   private List<InfinispanCacheConfigurer> configurers = Collections.emptyList();

   @Autowired(required = false)
   private InfinispanGlobalConfigurer infinispanGlobalConfigurer;

   @Bean(destroyMethod = "stop")
   public DefaultCacheManager defaultCacheManager() throws IOException {
      final String configXml = infinispanProperties.getEmbedded().getConfigXml();
      final GlobalConfiguration defaultGlobalConfiguration =
            new GlobalConfigurationBuilder()
                  .globalJmxStatistics().jmxDomain(DEFAULT_JMX_DOMAIN).enable()
                  .transport().clusterName(infinispanProperties.getEmbedded().getClusterName())
                  .build();

      final org.infinispan.configuration.cache.Configuration defaultConfiguration =
            new org.infinispan.configuration.cache.ConfigurationBuilder().build();

      final GlobalConfiguration globalConfiguration =
            infinispanGlobalConfigurer == null ? defaultGlobalConfiguration
                  : infinispanGlobalConfigurer.getGlobalConfiguration();

      final DefaultCacheManager manager =
            configXml.isEmpty() ? new DefaultCacheManager(globalConfiguration, defaultConfiguration)
                  : new DefaultCacheManager(configXml);

      configureCaches(manager);

      return manager;
   }

   private void configureCaches(final EmbeddedCacheManager manager) {
      configurers.forEach(configurer -> configurer.configureCache(manager));
   }
}
