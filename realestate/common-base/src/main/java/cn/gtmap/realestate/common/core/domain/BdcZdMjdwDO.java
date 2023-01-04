package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/15
 * @description  字典：面积单位
 */
@Table(name = "BDC_ZD_MJDW")
public class BdcZdMjdwDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "面积单位代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "面积单位名称")
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
        return "BdcZdMjdwDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
