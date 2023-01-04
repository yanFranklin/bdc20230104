package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/4/19
 * @description 公告类型
 */
@Api(value = "BdcZdGglxDO", description = "公告类型")
@Table(name = "BDC_ZD_GGLX")
public class BdcZdGglxDO {

    @Id
    @ApiModelProperty(value = "代码")
    private Integer dm;

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
