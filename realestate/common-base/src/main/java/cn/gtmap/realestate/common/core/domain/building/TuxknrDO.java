package cn.gtmap.realestate.common.core.domain.building;


import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "TUXKNR")
public class TuxknrDO {

    @Id
    private Integer objectid;
    private String tucd;
    private String tumc;
    private Blob tunr;
    private String tcm;
    private String tuxkid;
    private String ccml;
    private String bgjc;
    private Integer xh;
    private String old;
    private String id;
    private String fileid;
    private String scrq;

    public Integer getObjectid() {
        return objectid;
    }

    public void setObjectid(Integer objectid) {
        this.objectid = objectid;
    }

    public String getTucd() {
        return tucd;
    }

    public void setTucd(String tucd) {
        this.tucd = tucd;
    }

    public String getTumc() {
        return tumc;
    }

    public void setTumc(String tumc) {
        this.tumc = tumc;
    }

    public Blob getTunr() {
        return tunr;
    }

    public void setTunr(Blob tunr) {
        this.tunr = tunr;
    }

    public String getTcm() {
        return tcm;
    }

    public void setTcm(String tcm) {
        this.tcm = tcm;
    }

    public String getTuxkid() {
        return tuxkid;
    }

    public void setTuxkid(String tuxkid) {
        this.tuxkid = tuxkid;
    }

    public String getCcml() {
        return ccml;
    }

    public void setCcml(String ccml) {
        this.ccml = ccml;
    }

    public String getBgjc() {
        return bgjc;
    }

    public void setBgjc(String bgjc) {
        this.bgjc = bgjc;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    public String getScrq() {
        return scrq;
    }

    public void setScrq(String scrq) {
        this.scrq = scrq;
    }
}
