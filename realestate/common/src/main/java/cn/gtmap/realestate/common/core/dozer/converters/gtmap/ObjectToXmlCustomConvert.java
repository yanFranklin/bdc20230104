package cn.gtmap.realestate.common.core.dozer.converters.gtmap;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import cn.gtmap.realestate.common.util.groovy.XmlUtils;
import com.alibaba.fastjson.JSON;
import groovy.util.Node;
import org.apache.commons.lang3.StringUtils;
import org.dozer.CustomConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a herf="mailto:zhongjinpeng@zhongjinpeng.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/04
 * @description
 */
public class ObjectToXmlCustomConvert implements CustomConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectToXmlCustomConvert.class);

    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        if (sourceFieldValue != null) {
            // 创建输出流
            StringWriter sw = new StringWriter();
            try {
                // 利用jdk中自带的转换类实现
                JAXBContext context = JAXBContext.newInstance(sourceFieldValue.getClass());
                Marshaller marshaller = context.createMarshaller();
                // 格式化xml输出的格式
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                        Boolean.TRUE);
                // 将对象转换成输出流形式的xml
                marshaller.marshal(sourceFieldValue, sw);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(),e);
            }
            return sw.toString();
        }
        return "";
    }
}
