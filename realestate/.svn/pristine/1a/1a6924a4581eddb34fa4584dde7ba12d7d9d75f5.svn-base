package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 发票类型字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-11-27 09:24
 **/
@Table(name = "BDC_ZD_FPLX")
public class BdcZdFplxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "发票类型代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "发票类型名称")
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
        return "BdcZdFplxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
