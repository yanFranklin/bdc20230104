package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.0, 2019/8/23
 * @description 字典：查询内容
 */
@Table(name = "BDC_ZD_CXNR")
public class BdcZdCxnrDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "权利类型代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "权利类型名称")
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
        return "BdcZdCxnrDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
