package cn.gtmap.realestate.exchange.config.xsd.parser;

import cn.gtmap.realestate.exchange.core.bo.xsd.ServiceInfoBO;
import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.env.Environment;
import org.springframework.util.PropertyPlaceholderHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description
 */
public class ServiceInfoParser {
    protected static Logger LOGGER = LoggerFactory.getLogger(ServiceInfoParser.class);

    private final static String NODE_TAG_NAME = "ex:service-info";

    private final static String PROPERTY_TAG_NAME = "ex:property";

    private final static String PROPERTY_ATTR_NAME = "name";

    private final static String PROPERTY_ATTR_VALUE = "value";

    private final static String PROPERTY_ATTR_SOURCECLASS = "sourceClass";
    private final static String PROPERTY_ATTR_SOURCEMETHOD = "sourceMethod";
    private final static String PROPERTY_ATTR_SOURCEARGS = "sourceArgs";

    private final static String ATTR_REQUEST_TYPE = "request-type";

    private final PropertyPlaceholderHelper helper = new PropertyPlaceholderHelper("${", "}");

    public static void parse(Element element, ParserContext parserContext,
                                      BeanDefinitionBuilder builder){
        Environment environment = parserContext.getReaderContext().getEnvironment();
        NodeList nodeList = element.getElementsByTagName(NODE_TAG_NAME);
        ServiceInfoBO serviceInfoBO = new ServiceInfoBO();
        if(nodeList.getLength() == 1){
            Node serviceInfoNode = nodeList.item(0);
            NamedNodeMap attrs = serviceInfoNode.getAttributes();
            // 获取请求方式
            if(attrs.getNamedItem(ATTR_REQUEST_TYPE) != null){
                serviceInfoBO.setRefBeanName(attrs.getNamedItem(ATTR_REQUEST_TYPE).getNodeValue());
            }
            // 获取请求属性
            serviceInfoBO.setRequestInfo(parsePropMap(serviceInfoNode, environment));
        }
        builder.addPropertyValue("serviceInfoBO",serviceInfoBO);
    }


    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param parentNode
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description 解析property节点属性
     */
    private static Map<String,Object> parsePropMap(Node parentNode,Environment environment){
        Map<String,Object> propMap = new HashMap<>();
        if(parentNode instanceof DeferredElementNSImpl){
            NodeList propNodeList = ((DeferredElementNSImpl) parentNode).getElementsByTagName(PROPERTY_TAG_NAME);
            if(propNodeList.getLength() > 0){
                for(int i=0 ; i < propNodeList.getLength();i++){
                    Node propNode = propNodeList.item(i);
                    NamedNodeMap attrs = propNode.getAttributes();
                    if(attrs.getNamedItem(PROPERTY_ATTR_NAME) != null) {
                        String propName = attrs.getNamedItem(PROPERTY_ATTR_NAME).getNodeValue();
                        if (attrs.getNamedItem(PROPERTY_ATTR_VALUE) != null) {
                            String value = attrs.getNamedItem(PROPERTY_ATTR_VALUE).getNodeValue();
                            propMap.put(propName, environment.resolvePlaceholders(value));
                        }
                        if(attrs.getNamedItem(PROPERTY_ATTR_SOURCECLASS) != null
                                && attrs.getNamedItem(PROPERTY_ATTR_SOURCEMETHOD) != null){
                            String sourceClass = attrs.getNamedItem(PROPERTY_ATTR_SOURCECLASS).getNodeValue();
                            String sourceMethod = attrs.getNamedItem(PROPERTY_ATTR_SOURCEMETHOD).getNodeValue();
                            try {
                                Class sourceClazz = Class.forName(sourceClass);
                                Method[] methods = sourceClazz.getMethods();
                                Method targetMethod = null;
                                if(methods.length > 0){
                                    for(Method method : methods){
                                        if(StringUtils.equals(method.getName(),sourceMethod)){
                                            targetMethod = method;
                                            break;
                                        }
                                    }
                                }
                                Object[] args = new Object[0];
                                if(attrs.getNamedItem(PROPERTY_ATTR_SOURCEARGS) != null){
                                    String sourceArgs = environment.resolvePlaceholders(attrs.getNamedItem(PROPERTY_ATTR_SOURCEARGS).getNodeValue());
                                    args = sourceArgs.split(",");
                                }
                                Object sourceResult = targetMethod.invoke(sourceClazz.newInstance(),args);
                                if(sourceResult != null){
                                    propMap.put(propName, sourceResult);
                                }
                            } catch (ClassNotFoundException e) {
                                LOGGER.error("未找到资源类：{}",sourceClass,e);
                            } catch (IllegalAccessException e) {
                                LOGGER.error("资源类实例化异常：{}",sourceClass,e);
                            } catch (InstantiationException e) {
                                LOGGER.error("资源类实例化异常：{}",sourceClass,e);
                            } catch (InvocationTargetException e) {
                                LOGGER.error("资源类实例化异常：{}",sourceClass,e);
                            }
                        }
                    }
                }
            }
        }
        return propMap;
    }
}
