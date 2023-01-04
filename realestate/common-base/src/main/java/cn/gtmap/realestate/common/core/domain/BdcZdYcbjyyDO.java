package cn.gtmap.realestate.common.core.domain;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/2/14
 * @description 盐城廉防异常办件原因字典表
 */
@Table(name = "BDC_ZD_YCBJYY")
public class BdcZdYcbjyyDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "异常办件原因代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "异常办件原因名称")
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
        return "BdcZdYcbjyyDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
