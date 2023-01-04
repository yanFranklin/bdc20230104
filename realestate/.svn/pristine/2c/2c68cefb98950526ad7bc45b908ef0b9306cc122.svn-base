package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/6.
 * @description
 */
@Table(name = "BDC_ZD_CBTDYT")
public class BdcZdCbtdyt {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "承包土地用途代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "承包土地用途名称")
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
        return "BdcZdCbtdyt{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
