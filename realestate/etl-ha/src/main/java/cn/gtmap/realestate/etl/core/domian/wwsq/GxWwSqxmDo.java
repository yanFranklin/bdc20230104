package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 2020/06/01,1.0
 * @description 外网申请实体do
 */
@Table(name = "gx_ww_sqxm")
public class GxWwSqxmDo {

    /**
     * 全局唯一,主键
     */
    @Id
    private String xmid;
    /**
     * 外网申请受理编号
     */
    private String sqslbh;
    /**
     * 不动产登记受理编号
     */
    private String bdcdjslbh;
    /**
     * 申请时间
     */
    private Date sqsj;
    /**
     * 申请人
     */
    private String sqrmc;
    /**
     * 申请人ID
     */
    private String sqrid;
    /**
     * 第一次推送时间
     */
    private Date tssj;
    /**
     * 最后一次更新时间
     */
    private Date gxsj;
    /**
     * 登记状态名称
     */
    private String djzt;
    /**
     * 登记不允许创建，或者删除原因。
     */
    private String byslyy;
    /**
     * 业务总申请类型
     */
    private String sqlx;
    /**
     * 单位代码
     */
    private String dwdm;
    /**
     * 创建人
     */
    private String cjr;
    /**
     * 是否创建
     */
    private String sfcj;
    /**
     * 证书来源
     */
    private String zsly;
    /**
     * 申请来源
     */
    private String sqly;
    /**
     * 审核状态（0或者空为未审核，1为审核通过，2未审核不通过）
     */
    private String shzt;
    /**
     * 完税状态（0：未完税，1：已完税）
     */
    private String wszt;
    /**
     * 创建人实现方式
     */
    private String cjrsxfs;
    /**
     * 预约部门编码
     */
    private String yybmbm;
    /**
     * 创建时间
     */
    private Date cjsj;
    /**
     *
     */
    private String bcxrmc;
    /**
     * 创建时间
     */
    private Date bcxrsfzjzl;
    /**
     *
     */
    private String kzwjmc;
    /**
     *
     */
    private String cbr;
    /**
     *
     */
    private String fymc;
    /**
     *
     */
    private String bcxrzjh;
    /**
     * 一体化业务编号
     */
    private String ythywbh;
    /**
     *
     */
    private String tsbj;
    /**
     *
     */
    private String ysqslbh;
    /**
     * 是否合成电子证照
     */
    private Date sfhcdzzz;
    /**
     * 银行来源系统
     */
    private Date yhlyxt;
    /**
     *
     */
    private String fybh;
    /**
     * 创建人角色名称
     */
    private String cjrjsmc;
    /**
     * 领证方式
     */
    private String lzfs;
    /**
     * 申请人证件号
     */
    private String sqrzjh;
    /**
     * 自动转发 0不转发 1转发
     */
    private String zdzf;
    /**
     * 锁定状态（0或者空为未锁定，1为已锁定，2为锁定失败）
     */
    private String sdzt;
    /**
     *
     */
    private String tsxx;
    /**
     * 满意度
     */
    private String myd;
    /**
     * 评价内容
     */
    private String pjnr;
    /**
     *
     */
    private String kzcs;



    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }


    public String getSqslbh() {
        return sqslbh;
    }

    public void setSqslbh(String sqslbh) {
        this.sqslbh = sqslbh;
    }


    public String getBdcdjslbh() {
        return bdcdjslbh;
    }

    public void setBdcdjslbh(String bdcdjslbh) {
        this.bdcdjslbh = bdcdjslbh;
    }


    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }


    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }


    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }


    public Date getTssj() {
        return tssj;
    }

    public void setTssj(Date tssj) {
        this.tssj = tssj;
    }


    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }


    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }


    public String getByslyy() {
        return byslyy;
    }

    public void setByslyy(String byslyy) {
        this.byslyy = byslyy;
    }


    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }


    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }


    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }


    public String getSfcj() {
        return sfcj;
    }

    public void setSfcj(String sfcj) {
        this.sfcj = sfcj;
    }


    public String getZsly() {
        return zsly;
    }

    public void setZsly(String zsly) {
        this.zsly = zsly;
    }


    public String getSqly() {
        return sqly;
    }

    public void setSqly(String sqly) {
        this.sqly = sqly;
    }


    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }


    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }


    public String getCjrsxfs() {
        return cjrsxfs;
    }

    public void setCjrsxfs(String cjrsxfs) {
        this.cjrsxfs = cjrsxfs;
    }


    public String getYybmbm() {
        return yybmbm;
    }

    public void setYybmbm(String yybmbm) {
        this.yybmbm = yybmbm;
    }

    public String getZdzf() {
        return zdzf;
    }

    public void setZdzf(String zdzf) {
        this.zdzf = zdzf;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getBcxrmc() {
        return bcxrmc;
    }

    public void setBcxrmc(String bcxrmc) {
        this.bcxrmc = bcxrmc;
    }

    public Date getBcxrsfzjzl() {
        return bcxrsfzjzl;
    }

    public void setBcxrsfzjzl(Date bcxrsfzjzl) {
        this.bcxrsfzjzl = bcxrsfzjzl;
    }

    public String getKzwjmc() {
        return kzwjmc;
    }

    public void setKzwjmc(String kzwjmc) {
        this.kzwjmc = kzwjmc;
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr;
    }

    public String getFymc() {
        return fymc;
    }

    public void setFymc(String fymc) {
        this.fymc = fymc;
    }

    public String getBcxrzjh() {
        return bcxrzjh;
    }

    public void setBcxrzjh(String bcxrzjh) {
        this.bcxrzjh = bcxrzjh;
    }

    public String getYthywbh() {
        return ythywbh;
    }

    public void setYthywbh(String ythywbh) {
        this.ythywbh = ythywbh;
    }

    public String getTsbj() {
        return tsbj;
    }

    public void setTsbj(String tsbj) {
        this.tsbj = tsbj;
    }

    public String getYsqslbh() {
        return ysqslbh;
    }

    public void setYsqslbh(String ysqslbh) {
        this.ysqslbh = ysqslbh;
    }

    public Date getSfhcdzzz() {
        return sfhcdzzz;
    }

    public void setSfhcdzzz(Date sfhcdzzz) {
        this.sfhcdzzz = sfhcdzzz;
    }

    public Date getYhlyxt() {
        return yhlyxt;
    }

    public void setYhlyxt(Date yhlyxt) {
        this.yhlyxt = yhlyxt;
    }

    public String getFybh() {
        return fybh;
    }

    public void setFybh(String fybh) {
        this.fybh = fybh;
    }

    public String getCjrjsmc() {
        return cjrjsmc;
    }

    public void setCjrjsmc(String cjrjsmc) {
        this.cjrjsmc = cjrjsmc;
    }

    public String getLzfs() {
        return lzfs;
    }

    public void setLzfs(String lzfs) {
        this.lzfs = lzfs;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }

    public String getSdzt() {
        return sdzt;
    }

    public void setSdzt(String sdzt) {
        this.sdzt = sdzt;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public String getMyd() {
        return myd;
    }

    public void setMyd(String myd) {
        this.myd = myd;
    }

    public String getPjnr() {
        return pjnr;
    }

    public void setPjnr(String pjnr) {
        this.pjnr = pjnr;
    }

    public String getKzcs() {
        return kzcs;
    }

    public void setKzcs(String kzcs) {
        this.kzcs = kzcs;
    }
}
