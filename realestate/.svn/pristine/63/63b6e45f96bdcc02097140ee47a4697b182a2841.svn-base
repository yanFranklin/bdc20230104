package cn.gtmap.realestate.exchange.config.xsd.parser;

import cn.gtmap.realestate.common.core.support.spring.Container;
import cn.gtmap.realestate.exchange.core.bo.xsd.LogBO;
import cn.gtmap.realestate.exchange.core.bo.xsd.ServiceInfoBO;
import cn.gtmap.realestate.exchange.service.inf.log.LogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.env.Environment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-24
 * @description XSD 解析 日志信息实体
 */
public class LogParser {

    private final static String NODE_TAG_NAME = "ex:log";

    private final static String ATTR_LOGEVENTNAME = "logEventName";

    private final static String ATTR_DSFFLAG = "dsfFlag";

    private final static String ATTR_REQUESTER = "requester";

    private final static String ATTR_RESPONSER = "responser";

    // 可配置 不动产请求地址 仅用于记录日志
    private final static String ATTR_BDCDZ = "bdcdz";

    // 可配置 记录日志 使用哪个服务
    private final static String ATTR_LOG_SERVICE = "logService";

    private final static String ATTR_REQ_CUSTOM = "reqCustom";

    private final static String ATTR_RESP_CUSTOM = "respCustom";

    public static void parse(Element element, ParserContext parserContext,
                             BeanDefinitionBuilder builder){
        NodeList nodeList = element.getElementsByTagName(NODE_TAG_NAME);
        if(nodeList.getLength() == 1){
            LogBO logBO = new LogBO();
            Node serviceInfoNode = nodeList.item(0);
            NamedNodeMap attrs = serviceInfoNode.getAttributes();
            // 获取日志事件名称
            if(attrs.getNamedItem(ATTR_LOGEVENTNAME) != null){
                logBO.setLogEventName(attrs.getNamedItem(ATTR_LOGEVENTNAME).getNodeValue());
            }
            // 获取第三方标志
            if(attrs.getNamedItem(ATTR_DSFFLAG) != null){
                logBO.setDsfFlag(attrs.getNamedItem(ATTR_DSFFLAG).getNodeValue());
            }
            // 获取请求方
            if(attrs.getNamedItem(ATTR_REQUESTER) != null){
                logBO.setRequester(attrs.getNamedItem(ATTR_REQUESTER).getNodeValue());
            }
            // 获取响应方
            if(attrs.getNamedItem(ATTR_RESPONSER) != null){
                logBO.setResponser(attrs.getNamedItem(ATTR_RESPONSER).getNodeValue());
            }
            // 获取配置的请求地址
            if(attrs.getNamedItem(ATTR_BDCDZ) != null){
                logBO.setBdcdz(attrs.getNamedItem(ATTR_BDCDZ).getNodeValue());
            }
            // 获取 配置的 记录日志服务
            if(attrs.getNamedItem(ATTR_LOG_SERVICE) != null){
                logBO.setLogService(attrs.getNamedItem(ATTR_LOG_SERVICE).getNodeValue());
            }
            // 获取 入参配置的 自定义日志
            if(attrs.getNamedItem(ATTR_REQ_CUSTOM) != null){
                logBO.setReqCustom(attrs.getNamedItem(ATTR_REQ_CUSTOM).getNodeValue());
            }
            // 获取 出参配置的 自定义日志
            if(attrs.getNamedItem(ATTR_RESP_CUSTOM) != null){
                logBO.setRespCustom(attrs.getNamedItem(ATTR_RESP_CUSTOM).getNodeValue());
            }
            // 获取 配置的 记录日志服务
            logBO.setBeanId(element.getAttribute("id"));
            builder.addPropertyValue("logBO",logBO);
        }
    }
}
