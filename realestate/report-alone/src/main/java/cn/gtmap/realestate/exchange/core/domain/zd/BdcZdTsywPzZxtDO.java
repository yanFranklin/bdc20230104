package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/05/20
 * @description  字典：特殊业务配置项配置子系统
 */
@Table(name = "BDC_ZD_TSYWPZZXT")
public class BdcZdTsywPzZxtDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;
    /**
     * 名称
     */
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
        return "BdcZdTsywPzZxtDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
