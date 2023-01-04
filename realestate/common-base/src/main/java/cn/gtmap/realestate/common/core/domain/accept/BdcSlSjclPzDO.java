package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/28
 * @description 不动产受理收件材料配置
 */
@Table(name = "BDC_SL_SJCL_PZ")
@ApiModel(value = "BdcSlSjclPzDO", description = "不动产受理收件材料配置")
public class BdcSlSjclPzDO implements Serializable {
    private static final long serialVersionUID = -2707037574441863969L;
    @Id
    @ApiModelProperty(value = "收件材料配置ID")
    private String pzid;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "材料名称")
    private String clmc;

    @ApiModelProperty(value = "收件类型")
    private Integer sjlx;

    @ApiModelProperty(value = "收取部门")
    private String sqbm;

    @ApiModelProperty(value = "序号")
    private Integer xh;

    @ApiModelProperty(value = "默认份数")
    private Integer mrfs;

    @ApiModelProperty(value = "是否必传（1：必传，0：不必传）")
    private Integer sfbc;

    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
    }

    public Integer getSjlx() {
        return sjlx;
    }

    public void setSjlx(Integer sjlx) {
        this.sjlx = sjlx;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public Integer getMrfs() {
        return mrfs;
    }

    public void setMrfs(Integer mrfs) {
        this.mrfs = mrfs;
    }

    public String getSqbm() {
        return sqbm;
    }

    public void setSqbm(String sqbm) {
        this.sqbm = sqbm;
    }

    public Integer getSfbc() {
        return sfbc;
    }

    public void setSfbc(Integer sfbc) {
        this.sfbc = sfbc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlSjclPzDO{");
        sb.append("pzid='").append(pzid).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", clmc='").append(clmc).append('\'');
        sb.append(", sjlx=").append(sjlx);
        sb.append(", sqbm='").append(sqbm).append('\'');
        sb.append(", xh=").append(xh);
        sb.append(", mrfs=").append(mrfs);
        sb.append(", sfbc=").append(sfbc);
        sb.append('}');
        return sb.toString();
    }
}
