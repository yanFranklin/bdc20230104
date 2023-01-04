package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.3, 2019-07-17
 * @description 权利数据来源字典表
 */
@Table(name = "BDC_ZD_QLSJLY")
public class BdcZdQlsjly {
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
        return "BdcZdQlsjly{" +
                "dm='" + dm + '\'' +
                ", mc='" + mc + '\'' +
                '}';
    }
}
