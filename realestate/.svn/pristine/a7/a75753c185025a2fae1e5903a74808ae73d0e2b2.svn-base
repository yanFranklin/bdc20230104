package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/14
 * @description 字典：证件种类
 */
@Table(name = "BDC_ZD_ZJZL")
public class BdcZdZjzlDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "证件种类代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "证件种类名称")
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
        return "BdcZdZjzlDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
