package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/03/27
 * @description  字典：上报状态
 */
@Table(name = "BDC_ZD_SBZT")
public class BdcZdSbztDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "上报状态代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "上报状态名称")
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
        return "BdcZdSbztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
