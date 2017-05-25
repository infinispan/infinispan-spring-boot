package infinispan.autoconfigure.embedded;

import org.infinispan.configuration.global.GlobalConfigurationBuilder;

@FunctionalInterface
public interface InfinispanGlobalConfigurationCustomizer {
    void cusomize(GlobalConfigurationBuilder builder);
}
