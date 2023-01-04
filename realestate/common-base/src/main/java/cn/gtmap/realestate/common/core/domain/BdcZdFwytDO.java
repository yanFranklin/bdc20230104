package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/16
 * @description  字典：房屋用途
 */
@Table(name = "BDC_ZD_FWYT")
public class BdcZdFwytDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "房屋用途代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "房屋用途名称")
    private String mc;

    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

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

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        return "BdcZdFwytDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                ", sxh=" + sxh +
                '}';
    }
}
