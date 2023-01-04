package cn.gtmap.realestate.inquiry.core.aop;

import cn.gtmap.realestate.common.core.annotations.DataSourceType;
import cn.gtmap.realestate.common.util.UserManagerUtils;
import cn.gtmap.realestate.inquiry.core.dbs.DynamicDataSourceContextHolder;
import cn.gtmap.realestate.inquiry.core.dbs.SwitchDB;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;

/**
 * @description 动态数据源切面
 */
@ConditionalOnProperty("dynamic.enable")
@Aspect
@Component
public class DynamicDataSourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceAspect.class);

    @Autowired
    SwitchDB switchDB;

    @Autowired
    UserManagerUtils userManagerUtils;

    //自动注入环境类，用于获取配置文件的属性值
    @Autowired
    private Environment evn;

    @Pointcut("@annotation(cn.gtmap.realestate.common.core.annotations.DataSourceType)")
    public void pointCut(){};



    @Around("pointCut()")
    public void around(ProceedingJoinPoint point) {
        Object target = point.getTarget();
        //获取当前接口方法名
        String method = point.getSignature().getName();
        // 获取目标类的接口， 所以@DataSource需要写在接口上
        Class<?>[] classz = target.getClass().getInterfaces();
        //获取参数类型
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        try {
            //因为当前方法时父类中的方法，所以循环class确定方法存在哪一个类中
            Method m = null;
            for (Class<?> mclass : classz) {
                Method[] methods = mclass.getMethods();
                for (Method findMethod : methods) {
                    if (StringUtils.endsWithIgnoreCase(findMethod.getName(), method)) {
                        m = mclass.getMethod(method, parameterTypes);
                    }
                }
            }

            //判断当前方法中是否存在DataSourceType注解，如果存在，设置当前请求数据源
            if (m != null && m.isAnnotationPresent(DataSourceType.class)) {
                DataSourceType data = m.getAnnotation(DataSourceType.class);
                switchDB.change(data.value());
                Object[] args = point.getArgs();
                point.proceed(args);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
