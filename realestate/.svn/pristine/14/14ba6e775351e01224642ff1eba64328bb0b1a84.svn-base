package cn.gtmap.realestate.common.property;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * .
 * <p/>
 *
 * @author <a href="mailto:yangxin@gtmap.cn">yangxin</a>
 * @version V1.0, 13-5-8
 */
@Configuration
@ConfigurationProperties("httpclient")
public class HttpClientFactoryBean implements FactoryBean<HttpClient>, InitializingBean {
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 20;
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 30 * 1000;
    private static final int DEFAULT_TIME_TO_LIVE_MILLISECONDS = 30 * 1000;

    private int maxTotalConnections = DEFAULT_MAX_TOTAL_CONNECTIONS;
    private int maxConnectionsPerRoute = DEFAULT_MAX_CONNECTIONS_PER_ROUTE;
    private int soTimeout = DEFAULT_READ_TIMEOUT_MILLISECONDS;
    private int connectionTimeout = DEFAULT_READ_TIMEOUT_MILLISECONDS;
    private int timeToLive = DEFAULT_TIME_TO_LIVE_MILLISECONDS;

    private HttpClient httpClient;

    public void setMaxTotalConnections(int maxTotalConnections) {
        this.maxTotalConnections = maxTotalConnections;
    }

    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setTimeout(int timeout) {
        setSoTimeout(timeout);
        setConnectionTimeout(timeout);
    }

    @Override
    public HttpClient getObject() throws Exception {
        return httpClient;
    }

    @Override
    public Class<?> getObjectType() {
        return HttpClient.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(timeToLive, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(maxTotalConnections);
        connectionManager.setDefaultMaxPerRoute(maxConnectionsPerRoute);
        RequestConfig config = RequestConfig.custom().setSocketTimeout(soTimeout).setConnectTimeout(connectionTimeout).build();
        httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
    }
}
