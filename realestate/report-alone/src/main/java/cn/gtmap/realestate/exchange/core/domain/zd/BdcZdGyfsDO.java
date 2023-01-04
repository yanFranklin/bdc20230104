package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/19
 * @description 共有方式字典
 */
@Table(name = "BDC_ZD_GYFS")
public class BdcZdGyfsDO {

    @ApiModelProperty(value = "共有方式代码")
    Integer dm;
    @ApiModelProperty(value = "共有方式名称")
    String mc;

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
        return "BdcZdGyfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }

}
