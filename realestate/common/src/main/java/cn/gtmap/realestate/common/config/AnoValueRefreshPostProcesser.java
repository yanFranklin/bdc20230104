package cn.gtmap.realestate.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.*;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.*;
import org.springframework.expression.Expression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/05/24/10:27
 * @Description:
 */
@Component
public class AnoValueRefreshPostProcesser extends InstantiationAwareBeanPostProcessorAdapter implements EnvironmentAware  {
    private Map<String, List<FieldPair>> mapper = new HashMap<>();
    private Environment environment;
    private ConfigurableEnvironment configurableEnvironment;
    private static final Logger LOGGER = LoggerFactory.getLogger(AnoValueRefreshPostProcesser.class);

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        processMetaValue(bean);
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    /**
     * 获取支持动态刷新的配置属性，然后缓存起来
     * @param bean
     */
    private void processMetaValue(Object bean) {
        Class clz = bean.getClass();
        /*if (!clz.isAnnotationPresent(RefreshValue.class)) {
            return;
        }*/
        try {
            for (Field field : clz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Value.class)) {
                    Value val = field.getAnnotation(Value.class);
                    List<String> keyList = pickPropertyKey(val.value(), 0);
                    for (String key : keyList) {
                        mapper.computeIfAbsent(key, (k) -> new ArrayList<>()).add(new FieldPair(bean, field, val.value()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * 实现一个基础的配置文件参数动态刷新支持
     * @param value
     * @return 提取key列表
     */
    private List<String> pickPropertyKey(String value, int begin) {
        int start = value.indexOf("${", begin) + 2;
        if (start < 2) {
            return new ArrayList<>();
        }
        int middle = value.indexOf(":", start);
        int end = value.indexOf("}", start);
        String key;
        if (middle > 0 && middle < end) {
            // 包含默认值
            key = value.substring(start, middle);
        } else {
            // 不包含默认值
            key = value.substring(start, end);
        }
        List<String> keys = pickPropertyKey(value, end);
        keys.add(key);
        return keys;
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.configurableEnvironment = (ConfigurableEnvironment) environment;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FieldPair {

        Object bean;
        Field field;
        String value;

        public void updateValue(Environment environment,ConfigurableEnvironment configurableEnvironment) {
            // 定义EL表达式解释器
            SpelExpressionParser spelExpressionParser;
            spelExpressionParser = new SpelExpressionParser();
            TemplateParserContext templateParserContext;
            templateParserContext = new TemplateParserContext();
            String keyResolver, valueResolver = null;
            Object parserValue = null;

            MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            PropertySourcesPropertyResolver propertyResolver = new PropertySourcesPropertyResolver(propertySources);
            keyResolver = field.getAnnotation(Value.class).value();
            try {
                // 读取属性值
                valueResolver = propertyResolver.resolveRequiredPlaceholders(keyResolver);
                // EL表达式解析
                if(isBasicDataTypes(field)){
                    Expression expression = spelExpressionParser.parseExpression(valueResolver, templateParserContext);
                    Object value = expression.getValue();
                    parserValue = parseBasicDataTypes(field,value);
                }else{
                    parserValue = spelExpressionParser.parseExpression(valueResolver, templateParserContext).getValue(field.getType());
                }
            } catch (IllegalArgumentException e) {
                LOGGER.error("刷新属性值出错, bean:{}, field:{}, value:{}",bean.getClass().getName(), field.getName(), valueResolver);
            }
            if (Objects.nonNull(valueResolver)) {
                field.setAccessible(true);
                try {
                    field.set(bean, parserValue);
                } catch (IllegalAccessException e) {
                    LOGGER.error("刷新属性值出错, bean:{}, field:{}, value:{}",bean.getClass().getName(), field.getName(), valueResolver);
                }
                field.setAccessible(false);

            }
        }
    }

    @EventListener
    public void updateConfig(ConfigUpdateEvent configUpdateEvent) {
        List<FieldPair> list = mapper.get(configUpdateEvent.key);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(f -> f.updateValue(environment,configurableEnvironment));
        }
    }

    private static Object parseBasicDataTypes(Field field, Object value){
        Class<?> valueClass = value.getClass();
        Class<?> fieldType = field.getType();
        Object result = null;
        if((fieldType == byte.class || fieldType == Byte.class) && valueClass == String.class){
            result = Byte.parseByte((String) value);
        }else if((fieldType == short.class || fieldType == Short.class) && valueClass == String.class){
            result = Short.parseShort((String) value);
        }else if((fieldType == int.class || fieldType == Integer.class) && valueClass == String.class){
            result = Integer.parseInt((String) value);
        }else if((fieldType == long.class || fieldType == Long.class) && valueClass == String.class){
            result = Long.parseLong((String) value);
        }else if((fieldType == float.class || fieldType == Float.class) && valueClass == String.class){
            result = Float.parseFloat((String) value);
        }else if((fieldType == double.class || fieldType == Double.class) && valueClass == String.class){
            result = Double.parseDouble((String) value);
        }else if((fieldType == char.class || fieldType == Character.class) && valueClass == String.class){
            result = Byte.parseByte((String) value);
        }else if((fieldType == boolean.class || fieldType == Boolean.class) && valueClass == String.class){
            result = Boolean.parseBoolean((String) value);
        }
        return result;
    }

    private static boolean isBasicDataTypes(Field field){
        Class<?> fieldType = field.getType();
        if(fieldType == byte.class || fieldType == Byte.class ||fieldType == short.class || fieldType == Short.class ||
                fieldType == int.class || fieldType == Integer.class|| fieldType == long.class || fieldType == Long.class ||
                fieldType == float.class || fieldType == Float.class || fieldType == double.class || fieldType == Double.class||
                fieldType == char.class || fieldType == Character.class || fieldType == boolean.class || fieldType == Boolean.class){
            return true;
        }
        return false;
    }
}
