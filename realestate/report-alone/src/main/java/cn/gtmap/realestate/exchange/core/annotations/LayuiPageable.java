package cn.gtmap.realestate.exchange.core.annotations;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-16
 * @description layui组件传递分页参数注解
 * <strong>此注解获取 page 和 size 均是从请求头中获取，无法获取到 body 中数据</strong>
 * post 请求可以将 分页参数拼接到 url，或者在接收参数单独去处理
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LayuiPageable {
}
