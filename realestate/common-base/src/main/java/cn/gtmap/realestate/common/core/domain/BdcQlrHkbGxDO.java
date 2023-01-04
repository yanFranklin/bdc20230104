package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/8/3
 * @description 权利人与户口簿关系表
 */
@Table(
        name = "BDC_QLR_HKB_GX"
)
@ApiModel(value = "BdcQlrHkbGxDO",description = "权利人与户口簿关系表")
public class BdcQlrHkbGxDO {

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;

    @ApiModelProperty(value = "户口簿编码")
    private String hkbbm;

    @ApiModelProperty(value = "户口簿版本号")
    private Integer hkbbbh;

    @ApiModelProperty(value = "权利人ID")
    private String qlrid;

    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getHkbbm() {
        return hkbbm;
    }

    public void setHkbbm(String hkbbm) {
        this.hkbbm = hkbbm;
    }

    public Integer getHkbbbh() {
        return hkbbbh;
    }

    public void setHkbbbh(Integer hkbbbh) {
        this.hkbbbh = hkbbbh;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcQlrHkbGxDO{");
        sb.append("gxid='").append(gxid).append('\'');
        sb.append(", hkbbm='").append(hkbbm).append('\'');
        sb.append(", hkbbbh=").append(hkbbbh);
        sb.append(", qlrid='").append(qlrid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
