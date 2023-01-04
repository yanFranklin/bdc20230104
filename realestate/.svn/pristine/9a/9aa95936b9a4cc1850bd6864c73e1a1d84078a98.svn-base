package cn.gtmap.realestate.exchange.core.bo.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0  2021-04-22
 * @description 记录第三方接口档案验证日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DsfDaCheckLog {

    // 第三方标志
    String interfaceFlag() default "";

    // 第三方标志code
    String interfaceFlagCode() default "";

    // 记录日志的服务
    int checkFlagIndex() default 99;

    String checkFlagClassName() default "";

    String checkFlagLever() default "";

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

    // 请求的接口id
    String interfaceName() default "";

    // 请求的接口地址
    String interfaceUrl() default "";
}
