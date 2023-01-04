package cn.gtmap.realestate.exchange.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:jpzhong1994@gmail.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/7/21 09:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface OpenApi {

    /**
     * 接口名称
     * @return
     */
    String apiName() default "";

    /**
     * 接口描述
     * @return
     */
    String apiDescription() default "";

    /**
     * 是否记录日志 默认不记录
     * @return
     */
    boolean openLog() default false;

}
