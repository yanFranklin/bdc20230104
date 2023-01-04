package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/27
 * @description 土地等级字典表
 */
@Table(name = "BDC_ZD_TDDJ")
public class BdcZdTddjDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "土地等级代码")
    private Integer dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "土地等级名称")
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
        return "BdcZdTddjDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }

}
