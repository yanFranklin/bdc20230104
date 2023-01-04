package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 登记原因收件材料配置表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-12-11 14:10
 **/
@Table(name = "BDC_DJYY_SJCL_PZ")
@ApiModel(value = "BdcDjyySjclPzDO", description = "不动产登记原因收件材料配置")
public class BdcDjyySjclPzDO implements Serializable {
    private static final long serialVersionUID = -7477129612178218146L;

    @Id
    @ApiModelProperty(value = "配置ID")
    private String pzid;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "收件材料名称")
    private String clmc;
    @ApiModelProperty(value = "序号")
    private Integer xh;
    @ApiModelProperty(value = "默认份数")
    private Integer mrfs;
    @ApiModelProperty(value = "收件类型")
    private Integer sjlx;
    @ApiModelProperty(value = "收取部门")
    @Zd(tableClass = BdcSlZdSqbmDO.class)
    private String sqbm;
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

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getClmc() {
        return clmc;
    }

    public void setClmc(String clmc) {
        this.clmc = clmc;
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

    public Integer getSjlx() {
        return sjlx;
    }

    public void setSjlx(Integer sjlx) {
        this.sjlx = sjlx;
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
        final StringBuilder sb = new StringBuilder("BdcDjyySjclPzDO{");
        sb.append("pzid='").append(pzid).append('\'');
        sb.append(", djxl='").append(djxl).append('\'');
        sb.append(", djyy='").append(djyy).append('\'');
        sb.append(", clmc='").append(clmc).append('\'');
        sb.append(", xh=").append(xh);
        sb.append(", mrfs=").append(mrfs);
        sb.append(", sjlx=").append(sjlx);
        sb.append(", sqbm='").append(sqbm).append('\'');
        sb.append(", sfbc=").append(sfbc);
        sb.append('}');
        return sb.toString();
    }
}
