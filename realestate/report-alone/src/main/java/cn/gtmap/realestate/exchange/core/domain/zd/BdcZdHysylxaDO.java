package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/22
 * @description  海域使用类型A表
 */
@Table(name = "BDC_ZD_HYSYLXA")
public class BdcZdHysylxaDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "海域使用类型A代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "海域使用类型A名称")
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
        return "BdcZdHysylxaDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
