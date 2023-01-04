package cn.gtmap.realestate.exchange.core.bo.xsd;

import cn.gtmap.realestate.common.core.support.spring.Container;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-13
 * @description 交互共享 Bean
 */
public class ExchangeBean {

    private String id;

    private ServiceInfoBO serviceInfoBO;

    private RequestInfoBO requestInfoBO;

    private ResponseInfoBO responseInfoBO;

    private LogBO logBO;

    public String getId() {
        return id;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param beanName
     * @return cn.gtmap.realestate.exchange.core.bo.xsd.ExchangeBean
     * @description  获取ExchangeBean
     */
    public static ExchangeBean getExchangeBean(String beanName){
        ExchangeBean exchangeBean = null;
        if(StringUtils.isNotBlank(beanName)){
            Object bean = Container.getBean(beanName);
            if(bean != null){
                exchangeBean = (ExchangeBean) bean;
            }
        }
        return exchangeBean;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceInfoBO getServiceInfoBO() {
        return serviceInfoBO;
    }

    public void setServiceInfoBO(ServiceInfoBO serviceInfoBO) {
        this.serviceInfoBO = serviceInfoBO;
    }

    public RequestInfoBO getRequestInfoBO() {
        return requestInfoBO;
    }

    public void setRequestInfoBO(RequestInfoBO requestInfoBO) {
        this.requestInfoBO = requestInfoBO;
    }

    public ResponseInfoBO getResponseInfoBO() {
        return responseInfoBO;
    }

    public void setResponseInfoBO(ResponseInfoBO responseInfoBO) {
        this.responseInfoBO = responseInfoBO;
    }

    public LogBO getLogBO() {
        return logBO;
    }

    public void setLogBO(LogBO logBO) {
        this.logBO = logBO;
    }
}
