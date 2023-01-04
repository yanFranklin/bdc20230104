package cn.gtmap.realestate.exchange.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.annotation.Annotation;

/**
 * Created by zdd on 2015/11/19.
 * xml与Entity 对象转换
 */
@Service
public class XmlEntityConvertUtil {
    private static Logger logger = LoggerFactory.getLogger(XmlEntityConvertUtil.class);

    public static String entityToXmlStatic(Object object) {
        String xmlStr = "";
        try {
            if (object != null) {
                Annotation[] aos = object.getClass().getAnnotations();
                Boolean bol = false;
                for (Annotation ao : aos) {
                    if (ao.annotationType().equals(XmlRootElement.class)) {
                        bol = true;
                        break;
                    }
                }
                if (bol) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter writer = new StringWriter();
                    jaxbMarshaller.marshal(object, writer);
                    xmlStr = writer.toString().replaceAll("\\s*standalone=\"yes\"", "");
                }
            }
        } catch (Exception e) {
            logger.error("errorMsg:", e);
        }
        return xmlStr;
    }

    /**
     * @param jsonObject
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description json结构转xml字符串
     */
    public static String jsonToXml(JSONObject jsonObject) {
        String result = "";
        if (MapUtils.isNotEmpty(jsonObject)) {
            StringReader input = new StringReader(JSONObject.toJSONString(jsonObject));
            StringWriter output = new StringWriter();
            JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();

            try {
                XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
                XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
                writer = new PrettyXMLEventWriter(writer);
                writer.add(reader);
                reader.close();
                writer.close();
            } catch (Exception e) {
                logger.info("", e);
            } finally {
                try {
                    output.close();
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            result = output.toString();
            //remove <?xml version="1.0" encoding="UTF-8"?>
            result = result.substring(result.indexOf("<", 2), result.length());
            result = result.replace("\n", "").replace("\t", "");
        }
        return result;
    }


    public static <T> String convertObjectToXml(T obj) {
        String xmlStr="";
        try {
            StringWriter out = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(obj,out);
            xmlStr = out.toString();
        } catch (JAXBException e) {
            logger.error("推送信息转Xml失败！异常信息：{}",e);
            throw new AppException("推送信息转Xml失败:" + e.getMessage());
        }
        logger.info("推送信息转Xml信息：{}",xmlStr);
        //remove <?xml version="1.0" encoding="UTF-8"?>
        xmlStr = xmlStr.substring(xmlStr.indexOf("<", 2), xmlStr.length());
        xmlStr = xmlStr.replace("<ROOT>", "").replace("\t", "");

        xmlStr = xmlStr.replace("</ROOT>", "").replace("\t", "");

        xmlStr = xmlStr.replace("\n", "").replace("\t", "");
        xmlStr = xmlStr.replace("\r", "").replace("    ", "");
        return xmlStr;
    }

    /**
     * @param xml
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description xmlToJson
     */
    public static String xmlToJson(String xml) {
        StringReader input = new StringReader(xml);
        StringWriter output = new StringWriter();
        JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
        try {
            XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
            XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
            writer.add(reader);
            reader.close();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return output.toString();
    }


    public String entityToXml(Object object) {
        String xmlStr = "";
        try {
            if (object != null) {
                Annotation[] aos = object.getClass().getAnnotations();
                Boolean bol = false;
                for (Annotation ao : aos) {
                    if (ao.annotationType().equals(XmlRootElement.class)) {
                        bol = true;
                        break;
                    }
                }
                if (bol) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter writer = new StringWriter();
                    jaxbMarshaller.marshal(object, writer);
                    xmlStr = writer.toString().replaceAll("\\s*standalone=\"yes\"", "");
                }
            }
        } catch (Exception e) {
            logger.error("errorMsg:", e);
        }
        return xmlStr;
    }

    public String entityToXmlYh(Object object) {
        String xmlStr = "";
        try {
            if (object != null) {
                Annotation[] aos = object.getClass().getAnnotations();
                Boolean bol = false;
                for (Annotation ao : aos) {
                    if (ao.annotationType().equals(XmlRootElement.class)) {
                        bol = true;
                        break;
                    }
                }
                if (bol) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    StringWriter writer = new StringWriter();
                    jaxbMarshaller.marshal(object, writer);
                    xmlStr = writer.toString().replaceAll("\\s*standalone=\"yes\"", "");
                }
            }
        } catch (Exception e) {
            logger.error("errorMsg:", e);
        }
        return xmlStr;
    }

    public Object xmlToEntity(Class clazz, InputStream respondStream) {
        Object entity = null;
        try {
            Unmarshaller um = getUnmarshaller(clazz);
            if (um != null) {
                entity = um.unmarshal(respondStream);
            }
        } catch (Exception e) {
            logger.error("errorMsg:", e);
        }
        return entity;
    }

    /**
     * @param clazz
     * @param stringReader
     * @return
     */
    public Object xmlToEntity(Class clazz, StringReader stringReader) {
        Object entity = null;
        try {
            Unmarshaller um = getUnmarshaller(clazz);
            if (um != null) {
                entity = um.unmarshal(stringReader);
            }
        } catch (Exception e) {
            logger.error("errorMsg:", e);
        }
        return entity;
    }


    /**
     * @param obj
     * @return java.lang.String
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 使用xstream 方式 将实体转换为XML
     */
    public static String xstreamToXml(Object obj) {
        XStream xstream = new XStream(new DomDriver("utf-8")); //指定编码解析器,直接用jaxp dom来解释
        xstream.processAnnotations(obj.getClass());
        String xmlHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        return xmlHead + xstream.toXML(obj);
    }


    /**
     * @param xmlStr
     * @param cls
     * @return T
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @description 将XML转换为bean
     */
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
        XStream xstream = new XStream(new DomDriver("utf-8"));
        xstream.processAnnotations(cls);
        xstream.ignoreUnknownElements();
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }

    private static Unmarshaller getUnmarshaller(Class clazz) throws JAXBException {
        Unmarshaller um = null;
        if (clazz != null) {
            Annotation[] aos = clazz.getAnnotations();
            Boolean bol = false;
            for (Annotation ao : aos) {
                if (ao.annotationType().equals(XmlRootElement.class)) {
                    bol = true;
                    break;
                }
            }
            if (bol) {
                JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                um = jaxbContext.createUnmarshaller();
            }
        }
        return um;
    }


    public Object xmlToEntity1(Class clazz, InputStream respondStream) {
        Object entity = null;
        try {
            if (clazz != null) {
                Annotation[] aos = clazz.getAnnotations();
                Boolean bol = false;
                for (Annotation ao : aos) {
                    if (ao.annotationType().equals(XmlRootElement.class)) {
                        bol = true;
                        break;
                    }
                }
                if (bol) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
                    Unmarshaller um = jaxbContext.createUnmarshaller();
                    entity = um.unmarshal(respondStream);
                }
            }
        } catch (Exception e) {
            logger.info("异常", e);
            throw new AppException("Jaxb转换异常:");
        }
        return entity;
    }
}
