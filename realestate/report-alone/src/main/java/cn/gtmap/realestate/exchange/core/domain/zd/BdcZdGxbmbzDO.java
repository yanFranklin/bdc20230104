package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-03-17
 * @description 共享部门字典表
 */
@Table(name = "BDC_ZD_GXBMBZ")
public class BdcZdGxbmbzDO {

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
        return "BdcZdLzDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
