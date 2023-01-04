package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/10/27
 * @description  字典：币种
 */
@Table(name = "BDC_ZD_BIZ")
public class BdcZdBizDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "币种类型代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "币种类型名称")
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
        return "BdcZdBizDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
