package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hejian@gtmap.cn">hejian</a>
 * @date 2022/12/08
 * @description 有房无房查询范围字典表
 */
@Table(name = "BDC_ZD_YFWFCXFW")
public class BdcZdYfwfcxfwDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "部门")
    private String dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "查询范围")
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
        return "BdcZdYfwfcxfwDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
