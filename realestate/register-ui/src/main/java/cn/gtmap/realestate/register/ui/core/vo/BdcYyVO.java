package cn.gtmap.realestate.register.ui.core.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/2
 * @description 不动产登记簿异议页面
 */
public class BdcYyVO{
    /**
     * 权利人名称
     */
    private String sqr;
    /**
     * 证件种类
     */
    private String sqrzjzl;
    /**
     * 证件号
     */
    private String sqrzjh;
    /**
     * 权利id
     */
    private String qlid;
    /**
     * 异议事项
     */
    private String yysx;
    /**
     * 注销异议业务号
     */
    private String zxyyywh;
    /**
     * 注销异议原因
     */
    private String zxyyyy;
    /**
     * 注销异议登记时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date zxyydjsj;
    /**
     * 注销异议登簿人
     */
    private String zxyydbr;
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 项目ID
     */
    private String xmid;
    /**
     * 共有情况
     */
    private String gyqk;
    /**
     * 登记时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djsj;
    /**
     * 登簿人
     */
    private String dbr;
    /**
     * 登记机构
     */
    private String djjg;
    /**
     * 附记
     */
    private String fj;
    /**
     * 权属状态
     */
    private Integer qszt;
    /**
     * 备注
     */
    private String bz;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 不动产权证明号
     */
    private String bdcqzmh;
    /**
     * 不动产单元编号
     */
    private String bdcdywybh;

    /**
     * 坐落
     */
    private String zl;

    public String getSqr() {
        return sqr;
    }

    public void setSqr(String sqr) {
        this.sqr = sqr;
    }

    public String getSqrzjzl() {
        return sqrzjzl;
    }

    public void setSqrzjzl(String sqrzjzl) {
        this.sqrzjzl = sqrzjzl;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }
    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public String getZxyyywh() {
        return zxyyywh;
    }

    public void setZxyyywh(String zxyyywh) {
        this.zxyyywh = zxyyywh;
    }

    public String getZxyyyy() {
        return zxyyyy;
    }

    public void setZxyyyy(String zxyyyy) {
        this.zxyyyy = zxyyyy;
    }

    public Date getZxyydjsj() {
        return zxyydjsj;
    }

    public void setZxyydjsj(Date zxyydjsj) {
        this.zxyydjsj = zxyydjsj;
    }

    public String getZxyydbr() {
        return zxyydbr;
    }

    public void setZxyydbr(String zxyydbr) {
        this.zxyydbr = zxyydbr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
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

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
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
    @Override
    public String toString() {
        return "BdcYyVO{" +
                "sqr='" + sqr + '\'' +
                ", sqrzjzl='" + sqrzjzl + '\'' +
                ", sqrzjh='" + sqrzjh + '\'' +
                ", qlid='" + qlid + '\'' +
                ", yysx='" + yysx + '\'' +
                ", zxyyywh='" + zxyyywh + '\'' +
                ", zxyyyy='" + zxyyyy + '\'' +
                ", zxyydjsj=" + zxyydjsj +
                ", zxyydbr='" + zxyydbr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", djjg='" + djjg + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcqzmh='" + bdcqzmh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }
}
