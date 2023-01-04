package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/16
 * @description  字典：构(建)筑物类型
 */
@Table(name = "BDC_ZD_GJZWLX")
public class BdcZdGjzwlxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "构(建)筑物类型代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "构(建)筑物类型名称")
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
        return "BdcZdGjzwlxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
