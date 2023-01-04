package cn.gtmap.realestate.inquiry.ui.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/7/25
 * @description elasticsearch 连接工具配置
 */
@Configuration
public class ElasticSearchClientConfig {

    private static Logger logger = LoggerFactory.getLogger(ElasticSearchClientConfig.class);

    @Value("${log.es.hosts:}")
    private String esHosts;
    @Value("${log.es.username:}")
    private String esUserName;
    @Value("${log.es.password:}")
    private String esPassWord;

    @Bean
    public ElasticLowerClient initElasticLowerClient() {
        if (StringUtils.isEmpty(esHosts)) {
            logger.error("未配置 plumelog.server.es.esHosts 内容，无法创建es连接工具");
            return null;
        }
        return ElasticLowerClient.getInstance(esHosts, esUserName, esPassWord);
    }

}
