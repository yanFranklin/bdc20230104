package cn.gtmap.realestate.exchange.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-10
 * @description 针对使用@Valid系列验证注解的实体 做验证
 */
@Component
public class ValidUtil {


    @Autowired
    private Validator validator;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param obj
     * @return java.lang.String
     * @description 返回异常信息 返回空为验证通过
     */
    public String validateObject(Object obj){
        Set<ConstraintViolation<Object>> result = this.validator.validate(obj);
        StringBuilder sb = new StringBuilder("");
        if (!result.isEmpty()) {
            for (Iterator<ConstraintViolation<Object>> it = result.iterator(); it.hasNext();) {
                ConstraintViolation<Object> violation = it.next();
                sb.append(violation.getPropertyPath()).append(" - ").append(violation.getMessage());
                if (it.hasNext()) {
                    sb.append("; ");
                }
            }
        }
        return sb.toString();
    }
}
