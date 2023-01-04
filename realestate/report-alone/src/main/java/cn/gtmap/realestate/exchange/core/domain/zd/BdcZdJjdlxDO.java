package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 交接单类型
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Table(name = "BDC_ZD_JJDLX")
public class BdcZdJjdlxDO {
    @Id
    @ApiModelProperty(value = "交接单类型代码")
    private Integer dm;

    @ApiModelProperty(value = "交接单类型名称")
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
        return "BdcZdJjdlxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
