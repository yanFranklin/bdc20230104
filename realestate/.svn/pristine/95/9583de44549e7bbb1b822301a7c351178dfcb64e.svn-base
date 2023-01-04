package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/25
 * @description
 */
@Table(name = "BDC_ZD_SJLX")
public class BdcZdSjlxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "收件类型代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "收件类型名称")
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
        return "BdcZdSjlxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
