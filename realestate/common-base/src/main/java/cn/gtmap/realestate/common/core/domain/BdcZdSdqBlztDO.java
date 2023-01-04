package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 水电气办理状态
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Table(name = "BDC_ZD_SDQBLZT")
public class BdcZdSdqBlztDO {
    @Id
    @ApiModelProperty(value = "办理状态代码")
    private Integer dm;

    @ApiModelProperty(value = "办理状态名称名称")
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
        return "BdcZdSdqBlztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
