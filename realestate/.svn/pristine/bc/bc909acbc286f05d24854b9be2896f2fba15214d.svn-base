package cn.gtmap.realestate.exchange.core.bo.xsd;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description 相应结构体
 */
public class ResponseInfoBO extends TagWithRefereceBO {

    private DozerBeanMapper dozerBeanMapper;

    private List<ElementBO> elementBOList;

    private ExchangeBean sourceService;

    private String resultKey;

    private String tokenErrorBeanName;

    private String tokenType;

    private List<String> excludeKey;

    // 给参数赋给某个key 存放到response
    private String extendRequestBodyWithKey;

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenErrorBeanName() {
        return tokenErrorBeanName;
    }

    public void setTokenErrorBeanName(String tokenErrorBeanName) {
        this.tokenErrorBeanName = tokenErrorBeanName;
    }

    private boolean extendRequestBody;

    public List<ElementBO> getElementBOList() {
        return elementBOList;
    }

    public void setElementBOList(List<ElementBO> elementBOList) {
        this.elementBOList = elementBOList;
    }

    public DozerBeanMapper getDozerBeanMapper() {
        return dozerBeanMapper;
    }

    public void setDozerBeanMapper(DozerBeanMapper dozerBeanMapper) {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    public ExchangeBean getSourceService() {
        return sourceService;
    }

    public void setSourceService(ExchangeBean sourceService) {
        this.sourceService = sourceService;
    }

    public String getResultKey() {
        return resultKey;
    }

    public void setResultKey(String resultKey) {
        this.resultKey = resultKey;
    }

    public boolean isExtendRequestBody() {
        return extendRequestBody;
    }

    public void setExtendRequestBody(boolean extendRequestBody) {
        this.extendRequestBody = extendRequestBody;
    }

    public List<String> getExcludeKey() {
        return excludeKey;
    }

    public void setExcludeKey(List<String> excludeKey) {
        this.excludeKey = excludeKey;
    }

    public String getExtendRequestBodyWithKey() {
        return extendRequestBodyWithKey;
    }

    public void setExtendRequestBodyWithKey(String extendRequestBodyWithKey) {
        this.extendRequestBodyWithKey = extendRequestBodyWithKey;
    }
}
