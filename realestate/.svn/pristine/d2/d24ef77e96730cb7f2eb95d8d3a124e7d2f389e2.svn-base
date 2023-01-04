package cn.gtmap.realestate.exchange.core.bo.xsd;

import cn.gtmap.realestate.common.core.dozer.DozerBeanMapper;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-14
 * @description 请求结构体
 */
public class RequestInfoBO extends TagWithRefereceBO{

    private DozerBeanMapper dozerBeanMapper;

    private List<ElementBO> elementBOList;

    private ExchangeBean sourceService;

    private String resultKey;

    private boolean paramRequired = true;

    private String name;

    private boolean extendRequestBody;

    private List<String> excludeKey;

    //是否替换整个requestBody
    private boolean replaceRequestBody;

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

    public boolean isParamRequired() {
        return paramRequired;
    }

    public void setParamRequired(boolean paramRequired) {
        this.paramRequired = paramRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isReplaceRequestBody() {
        return replaceRequestBody;
    }

    public void setReplaceRequestBody(boolean replaceRequestBody) {
        this.replaceRequestBody = replaceRequestBody;
    }
}
