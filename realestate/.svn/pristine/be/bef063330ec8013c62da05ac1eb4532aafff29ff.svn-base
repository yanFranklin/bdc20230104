package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2018/12/29 21:21
 * @description 建筑物状态字典表
 */
@Table(name = "BDC_ZD_JZWZT")
public class BdcZdJzwztDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "无居民海岛用途")
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
        return "BdcZdJzwztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
