package cn.gtmap.realestate.common.core.annotations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @param
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @return
 * @description 证书打印的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZsDyZt {
    /**
     * 需要获取参数的名称
     */
    @AliasFor("paramName")
    String paramName() default "";
}
