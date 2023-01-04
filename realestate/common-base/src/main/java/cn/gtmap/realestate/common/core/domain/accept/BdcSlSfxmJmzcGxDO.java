package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 收费项目减免政策关系表
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-09 17:09
 **/
@Table(name = "BDC_SL_SFXM_JMZC_GX")
@ApiModel(value = "BdcDjyySjclPzDO", description = "不动产登记原因收件材料配置")
public class BdcSlSfxmJmzcGxDO implements Serializable {

    private static final long serialVersionUID = -6924079619011964491L;

    @Id
    @ApiModelProperty(value = "关系ID")
    private String gxid;
    @ApiModelProperty(value = "减免政策")
    private String jmzc;
    @ApiModelProperty(value = "减免政策标志（一般为sfxx表减免字段英文缩写）")
    private String jmzcbz;
    @ApiModelProperty(value = "减免状态(选择是还是选择否生效减免政策)")
    private Integer jmzt;
    @ApiModelProperty(value = "收费项目代码")
    private String sfxmdm;
    @ApiModelProperty(value = "收费项目单价")
    private Double sfxmdj;
    @ApiModelProperty(value = "收费项目数量")
    private Integer sfxmsl;


    public String getGxid() {
        return gxid;
    }

    public void setGxid(String gxid) {
        this.gxid = gxid;
    }

    public String getJmzc() {
        return jmzc;
    }

    public void setJmzc(String jmzc) {
        this.jmzc = jmzc;
    }

    public String getJmzcbz() {
        return jmzcbz;
    }

    public void setJmzcbz(String jmzcbz) {
        this.jmzcbz = jmzcbz;
    }

    public Integer getJmzt() {
        return jmzt;
    }

    public void setJmzt(Integer jmzt) {
        this.jmzt = jmzt;
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public Double getSfxmdj() {
        return sfxmdj;
    }

    public void setSfxmdj(Double sfxmdj) {
        this.sfxmdj = sfxmdj;
    }

    public Integer getSfxmsl() {
        return sfxmsl;
    }

    public void setSfxmsl(Integer sfxmsl) {
        this.sfxmsl = sfxmsl;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmJmzcGxDO{" +
                "gxid='" + gxid + '\'' +
                ", jmzc='" + jmzc + '\'' +
                ", jmzcbz='" + jmzcbz + '\'' +
                ", jmzt=" + jmzt +
                ", sfxmdm='" + sfxmdm + '\'' +
                ", sfxmdj=" + sfxmdj +
                ", sfxmsl=" + sfxmsl +
                '}';
    }
}
