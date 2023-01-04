package cn.gtmap.realestate.exchange.config.dozer;

import com.alibaba.fastjson.JSONObject;
import org.dozer.fieldmap.HintContainer;
import org.dozer.propertydescriptor.DozerPropertyDescriptor;
import org.dozer.propertydescriptor.MapPropertyDescriptor;
import org.dozer.propertydescriptor.PropertyDescriptorCreationStrategy;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-12
 * @description 第三方子系统 dozer自定义 结构处理 构造服务
 */
public class ExchangeProperterDescriptorCreation implements PropertyDescriptorCreationStrategy {

    private static final String SET_METHOD = "put";

    private static final String GET_METHOD = "get";

    private static final String FIELD_NAME = "this";

    @Override
    public DozerPropertyDescriptor buildFor(Class<?> clazz, String fieldName, boolean isIndexed, int index, HintContainer srcDeepIndexHintContainer, HintContainer destDeepIndexHintContainer) {
        return new ExchangeJsonPropertyDescriptor(clazz, FIELD_NAME, isIndexed, index, SET_METHOD,
                GET_METHOD, fieldName,
                srcDeepIndexHintContainer, destDeepIndexHintContainer);
    }

    @Override
    public boolean isApplicable(Class<?> clazz, String fieldName) {
        if(clazz.equals(JSONObject.class)){
            return true;
        }
        return false;
    }
}
