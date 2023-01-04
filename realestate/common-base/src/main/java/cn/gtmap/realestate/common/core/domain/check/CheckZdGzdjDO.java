package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 规则等级字典表
 */
@Table(name = "CHECK_ZD_GZDJ")
@ApiModel(value = "CheckZdGzdjDO",description = "规则等级字典表")
public class CheckZdGzdjDO {
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;
    @ApiModelProperty(value = "名称")
    private String mc;
    @ApiModelProperty(value = "说明")
    private String sm;

    @Override
    public String toString() {
        return "CheckZdGzdj{" +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                ", sm='" + sm + '\'' +
                '}';
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm;
    }
}
