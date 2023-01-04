package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2018/12/29 21:19
 * @description 无居民海岛用途字典表
 */
@Table(name = "BDC_ZD_WJMHDYT")
public class BdcZdWjmhdytDO {
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
        return "BdcZdWjmhdytDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
