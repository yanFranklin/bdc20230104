package cn.gtmap.realestate.exchange.core.bo.anno;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-24
 * @description 记录第三方接口日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DsfLog {

    // 第三方标志
    String dsfFlag() default "";

    // 响应方
    String responser() default "";

    // 请求方
    String requester() default "";

    // 日志事件名称
    String logEventName() default "";

    // 记录日志的服务
    String logService() default "";
}
