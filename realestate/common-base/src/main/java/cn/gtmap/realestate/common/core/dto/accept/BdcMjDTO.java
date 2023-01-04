package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 不动产面积DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-06-19 14:08
 **/
@ApiModel(value = "BdcMjDTO", description = "不动产各类面积字段")
public class BdcMjDTO implements Serializable {

    private static final long serialVersionUID = -8730458321541572589L;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "定着物面积")
    private Double dzwmj;

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    @Override
    public String toString() {
        return "BdcMjDTO{" +
                "zdzhmj=" + zdzhmj +
                ", dzwmj=" + dzwmj +
                '}';
    }
}
