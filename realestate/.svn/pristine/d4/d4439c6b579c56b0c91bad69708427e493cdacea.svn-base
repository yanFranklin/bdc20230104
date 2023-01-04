package cn.gtmap.realestate.check.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author <a href="mailto:lst@gtmap.cn">lst</a>
 * @version 1.0  2018/10/30
 * @description 其他系统服务
 */
@Configuration
@ImportResource(locations={"classpath:conf/spring/config-service.xml"})
public class OtherBeanConfig {
    @Value("${thread.max:}")
    private Integer threadMax;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        Integer maxSize=threadMax!=null ? threadMax : Runtime.getRuntime().availableProcessors()*2;
        //lst 默认设置最小50  优先走thread.max配置
        if(maxSize<50){
            maxSize=50;
        }
        pool.setCorePoolSize(maxSize);
        pool.setMaxPoolSize(maxSize);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }
}
