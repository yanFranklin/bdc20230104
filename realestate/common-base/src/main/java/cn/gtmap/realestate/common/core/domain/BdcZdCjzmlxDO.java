package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 出具证明类型
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 17:00
 **/
@Table(name = "BDC_ZD_CJZMLX")
public class BdcZdCjzmlxDO {
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
        return "BdcZdQdfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
