package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/17
 * @description  字典：养殖业方式
 */
@Table(name = "BDC_ZD_YZYFS")
public class BdcZdYzyfsDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "养殖业方式代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "养殖业方式名称")
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
        return "BdcZdYzyfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
