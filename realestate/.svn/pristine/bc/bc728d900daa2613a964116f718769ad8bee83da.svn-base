package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/27
 * @description 税务种类字典表
 */
@Table(name = "BDC_SL_ZD_SWZL")
public class BdcSlZdSwzlDO {

    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;

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
        return "BdcSlZdSwzlDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
