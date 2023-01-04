package cn.gtmap.realestate.building.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/1
 * @description
 */
@Configuration
public class Swagger2 {
    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param 
     * @return springfox.documentation.spring.web.plugins.Docket
     * @description 
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("cn.gtmap.realestate.building.web.rest")).paths(PathSelectors.any()).build();
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param 
     * @return springfox.documentation.service.ApiInfo
     * @description 
     */
    private static ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("不动产登记3.0").description(" 楼盘表管理子系统 RESTful API ").version("1.0").build();
    }
}
