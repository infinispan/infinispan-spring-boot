package infinispan.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("infinispan")
public class InfinispanProperties {
    private String configXml = "";
    private String machineId = "";
    private String clusterName = "default-autoconfigure";

    public String getConfigXml() {
        return configXml;
    }

    public void setConfigXml(final String configXml) {
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
}
