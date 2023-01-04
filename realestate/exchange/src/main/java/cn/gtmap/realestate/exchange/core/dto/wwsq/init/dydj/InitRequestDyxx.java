package cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-12
 * @description 抵押首次 抵押信息
 */
public class InitRequestDyxx {

    private String dyfs;

    private String bdbzzqse;

    private String zwlxksqx;

    private String zwlxjsqx;

    private String fj;

    private String qlsx;

    private String dyfw;

    private String bdcdjzmh;

    private String dkfs;

    private String dbfw;

    /**
     * 37592 【常州】登记3.0修改抵押首次创建流程接口.doc #3
     * 增加以下字段
     */
    //币种代码
    private Integer bzdm;
    //币种名称
    private String bzmc;
    //抵押套数
    private Integer dyts;
    //抵押金额
    private Double dywjz;

    //是否禁止抵押期间抵押物转让 1：禁止，0：同意
    private String sfjzdyqjdywzr;

    private String zgzqe;

    //M	抵押传18，预告抵押传19，放代码
    private String qllx;
    //M	抵押传18，预告抵押传19，放名称
    private String qllxmc;
    //M	抵押传18，预告抵押传19，放名称
    private String dyjelxdm;
    private String dyjelxmc;

    public String getDyjelxdm() {
        return dyjelxdm;
    }

    public void setDyjelxdm(String dyjelxdm) {
        this.dyjelxdm = dyjelxdm;
    }

    public String getDyjelxmc() {
        return dyjelxmc;
    }

    public void setDyjelxmc(String dyjelxmc) {
        this.dyjelxmc = dyjelxmc;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getZgzqe() {
        return zgzqe;
    }

    public void setZgzqe(String zgzqe) {
        this.zgzqe = zgzqe;
    }

    public String getSfjzdyqjdywzr() {
        return sfjzdyqjdywzr;
    }

    public void setSfjzdyqjdywzr(String sfjzdyqjdywzr) {
        this.sfjzdyqjdywzr = sfjzdyqjdywzr;
    }

    public Integer getBzdm() {
        return bzdm;
    }

    public void setBzdm(Integer bzdm) {
        this.bzdm = bzdm;
    }

    public String getBzmc() {
        return bzmc;
    }

    public void setBzmc(String bzmc) {
        this.bzmc = bzmc;
    }

    public Integer getDyts() {
        return dyts;
    }

    public void setDyts(Integer dyts) {
        this.dyts = dyts;
    }

    public Double getDywjz() {
        return dywjz;
    }

    public void setDywjz(Double dywjz) {
        this.dywjz = dywjz;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(String bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public String getZwlxksqx() {
        return zwlxksqx;
    }

    public void setZwlxksqx(String zwlxksqx) {
        this.zwlxksqx = zwlxksqx;
    }

    public String getZwlxjsqx() {
        return zwlxjsqx;
    }

    public void setZwlxjsqx(String zwlxjsqx) {
        this.zwlxjsqx = zwlxjsqx;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getQlsx() {
        return qlsx;
    }

    public void setQlsx(String qlsx) {
        this.qlsx = qlsx;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }

    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    @Override
    public String toString() {
        return "InitRequestDyxx{" +
                "dyfs='" + dyfs + '\'' +
                ", bdbzzqse='" + bdbzzqse + '\'' +
                ", zwlxksqx='" + zwlxksqx + '\'' +
                ", zwlxjsqx='" + zwlxjsqx + '\'' +
                ", fj='" + fj + '\'' +
                ", qlsx='" + qlsx + '\'' +
                ", dyfw='" + dyfw + '\'' +
                ", bdcdjzmh='" + bdcdjzmh + '\'' +
                ", dkfs='" + dkfs + '\'' +
                ", dbfw='" + dbfw + '\'' +
                ", bzdm=" + bzdm +
                ", bzmc='" + bzmc + '\'' +
                ", dyts=" + dyts +
                ", dywjz=" + dywjz +
                ", sfjzdyqjdywzr='" + sfjzdyqjdywzr + '\'' +
                ", zgzqe='" + zgzqe + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qllxmc='" + qllxmc + '\'' +
                ", dyjelxdm='" + dyjelxdm + '\'' +
                ", dyjelxmc='" + dyjelxmc + '\'' +
                '}';
    }
}
