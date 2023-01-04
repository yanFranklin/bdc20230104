package cn.gtmap.realestate.inquiry.ui.config;

import feign.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/2/2
 * @description Dalston SR3版本Feign超时时间配置
 */
@Configuration
public class FeignClientConfiguration {
    @Value("${bdc.feignclient.connectTimeoutMillis:600000}")
    private int connectTimeoutMillis;

    @Value("${bdc.feignclient.readTimeoutMillis:600000}")
    private int readTimeoutMillis;


    // 配置系统有些会大批量生成某些内容，所以超时时间设置长些
    @Bean
    public Request.Options options() {
        return new Request.Options(connectTimeoutMillis, readTimeoutMillis);
    }
}
