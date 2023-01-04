package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.IllegalArgumentException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * 基于 javax.validation 注解校验参数
 * NotBlank 需要使用 org.hibernate.validator
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 */
public class ValidatorUtils {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorUtils() {
    }

    /**
     * 整体校验，有不合规范，抛出第1个违规异常
     *
     * @param obj    校验参数
     * @param groups 分组
     */
    public static void validate(Object obj, Class... groups) {
        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
        if (!resultSet.isEmpty()) {
            //如果存在错误结果，则将其解析并进行拼凑后异常抛出
            List<String> errorMessageList = resultSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            StringJoiner errorMessage = new StringJoiner(";");
            errorMessageList.forEach(errorMessage::add);
            throw new IllegalArgumentException(errorMessage.toString());
        }
    }

    /**
     * 不抛出异常，直接返回错误信息
     *
     * @param obj    校验参数
     * @param groups 分组
     */
    public static StringJoiner sjValidate(Object obj, Class... groups) {
        StringJoiner errorMessage = new StringJoiner(";");
        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj, groups);
        if (!resultSet.isEmpty()) {
            //如果存在错误结果，则将其解析并进行拼凑后异常抛出
            List<String> errorMessageList = resultSet.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
            errorMessageList.forEach(errorMessage::add);
        }
        return errorMessage;
    }
}
