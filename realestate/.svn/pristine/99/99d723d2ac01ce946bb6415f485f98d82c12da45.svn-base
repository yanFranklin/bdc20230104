package cn.gtmap.realestate.etl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/1
 * @description 接口文档说明配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * @return Docket
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 添加摘要信息
     */
    @Bean
    public Docket configApi() {
        // useDefaultResponseMessages，设置是否使用swagger提供的默认的返回状态码
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfoBuilder()
                        .title("不动产登记系统3.0ETL子系统API")
                        .description("描述：ETL子系统相关功能")
                        .contact(new Contact("测试版本", null, null))
                        .version("版本号:v3.1")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gtmap.realestate.etl.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}
