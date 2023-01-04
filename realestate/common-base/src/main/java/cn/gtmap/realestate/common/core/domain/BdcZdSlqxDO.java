package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BDC_ZD_SLQX")
public class BdcZdSlqxDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "设立情形代码")
    private Integer dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "设立情形名称")
    private String mc;

    public Integer getDm() {
        return dm;
    }

    public void setDm(Integer dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public String toString() {
        return "BDC_ZD_SLQX{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
