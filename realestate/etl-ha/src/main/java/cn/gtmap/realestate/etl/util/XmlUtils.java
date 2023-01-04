package cn.gtmap.realestate.etl.util;

import com.google.common.collect.Lists;
import org.dom4j.Document;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
}
