package cn.gtmap.realestate.exchange.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

//import lombok.extern.slf4j.Slf4j;

/**
 * @Author:
 * @Date:
 */
//@Slf4j
@Component
public class XmlUtils {
    protected static final Logger log = LoggerFactory.getLogger(XmlUtils.class);

    private static final String ENCODING = "UTF-8";

    public List<Element> getElementsByNode(String xmlStr, String node) throws Exception {
        List<Map<String, String>> result = new ArrayList<>();

        if (null == xmlStr || "".equals(xmlStr)) {
//            log.warn("xml字符串为空!");
            return Lists.newArrayList();
        }
        Document document = DocumentHelper.parseText(xmlStr);
        // 获取根元素
        Element root = document.getRootElement();

        return root.elements(node);
    }

    public static Object getObjectByXmlStr(String xmlStr, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new StringReader(xmlStr));
    }

    public static String getXmlStrByObject(Object object, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static String getXmlStrByObjectGBK(Object object, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
//        marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", false);

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");

        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static String getXmlStrByObjectGBKNotIgnoreNull(Object object, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
        marshaller.setListener(new Marshaller.Listener() {
            @Override
            public void beforeMarshal(Object source) {
                Field[] fields = source.getClass().getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(true);
                    try {
                        //对象为空且类型为String时候设置空值
                        if (f.get(source) == null) {
                            if (f.get(source) instanceof String ) {
                                f.set(source, "");
                            } else if (f.get(source) instanceof List){
                                f.set(source, new ArrayList<>());
                            } else if (f.get(source) instanceof Map) {
                                f.set(source, new HashMap<>());
                            } else {
                                try {
                                    f.set(source, f.getType().newInstance());
                                } catch (InstantiationException e) {
                                    f.set(source, null);
                                }
                            }
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static String getXmlStrByObjectWithOutFormat(Object object, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    public static String getXmlStrByObjectWithOutEncoding(Object object, Class clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
        StringWriter writer = new StringWriter();
        marshaller.marshal(object, writer);
        return writer.toString();
    }

    /**
     * @param xmlStr map
     * @return String
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xml元素名称和map种的key匹配，给xml赋值对应的value
     */
    public static String setXmlData(String xmlStr,Map<String,String> map){
        if(StringUtils.isBlank(xmlStr) || map == null || map.isEmpty()){
            return xmlStr;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            log.info("======遍历子节点======");
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()){
                Element stuChild = (Element) iterator1.next();
                String value = (String) map.get(stuChild.getName());
                if(org.apache.commons.lang.StringUtils.isNotBlank(value)){
                    stuChild.setText(value);
                }
                log.info("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
        return document.asXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n","");
    }

    /**
     * @param xmlStr map
     * @return String
     * @author <a href ="mailto:wutao@gtmap.cn">wutao</a>
     * @description 根据xml元素名称和map种的key匹配，给xml赋值对应的value
     */
    public static String getXmlElementValue(String xmlStr,String elementName){
        if(StringUtils.isBlank(xmlStr) || StringUtils.isBlank(elementName)){
            return elementName;
        }
        Document document = null;
        try {
            document = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            log.info("======遍历子节点======");
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()){
                Element stuChild = (Element) iterator1.next();
                if(stuChild.getName().equals(elementName)){
                    return stuChild.getStringValue();
                }
                log.info("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
        return document.asXML().replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n","");
    }



}
