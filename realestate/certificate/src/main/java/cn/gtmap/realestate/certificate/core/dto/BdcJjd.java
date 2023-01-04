package cn.gtmap.realestate.certificate.core.dto;

import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @description （海门）2.0系统中档案移交的的交接单实体（为了契合原有档案系统，3.0传输的数据格式不变）
 */
public class BdcJjd {
    private String jjdid;     // 交接单id
    private String jjdbh;     // 交接单编号
    private Date jjrq;        // 交接时间
    private String jjr;       // 交件人
    private String sjr;       // 接件人
    private String jjsfcg;    // 交接是否成功
    private String thyy;      // 退回原因
    private String thr;       // 退回人
    private String gdlx;      // 归档类型
    private String jjdlx;     // 交接单类型
    private String zfks;      // 转发科室

    public String getJjdid() {
        return jjdid;
    }

    public void setJjdid(String jjdid) {
        this.jjdid = jjdid;
    }

    public String getJjdbh() {
        return jjdbh;
    }

    public void setJjdbh(String jjdbh) {
        this.jjdbh = jjdbh;
    }

    public Date getJjrq() {
        return jjrq;
    }

    public void setJjrq(Date jjrq) {
        this.jjrq = jjrq;
    }

    public String getJjr() {
        return jjr;
    }

    public void setJjr(String jjr) {
        this.jjr = jjr;
    }

    public String getSjr() {
        return sjr;
    }

    public void setSjr(String sjr) {
        this.sjr = sjr;
    }

    public String getJjsfcg() {
        return jjsfcg;
    }

    public void setJjsfcg(String jjsfcg) {
        this.jjsfcg = jjsfcg;
    }

    public String getThyy() {
        return thyy;
    }

    public void setThyy(String thyy) {
        this.thyy = thyy;
    }

    public String getThr() {
        return thr;
    }

    public void setThr(String thr) {
        this.thr = thr;
    }

    public String getGdlx() {
        return gdlx;
    }

    public void setGdlx(String gdlx) {
        this.gdlx = gdlx;
    }

    public String getJjdlx() {
        return jjdlx;
    }

    public void setJjdlx(String jjdlx) {
        this.jjdlx = jjdlx;
    }

    public String getZfks() {
        return zfks;
    }

    public void setZfks(String zfks) {
        this.zfks = zfks;
    }
}
