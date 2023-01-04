package cn.gtmap.realestate.exchange.config.xsd.parser;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ResponseInfoBO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description
 */
public class ResponseParser {

    private final static String NODE_TAG_NAME = "ex:response";

    private final static String ATTR_DOZERXML = "dozerXml";

    private final static String ATT_SOURCESERVICE = "sourceService";

    private final static String ATT_RESULTKEY = "resultKey";

    private final static String ATT_EXTENDREQUESTBODY = "extendRequestBody";

    // 给参数赋给某个key 存放到response
    private final static String ATT_EXTENDERQUESTBODYWITHKEY = "extendRequestBodyWithKey";

    private final static String ATT_TOKENERROR_CHECK = "tokenErrorService";
    private final static String ATT_TOKEN_TYPE = "tokenType";

    private final static String ATT_EXCLUDEKEY = "excludeKey";

    public static void parse(Element element, ParserContext parserContext,
                             BeanDefinitionBuilder builder){
        NodeList nodeList = element.getElementsByTagName(NODE_TAG_NAME);
        ResponseInfoBO responseInfoBO = new ResponseInfoBO();
        if(nodeList.getLength() == 1){
            Node responseNode = nodeList.item(0);
            List<ElementBO> elementBOList = ElementParser.parse(element,responseNode,parserContext);
            if(CollectionUtils.isNotEmpty(elementBOList)){
                responseInfoBO.setElementBOList(elementBOList);
            }
            NamedNodeMap attrs = responseNode.getAttributes();
            if(attrs.getNamedItem(ATTR_DOZERXML) != null){
                String dozerFilePath = attrs.getNamedItem(ATTR_DOZERXML).getNodeValue();
                List<String> mappingFiles = new ArrayList<>();
                mappingFiles.add(dozerFilePath);
                DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
                dozerBeanMapper.setMappingFiles(mappingFiles);
                responseInfoBO.setDozerBeanMapper(dozerBeanMapper);
            }
            if(attrs.getNamedItem(ATT_SOURCESERVICE) != null){
                responseInfoBO.setRefBeanName(attrs.getNamedItem(ATT_SOURCESERVICE).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_RESULTKEY) != null){
                responseInfoBO.setResultKey(attrs.getNamedItem(ATT_RESULTKEY).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_TOKENERROR_CHECK) != null){
                responseInfoBO.setTokenErrorBeanName(attrs.getNamedItem(ATT_TOKENERROR_CHECK).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_TOKEN_TYPE) != null){
                responseInfoBO.setTokenType(attrs.getNamedItem(ATT_TOKEN_TYPE).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_EXTENDREQUESTBODY) != null){
                String extendRequest = attrs.getNamedItem(ATT_EXTENDREQUESTBODY).getNodeValue();
                responseInfoBO.setExtendRequestBody(Boolean.parseBoolean(extendRequest));
            }

            if(attrs.getNamedItem(ATT_EXTENDREQUESTBODY) != null){
                String extendRequest = attrs.getNamedItem(ATT_EXTENDREQUESTBODY).getNodeValue();
                responseInfoBO.setExtendRequestBody(Boolean.parseBoolean(extendRequest));
            }

            if(attrs.getNamedItem(ATT_EXCLUDEKEY) != null){
                String excludeKey = attrs.getNamedItem(ATT_EXCLUDEKEY).getNodeValue();
                String[] arr = excludeKey.split(",");
                responseInfoBO.setExcludeKey(Arrays.asList(arr));
            }

            if(attrs.getNamedItem(ATT_EXTENDERQUESTBODYWITHKEY) != null){
                String temp = attrs.getNamedItem(ATT_EXTENDERQUESTBODYWITHKEY).getNodeValue();
                responseInfoBO.setExtendRequestBodyWithKey(temp);
            }
        }
        builder.addPropertyValue("responseInfoBO",responseInfoBO);
    }

}
