package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 权属转移对象字典表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-07-01 15:08
 **/
@Table(name = "BDC_SL_ZD_QSZYDX")
public class BdcSlZdQszydxDO {
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;

    @ApiModelProperty(value = "名称")
    private String mc;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
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
        return "BdcSlZdQszydxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
