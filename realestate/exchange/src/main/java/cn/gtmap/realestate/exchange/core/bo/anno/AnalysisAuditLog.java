package cn.gtmap.realestate.exchange.core.bo.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-24
 * @description
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
public @interface AnalysisAuditLog {

    String name() default "";

    String description() default "";

    String userId() default "";
}
