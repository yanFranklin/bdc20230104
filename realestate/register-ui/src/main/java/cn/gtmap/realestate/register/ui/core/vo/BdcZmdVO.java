package cn.gtmap.realestate.register.ui.core.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/7/18
 * @description 证明单VO实体类
 */
public class BdcZmdVO {
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 证书ID
     */
    private String zsid;
    /**
     * 打印状态
     */
    private Integer dyzt;

    /**
     * 证书项目的权属状态
     */
    private Integer qszt;
    /**
     * 不动产权证
     */
    private String bdcqzh;
    /**
     * 证书年份
     */
    private String nf;
    /**
     * 证号流水号
     */
    private String zhlsh;
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 预售许可证
     */
    private String ysxkzh;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 房屋性质
     */
    private String fwxz;
    /**
     * 定着物用途
     */
    private String dzwyt;
    /**
     * 房屋结构
     */
    private String fwjg;
    /**
     * 建筑层数（总层数）
     */
    private Integer zcs;
    /**
     * 所在层数（所在层）
     */
    private String szc;
    /**
     * 所在名义层
     */
    private String szmyc;
    /**
     * 竣工时间
     */
    private String jgsj;
    /**
     * 建筑面积
     */
    private Double dzwmj;
    /**
     * 分摊建筑面积
     */
    private Double ftjzmj;
    /**
     * 套内建筑面积
     */
    private Double tnjzmj;
    /**
     * 建筑面积
     */
    private Double jzmj;
    /**
     * 附记
     */
    private String fj;
    /**
     * 土地产权证
     */
    private String tdcqzh;
    /**
     * 地籍号
     */
    private String zdzhh;
    /**
     * 土地使用结束时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date tdsyjssj;
    /**
     * 土地使用权性质
     */
    private String tdqlxz;
    /**
     * 土地权利类型
     */
    private String tdqllx;
    /**
     * 宗地宗海用途
     */
    private String zdzhyt;
    /**
     * 宗地宗海面积
     */
    private Double zdzhmj;
    /**
     * 土地使用权面积
     */
    private Double tdsyqmj;
    /**
     * 独用土地面积
     */
    private Double dytdmj;
    /**
     * 分摊土地面积
     */
    private Double fttdmj;
    /**
     * 登记机构
     */
    private String djjg;
    /**
     * 登记时间
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date djsj;
    /**
     * 缮证人
     */
    private String szr;
    /**
     * 登簿人
     */
    private String dbr;
    /**
     * 二维码内容
     */
    private String ewmnr;

    /**
     * 面积核定文件号
     */
    private String mjhdwjh;

    /**
     * 合同备案号
     */
    private String htbah;

    /**
     * 使用期限
     */
    private String syqx;

    /**
     * 是否主房
     */
    private Integer sfzf;

    /**
     * 证明内容
     */
    private String zmnr;

    public Integer getSfzf() {
        return sfzf;
    }

    public void setSfzf(Integer sfzf) {
        this.sfzf = sfzf;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public Integer getQszt() {
        return qszt;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }
    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getYsxkzh() {
        return ysxkzh;
    }

    public void setYsxkzh(String ysxkzh) {
        this.ysxkzh = ysxkzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }


    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public Integer getZcs() {
        return zcs;
    }

    public void setZcs(Integer zcs) {
        this.zcs = zcs;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public Double getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(Double ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public Double getTnjzmj() {
        return tnjzmj;
    }

    public void setTnjzmj(Double tnjzmj) {
        this.tnjzmj = tnjzmj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getTdcqzh() {
        return tdcqzh;
    }

    public void setTdcqzh(String tdcqzh) {
        this.tdcqzh = tdcqzh;
    }

    public String getZdzhh() {
        return zdzhh;
    }

    public void setZdzhh(String zdzhh) {
        this.zdzhh = zdzhh;
    }

    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public String getTdqlxz() {
        return tdqlxz;
    }

    public void setTdqlxz(String tdqlxz) {
        this.tdqlxz = tdqlxz;
    }

    public String getTdqllx() {
        return tdqllx;
    }

    public void setTdqllx(String tdqllx) {
        this.tdqllx = tdqllx;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
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

    public String getSzr() {
        return szr;
    }

    public void setSzr(String szr) {
        this.szr = szr;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getMjhdwjh() {
        return mjhdwjh;
    }

    public void setMjhdwjh(String mjhdwjh) {
        this.mjhdwjh = mjhdwjh;
    }

    public String getHtbah() {
        return htbah;
    }

    public void setHtbah(String htbah) {
        this.htbah = htbah;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public String getSyqx() {
        return syqx;
    }

    public void setSyqx(String syqx) {
        this.syqx = syqx;
    }

    public String getZmnr() {
        return zmnr;
    }

    public void setZmnr(String zmnr) {
        this.zmnr = zmnr;
    }

    @Override
    public String toString() {
        return "BdcZmdVO{" +
                "slbh='" + slbh + '\'' +
                ", zsid='" + zsid + '\'' +
                ", dyzt=" + dyzt +
                ", qszt=" + qszt +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", nf='" + nf + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ysxkzh='" + ysxkzh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", dzwyt='" + dzwyt + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", zcs=" + zcs +
                ", szc='" + szc + '\'' +
                ", szmyc='" + szmyc + '\'' +
                ", jgsj='" + jgsj + '\'' +
                ", dzwmj=" + dzwmj +
                ", ftjzmj=" + ftjzmj +
                ", tnjzmj=" + tnjzmj +
                ", jzmj=" + jzmj +
                ", fj='" + fj + '\'' +
                ", tdcqzh='" + tdcqzh + '\'' +
                ", zdzhh='" + zdzhh + '\'' +
                ", tdsyjssj=" + tdsyjssj +
                ", tdqlxz='" + tdqlxz + '\'' +
                ", tdqllx='" + tdqllx + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", zdzhmj=" + zdzhmj +
                ", tdsyqmj=" + tdsyqmj +
                ", dytdmj=" + dytdmj +
                ", fttdmj=" + fttdmj +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", szr='" + szr + '\'' +
                ", dbr='" + dbr + '\'' +
                ", ewmnr='" + ewmnr + '\'' +
                ", mjhdwjh='" + mjhdwjh + '\'' +
                ", htbah='" + htbah + '\'' +
                ", syqx='" + syqx + '\'' +
                ", sfzf=" + sfzf +
                ", zmnr='" + zmnr + '\'' +
                '}';
    }
}
