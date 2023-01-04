package cn.gtmap.realestate.common.core.domain.inquiry;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/3/30
 * @description 质检明细信息
 */
@Api(value = "BdcZjMXDO", description = "不动产质检明细信息")
@Table(name = "BDC_ZJ_MX")
public class BdcZjMxDO {

    @ApiModelProperty(value = "质检明细ID")
    @Id
    private String zjmxid;
    @ApiModelProperty(value = "质检ID")
    private String zjid;
    @ApiModelProperty(value = "质检内容")
    private String zjnr;
    @ApiModelProperty(value = "是否通过")
    private Integer sftg;
    @ApiModelProperty(value = "质检情况")
    private String zjqk;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    public String getZjmxid() {
        return zjmxid;
    }

    public void setZjmxid(String zjmxid) {
        this.zjmxid = zjmxid;
    }

    public String getZjid() {
        return zjid;
    }

    public void setZjid(String zjid) {
        this.zjid = zjid;
    }

    public String getZjnr() {
        return zjnr;
    }

    public void setZjnr(String zjnr) {
        this.zjnr = zjnr;
    }

    public Integer getSftg() {
        return sftg;
    }

    public void setSftg(Integer sftg) {
        this.sftg = sftg;
    }

    public String getZjqk() {
        return zjqk;
    }

    public void setZjqk(String zjqk) {
        this.zjqk = zjqk;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZjMxDO{");
        sb.append("zjmxid='").append(zjmxid).append('\'');
        sb.append(", zjid='").append(zjid).append('\'');
        sb.append(", zjnr='").append(zjnr).append('\'');
        sb.append(", sftg=").append(sftg);
        sb.append(", zjqk='").append(zjqk).append('\'');
        sb.append(", sxh=").append(sxh);
        sb.append('}');
        return sb.toString();
    }
}
