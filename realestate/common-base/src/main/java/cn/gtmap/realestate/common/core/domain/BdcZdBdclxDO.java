package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Table;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/14
 * @description 不动产类型字典
 */
@Table(name = "bdc_zd_bdclx")
public class BdcZdBdclxDO {
    /**
     * 代码
     */
    @ApiModelProperty(value = "bdclx代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "bdclx名称")
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
        return "BdcZdBdclxDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }


}
