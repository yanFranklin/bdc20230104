package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.util.Base64Utils;
import com.alibaba.fastjson.JSONObject;
import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;


/**
 * @author <a herf="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2021/5/14
 * @description 将json
 */
public class JsonToBase64StringCustomConvert implements CustomConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonToBase64StringCustomConvert.class);

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null) {
            try {
                return Base64Utils.encodeByteToBase64Str(JSONObject.toJSONString(sourceFieldValue).getBytes("UTF-8"));
            }catch (UnsupportedEncodingException e){
                LOGGER.error("数据转换失败：{}",sourceFieldValue);
            }
        }
        return "";
    }
}
