package org.infinispan.spring.starter.embedded;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("infinispan.embedded")
public class InfinispanEmbeddedConfigurationProperties {
    public static final String DEFAULT_CLUSTER_NAME = "default-autoconfigure";

    /**
     * Enable Infinispan support.
     */
    private boolean enabled = true;

    /**
     * Specify an XML configuration file to use as a template
     * when creating caches.
     */
    private String configXml = "";

    /**
     * Specify a Spring state machine ID.
     */
    private String machineId = "";

    /**
     * Specify the name of the Infinispan cluster.
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
