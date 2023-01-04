package cn.gtmap.realestate.common.core.support.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean的工具类
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0  2018/11/2.
 * @description
 */
@Component
public class Container implements ApplicationContextAware {

    /**
     * 上下文对象
     */
    private static ApplicationContext context;

    /**
     * 通过id获取bean
     * @param beanId
     * @return
     */
    public static Object getBean(String beanId) {
        if(context != null && context.containsBean(beanId)) {
            return context.getBean(beanId);
        } else {
            throw new NoSuchBeanDefinitionException(beanId, " not found!");
        }
    }

    /**
     * 通过class获取bean
     * @param beanId
     * @return
     */
    public static <T> T getBean(Class<T> var) {
        return context.getBean(var);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context=applicationContext;
    }
}
