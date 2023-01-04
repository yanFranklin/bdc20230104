package cn.gtmap.realestate.certificate.config;

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
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @version 1.0, 2018/11/11
 * @description 接口文档说明配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
     * @description api摘要
     * @return Docket
     */
    @Bean
    public Docket configApi() {
        // useDefaultResponseMessages，设置是否使用swagger提供的默认的返回状态码
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(new ApiInfoBuilder()
                        .title("证书归档子系统API")
                        .description("描述：证书归档相关功能")
                        .contact(new Contact("测试版本", null, null))
                        .version("版本号:v1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gtmap.realestate.certificate.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}
