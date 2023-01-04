package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 一体化审批业务类型
 */
@Table(
        name = "BDC_ZD_YTHSPYWLX"
)
public class BdcZdYthspywlxDO {
    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "不动产单元状态字典表代码")
    private Integer dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "不动产单元状态名称")
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
