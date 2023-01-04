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
 * @description 土地承包经营权、农用地使用权
 */
@Table(
        name = "BDC_TDCBNYDSYQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcTdcbnydsyqDO.class)
@ApiModel(value = "BdcTdcbnydsyqDO", description = "土地承包经营权、农用地使用权")
public class BdcTdcbnydsyqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @Zd(tableClass = BdcZdSyttlxDO.class)
    @ApiModelProperty(value = "水域滩涂类型")
    private Integer syttlx;
    @Zd(tableClass = BdcZdYzyfsDO.class)
    @ApiModelProperty(value = "养殖业方式")
    private Integer yzyfs;
    @ApiModelProperty(value = "草原质量")
    private String cyzl;
    @ApiModelProperty(value = "适宜载畜量")
    private Integer syzcl;
    @ApiModelProperty(value = "承包（使用权）面积")
    private Double cbmj;
    @Zd(tableClass = BdcZdTdsyqxzDO.class)
    @ApiModelProperty(value = "土地所有权性质")
    private Integer tdsyqxz;
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
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "承包（使用）起始时间", example = "2018-10-01 12:18:48")
    private Date cbqssj;
    @ApiModelProperty(value = "承包（使用）结束时间", example = "2018-10-01 12:18:48")
    private Date cbjssj;
    @ApiModelProperty(value = "发包方名称")
    private String fbfmc;
    @ApiModelProperty(value = "发包方代码")
    private String fbfdm;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "草层高度")
    private Double cdgd;
    @ApiModelProperty(value = "草地优势种")
    private String cdysz;
    @ApiModelProperty(value = "草地建群")
    private String cdjq;
    @ApiModelProperty(value = "草地覆盖度")
    private String cdfgd;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;
    @ApiModelProperty(value = "地块编码")
    private String dkbm;
    @ApiModelProperty(value = "确权情况")
    private String qqqk;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "是否基本农田")
    private Integer sfjbnt;
    @Zd(tableClass = BdcZdCbtdyt.class)
    @ApiModelProperty(value = "承包土地用途")
    private Integer cbtdyt;
    @Zd(tableClass = BdcZdCbdldj.class)
    @ApiModelProperty(value = "承包地类等级")
    private String cbdldj;
    @Zd(tableClass = BdcZdCbfs.class)
    @ApiModelProperty(value = "承包方式")
    private String cbfs;
    @ApiModelProperty(value = "实测面积")
    private Double scmj;
    @ApiModelProperty(value = "用地用海分类")
    private String ydyhfl;

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

    public Integer getSyttlx() {
        return syttlx;
    }

    public void setSyttlx(Integer syttlx) {
        this.syttlx = syttlx;
    }

    public Integer getYzyfs() {
        return yzyfs;
    }

    public void setYzyfs(Integer yzyfs) {
        this.yzyfs = yzyfs;
    }

    public String getCyzl() {
        return cyzl;
    }

    public Double getScmj() {
        return scmj;
    }

    public void setScmj(Double scmj) {
        this.scmj = scmj;
    }

    @Override
    public String toString() {
        return "BdcTdcbnydsyqDO{" +
                "qlid='" + qlid + '\'' +
                ", syttlx=" + syttlx +
                ", yzyfs=" + yzyfs +
                ", cyzl='" + cyzl + '\'' +
                ", syzcl=" + syzcl +
                ", cbmj=" + cbmj +
                ", tdsyqxz=" + tdsyqxz +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", cbqssj=" + cbqssj +
                ", cbjssj=" + cbjssj +
                ", fbfmc='" + fbfmc + '\'' +
                ", fbfdm='" + fbfdm + '\'' +
                ", bz='" + bz + '\'' +
                ", cdgd=" + cdgd +
                ", cdysz='" + cdysz + '\'' +
                ", cdjq='" + cdjq + '\'' +
                ", cdfgd='" + cdfgd + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", djjg='" + djjg + '\'' +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr='" + zxdbr + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", dkbm='" + dkbm + '\'' +
                ", qqqk='" + qqqk + '\'' +
                ", sfjbnt=" + sfjbnt +
                ", cbtdyt=" + cbtdyt +
                ", cbdldj='" + cbdldj + '\'' +
                ", cbfs='" + cbfs + '\'' +
                ", scmj=" + scmj +
                ", ydyhfl='" + ydyhfl + '\'' +
                '}';
    }

    public void setCyzl(String cyzl) {
        this.cyzl = cyzl;
    }

    public Integer getSyzcl() {
        return syzcl;
    }

    public void setSyzcl(Integer syzcl) {
        this.syzcl = syzcl;
    }

    public Double getCbmj() {
        return cbmj;
    }

    public void setCbmj(Double cbmj) {
        this.cbmj = cbmj;
    }

    public Integer getTdsyqxz() {
        return tdsyqxz;
    }

    public void setTdsyqxz(Integer tdsyqxz) {
        this.tdsyqxz = tdsyqxz;
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

    @Override
    public String getDjjg() {
        return djjg;
    }

    @Override
    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }

    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Double getCdgd() {
        return cdgd;
    }

    public void setCdgd(Double cdgd) {
        this.cdgd = cdgd;
    }

    public String getCdysz() {
        return cdysz;
    }

    public void setCdysz(String cdysz) {
        this.cdysz = cdysz;
    }

    public String getCdjq() {
        return cdjq;
    }

    public void setCdjq(String cdjq) {
        this.cdjq = cdjq;
    }

    public String getCdfgd() {
        return cdfgd;
    }

    public void setCdfgd(String cdfgd) {
        this.cdfgd = cdfgd;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
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

    public String getDkbm() {
        return dkbm;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public String getQqqk() {
        return qqqk;
    }

    public void setQqqk(String qqqk) {
        this.qqqk = qqqk;
    }

    public Integer getSfjbnt() {
        return sfjbnt;
    }

    public void setSfjbnt(Integer sfjbnt) {
        this.sfjbnt = sfjbnt;
    }

    public Integer getCbtdyt() {
        return cbtdyt;
    }

    public void setCbtdyt(Integer cbtdyt) {
        this.cbtdyt = cbtdyt;
    }

    public String getCbdldj() {
        return cbdldj;
    }

    public void setCbdldj(String cbdldj) {
        this.cbdldj = cbdldj;
    }

    public String getCbfs() {
        return cbfs;
    }

    public void setCbfs(String cbfs) {
        this.cbfs = cbfs;
    }

    public String getYdyhfl() {
        return ydyhfl;
    }

    public void setYdyhfl(String ydyhfl) {
        this.ydyhfl = ydyhfl;
    }
}
