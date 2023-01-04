package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/05/15
 * @description  字典：领证方式
 */
@Table(name = "BDC_ZD_LZFS")
public class BdcZdLzfsDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "领证方式代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "领证方式名称")
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
        return "BdcZdLzfsDO{" +
                "dm=" + dm +
                ", mc='" + mc + '\'' +
                '}';
    }
}
