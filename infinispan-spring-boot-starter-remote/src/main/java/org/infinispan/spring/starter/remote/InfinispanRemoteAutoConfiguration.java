package org.infinispan.spring.starter.remote;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan
@AutoConfigureBefore(CacheAutoConfiguration.class)
//Since a jar with configuration might be missing (which would result in TypeNotPresentExceptionProxy), we need to
//use String based methods.
//See https://github.com/spring-projects/spring-boot/issues/1733
@ConditionalOnClass(name = "org.infinispan.client.hotrod.RemoteCacheManager")
@ConditionalOnProperty(value = "infinispan.remote.enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(InfinispanRemoteConfigurationProperties.class)
public class InfinispanRemoteAutoConfiguration {

   public static final String REMOTE_CACHE_MANAGER_BEAN_QUALIFIER = "remoteCacheManager";

   @Autowired
   private InfinispanRemoteConfigurationProperties infinispanProperties;

   @Autowired(required = false)
   private InfinispanRemoteConfigurer infinispanRemoteConfigurer;

   @Autowired(required = false)
   private org.infinispan.client.hotrod.configuration.Configuration infinispanConfiguration;

   @Autowired(required = false)
   private List<InfinispanRemoteCacheCustomizer> cacheCustomizers = Collections.emptyList();

   @Autowired
   private ApplicationContext ctx;

   @Bean
   @Conditional({ConditionalOnCacheType.class, ConditionalOnConfiguration.class})
   @ConditionalOnMissingBean
   @Qualifier(REMOTE_CACHE_MANAGER_BEAN_QUALIFIER)
   public RemoteCacheManager remoteCacheManager() throws IOException {

      boolean hasHotRodPropertiesFile = ctx.getResource(infinispanProperties.getClientProperties()).exists();
      boolean hasConfigurer = infinispanRemoteConfigurer != null;
      boolean hasProperties = StringUtils.hasText(infinispanProperties.getServerList());
      ConfigurationBuilder builder;

      if (hasConfigurer) {
         org.infinispan.client.hotrod.configuration.Configuration configuration = infinispanRemoteConfigurer.getRemoteConfiguration();
         Objects.nonNull(configuration);

         builder = new ConfigurationBuilder().read(configuration);
         cacheCustomizers.forEach(c -> c.customize(builder));
      } else if (hasHotRodPropertiesFile) {
         String remoteClientPropertiesLocation = infinispanProperties.getClientProperties();
         Resource hotRodClientPropertiesFile = ctx.getResource(remoteClientPropertiesLocation);
         Properties hotrodClientProperties = new Properties();
         try (InputStream stream = hotRodClientPropertiesFile.getURL().openStream()) {
            hotrodClientProperties.load(stream);

            builder = new ConfigurationBuilder().withProperties(hotrodClientProperties);

            cacheCustomizers.forEach(c -> c.customize(builder));

         }
      } else if (hasProperties) {
         builder = infinispanProperties.getConfigurationBuilder();

         cacheCustomizers.forEach(c -> c.customize(builder));

      } else if (infinispanConfiguration != null) {
         builder = new ConfigurationBuilder().read(infinispanConfiguration);

         cacheCustomizers.forEach(c -> c.customize(builder));

      } else {
         throw new IllegalStateException("Not enough data to create RemoteCacheManager. Check InfinispanRemoteCacheManagerChecker" +
               "and update conditions.");
      }

      return new RemoteCacheManager(builder.build());
   }

   public static class ConditionalOnConfiguration extends AnyNestedCondition {
      public ConditionalOnConfiguration() {
         super(ConfigurationCondition.ConfigurationPhase.REGISTER_BEAN);
      }

      @Conditional(ConditionalOnConfigurationResources.class)
      static class OnConfigurationResources {
      }

      @ConditionalOnBean(InfinispanRemoteConfigurer.class)
      static class OnRemoteConfigurer {
      }

      @ConditionalOnBean(org.infinispan.client.hotrod.configuration.Configuration.class)
      static class OnConfiguration {
      }
   }

   public static class ConditionalOnCacheType implements Condition {
      @Override
      public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
         String cacheType = ctx.getEnvironment().getProperty("spring.cache.type");
         return cacheType == null || CacheType.INFINISPAN.name().equalsIgnoreCase(cacheType);
      }
   }

   public static class ConditionalOnConfigurationResources implements Condition {
      @Override
      public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
         return hasHotRodClientPropertiesFile(ctx) || hasServersProperty(ctx);
      }

      private boolean hasServersProperty(ConditionContext conditionContext) {
         return conditionContext.getEnvironment().getProperty("infinispan.remote.server-list") != null;
      }

      private boolean hasHotRodClientPropertiesFile(ConditionContext conditionContext) {
         String hotRodPropertiesPath = conditionContext.getEnvironment().getProperty("infinispan.remote.client-properties");
         if (hotRodPropertiesPath == null) {
            hotRodPropertiesPath = InfinispanRemoteConfigurationProperties.DEFAULT_CLIENT_PROPERTIES;
         }

         return conditionContext.getResourceLoader().getResource(hotRodPropertiesPath).exists();
      }
   }

}
