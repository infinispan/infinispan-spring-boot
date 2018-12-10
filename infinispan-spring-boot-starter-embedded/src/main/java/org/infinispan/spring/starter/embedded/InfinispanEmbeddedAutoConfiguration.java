package org.infinispan.spring.starter.embedded;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@AutoConfigureBefore(CacheAutoConfiguration.class)
//Since a jar with configuration might be missing (which would result in TypeNotPresentExceptionProxy), we need to
//use String based methods.
//See https://github.com/spring-projects/spring-boot/issues/1733
@ConditionalOnClass(name = "org.infinispan.manager.EmbeddedCacheManager")
@ConditionalOnProperty(value = "infinispan.embedded.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(InfinispanEmbeddedConfigurationProperties.class)
public class InfinispanEmbeddedAutoConfiguration {

   public static final String DEFAULT_JMX_DOMAIN = "infinispan";
   public static final String DEFAULT_CACHE_MANAGER_QUALIFIER = "defaultCacheManager";

   @Autowired
   private InfinispanEmbeddedConfigurationProperties infinispanProperties;

   @Autowired(required = false)
   private List<InfinispanCacheConfigurer> configurers = Collections.emptyList();

   @Autowired(required = false)
   private List<InfinispanConfigurationCustomizer> configurationCustomizers = Collections.emptyList();

   @Autowired(required = false)
   private Map<String, org.infinispan.configuration.cache.Configuration> cacheConfigurations = Collections.emptyMap();

   @Autowired(required = false)
   private InfinispanGlobalConfigurer infinispanGlobalConfigurer;

   @Autowired(required = false)
   private List<InfinispanGlobalConfigurationCustomizer> globalConfigurationCustomizers = Collections.emptyList();

   @Bean(destroyMethod = "stop")
   @Conditional(InfinispanEmbeddedCacheManagerChecker.class)
   @ConditionalOnMissingBean
   @Qualifier(DEFAULT_CACHE_MANAGER_QUALIFIER)
   public DefaultCacheManager defaultCacheManager() throws IOException {
      final String configXml = infinispanProperties.getConfigXml();
      final DefaultCacheManager manager;

      if (!configXml.isEmpty()) {
         manager = new DefaultCacheManager(configXml);
      } else {
         GlobalConfigurationBuilder globalConfigurationBuilder = new GlobalConfigurationBuilder();
         ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

         if (infinispanGlobalConfigurer != null) {
            globalConfigurationBuilder.read(infinispanGlobalConfigurer.getGlobalConfiguration());
         } else {
            globalConfigurationBuilder.globalJmxStatistics().jmxDomain(DEFAULT_JMX_DOMAIN).enable();
            globalConfigurationBuilder.transport().clusterName(infinispanProperties.getClusterName());
         }

         globalConfigurationCustomizers.forEach(customizer -> customizer.customize(globalConfigurationBuilder));
         configurationCustomizers.forEach(customizer -> customizer.customize(configurationBuilder));

         manager = new DefaultCacheManager(globalConfigurationBuilder.build(), configurationBuilder.build());
      }

      cacheConfigurations.forEach(manager::defineConfiguration);
      configurers.forEach(configurer -> configurer.configureCache(manager));

      return manager;
   }
}
