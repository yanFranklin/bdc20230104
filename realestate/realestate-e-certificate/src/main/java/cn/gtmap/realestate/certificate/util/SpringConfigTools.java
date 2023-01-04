package cn.gtmap.realestate.certificate.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:zhangyiyang@gtmap.cn">zhangyiyang</a>
 * @version 1.0  2017/10/12
 * @description 获取spring注入管理的bean对象
 * 增加此类的目的为解决单存反射获取的bean对象中的@Autowired对象失效问题
 * 网上代码片段组织生成
 */
@Service
public class SpringConfigTools implements ApplicationContextAware {
    private static ApplicationContext context = null;
    private static SpringConfigTools stools = null;
    public synchronized static SpringConfigTools init(){
        if(stools == null){
            stools = new SpringConfigTools();
        }
        return stools;
    }

    public synchronized static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return context;
    }
    //通过class获取Bean.
    public synchronized static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
}
