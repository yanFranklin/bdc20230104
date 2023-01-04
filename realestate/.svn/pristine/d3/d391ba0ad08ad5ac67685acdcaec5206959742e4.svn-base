package cn.gtmap.realestate.exchange.core.bo.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-10
 * @description 判断实体是否可以强转时  给实体增加注解  可 双向判断
 * 为解决 JSON 比实体中定义的字段 多 导致对照不了的问题
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreCast {

    int ignoreNum() default 3;
}
