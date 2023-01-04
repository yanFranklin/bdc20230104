package cn.gtmap.realestate.exchange.config.xsd.parser;

import cn.gtmap.realestate.common.core.ex.AppException;
import cn.gtmap.realestate.common.util.CheckParameter;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import com.sun.org.apache.xerces.internal.dom.DeferredElementNSImpl;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description
 */
public class ElementParser {

    /**
     * 日志组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ElementParser.class);

    public static final String NODE_TAG_NAME = "ex:element";

    private final static String ATTR_NAME = "name";

    private final static String ATTR_DOZERXML = "dozerXml";

    // 相对路径 的xml文件名
    private final static String ATTR_DOZERRELXML = "dozerRelXml";

    private final static String ATT_SOURCESERVICE = "sourceService";

    private final static String ATT_LISTTOOBJECT = "listToObject";

    private final static String ATT_OBJECTTOLIST = "objectToList";

    private final static String ATT_NOTNULLRETURN = "notNullReturn";

    private final static String ATT_OBJECTTOJSONSTRING = "objectToJsonString";

    private final static String ATT_APPENDREQUESTBODY = "appendRequestBody";

    private final static String ATT_NOTEMPTY = "notEmpty";

    private final static String ATT_EMPTYEXMSG = "emptyExMsg";

    private final static String ATT_IGNOREEXCEPTION = "ignoreException";

    private final static String ATT_SAVENULL = "saveNull";

    // 加密算法
    private final static String ATT_ENCODEMETHOD = "encodeMethod";

    // 解密算法
    private final static String ATT_DECODEMETHOD = "decodeMethod";

    // 实体转XML 使用的方法
    private final static String ATT_OBJECTTOXMLSTRINGMETHOD = "objectToXmlStringMethod";

    // xml转实体 默认使用xstream方式 参数为 转换的实体类全名称
    private final static String ATT_XMLTOOBJECT = "xmlToObject";

    // base64解码
    private final static String ATT_BASE64DECODE = "base64Decode";

    //对返回值进行格式转换
    private final static String ATT_CHARASETCHANGE = "charsetChange";

    // 是否先执行dozer转换
    private final static String ATT_DOZERFIRST = "dozerFirst";

    // 是否先执行source
    private final static String ATT_SOURCEFIRST = "sourceFirst";

    private final static String ATT_NOSETREQUESTBODY = "noSetRequestBody";

    public static List<ElementBO> parse(Element element, Node parentNode, ParserContext parserContext){
        List<ElementBO> elementBOList = new ArrayList<>();
        if(parentNode instanceof DeferredElementNSImpl){
            NodeList elemNodeList = ((DeferredElementNSImpl) parentNode).getElementsByTagName(NODE_TAG_NAME);
            if(elemNodeList.getLength() > 0){
                for(int i = 0 ; i < elemNodeList.getLength() ; i++){
                    ElementBO elementBO = parseItem(element,parentNode,elemNodeList.item(i),parserContext);
                    if(CheckParameter.checkAnyParameter(elementBO)){
                        elementBOList.add(elementBO);
                    }
                }
            }
        }
        return elementBOList;
    }

    private static ElementBO parseItem(Element beanElem,Node parentNode,Node element,ParserContext parserContext){
        ElementBO elementBO = new ElementBO();
        NamedNodeMap attrs = element.getAttributes();
        if(attrs.getNamedItem(ATTR_NAME) != null){
            elementBO.setName(attrs.getNamedItem(ATTR_NAME).getNodeValue());
        }
        if(attrs.getNamedItem(ATTR_DOZERXML) != null){
            // 定义 对照文件 全路径
            String dozerFilePath = attrs.getNamedItem(ATTR_DOZERXML).getNodeValue();
            elementBO.setDozerXml(dozerFilePath);
        }

        if(attrs.getNamedItem(ATTR_DOZERRELXML) != null){
            String dozerRelFilePath = attrs.getNamedItem(ATTR_DOZERRELXML).getNodeValue();
            // 设置默认DOZER存放位置，约束为当前bean定义的xml路径 + beanId + request/response

            try {
                String curXmlPath = parserContext.getReaderContext().getResource().getURL().getPath();
                if(StringUtils.isNotBlank(curXmlPath) && curXmlPath.contains("exchange") && curXmlPath.endsWith(".xml")){
                    curXmlPath = curXmlPath.substring(curXmlPath.lastIndexOf("exchange")+8,curXmlPath.length());
                    curXmlPath = curXmlPath.replace(".xml","");
                    curXmlPath += "/" + beanElem.getAttribute("id") + "/" + parentNode.getLocalName();
                    curXmlPath = "conf/dozer"+curXmlPath + "/" + dozerRelFilePath;
                    elementBO.setDozerRelXml(curXmlPath);
                }
            } catch (IOException e) {
                LOGGER.error("解析dozerRelXml配置路径异常");
                throw new AppException("解析dozerRelXml配置路径异常");
            }
        }
        if(attrs.getNamedItem(ATT_SOURCESERVICE) != null){
            elementBO.setRefBeanName(attrs.getNamedItem(ATT_SOURCESERVICE).getNodeValue());
        }

        if(attrs.getNamedItem(ATT_LISTTOOBJECT) != null){
            String listToObject = attrs.getNamedItem(ATT_LISTTOOBJECT).getNodeValue();
            elementBO.setListToObject(BooleanUtils.toBoolean(listToObject));
        }

        if(attrs.getNamedItem(ATT_OBJECTTOLIST) != null){
            String objectToList = attrs.getNamedItem(ATT_OBJECTTOLIST).getNodeValue();
            elementBO.setObjectToList(BooleanUtils.toBoolean(objectToList));
        }

        if(attrs.getNamedItem(ATT_NOTNULLRETURN) != null){
            String notNullReturn = attrs.getNamedItem(ATT_NOTNULLRETURN).getNodeValue();
            elementBO.setNotNullReturn(BooleanUtils.toBoolean(notNullReturn));
        }
        if(attrs.getNamedItem(ATT_OBJECTTOJSONSTRING) != null){
            String objectToJsonString = attrs.getNamedItem(ATT_OBJECTTOJSONSTRING).getNodeValue();
            elementBO.setObjectToJsonString(BooleanUtils.toBoolean(objectToJsonString));
        }

        if(attrs.getNamedItem(ATT_APPENDREQUESTBODY) != null){
            String appendRequestBody = attrs.getNamedItem(ATT_APPENDREQUESTBODY).getNodeValue();
            elementBO.setAppendRequestBody(BooleanUtils.toBoolean(appendRequestBody));
        }

        if(attrs.getNamedItem(ATT_NOTEMPTY) != null){
            String notEmpty = attrs.getNamedItem(ATT_NOTEMPTY).getNodeValue();
            elementBO.setNotEmpty(BooleanUtils.toBoolean(notEmpty));
        }
        if(attrs.getNamedItem(ATT_IGNOREEXCEPTION) != null){
            String ignoreException = attrs.getNamedItem(ATT_IGNOREEXCEPTION).getNodeValue();
            elementBO.setIgnoreException(BooleanUtils.toBoolean(ignoreException));
        }
        if(attrs.getNamedItem(ATT_SAVENULL) != null){
            String saveNull = attrs.getNamedItem(ATT_SAVENULL).getNodeValue();
            elementBO.setSaveNull(saveNull);
        }

        if(attrs.getNamedItem(ATT_EMPTYEXMSG) != null){
            elementBO.setEmptyExMsg(attrs.getNamedItem(ATT_EMPTYEXMSG).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_ENCODEMETHOD) != null){
            elementBO.setEncodeMethod(attrs.getNamedItem(ATT_ENCODEMETHOD).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_DECODEMETHOD) != null){
            elementBO.setDecodeMethod(attrs.getNamedItem(ATT_DECODEMETHOD).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_OBJECTTOXMLSTRINGMETHOD) != null){
            elementBO.setObjectToXmlStringMethod(attrs.getNamedItem(ATT_OBJECTTOXMLSTRINGMETHOD).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_XMLTOOBJECT) != null){
            elementBO.setXmlToObject(attrs.getNamedItem(ATT_XMLTOOBJECT).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_BASE64DECODE) != null){
            elementBO.setBase64Decode(attrs.getNamedItem(ATT_BASE64DECODE).getNodeValue());
        }

        if(attrs.getNamedItem(ATT_CHARASETCHANGE) != null){
            elementBO.setCharsetChange(attrs.getNamedItem(ATT_CHARASETCHANGE).getNodeValue());
        }

        if(attrs.getNamedItem(ATT_DOZERFIRST) != null){
            elementBO.setDozerFirst(attrs.getNamedItem(ATT_DOZERFIRST).getNodeValue());
        }
        if(attrs.getNamedItem(ATT_SOURCEFIRST) != null){
            elementBO.setSourceFirst(attrs.getNamedItem(ATT_SOURCEFIRST).getNodeValue());
        }

        if(attrs.getNamedItem(ATT_NOSETREQUESTBODY) != null){
            String notNullReturn = attrs.getNamedItem(ATT_NOSETREQUESTBODY).getNodeValue();
            elementBO.setNoSetRequestBody(BooleanUtils.toBoolean(notNullReturn));
        }
        return elementBO;
    }
}
