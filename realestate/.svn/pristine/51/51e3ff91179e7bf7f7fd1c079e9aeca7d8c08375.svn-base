package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/22
 * @description
 */
@Table(name = "BDC_ZD_TDYT")
public class BdcZdTdytDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "土地用途代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "土地用途代码名称")
    private String mc;

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

    @Override
    public String toString() {
        return "BdcZdTdytDO{" +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                '}';
    }
}
