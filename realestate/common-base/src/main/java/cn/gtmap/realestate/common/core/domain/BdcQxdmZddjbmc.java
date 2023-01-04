package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
 * @version 1.0
 * @date 2022/10/21 16:43
 * @description  字典：登记簿名称对照
 */
@Table(name = "BDC_ZD_QXDMDJBMC")
public class BdcQxdmZddjbmc {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "编码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "登记簿名称")
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
        return "BdcZdDjjg{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }

}
