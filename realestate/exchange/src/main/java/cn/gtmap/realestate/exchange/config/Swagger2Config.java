package cn.gtmap.realestate.exchange.config;

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
     * @param
     * @return Docket
     * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
     * @description 添加摘要信息
     */
    @Bean
    public Docket configApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)//设置是否使用swagger提供的默认的返回状态码
                .apiInfo(new ApiInfoBuilder()
                        .title("第三方交互子系统API")
                        .description("描述：第三方信息交互模块")
                        .contact(new Contact("测试版本", null, null))
                        .version("版本号:v1.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gtmap.realestate.exchange.web.rest"))
                .paths(PathSelectors.any())
                .build();
    }
}
