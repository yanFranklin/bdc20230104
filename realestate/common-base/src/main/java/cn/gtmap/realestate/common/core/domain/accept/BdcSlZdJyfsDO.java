package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 交易方式字典表
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-08-27 15:08
 **/
@Table(name = "BDC_SL_ZD_JYFS")
public class BdcSlZdJyfsDO {
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
        return "BdcSlZdJyfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
