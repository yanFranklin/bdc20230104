package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/27
 * @description 字典：权利设定方式
 */
@Table(name = "BDC_ZD_QLSDFS")
public class BdcZdQlsdfsDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "权利设定方式代码")
    private Integer dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "权利设定方式名称")
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
        return "BdcZdQlsdfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }

}
