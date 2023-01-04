package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.3, 2020/12/11
 * @description 查询目的或用途
 */
@Table(name = "BDC_ZD_CXMDHYT")
public class BdcZdCxmdhytDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
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
        return "BdcZdCxmdhytDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
