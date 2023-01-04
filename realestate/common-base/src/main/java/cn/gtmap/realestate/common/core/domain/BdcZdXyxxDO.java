package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.3, 2022/05/09
 * @description 响应信息字典表
 */
@Table(name = "BDC_ZD_XYXX")
public class BdcZdXyxxDO {

    /**
     * 代码
     */
    @Id
    @ApiModelProperty(value = "响应信息代码")
    private String dm;
    /**
     * 名称
     */
    @ApiModelProperty(value = "响应信息名称")
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
        final StringBuilder sb = new StringBuilder("BdcZdXyxxDO{");
        sb.append("dm='").append(dm).append('\'');
        sb.append(", mc='").append(mc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
