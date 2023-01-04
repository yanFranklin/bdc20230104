package cn.gtmap.realestate.exchange.core.domain;

import javax.persistence.Table;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2019/12/30,1.0
 * @description
 */
@Table(name = "dv_hlw")
public class DvHlwDo {

    /**
     * 权利人名称
     */
    private String qlrmc;
    /**
     * 权利人证件类型
     */
    private String zjlx;
    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 共有方式
     */
    private String gyfs;
    /**
     * 共有比例
     */
    private String gybl;
    /**
     * ROOMID
     */
    private String roomid;
    /**
     * 不定产单元号
     */
    private String bdcdyh;
    /**
     * 不动产权证号/房产证号
     */
    private String cqzh;
    /**
     * 房产坐落
     */
    private String fczl;
    /**
     * 房屋类型
     */
    private String fwlx;
    /**
     * 房屋性质
     */
    private String fwxz;
    /**
     * 房屋结构
     */
    private String fwjg;
    /**
     * 房屋用途
     */
    private String fwyt;
    /**
     * 总层数
     */
    private Integer zcs;
    /**
     * 所在层
     */
    private String szc;
    /**
     * 专用建筑面积
     */
    private Double zyjzmj;
    /**
     * 分摊建筑面积
     */
    private Double ftjzmj;
    /**
     * 土地
     */
    private String tdzh;
    /**
     * 土地坐落
     */
    private String tdzl;
    /**
     * 权属性质
     */
    private String qsxz;
    /**
     * 使用权类型
     */
    private String syqlx;
    /**
     * 地籍号
     */
    private String djh;
    /**
     * 宗地面积
     */
    private Double zdmj;
    /**
     * 分摊土地面积
     */
    private Double fttdmj;
    /**
     * 土地使用开始日期
     */
    private String tdsyksrq;
    /**
     * 土地使用结束日期
     */
    private String tdsyjsrq;
    /**
     * 调用类型
     */
    private String dylx;
    /**
     * 是否查封
     */
    private String sfcf;
    /**
     * 是否抵押
     */
    private String sfdy;
    /**
     * 是否异议
     */
    private String sfyy;
    /**
     * 土地用途
     */
    private String tdyt;
    /**
     * 独用土地面积
     */
    private Double dytdmj;
    /**
     * 建筑面积
     */
    private Double jzmj;
    /**
     * 区县代码
     */
    private String qxdm;
    /**
     * 施工编号
     */
    private String sgbh;
    /**
     * 收费用途
     */
    private String sfyt;
    /**
     * 登簿时间
     */
    private String dbsj;
    /**
     * 幢号
     */
    private String zh;
    /**
     * 交易价格
     */
    private Double jyjg;

    private String ssjd;

    private String ssjddm;

    private String fh;

    private String qdjg;

    private String qlslqk;

    public String getQdjg() {
        return qdjg;
    }

    public void setQdjg(String qdjg) {
        this.qdjg = qdjg;
    }

    public String getQlslqk() {
        return qlslqk;
    }

    public void setQlslqk(String qlslqk) {
        this.qlslqk = qlslqk;
    }

    public String getFh() {
        return fh;
    }

    public void setFh(String fh) {
        this.fh = fh;
    }

    public String getSsjd() {
        return ssjd;
    }

    public void setSsjd(String ssjd) {
        this.ssjd = ssjd;
    }

    public String getSsjddm() {
        return ssjddm;
    }

    public void setSsjddm(String ssjddm) {
        this.ssjddm = ssjddm;
    }

    public String getSgbh() {
        return sgbh;
    }

    public void setSgbh(String sgbh) {
        this.sgbh = sgbh;
    }

    public String getSfyt() {
        return sfyt;
    }

    public void setSfyt(String sfyt) {
        this.sfyt = sfyt;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjlx() {
        return zjlx;
    }

    public void setZjlx(String zjlx) {
        this.zjlx = zjlx;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGybl() {
        return gybl;
    }

    public void setGybl(String gybl) {
        this.gybl = gybl;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getFczl() {
        return fczl;
    }

    public void setFczl(String fczl) {
        this.fczl = fczl;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
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

    public Double getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(Double zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    public Double getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(Double ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getTdzl() {
        return tdzl;
    }

    public void setTdzl(String tdzl) {
        this.tdzl = tdzl;
    }

    public String getQsxz() {
        return qsxz;
    }

    public void setQsxz(String qsxz) {
        this.qsxz = qsxz;
    }

    public String getSyqlx() {
        return syqlx;
    }

    public void setSyqlx(String syqlx) {
        this.syqlx = syqlx;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public Double getZdmj() {
        return zdmj;
    }

    public void setZdmj(Double zdmj) {
        this.zdmj = zdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getTdsyksrq() {
        return tdsyksrq;
    }

    public void setTdsyksrq(String tdsyksrq) {
        this.tdsyksrq = tdsyksrq;
    }

    public String getTdsyjsrq() {
        return tdsyjsrq;
    }

    public void setTdsyjsrq(String tdsyjsrq) {
        this.tdsyjsrq = tdsyjsrq;
    }

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    public String getSfyy() {
        return sfyy;
    }

    public void setSfyy(String sfyy) {
        this.sfyy = sfyy;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getDbsj() {
        return dbsj;
    }

    public void setDbsj(String dbsj) {
        this.dbsj = dbsj;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    @Override
    public String toString() {
        return "DvHlwDo{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zjlx='" + zjlx + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gybl='" + gybl + '\'' +
                ", roomid='" + roomid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", fczl='" + fczl + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", fwyt='" + fwyt + '\'' +
                ", zcs=" + zcs +
                ", szc='" + szc + '\'' +
                ", zyjzmj=" + zyjzmj +
                ", ftjzmj=" + ftjzmj +
                ", tdzh='" + tdzh + '\'' +
                ", tdzl='" + tdzl + '\'' +
                ", qsxz='" + qsxz + '\'' +
                ", syqlx='" + syqlx + '\'' +
                ", djh='" + djh + '\'' +
                ", zdmj=" + zdmj +
                ", fttdmj=" + fttdmj +
                ", tdsyksrq='" + tdsyksrq + '\'' +
                ", tdsyjsrq='" + tdsyjsrq + '\'' +
                ", dylx='" + dylx + '\'' +
                ", sfcf='" + sfcf + '\'' +
                ", sfdy='" + sfdy + '\'' +
                ", sfyy='" + sfyy + '\'' +
                ", tdyt='" + tdyt + '\'' +
                ", dytdmj=" + dytdmj +
                ", jzmj=" + jzmj +
                ", qxdm='" + qxdm + '\'' +
                ", sgbh='" + sgbh + '\'' +
                ", sfyt='" + sfyt + '\'' +
                ", dbsj='" + dbsj + '\'' +
                ", zh='" + zh + '\'' +
                ", jyjg=" + jyjg +
                ", ssjd='" + ssjd + '\'' +
                ", ssjddm='" + ssjddm + '\'' +
                '}';
    }
}
