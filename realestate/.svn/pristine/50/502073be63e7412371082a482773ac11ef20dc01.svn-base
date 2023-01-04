package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 户型结构
 */
@Table(name = "BDC_ZD_HXJG")
public class BdcZdHxjgDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "户型结构代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "户型结构名称")
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
        return "BdcZdHxjgDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
