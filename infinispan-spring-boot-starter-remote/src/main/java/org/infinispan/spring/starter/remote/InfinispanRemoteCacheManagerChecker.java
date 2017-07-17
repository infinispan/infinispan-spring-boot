package org.infinispan.spring.starter.remote;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.autoconfigure.cache.CacheType;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class InfinispanRemoteCacheManagerChecker implements Condition {
   @Override
   public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
       return checkCacheType(ctx) && (hasConfigurer(ctx) || hasHotRodClientPropertiesFile(ctx) || hasServersProperty(ctx) || hasConfiguration(ctx));
   }

   private boolean checkCacheType(ConditionContext context) {
      String cacheType = context.getEnvironment().getProperty("spring.cache.type");
      return cacheType == null || CacheType.INFINISPAN.name().equalsIgnoreCase(cacheType);
   }

   private boolean hasServersProperty(ConditionContext conditionContext) {
      return conditionContext.getEnvironment().getProperty("infinispan.remote.server-list") != null;
   }

   private boolean hasConfigurer(ConditionContext conditionContext) {
      try {
         conditionContext.getBeanFactory().getBean(InfinispanRemoteConfigurer.class);
         return true;
      } catch (NoSuchBeanDefinitionException e) {
         return false;
      }
   }

   private boolean hasConfiguration(ConditionContext conditionContext) {
      try {
         conditionContext.getBeanFactory().getBean(org.infinispan.client.hotrod.configuration.Configuration.class);
         return true;
      } catch (NoSuchBeanDefinitionException e) {
         return false;
      }
   }

   private boolean hasHotRodClientPropertiesFile(ConditionContext conditionContext) {
      String hotRodPropertiesPath = conditionContext.getEnvironment().getProperty("infinispan.remote.client-properties");
      if (hotRodPropertiesPath == null) {
         hotRodPropertiesPath = InfinispanRemoteConfigurationProperties.DEFAULT_CLIENT_PROPERTIES;
      }

      return conditionContext.getResourceLoader().getResource(hotRodPropertiesPath).exists();
   }
}
