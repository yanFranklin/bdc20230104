package cn.gtmap.realestate.init.config;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2019/12/17.
 * @description
 */
@Component
public class AfterServiceStarted implements ApplicationRunner {
    @Autowired
    private InitBeanFactory initBeanFactory;
    /**
     * 会在服务启动完成后立即执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        /**
         * 验证特殊服务是否存在
         */
        StringBuilder builder=new StringBuilder();
        if (CollectionUtils.isNotEmpty(initBeanFactory.getZsServices())) {
            List<String> zsfwList=new ArrayList<>();
            for(String beanName:initBeanFactory.getZsServices()){
                try {
                    Container.getBean(beanName);
                }catch (NoSuchBeanDefinitionException e){
                    zsfwList.add(beanName);
                }
            }
            if(CollectionUtils.isNotEmpty(zsfwList)){
                builder.append("证书特殊服务中不存在此配置："+ StringUtils.join(zsfwList, CommonConstantUtils.ZF_YW_DH));
            }
        }
        if (CollectionUtils.isNotEmpty(initBeanFactory.getTshzServices())) {
            List<String> tshzList=new ArrayList<>();
            for(String beanName:initBeanFactory.getTshzServices()){
                try {
                    Container.getBean(beanName);
                }catch (NoSuchBeanDefinitionException e){
                    tshzList.add(beanName);
                }
            }
            if(CollectionUtils.isNotEmpty(tshzList)){
                builder.append("特殊服务中不存在此配置："+StringUtils.join(tshzList, CommonConstantUtils.ZF_YW_DH));
            }
        }
        if(StringUtils.isNotBlank(builder.toString())){
            throw  new AppException(builder.toString());
        }
    }
}