package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2020/11/11 16:17
 * @description 字典：审批来源
 */
@Table(name = "BDC_ZD_SPLY")
public class BdcZdSplyDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "编码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "来源")
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
        return "BdcZdSplyDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
