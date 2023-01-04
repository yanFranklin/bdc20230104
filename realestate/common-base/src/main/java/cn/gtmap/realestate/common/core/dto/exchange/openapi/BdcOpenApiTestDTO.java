package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gmail.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/10/28 14:22
 */
public class BdcOpenApiTestDTO {

    @ApiModelProperty(value = "接口id")
    private String apiId;

    @ApiModelProperty(value = "接口地址")
    private String url;

    @ApiModelProperty(value = "参数")
    private String paramJson;

    @ApiModelProperty(value = "标识 0页面测试 1调用")
    private Integer flag ;

    @ApiModelProperty(value = "接口类型")
    private String type ;

    @ApiModelProperty(value = "beanId")
    private String beanId;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "接口名称")
    private String methodName;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParamJson() {
        return paramJson;
    }

    public void setParamJson(String paramJson) {
        this.paramJson = paramJson;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeanId() {
        return beanId;
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
