package cn.gtmap.realestate.register.ui.core.vo;

import cn.gtmap.realestate.common.core.domain.BdcYgDO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2019/1/3
 * @description
 */
public class BdcdjbYgVO extends BdcYgDO {
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
     * 义务人
     */
    private String ywr;
    /**
     * 义务人证件种类
     */
    private String ywrzjzl;
    /**
     * 义务人证件号
     */
    private String ywrzjh;
    /**
     * 不动产权证明号
     */
    private String bdcqzmh;
    /**
     * 预告登记种类名称
     */
    private String ygdjzlmc;
    /**
     * 登记类型名称
     */
    private String djlxmc;

    /**
     * 登记日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djrq;
    private String ghytmc;
    private String fwxzmc;

    /**
     * 是否存在禁止或限制转让抵押不动产的约定
     */
    private String jzzrmc;

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

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getBdcqzmh() {
        return bdcqzmh;
    }

    public void setBdcqzmh(String bdcqzmh) {
        this.bdcqzmh = bdcqzmh;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }
    public String getYgdjzlmc() {
        return ygdjzlmc;
    }

    public void setYgdjzlmc(String ygdjzlmc) {
        this.ygdjzlmc = ygdjzlmc;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    @Override
    public String getGhytmc() {
        return ghytmc;
    }

    @Override
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

    public String getJzzrmc() {
        return jzzrmc;
    }

    public void setJzzrmc(String jzzrmc) {
        this.jzzrmc = jzzrmc;
    }

    @Override
    public String toString() {
        return "BdcdjbYgVO{" +
                "qlr='" + qlr + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjzl='" + ywrzjzl + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", bdcqzmh='" + bdcqzmh + '\'' +
                ", ygdjzlmc='" + ygdjzlmc + '\'' +
                ", djlxmc='" + djlxmc + '\'' +
                ", djrq=" + djrq +
                ", ghytmc='" + ghytmc + '\'' +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", jzzrmc='" + jzzrmc + '\'' +
                '}';
    }

}
