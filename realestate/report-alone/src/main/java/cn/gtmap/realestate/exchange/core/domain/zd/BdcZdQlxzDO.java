package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhuyong</a>
 * @version 1.0, 2018/12/27
 * @description 字典：权利性质
 */
@Table(name = "BDC_ZD_QLXZ")
public class BdcZdQlxzDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "权利性质代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "权利性质名称")
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
        return "BdcZdQlxzDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
