package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/17.
 * @description 省市字典表
 */
@Table(name = "BDC_ZD_SS")
public class BdcZdSsDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "省市代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "省市名称")
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
        return "BdcZdSs{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
