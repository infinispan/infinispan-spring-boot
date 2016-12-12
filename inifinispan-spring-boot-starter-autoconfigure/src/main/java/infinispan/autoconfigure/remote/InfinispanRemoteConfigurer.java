package infinispan.autoconfigure.remote;

import org.infinispan.client.hotrod.configuration.Configuration;

@FunctionalInterface
public interface InfinispanRemoteConfigurer {
    Configuration getRemoteConfiguration();
}
