package cn.gtmap.realestate.common.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Table(name = "BDC_ZD_QLRLB")
public class BdcZdQlrlbDO {
    @Id
    @ApiModelProperty(value = "权利人类别代码")
    private Integer dm;

    @ApiModelProperty(value = "权利人类别名称")
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
        return "BdcZdQlrlbDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
