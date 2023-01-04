package cn.gtmap.realestate.common.core.support.spring;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 获取配置
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/2.
 * @description
 */
@Configuration(value = "EnvironmentConfig")
public class EnvironmentConfig implements EnvironmentAware {

    private static Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        env=environment;
    }

    public static Environment getEnvironment(){
        return env;
    }
}
