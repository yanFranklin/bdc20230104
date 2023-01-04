package cn.gtmap.realestate.common.core.dto.etl;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 2021/07/28,1.0
 * @description
 */
public class CfmjDTO {

    @ApiModelProperty(value = "使用权面积")
    private Double syqmj;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    public Double getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(Double syqmj) {
        this.syqmj = syqmj;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }
}
