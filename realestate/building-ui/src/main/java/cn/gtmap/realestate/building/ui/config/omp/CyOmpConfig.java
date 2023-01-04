package cn.gtmap.realestate.building.ui.config.omp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-02
 * @description 其他（草原）图层配置
 */
@Configuration
@ConfigurationProperties(prefix = "bdc-cydw")
public class CyOmpConfig extends ZdOmpConfig{
}
