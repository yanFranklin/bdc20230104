package cn.gtmap.realestate.supervise.core.qo;

import cn.gtmap.realestate.supervise.core.domain.BdcLfYcbjyjYcyyDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * 异常办件原因查询
 */
public class LfYcyyQO extends BdcLfYcbjyjYcyyDO {
    @ApiModelProperty(value = "录入时间起")
    private String lrsjq;

    @ApiModelProperty(value = "录入时间止")
    private String lrsjz;


    public String getLrsjq() {
        return lrsjq;
    }

    public void setLrsjq(String lrsjq) {
        this.lrsjq = lrsjq;
    }

    public String getLrsjz() {
        return lrsjz;
    }

    public void setLrsjz(String lrsjz) {
        this.lrsjz = lrsjz;
    }
}
