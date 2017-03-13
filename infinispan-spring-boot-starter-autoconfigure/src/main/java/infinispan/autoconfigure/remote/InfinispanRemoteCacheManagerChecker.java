package infinispan.autoconfigure.remote;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import infinispan.autoconfigure.common.InfinispanProperties;

public class InfinispanRemoteCacheManagerChecker implements Condition {

   @Override
   public boolean matches(ConditionContext ctx, AnnotatedTypeMetadata atm) {
      return hasConfigurer(ctx) || hasHotRodClientPropertiesFile(ctx) || hasServersProperty(ctx);
   }

   public boolean hasServersProperty(ConditionContext conditionContext) {
      return conditionContext.getEnvironment().getProperty("infinispan.remote.server-list") != null;
   }

   public boolean hasConfigurer(ConditionContext conditionContext) {
      try {
         conditionContext.getBeanFactory().getBean(InfinispanRemoteConfigurer.class);
         return true;
      } catch (NoSuchBeanDefinitionException e) {
         return false;
      }
   }

   public boolean hasHotRodClientPropertiesFile(ConditionContext conditionContext) {
      String hotRodPropertiesPath = conditionContext.getEnvironment().getProperty("infinispan.remote.client-properties");
      if (hotRodPropertiesPath == null) {
         hotRodPropertiesPath = InfinispanProperties.Remote.DEFAULT_CLIENT_PROPERTIES;
      }

      return conditionContext.getResourceLoader().getResource(hotRodPropertiesPath).exists();
   }
}
