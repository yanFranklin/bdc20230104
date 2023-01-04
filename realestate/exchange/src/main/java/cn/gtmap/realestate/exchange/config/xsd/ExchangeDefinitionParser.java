package cn.gtmap.realestate.exchange.config.xsd;

import cn.gtmap.realestate.exchange.config.xsd.parser.LogParser;
import cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean;
import cn.gtmap.realestate.exchange.config.xsd.parser.RequestParser;
import cn.gtmap.realestate.exchange.config.xsd.parser.ResponseParser;
import cn.gtmap.realestate.exchange.config.xsd.parser.ServiceInfoParser;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-13
 * @description 自定义 Bean 解析器
 */
public class ExchangeDefinitionParser extends AbstractSingleBeanDefinitionParser {

    /**
     * Determine the bean class corresponding to the supplied {@link Element}.
     * <p>Note that, for application classes, it is generally preferable to
     * override {@link #getBeanClassName} instead, in order to avoid a direct
     * dependence on the bean implementation class. The BeanDefinitionParser
     * and its NamespaceHandler can be used within an IDE plugin then, even
     * if the application classes are not available on the plugin's classpath.
     *
     * @param element the {@code ElementBO} that is being parsed
     * @return the {@link Class} of the bean that is being defined via parsing
     * the supplied {@code ElementBO}, or {@code null} if none
     * @see #getBeanClassName
     */
    @Override
    protected Class<?> getBeanClass(Element element) {
        return ExchangeBean.class;
    }

    /**
     * Parse the supplied {@link Element} and populate the supplied
     * {@link BeanDefinitionBuilder} as required.
     * <p>The default implementation does nothing.
     *
     * @param element the XML element being parsed
     * @param builder used to define the {@code BeanDefinition}
     */
    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        builder.addPropertyValue("id",element.getAttribute("id"));
        // service-info 标签解析
        ServiceInfoParser.parse(element,parserContext,builder);
        // request 标签解析
        RequestParser.parse(element,parserContext,builder);
        // response 标签解析
        ResponseParser.parse(element,parserContext,builder);

        LogParser.parse(element,parserContext,builder);
    }
}
