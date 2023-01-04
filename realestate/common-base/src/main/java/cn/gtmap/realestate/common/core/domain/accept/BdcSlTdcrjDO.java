package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/10/8
 * @description 土地出让金对象
 */
@Table(name = "BDC_SL_TDCRJ")
@ApiModel(value = "BdcSlTdcrjDO", description = "土地出让金")
public class BdcSlTdcrjDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String tdcrjid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "是否征收土地出让金")
    private Integer zstdcrj;

    @ApiModelProperty(value = "征收金额")
    private Double zsje;

    @ApiModelProperty(value = "缴费人类别")
    private String jfrlb;

    @ApiModelProperty(value = "征收项目")
    private String zsxm;

    @ApiModelProperty(value = "征收品目")
    private String zspm;

    @ApiModelProperty(value = "征收比例")
    private String zsbl;

    @ApiModelProperty(value = "是否征收土地契税")
    private Integer zstdqs;

    public String getTdcrjid() {
        return tdcrjid;
    }

    public void setTdcrjid(String tdcrjid) {
        this.tdcrjid = tdcrjid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getZstdcrj() {
        return zstdcrj;
    }

    public void setZstdcrj(Integer zstdcrj) {
        this.zstdcrj = zstdcrj;
    }

    public Double getZsje() {
        return zsje;
    }

    public void setZsje(Double zsje) {
        this.zsje = zsje;
    }

    public String getJfrlb() {
        return jfrlb;
    }

    public void setJfrlb(String jfrlb) {
        this.jfrlb = jfrlb;
    }

    public String getZsxm() {
        return zsxm;
    }

    public void setZsxm(String zsxm) {
        this.zsxm = zsxm;
    }

    public String getZspm() {
        return zspm;
    }

    public void setZspm(String zspm) {
        this.zspm = zspm;
    }

    public String getZsbl() {
        return zsbl;
    }

    public void setZsbl(String zsbl) {
        this.zsbl = zsbl;
    }

    public Integer getZstdqs() {
        return zstdqs;
    }

    public void setZstdqs(Integer zstdqs) {
        this.zstdqs = zstdqs;
    }
}
