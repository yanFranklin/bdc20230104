package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/14
 * @description
 */
@ApiModel(value = "CbzdResponseDTO", description = "承包宗地DTO")
public class CbzdResponseDTO extends ZdResponseDTO {
    /**
     * 发包方名称
     */
    @ApiModelProperty(value = "发包方名称")
    private String fbfmc;
    /**
     * 承包方名称
     */
    @ApiModelProperty(value = "承包方名称")
    private String cbfmc;

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    public String getCbfmc() {
        return cbfmc;
    }

    public void setCbfmc(String cbfmc) {
        this.cbfmc = cbfmc;
    }

    @Override
    public String toString() {
        return "CbzdResponseDTO{" +
                "fbfmc='" + fbfmc + '\'' +
                ", cbfmc='" + cbfmc + '\'' +
                '}';
    }
}