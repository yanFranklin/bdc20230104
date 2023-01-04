package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 地段级别字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-08 10:01
 **/
@Table(name = "BDC_ZD_DDJB")
public class BdcZdDdjbDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "地段级别代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "地段级别名称")
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
        return "BdcDdjbDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
