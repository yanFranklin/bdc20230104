package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 受理收费信息页面数据DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-07-17 10:31
 **/
@ApiModel(value = "BdcSlSfxxDTO", description = "受理收费信息页面数据")
public class BdcSlSfxxDTO implements Serializable {
    private static final long serialVersionUID = -1536028803099498243L;
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "定着物面积")
    private Double dzwmj;
    @ApiModelProperty(value = "土地使用权面积")
    private Double tdsyqmj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "权利人信息和收费信息")
    private BdcSlSqrSfxxDTO bdcSlQlrSfxxDTO;
    @ApiModelProperty(value = "义务人信息和收费信息")
    private BdcSlSqrSfxxDTO bdcSlYwrSfxxDTO;
    @ApiModelProperty(value = "受理人")
    private String slr;
    @ApiModelProperty(value = "收费信息项目ID")
    private String xmid;
    @ApiModelProperty(value = "提示信息")
    private String tsxx;
    @ApiModelProperty(value = "是否收费")
    private Boolean sfsf = true;
    @ApiModelProperty(value = "规划用途")
    private Integer ghyt;
    @ApiModelProperty(value = "减免事由")
    private String jmsy;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;


    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public BdcSlSqrSfxxDTO getBdcSlQlrSfxxDTO() {
        return bdcSlQlrSfxxDTO;
    }

    public void setBdcSlQlrSfxxDTO(BdcSlSqrSfxxDTO bdcSlQlrSfxxDTO) {
        this.bdcSlQlrSfxxDTO = bdcSlQlrSfxxDTO;
    }

    public BdcSlSqrSfxxDTO getBdcSlYwrSfxxDTO() {
        return bdcSlYwrSfxxDTO;
    }

    public void setBdcSlYwrSfxxDTO(BdcSlSqrSfxxDTO bdcSlYwrSfxxDTO) {
        this.bdcSlYwrSfxxDTO = bdcSlYwrSfxxDTO;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public Boolean getSfsf() {
        return sfsf;
    }

    public void setSfsf(Boolean sfsf) {
        this.sfsf = sfsf;
    }

    public Integer getGhyt() {
        return ghyt;
    }

    public void setGhyt(Integer ghyt) {
        this.ghyt = ghyt;
    }

    public String getJmsy() { return jmsy; }

    public void setJmsy(String jmsy) { this.jmsy = jmsy; }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlSfxxDTO{");
        sb.append("djxl='").append(djxl).append('\'');
        sb.append(", djlx=").append(djlx);
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", zdzhmj=").append(zdzhmj);
        sb.append(", dzwmj=").append(dzwmj);
        sb.append(", tdsyqmj=").append(tdsyqmj);
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", qllx=").append(qllx);
        sb.append(", bdcSlQlrSfxxDTO=").append(bdcSlQlrSfxxDTO);
        sb.append(", bdcSlYwrSfxxDTO=").append(bdcSlYwrSfxxDTO);
        sb.append(", slr='").append(slr).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", tsxx='").append(tsxx).append('\'');
        sb.append(", sfsf=").append(sfsf);
        sb.append(", ghyt=").append(ghyt);
        sb.append(", jmsy=").append(jmsy);
        sb.append(", qxdm=").append(qxdm);
        sb.append('}');
        return sb.toString();
    }
}
