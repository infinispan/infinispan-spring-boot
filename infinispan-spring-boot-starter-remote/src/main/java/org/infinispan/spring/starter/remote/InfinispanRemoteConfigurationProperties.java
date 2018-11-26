package org.infinispan.spring.starter.remote;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("infinispan.remote")
public class InfinispanRemoteConfigurationProperties {
    public static final String DEFAULT_CLIENT_PROPERTIES = "classpath:hotrod-client.properties";

    /**
     * Enable Infinispan support.
     */
    private boolean enabled = true;

    /**
     * Specifies a custom filename for Hot Rod client properties.
     */
    private String clientProperties = DEFAULT_CLIENT_PROPERTIES;

    /**
     * Defines a comma-separated list of Infinispan servers in this format:
     * `host1[:port],host2[:port]`.
     */
    private String serverList;

    /**
     * Sets a timeout value, in milliseconds, for socket connections.
     */
    private Integer socketTimeout;

    /**
     * Sets a timeout value for initializing connections with
     * Infinispan servers.
     */
    private Integer connectTimeout;

    /**
     * Sets the maximum number of attempts to connect to Infinispan servers.
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
