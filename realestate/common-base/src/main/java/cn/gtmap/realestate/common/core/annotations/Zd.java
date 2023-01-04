package cn.gtmap.realestate.common.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/2
 * @description 自定义注解, 使用该注解, 用来标注字段是否为字典
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Zd {
    /**
     * 对应的表名
     */
    String table() default "";

    /**
     * 代码在字典表中对应的字段
     */
    String dm() default "dm";

    /**
     * 名称在字典表中对应的字段
     */
    String mc() default "mc";

    /**
     * 对应实体类
     */
    Class tableClass();
}
