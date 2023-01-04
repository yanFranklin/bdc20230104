package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.sdk.mybatis.plugin.annotation.Crypt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/3/26
 * @description 操作日志表
 */
@Table(name = "BDC_CZRZ")
@ApiModel(value = "BdcCzrzDO", description = "操作日志表")
public class BdcCzrzDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String rzid;

    @Crypt
    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    @ApiModelProperty(value = "受理人")
    private String slr;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "操作人")
    private String czr;

    @ApiModelProperty(value = "操作时间")
    private Date czsj;

    @ApiModelProperty(value = "操作类型")
    private Integer czlx;

    @ApiModelProperty(value = "操作原因")
    private String czyy;

    @ApiModelProperty(value = "操作状态：0（失败）1（成功）")
    private Integer czzt;

    @ApiModelProperty(value = "操作IP")
    private String ip;

    @ApiModelProperty(value = "操作参数")
    private String czcs;

    @ApiModelProperty(value = "操作结果")
    private String czjg;

    @ApiModelProperty(value = "操作名称")
    private String czmc;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public Integer getCzlx() {
        return czlx;
    }

    public void setCzlx(Integer czlx) {
        this.czlx = czlx;
    }

    public String getCzyy() {
        return czyy;
    }

    public void setCzyy(String czyy) {
        this.czyy = czyy;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getCzzt() {
        return czzt;
    }

    public void setCzzt(Integer czzt) {
        this.czzt = czzt;
    }


    public String getCzcs() {
        return czcs;
    }

    public void setCzcs(String czcs) {
        this.czcs = czcs;
    }

    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCzmc() {
        return czmc;
    }

    public void setCzmc(String czmc) {
        this.czmc = czmc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcCzrzDO{");
        sb.append("rzid='").append(rzid).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", spxtywh='").append(spxtywh).append('\'');
        sb.append(", lcmc='").append(lcmc).append('\'');
        sb.append(", slsj=").append(slsj);
        sb.append(", slr='").append(slr).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", czr='").append(czr).append('\'');
        sb.append(", czsj=").append(czsj);
        sb.append(", czlx=").append(czlx);
        sb.append(", czyy='").append(czyy).append('\'');
        sb.append(", czzt=").append(czzt);
        sb.append(", ip='").append(ip).append('\'');
        sb.append(", czcs='").append(czcs).append('\'');
        sb.append(", czjg='").append(czjg).append('\'');
        sb.append(", czmc='").append(czmc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
