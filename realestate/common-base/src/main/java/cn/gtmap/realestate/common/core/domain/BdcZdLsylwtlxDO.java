package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 历史遗留问题类型
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-21 09:55
 **/
@Table(name = "BDC_ZD_LSYLWTLX")
public class BdcZdLsylwtlxDO {

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
}
