package infinispan.autoconfigure.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("infinispan")
public class InfinispanProperties {

   private Remote remote = new Remote();
   private Embedded embedded = new Embedded();

   public Remote getRemote() {
      return remote;
   }

   public void setRemote(Remote remote) {
      this.remote = remote;
   }

   public Embedded getEmbedded() {
      return embedded;
   }

   public void setEmbedded(Embedded embedded) {
      this.embedded = embedded;
   }

   public static class Remote {

      public static final String DEFAULT_CLIENT_PROPERTIES = "classpath:hotrod-client.properties";

      /**
       * Enable remote cache.
       */
      private boolean enabled = true;

      /**
       * The hotrod client properties location.
       */
      private String clientProperties = DEFAULT_CLIENT_PROPERTIES;

      /**
       * A list of remote servers in the form: host1[:port][;host2[:port]]...
       */
      private String serverList;

      /**
       * The maximum socket read timeout in milliseconds before giving up waiting
       * for bytes from the server.
       */
      private Integer socketTimeout;

      /**
       * The maximum socket connect timeout before giving up connecting to the
       * server.
       */
      private Integer connectTimeout;

      /**
       * The maximum number of retries for each request.
       */
      private Integer maxRetries;

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

      public String getServerList() {
         return serverList;
      }

      public void setServerList(String serverList) {
         this.serverList = serverList;
      }

      public Integer getSocketTimeout() {
         return socketTimeout;
      }

      public void setSocketTimeout(Integer socketTimeout) {
         this.socketTimeout = socketTimeout;
      }

      public Integer getConnectTimeout() {
         return connectTimeout;
      }

      public void setConnectTimeout(Integer connectTimeout) {
         this.connectTimeout = connectTimeout;
      }

      public Integer getMaxRetries() {
         return maxRetries;
      }

      public void setMaxRetries(Integer maxRetries) {
         this.maxRetries = maxRetries;
      }
   }

   public static class Embedded {

      public static final String DEFAULT_CLUSTER_NAME = "default-autoconfigure";

      /**
       * Enable embedded cache.
       */
      private boolean enabled = true;

      /**
       *  The configuration file to use as a template for all caches created.
       */
      private String configXml = "";

      private String machineId = "";

      /**
       *The name of the cluster.
       */
      private String clusterName = DEFAULT_CLUSTER_NAME;

      public String getConfigXml() {
         return configXml;
      }

      public void setConfigXml(String configXml) {
         this.configXml = configXml;
      }

      public String getMachineId() {
         return machineId;
      }

      public void setMachineId(String machineId) {
         this.machineId = machineId;
      }

      public String getClusterName() {
         return clusterName;
      }

      public void setClusterName(String clusterName) {
         this.clusterName = clusterName;
      }

      public boolean isEnabled() {
         return enabled;
      }

      public void setEnabled(boolean enabled) {
         this.enabled = enabled;
      }
   }

}
