package infinispan.autoconfigure.remote;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import infinispan.autoconfigure.common.InfinispanProperties;

public class InfinispanRemoteFileChecker implements Condition {

   @Override
   public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
      String hotRodPropertiesPath = conditionContext.getEnvironment().getProperty("infinispan.remote.client-properties");
      if (hotRodPropertiesPath == null) {
         hotRodPropertiesPath = InfinispanProperties.Remote.DEFAULT_CLIENT_PROPERTIES;
      }

      return conditionContext.getResourceLoader().getResource(hotRodPropertiesPath).exists();
   }
}
