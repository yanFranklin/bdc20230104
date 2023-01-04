package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/06/08
 * @description  字典：查封文件
 */
@Table(name = "BDC_ZD_CFWJ")
public class BdcZdCfwjDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "查封文件代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "查封文件名称")
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
        return "BdcZdCfwjDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
