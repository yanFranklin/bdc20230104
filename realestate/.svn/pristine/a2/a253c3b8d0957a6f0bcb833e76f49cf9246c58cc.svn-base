package cn.gtmap.realestate.exchange.core.domain.zd;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/3
 * @description 与户主关系字典表
 */
@Table(name = "BDC_ZD_YHZGX")
public class BdcZdYhzgxDO {

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
        final StringBuilder sb = new StringBuilder("BdcZdYhzgxDO{");
        sb.append("dm='").append(dm).append('\'');
        sb.append(", mc='").append(mc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
