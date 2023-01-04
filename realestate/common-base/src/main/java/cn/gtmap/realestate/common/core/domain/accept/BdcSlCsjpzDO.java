package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @program: realestate
 * @description: 长三角配置数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-05-10 10:04
 **/
@Table(name = "BDC_SL_CSJPZ")
public class BdcSlCsjpzDO {

    @Id
    @ApiModelProperty(value = "配置id主键")
    private String pzid;

    @ApiModelProperty(value = "工作流定义id")
    private String gzldyid;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;


    @ApiModelProperty(value = "证照类型")
    private String zzlx;

    @ApiModelProperty(value = "业务编码")
    private String ywbm;

    @ApiModelProperty(value = "本省证照名称")
    private String bszzmc;

    @ApiModelProperty(value = "标准证照名称")
    private String bzzzmc;


    public String getPzid() {
        return pzid;
    }

    public void setPzid(String pzid) {
        this.pzid = pzid;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getZzlx() {
        return zzlx;
    }

    public void setZzlx(String zzlx) {
        this.zzlx = zzlx;
    }

    public String getYwbm() {
        return ywbm;
    }

    public void setYwbm(String ywbm) {
        this.ywbm = ywbm;
    }

    public String getBszzmc() {
        return bszzmc;
    }

    public void setBszzmc(String bszzmc) {
        this.bszzmc = bszzmc;
    }

    public String getBzzzmc() {
        return bzzzmc;
    }

    public void setBzzzmc(String bzzzmc) {
        this.bzzzmc = bzzzmc;
    }

    @Override
    public String toString() {
        return "BdcSlCsjpzDO{" +
                "pzid='" + pzid + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", zzlx='" + zzlx + '\'' +
                ", ywbm='" + ywbm + '\'' +
                ", bszzmc='" + bszzmc + '\'' +
                ", bzzzmc='" + bzzzmc + '\'' +
                '}';
    }
}
