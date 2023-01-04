package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:ZXY@gtmap.cn">ZXY</a>
 * @version 1.0, 2020/8/3
 * @description 与申请人关系字典表
 */
@Table(name = "BDC_ZD_YSQRGX")
public class BdcZdYsqrgxDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private String dm;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
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
        final StringBuilder sb = new StringBuilder("BdcZdYsqrgxDO{");
        sb.append("dm='").append(dm).append('\'');
        sb.append(", mc='").append(mc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
