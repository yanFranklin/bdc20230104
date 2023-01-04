package cn.gtmap.realestate.certificate;

import cn.gtmap.gtc.starter.gscas.GTMapSecCloudApplication;
import cn.gtmap.realestate.common.core.support.i18n.SpringMessageProvider;
import cn.gtmap.realestate.common.core.support.spring.ControllerExceptionHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen</a>
 * @description 启动类
 */
@ComponentScan(basePackages = "cn.gtmap.realestate")
@GTMapSecCloudApplication(feignClientsPackages = {"cn.gtmap.realestate.common.core.service.feign", "cn.gtmap.gtc.clients", "cn.gtmap.gtc.workflow", "cn.gtmap.gtc.storage.clients", "cn.gtmap.gtc.formclient"})
@Import({ControllerExceptionHandler.class, SpringMessageProvider.class})
@MapperScan("cn.gtmap.realestate.certificate.core.mapper")
@DependsOn("automaticLoadingConfig")
public class CertificateApp {
    /**
     * @param args 参数
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 启动入口主函数
     */
    public static void main(String[] args) {
        SpringApplication.run(CertificateApp.class, args);
    }


}
