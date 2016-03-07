package infinispan.autoconfigure;

import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Configuration
@ComponentScan
@ConditionalOnClass(CacheContainer.class)
@EnableConfigurationProperties(InfinispanProperties.class)
public class InfinispanAutoConfiguration {

    public static final String DEFAULT_JMX_DOMAIN = "spring.infinispan";

    @Autowired
    private InfinispanProperties infinispanProperties;

    @Autowired(required = false)
    private List<InfinispanCacheConfigurer> configurers = Collections.emptyList();

    @Autowired(required = false)
    private InfinispanGlobalConfigurer infinispanGlobalConfigurer;

    @ConditionalOnMissingBean(EmbeddedCacheManager.class)
    @Bean(destroyMethod = "stop")
    public DefaultCacheManager defaultCacheManager() throws IOException {
        final String configXml = infinispanProperties.getConfigXml();
        final GlobalConfiguration defaultGlobalConfiguration =
                new GlobalConfigurationBuilder()
                    .clusteredDefault()
                    .globalJmxStatistics().jmxDomain(DEFAULT_JMX_DOMAIN).enable()
                    .transport().defaultTransport()
                    .clusterName(infinispanProperties.getClusterName())
                    .build();

        final GlobalConfiguration globalConfiguration =
            infinispanGlobalConfigurer == null? defaultGlobalConfiguration
                                              : infinispanGlobalConfigurer.getGlobalConfiguration();

        final DefaultCacheManager manager =
                configXml.isEmpty()? new DefaultCacheManager(globalConfiguration)
                                   : new DefaultCacheManager(configXml);

        configureCaches(manager);

        return manager;
    }

    private void configureCaches(final EmbeddedCacheManager manager) {
        configurers.forEach(configurer -> configurer.configureCache(manager));
    }
}