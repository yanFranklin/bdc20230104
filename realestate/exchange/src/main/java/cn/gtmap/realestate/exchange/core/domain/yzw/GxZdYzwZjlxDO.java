package cn.gtmap.realestate.exchange.core.domain.yzw;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description 共享证件种类字典表
 */
@Table(name = "INF_ZD_ZJLX")
public class GxZdYzwZjlxDO {

    @Id
    @ApiModelProperty(value = "代码")
    private String dm;

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
