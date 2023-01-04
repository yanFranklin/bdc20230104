package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/30
 * @description 案件状态字典表
 */
@Table(name = "BDC_ZD_AJZT")
public class BdcZdAjztDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "案件状态代码")
    private Integer dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "案件状态名称")
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
        return "BdcZdAjztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
