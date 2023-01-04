package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/17
 * @description  字典：操作日志操作状态
 */
@Table(name = "BDC_ZD_CZRZCZZT")
public class BdcZdCzrzczztDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "操作状态")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "操作名称")
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
        return "BdcZdCzrzczztDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
