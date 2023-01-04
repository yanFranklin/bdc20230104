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
 * @description 其他相关权利--取水权
 */
@Table(
        name = "BDC_QTXGQL"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcQtxgqlDO.class)
@ApiModel(value = "BdcQtxgqlDO", description = "其他相关权利")
public class BdcQtxgqlDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "水源类型")
    private String sylx;
    @ApiModelProperty(value = "取水方式")
    private String qsfs;
    @ApiModelProperty(value = "取水量")
    private Double qsl;
    @ApiModelProperty(value = "取水用途")
    private String qsyt;
    @ApiModelProperty(value = "勘查面积")
    private Double kcmj;
    @ApiModelProperty(value = "开采矿种")
    private String kckz;
    @ApiModelProperty(value = "开采方式")
    private String kcfs;
    @ApiModelProperty(value = "生产规模")
    private String scgm;
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
    @ApiModelProperty(value = "权利结束时间", example = "2018-10-01 12:18:48")
    private Date qljssj;
    @ApiModelProperty(value = "权利起始时间", example = "2018-10-01 12:18:48")
    private Date qlqssj;
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
        return "BdcQtxgqlDO{" +
                "qlid='" + qlid + '\'' +
                ", sylx='" + sylx + '\'' +
                ", qsfs='" + qsfs + '\'' +
                ", qsl=" + qsl +
                ", qsyt='" + qsyt + '\'' +
                ", kcmj=" + kcmj +
                ", kckz='" + kckz + '\'' +
                ", kcfs='" + kcfs + '\'' +
                ", scgm='" + scgm + '\'' +
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
                ", qljssj=" + qljssj +
                ", qlqssj=" + qlqssj +
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

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getSylx() {
        return sylx;
    }

    public void setSylx(String sylx) {
        this.sylx = sylx;
    }

    public String getQsfs() {
        return qsfs;
    }

    public void setQsfs(String qsfs) {
        this.qsfs = qsfs;
    }

    public Double getQsl() {
        return qsl;
    }

    public void setQsl(Double qsl) {
        this.qsl = qsl;
    }

    public String getQsyt() {
        return qsyt;
    }

    public void setQsyt(String qsyt) {
        this.qsyt = qsyt;
    }

    public Double getKcmj() {
        return kcmj;
    }

    public void setKcmj(Double kcmj) {
        this.kcmj = kcmj;
    }

    public String getKckz() {
        return kckz;
    }

    public void setKckz(String kckz) {
        this.kckz = kckz;
    }

    public String getKcfs() {
        return kcfs;
    }

    public void setKcfs(String kcfs) {
        this.kcfs = kcfs;
    }

    public String getScgm() {
        return scgm;
    }

    public void setScgm(String scgm) {
        this.scgm = scgm;
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

    public Date getQljssj() {
        return qljssj;
    }

    public void setQljssj(Date qljssj) {
        this.qljssj = qljssj;
    }

    public Date getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(Date qlqssj) {
        this.qlqssj = qlqssj;
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


    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

}
