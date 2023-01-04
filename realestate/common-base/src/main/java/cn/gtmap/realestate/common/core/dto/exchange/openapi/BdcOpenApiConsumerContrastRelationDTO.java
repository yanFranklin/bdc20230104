package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/11/30 15:13
 * @description TODO
 */
public class BdcOpenApiConsumerContrastRelationDTO {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "消费方")
    private String consumer;

    @ApiModelProperty(value = "权限标识")
    private String principal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }
}
