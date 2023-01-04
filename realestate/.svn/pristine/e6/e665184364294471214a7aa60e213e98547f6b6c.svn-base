package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:songjiawei@gtmap.cn">songjiawei</a>
 * @description 土地所有权
 */
@Table(
        name = "BDC_TDSYQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcTdsyqDO.class)
@ApiModel(value = "BdcTdsyqDO", description = "土地所有权")
public class BdcTdsyqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @Zd(tableClass = BdcZdMjdwDO.class)
    @ApiModelProperty(value = "面积单位")
    private Integer mjdw;
    @ApiModelProperty(value = "农用地面积")
    private Double nydmj;
    @ApiModelProperty(value = "耕地面积")
    private Double gdmj;
    @ApiModelProperty(value = "基本农田面积")
    private Double ntmj;
    @ApiModelProperty(value = "林地面积")
    private Double ldmj;
    @ApiModelProperty(value = "草地面积")
    private Double cdmj;
    @ApiModelProperty(value = "其他农用地面积")
    private Double qtnydmj;
    @ApiModelProperty(value = "建设用地面积")
    private Double jsydmj;
    @ApiModelProperty(value = "未利用地面积")
    private Double wlydmj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;

    @Override
    public String toString() {
        return "BdcTdsyqDO{" +
                "qlid='" + qlid + '\'' +
                ", mjdw=" + mjdw +
                ", nydmj=" + nydmj +
                ", gdmj=" + gdmj +
                ", ntmj=" + ntmj +
                ", ldmj=" + ldmj +
                ", cdmj=" + cdmj +
                ", qtnydmj=" + qtnydmj +
                ", jsydmj=" + jsydmj +
                ", wlydmj=" + wlydmj +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr=" + zxdbr +
                ", zxdjsj='" + zxdjsj + '\'' +
                '}';
    }

    public String getZxywh() {
        return zxywh;
    }

    public void setZxywh(String zxywh) {
        this.zxywh = zxywh;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Integer getMjdw() {
        return mjdw;
    }

    public String getZxdbr() {
        return zxdbr;
    }

    public void setZxdbr(String zxdbr) {
        this.zxdbr = zxdbr;
    }

    public Date getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(Date zxdjsj) {
        this.zxdjsj = zxdjsj;
    }

    public void setMjdw(Integer mjdw) {
        this.mjdw = mjdw;
    }

    public Double getNydmj() {
        return nydmj;
    }

    public void setNydmj(Double nydmj) {
        this.nydmj = nydmj;
    }

    public Double getGdmj() {
        return gdmj;
    }

    public void setGdmj(Double gdmj) {
        this.gdmj = gdmj;
    }

    public Double getNtmj() {
        return ntmj;
    }

    public void setNtmj(Double ntmj) {
        this.ntmj = ntmj;
    }

    public Double getLdmj() {
        return ldmj;
    }

    public void setLdmj(Double ldmj) {
        this.ldmj = ldmj;
    }

    public Double getCdmj() {
        return cdmj;
    }

    public void setCdmj(Double cdmj) {
        this.cdmj = cdmj;
    }

    public Double getQtnydmj() {
        return qtnydmj;
    }

    public void setQtnydmj(Double qtnydmj) {
        this.qtnydmj = qtnydmj;
    }

    public Double getJsydmj() {
        return jsydmj;
    }

    public void setJsydmj(Double jsydmj) {
        this.jsydmj = jsydmj;
    }

    public Double getWlydmj() {
        return wlydmj;
    }

    public void setWlydmj(Double wlydmj) {
        this.wlydmj = wlydmj;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

}
