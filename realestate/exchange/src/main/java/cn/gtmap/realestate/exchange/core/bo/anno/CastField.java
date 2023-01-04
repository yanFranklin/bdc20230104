package cn.gtmap.realestate.exchange.core.bo.anno;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-18
 * @description 判断是否可强转时，必须存在的字段
 */
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CastField {
}
