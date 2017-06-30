package infinispan.autoconfigure.remote;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class InfinispanRemoteCacheManagerChecker implements Condition {
   @Override
   public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
      return hasConfigurer(ctx) || hasHotRodClientPropertiesFile(ctx) || hasServersProperty(ctx) || hasConfiguration(ctx);
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
