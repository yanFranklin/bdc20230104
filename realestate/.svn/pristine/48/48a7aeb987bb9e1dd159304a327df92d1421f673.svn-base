package cn.gtmap.realestate.inquiry.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 1.0
 * @Description:
 */
@Configuration
public class JestClientConfig {

    @Value("${spring.elasticsearch.jest.uris:}")
    private String serverUri;
    @Value("${spring.elasticsearch.jest.username:}")
    private String username;
    @Value("${spring.elasticsearch.jest.password:}")
    private String password;
    @Value("${spring.elasticsearch.jest.connection-timeout:80000}")
    private int connTimeout;
    @Value("${spring.elasticsearch.jest.read-timeout:80000}")
    private int readTimeout;
    @Bean
    public JestClient jestClient(){
        if(StringUtils.isNotBlank(serverUri)){
            if (StringUtils.isBlank(username) && StringUtils.isBlank(password)) {
                JestClientFactory factory = new JestClientFactory();
                factory.setHttpClientConfig(new HttpClientConfig.Builder(serverUri)
                        .connTimeout(connTimeout)
                        .readTimeout(readTimeout)
                        .multiThreaded(true)
                        .build());
                return factory.getObject();
            } else {
                return this.getJestClient(serverUri, username, password);
            }
        }
        return null;
    }

    /**
     * 创建带密码的JestClient
     * @param ip ES的地址
     * @param userName 用户名
     * @param password 密码
     */
    private JestClient getJestClient(String ip, String userName, String password){
        JestClientFactory factory = new JestClientFactory();
        HttpClientConfig.Builder builder = new HttpClientConfig.Builder(ip);
        if (!StringUtils.isEmpty(userName) && !StringUtils.isEmpty(password)) {
            builder.defaultCredentials(userName, password);
        }
        factory.setHttpClientConfig(builder.connTimeout(connTimeout).readTimeout(readTimeout).multiThreaded(true).build());
        return factory.getObject();
    }
}
