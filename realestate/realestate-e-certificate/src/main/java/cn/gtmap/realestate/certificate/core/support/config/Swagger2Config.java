package cn.gtmap.realestate.certificate.core.support.config;

import cn.gtmap.realestate.certificate.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yanzhenkun@gtmap.cn">yanzhenkun</a>
 * @version 1.0, 2018/11/1
 * @description 接口文档说明配置
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket configApi() {
        List<Parameter> pars = new ArrayList<>();

        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name(Constants.REQUEST_PARAM_TOKEN).description("user token")
                .modelRef(new ModelRef("string")).parameterType("query")
                .required(false).build();
        pars.add(ticketPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.gtmap.realestate.certificate.web"))
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("电子证照系统 测试使用 Swagger2 构建RESTful API")
                .contact(new Contact("测试版本", null, null))
                //版本号
                .version("3.0")
                //描述
                .description("API描述:电子证照3.0")
                .build();
    }
}
