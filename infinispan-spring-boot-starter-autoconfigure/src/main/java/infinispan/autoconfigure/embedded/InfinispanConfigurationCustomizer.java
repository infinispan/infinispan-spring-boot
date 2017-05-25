package infinispan.autoconfigure.embedded;

import org.infinispan.configuration.cache.ConfigurationBuilder;

@FunctionalInterface
public interface InfinispanConfigurationCustomizer {
    void cusomize(ConfigurationBuilder builder);
}
