package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/12/29
 * @description 区县字典表
 */
@Table(name = "BDC_ZD_QX")
public class BdcZdQxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "区县代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "区县名称")
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
        return "BdcZdQxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}