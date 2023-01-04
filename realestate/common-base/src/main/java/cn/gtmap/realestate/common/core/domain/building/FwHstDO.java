package cn.gtmap.realestate.common.core.domain.building;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name = "fw_hst")
public class FwHstDO {
    @Id
    /**
     * 主键
     */
    private String fwHstIndex;

    /**
     * 户室图
     */
    private Blob hst;

    /**
     * 户室图名称
     */
    private String hstmc;

    /**
     * 户室图下载ID
     */
    private String hstdownid;

    /**
     * 户室图CAD
     */
    private Blob hstcad;

    /**
     * 户室图CAD名称
     */
    private String hstcadmc;

    /**
     * 户室图CAD下载ID
     */
    private String hstcaddownid;

    /**
     * 上传用户名
     */
    private String jlyhm;

    /**
     * 上传机器名
     */
    private String scjqm;

    /**
     * 上传时间
     */
    @JsonIgnore
    private String scsj;

    /**
     * 房屋宗地图
     */
    private Blob fwzdt;

    /**
     * 房屋宗地图下载ID
     */
    private String fwzdtdownid;

    /**
     * 相对路径ID
     */
    private String fwzdtpath;

    /**
     * MXD
     */
    private Blob fwzdtmxd;

    /**
     * MXD 下载ID
     */
    private String fwzdtmxddownid;

    /**
     * 宗地图名称
     */
    private String fwzdtmc;

    /**
     * 户室图备份
     */
    private Blob hstbz;

    /**
     * 户室图备份 下载ID
     */
    private String hstbzdownid;

    /**
     * 户室图备份 名称
     */
    private String hstbzmc;

    public String getFwHstIndex() {
        return fwHstIndex;
    }

    public void setFwHstIndex(String fwHstIndex) {
        this.fwHstIndex = fwHstIndex;
    }


    public String getHstmc() {
        return hstmc;
    }

    public void setHstmc(String hstmc) {
        this.hstmc = hstmc;
    }

    public String getHstdownid() {
        return hstdownid;
    }

    public void setHstdownid(String hstdownid) {
        this.hstdownid = hstdownid;
    }


    public String getHstcadmc() {
        return hstcadmc;
    }

    public void setHstcadmc(String hstcadmc) {
        this.hstcadmc = hstcadmc;
    }

    public String getHstcaddownid() {
        return hstcaddownid;
    }

    public void setHstcaddownid(String hstcaddownid) {
        this.hstcaddownid = hstcaddownid;
    }

    public String getJlyhm() {
        return jlyhm;
    }

    public void setJlyhm(String jlyhm) {
        this.jlyhm = jlyhm;
    }

    public String getScjqm() {
        return scjqm;
    }

    public void setScjqm(String scjqm) {
        this.scjqm = scjqm;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }


    public String getFwzdtdownid() {
        return fwzdtdownid;
    }

    public void setFwzdtdownid(String fwzdtdownid) {
        this.fwzdtdownid = fwzdtdownid;
    }

    public String getFwzdtpath() {
        return fwzdtpath;
    }

    public void setFwzdtpath(String fwzdtpath) {
        this.fwzdtpath = fwzdtpath;
    }


    public String getFwzdtmxddownid() {
        return fwzdtmxddownid;
    }

    public void setFwzdtmxddownid(String fwzdtmxddownid) {
        this.fwzdtmxddownid = fwzdtmxddownid;
    }

    public String getFwzdtmc() {
        return fwzdtmc;
    }

    public void setFwzdtmc(String fwzdtmc) {
        this.fwzdtmc = fwzdtmc;
    }


    public String getHstbzdownid() {
        return hstbzdownid;
    }

    public void setHstbzdownid(String hstbzdownid) {
        this.hstbzdownid = hstbzdownid;
    }

    public String getHstbzmc() {
        return hstbzmc;
    }

    public void setHstbzmc(String hstbzmc) {
        this.hstbzmc = hstbzmc;
    }

    public Blob getHst() {
        return hst;
    }

    public void setHst(Blob hst) {
        this.hst = hst;
    }

    public Blob getHstcad() {
        return hstcad;
    }

    public void setHstcad(Blob hstcad) {
        this.hstcad = hstcad;
    }

    public Blob getFwzdt() {
        return fwzdt;
    }

    public void setFwzdt(Blob fwzdt) {
        this.fwzdt = fwzdt;
    }

    public Blob getFwzdtmxd() {
        return fwzdtmxd;
    }

    public void setFwzdtmxd(Blob fwzdtmxd) {
        this.fwzdtmxd = fwzdtmxd;
    }

    public Blob getHstbz() {
        return hstbz;
    }

    public void setHstbz(Blob hstbz) {
        this.hstbz = hstbz;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwHstDO{");
        sb.append("fwHstIndex='").append(fwHstIndex).append('\'');
        sb.append(", hst='").append(hst).append('\'');
        sb.append(", hstmc='").append(hstmc).append('\'');
        sb.append(", hstdownid='").append(hstdownid).append('\'');
        sb.append(", hstcad='").append(hstcad).append('\'');
        sb.append(", hstcadmc='").append(hstcadmc).append('\'');
        sb.append(", hstcaddownid='").append(hstcaddownid).append('\'');
        sb.append(", jlyhm='").append(jlyhm).append('\'');
        sb.append(", scjqm='").append(scjqm).append('\'');
        sb.append(", scsj='").append(scsj).append('\'');
        sb.append(", fwzdt='").append(fwzdt).append('\'');
        sb.append(", fwzdtdownid='").append(fwzdtdownid).append('\'');
        sb.append(", fwzdtpath='").append(fwzdtpath).append('\'');
        sb.append(", fwzdtmxd='").append(fwzdtmxd).append('\'');
        sb.append(", fwzdtmxddownid='").append(fwzdtmxddownid).append('\'');
        sb.append(", fwzdtmc='").append(fwzdtmc).append('\'');
        sb.append(", hstbz='").append(hstbz).append('\'');
        sb.append(", hstbzdownid='").append(hstbzdownid).append('\'');
        sb.append(", hstbzmc='").append(hstbzmc).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
