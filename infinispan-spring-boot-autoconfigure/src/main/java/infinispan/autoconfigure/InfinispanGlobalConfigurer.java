package infinispan.autoconfigure;

import org.infinispan.configuration.global.GlobalConfiguration;

@FunctionalInterface
public interface InfinispanGlobalConfigurer {
    GlobalConfiguration getGlobalConfiguration();
}
