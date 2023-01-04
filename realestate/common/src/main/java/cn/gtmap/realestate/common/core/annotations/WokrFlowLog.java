package cn.gtmap.realestate.common.core.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WokrFlowLog {
    String name() default "";


}
