package cn.gtmap.realestate.common.core.dto.init.znsh;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/29
 * @description 数据核验对比结果
 */
public class BdcSjjybdDTO {

    @ApiModelProperty(value = "是否一致")
    private Integer sfyz;

    @ApiModelProperty(value = "数据来源")
    private String sjly;

    @ApiModelProperty(value = "数据值")
    private String value;

    public Integer getSfyz() {
        return sfyz;
    }

    public void setSfyz(Integer sfyz) {
        this.sfyz = sfyz;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
