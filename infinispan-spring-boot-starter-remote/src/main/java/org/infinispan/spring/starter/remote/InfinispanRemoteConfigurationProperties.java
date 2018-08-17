package org.infinispan.spring.starter.remote;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "infinispan.remote", ignoreInvalidFields = true)
public class InfinispanRemoteConfigurationProperties extends org.infinispan.client.hotrod.impl.ConfigurationProperties {
   public static final String DEFAULT_CLIENT_PROPERTIES = "classpath:hotrod-client.properties";

   /**
    * Enable Infinispan support.
    */
   private boolean enabled = true;

   /**
    * Specifies a custom filename for Hot Rod client properties.
    */
   private String clientProperties = DEFAULT_CLIENT_PROPERTIES;
   private final Map<String, String> saslProperties = new HashMap<>();
   private final Map<String, String> cluster = new HashMap<>();

   public String getClientProperties() {
      return clientProperties;
   }

   public void setClientProperties(String clientProperties) {
      this.clientProperties = clientProperties;
   }

   public boolean isEnabled() {
      return enabled;
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public ConfigurationBuilder getConfigurationBuilder() {
      ConfigurationBuilder builder = new ConfigurationBuilder();
      Properties properties = this.getProperties();
      saslProperties.forEach((k, v) -> this.getProperties().setProperty(SASL_PROPERTIES_PREFIX + "." + k, v));
      cluster.forEach((k, v) -> this.getProperties().setProperty(CLUSTER_PROPERTIES_PREFIX + "." + k, v));
      builder.withProperties(properties);
      return builder;
   }

   public Map<String, String> getSaslProperties() {
      return saslProperties;
   }

   public Map<String, String> getCluster() {
      return cluster;
   }

   public void setSslProtocol(String sslProtocol) {
      this.getProperties().setProperty(SSL_PROTOCOL, sslProtocol);
   }

   public void setAuthUserName(String authUserName) {
      super.setAuthUsername(authUserName);
   }

}
