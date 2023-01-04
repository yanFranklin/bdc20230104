package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/19
 * @description
 */
@ApiModel(value = "AcceptPageResponseDTO", description = "受理系统所需DTO")
public class AcceptPageResponseDTO {
    /**
     * 权籍id
     */
    @ApiModelProperty(value = "权籍id")
    private String qjid;

    public String getQjid() {
        return qjid;
    }

    public void setQjid(String qjid) {
        this.qjid = qjid;
    }

    @Override
    public String toString() {
        return "AcceptPageResponseDTO{" +
                "qjid='" + qjid + '\'' +
                '}';
    }
}