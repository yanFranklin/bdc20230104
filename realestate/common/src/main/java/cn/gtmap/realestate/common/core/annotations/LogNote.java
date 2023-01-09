package cn.gtmap.realestate.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/9/25.
 * @description 日志记录注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogNote {
    /**
     * 日志名称
     */
    String value();

    /**
     * 日志类型
     */
    String action();

    /**
     * 自定义日志记录
     */
    String custom() default "";

    /**
     * 是否记录数据库日志
     *    默认为：false 不记录， true 记录
     */
    boolean dbRecord() default false;
}
