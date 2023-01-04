package cn.gtmap.realestate.exchange.config.xsd.parser;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;
import cn.gtmap.realestate.exchange.core.bo.xsd.ElementBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.RequestInfoBO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
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
public class RequestParser {

    private final static String NODE_TAG_NAME = "ex:request";

    private final static String ATTR_DOZERXML = "dozerXml";

    private final static String ATT_SOURCESERVICE = "sourceService";

    private final static String ATT_RESULTKEY = "resultKey";

    private final static String ATT_PARAMREQUIRED = "paramRequired";

    private final static String ATT_NAME = "name";

    private final static String ATT_EXTENDREQUESTBODY = "extendRequestBody";

    private final static String ATT_EXCLUDEKEY = "excludeKey";

    private final static String ATT_REPLACEREQUESTBODY = "replaceRequestBody";


    public static void parse(Element element, ParserContext parserContext,
                             BeanDefinitionBuilder builder){
        NodeList nodeList = element.getElementsByTagName(NODE_TAG_NAME);
        RequestInfoBO requestInfoBO = new RequestInfoBO();
        if(nodeList.getLength() == 1){
            Node requestNode = nodeList.item(0);
            List<ElementBO> elementBOList = ElementParser.parse(element,requestNode,parserContext);
            if(CollectionUtils.isNotEmpty(elementBOList)){
                requestInfoBO.setElementBOList(elementBOList);
            }
            NamedNodeMap attrs = requestNode.getAttributes();
            if(attrs.getNamedItem(ATTR_DOZERXML) != null){
                // 定义 对照服务
                String dozerFilePath = attrs.getNamedItem(ATTR_DOZERXML).getNodeValue();
                List<String> mappingFiles = new ArrayList<>();
                mappingFiles.add(dozerFilePath);
                DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
                dozerBeanMapper.setMappingFiles(mappingFiles);
                requestInfoBO.setDozerBeanMapper(dozerBeanMapper);
            }
            if(attrs.getNamedItem(ATT_SOURCESERVICE) != null){
                requestInfoBO.setRefBeanName(attrs.getNamedItem(ATT_SOURCESERVICE).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_RESULTKEY) != null){
                requestInfoBO.setResultKey(attrs.getNamedItem(ATT_RESULTKEY).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_PARAMREQUIRED) != null){
                String paramterRequired = attrs.getNamedItem(ATT_PARAMREQUIRED).getNodeValue();
                requestInfoBO.setParamRequired(Boolean.parseBoolean(paramterRequired));
            }

            if(attrs.getNamedItem(ATT_NAME) != null){
                requestInfoBO.setName(attrs.getNamedItem(ATT_NAME).getNodeValue());
            }
            if(attrs.getNamedItem(ATT_EXTENDREQUESTBODY) != null){
                String extendRequest = attrs.getNamedItem(ATT_EXTENDREQUESTBODY).getNodeValue();
                requestInfoBO.setExtendRequestBody(Boolean.parseBoolean(extendRequest));
            }

            if(attrs.getNamedItem(ATT_EXCLUDEKEY) != null){
                String excludeKey = attrs.getNamedItem(ATT_EXCLUDEKEY).getNodeValue();
                String[] arr = excludeKey.split(",");
                requestInfoBO.setExcludeKey(Arrays.asList(arr));
            }

            if(attrs.getNamedItem(ATT_REPLACEREQUESTBODY) != null){
                String replace = attrs.getNamedItem(ATT_REPLACEREQUESTBODY).getNodeValue();
                requestInfoBO.setReplaceRequestBody(BooleanUtils.toBoolean(replace));
            }
        }



        builder.addPropertyValue("requestInfoBO",requestInfoBO);
    }
}
