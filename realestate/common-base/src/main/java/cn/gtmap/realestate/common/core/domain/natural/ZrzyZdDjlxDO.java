package cn.gtmap.realestate.common.core.domain.natural;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href=""mailto:liaoxiang@gtmap.cn>liaoxiang</a>
 * @version 1.0, 2021/10/23.
 * @description 登记类型字典表
 */
@Table(name = "ZRZY_ZD_DJLX")
public class ZrzyZdDjlxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
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


}
