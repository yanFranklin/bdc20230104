package cn.gtmap.realestate.exchange.config.xsd;

import cn.gtmap.realestate.exchange.config.xsd.parser.ElementParser;
import cn.gtmap.realestate.exchange.config.xsd.parser.ServiceInfoParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-13
 * @description 自定义命名空间
 */
public class NameSpaceHandler extends NamespaceHandlerSupport {
    /**
     * Invoked by the {@link DefaultBeanDefinitionDocumentReader} after
     * construction but before any custom elements are parsed.
     *
     * @see NamespaceHandlerSupport#registerBeanDefinitionParser(String, BeanDefinitionParser)
     */
    @Override
    public void init() {
        registerBeanDefinitionParser("bean",new ExchangeDefinitionParser());
    }
}
