package org.infinispan.spring.starter.embedded;

import org.infinispan.configuration.cache.ConfigurationBuilder;

@FunctionalInterface
public interface InfinispanConfigurationCustomizer {
    void customize(ConfigurationBuilder builder);
}
