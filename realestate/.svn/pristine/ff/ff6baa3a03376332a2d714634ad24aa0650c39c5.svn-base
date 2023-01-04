package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.3, 2022/05/11
 * @description 黑名单类别字典表
 */
@Table(name = "BDC_ZD_HMDLB")
public class BdcZdHmdlbDO {

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZdHmdlbDO{");
        sb.append("dm=").append(dm);
        sb.append(", mc='").append(mc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
