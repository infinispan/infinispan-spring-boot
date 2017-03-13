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

      private String clientProperties = DEFAULT_CLIENT_PROPERTIES;
      private boolean enabled = true;
      private String serverList;
      private Integer socketTimeout;
      private Integer connectTimeout;
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

      private String configXml = "";
      private String machineId = "";
      private String clusterName = DEFAULT_CLUSTER_NAME;
      private boolean enabled = true;

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
