package org.infinispan.spring.starter.remote;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("infinispan.remote")
public class InfinispanRemoteConfigurationProperties {
    public static final String DEFAULT_CLIENT_PROPERTIES = "classpath:hotrod-client.properties";

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
