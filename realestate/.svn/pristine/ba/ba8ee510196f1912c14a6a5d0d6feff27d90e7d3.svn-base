package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/3
 * @description
 */
public class BdcdjbFdcqVO extends BdcFdcqDO{
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 权利人证件种类
     */
    private String qlrzjzl;
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 权利人类型
     */
    private String qlrlx;
    /**
     * 不动产权证
     */
    private String bdcqzh;


    @Zd(table = "bdc_zd_fwyt", tableClass = BdcZdFwytDO.class)
    private String ghytmc;

    @Zd(table = "bdc_zd_fwxz", tableClass = BdcZdFwxzDO.class)
    private String fwxzmc;

    @Zd(table = "bdc_zd_fwjg", tableClass = BdcZdFwjgDO.class)
    private String fwjgmc;

    @Zd(table = "bdc_zd_djlx", tableClass = BdcZdDjlxDO.class)
    private String djlxmc;

    /**
     * 土地使用起始日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date tdsyqsrq;

    /**
     * 土地使用结束日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date tdsyjsrq;
    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djrq;

    /**
     * 注销登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    Date zxdjrq;

    @Override
    public String toString() {
        return "BdcdjbFdcqVO{" +
                "qlr='" + qlr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", ghytmc='" + ghytmc + '\'' +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", fwjgmc='" + fwjgmc + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", tdsyqsrq=" + tdsyqsrq +
                ", tdsyjsrq=" + tdsyjsrq +
                ", djrq=" + djrq +
                ", zxdjrq=" + zxdjrq +
                ", bdcFdcqXmList=" + bdcFdcqXmList +
                '}';
    }

    public Date getZxdjrq() {
        return zxdjrq;
    }

    public void setZxdjrq(Date zxdjrq) {
        this.zxdjrq = zxdjrq;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public Date getTdsyqsrq() {
        return tdsyqsrq;
    }

    public void setTdsyqsrq(Date tdsyqsrq) {
        this.tdsyqsrq = tdsyqsrq;
    }

    public Date getTdsyjsrq() {
        return tdsyjsrq;
    }

    public void setTdsyjsrq(Date tdsyjsrq) {
        this.tdsyjsrq = tdsyjsrq;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    public List getBdcFdcqXmList() {
        return bdcFdcqXmList;
    }

    public void setBdcFdcqXmList(List bdcFdcqXmList) {
        this.bdcFdcqXmList = bdcFdcqXmList;
    }

    /**
     * 项目内多幢信息集合
     */
    private List bdcFdcqXmList;

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    @Override
    public String getFwxzmc() {
        return fwxzmc;
    }

    @Override
    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

    @Override
    public String getFwjgmc() {
        return fwjgmc;
    }

    @Override
    public void setFwjgmc(String fwjgmc) {
        this.fwjgmc = fwjgmc;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

}
