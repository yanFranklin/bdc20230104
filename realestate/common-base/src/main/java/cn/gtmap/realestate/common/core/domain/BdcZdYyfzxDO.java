package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2021-03-04
 * @description  字典：预约分中心
 */
@Table(name = "BDC_ZD_YYFZX ")
public class BdcZdYyfzxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "预约分中心代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "预约分中心名称")
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
        return "BdcZdYyfzxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
