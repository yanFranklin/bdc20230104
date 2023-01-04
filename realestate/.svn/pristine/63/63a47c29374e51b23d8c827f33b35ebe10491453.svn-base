package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BDC_SL_ZD_GGTZ")
public class BdcZdGgptDO {
    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String mc;



    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    @Override
    public String toString() {
        return "BdcZdGgptDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
