package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @version 1.0  2021/07/12
 * @description 不动产综合查询台账查询和打印操作日志表
 */
@Table(name = "BDC_ZHCX_LOG")
public class BdcZhcxLogDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String rzid;

    @ApiModelProperty(value = "日志类型")
    private String rzlx;

    @ApiModelProperty(value = "查询账号")
    private String cxzh;

    @ApiModelProperty(value = "查询人")
    private String cxr;

    @ApiModelProperty(value = "查询人id")
    private String cxrid;

    @ApiModelProperty(value = "所在部门")
    private String szbm;

    @ApiModelProperty(value = "所在部门编码")
    private String szbmdm;

    @ApiModelProperty(value = "查询条件")
    private String cxtj;

    @ApiModelProperty(value = "查询操作或打印操作结果")
    private String czjg;

    @ApiModelProperty(value = "操作时间", example = "2018-10-01 12:18:48")
    private Date czsj;

    @ApiModelProperty(value = "打印类型")
    private String dylx;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getRzlx() {
        return rzlx;
    }

    public void setRzlx(String rzlx) {
        this.rzlx = rzlx;
    }

    public String getCxzh() {
        return cxzh;
    }

    public void setCxzh(String cxzh) {
        this.cxzh = cxzh;
    }

    public String getCxr() {
        return cxr;
    }

    public void setCxr(String cxr) {
        this.cxr = cxr;
    }

    public String getSzbm() {
        return szbm;
    }

    public void setSzbm(String szbm) {
        this.szbm = szbm;
    }

    public String getCxtj() {
        return cxtj;
    }

    public void setCxtj(String cxtj) {
        this.cxtj = cxtj;
    }

    public String getCzjg() {
        return czjg;
    }

    public void setCzjg(String czjg) {
        this.czjg = czjg;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getCxrid() {
        return cxrid;
    }

    public void setCxrid(String cxrid) {
        this.cxrid = cxrid;
    }

    public String getSzbmdm() {
        return szbmdm;
    }

    public void setSzbmdm(String szbmdm) {
        this.szbmdm = szbmdm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcZhcxLogDO{");
        sb.append("rzid='").append(rzid).append('\'');
        sb.append(", rzlx='").append(rzlx).append('\'');
        sb.append(", cxzh='").append(cxzh).append('\'');
        sb.append(", cxr='").append(cxr).append('\'');
        sb.append(", cxrid='").append(cxrid).append('\'');
        sb.append(", szbm='").append(szbm).append('\'');
        sb.append(", szbmdm='").append(szbmdm).append('\'');
        sb.append(", cxtj='").append(cxtj).append('\'');
        sb.append(", czjg='").append(czjg).append('\'');
        sb.append(", czsj=").append(czsj);
        sb.append(", dylx='").append(dylx).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
