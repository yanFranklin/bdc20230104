package cn.gtmap.realestate.common.core.domain.naturalresource;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wyh
 * @version 1.0
 * @date 2021/10/25 16:06
 */
@Table(name = "Pwqglxx")
@Data
@EqualsAndHashCode
public class PwqglxxDO implements Serializable {
    private String zrzydjdyh;

    private String dwmc;

    private String fzjg;

    private String pfndxz;

    private String pwxkzh;

    private String qxdm;

    private String wrwzl;

    private String ysdm;

    private String pwqyxqx;

    private String bdcdxxgldbsm;

    private String zrzydjdyhzh;

    public String getZrzydjdyh() {
        return zrzydjdyh;
    }

    public void setZrzydjdyh(String zrzydjdyh) {
        this.zrzydjdyh = zrzydjdyh;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getPfndxz() {
        return pfndxz;
    }

    public void setPfndxz(String pfndxz) {
        this.pfndxz = pfndxz;
    }

    public String getPwxkzh() {
        return pwxkzh;
    }

    public void setPwxkzh(String pwxkzh) {
        this.pwxkzh = pwxkzh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getWrwzl() {
        return wrwzl;
    }

    public void setWrwzl(String wrwzl) {
        this.wrwzl = wrwzl;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getPwqyxqx() {
        return pwqyxqx;
    }

    public void setPwqyxqx(String pwqyxqx) {
        this.pwqyxqx = pwqyxqx;
    }

    public String getBdcdxxgldbsm() {
        return bdcdxxgldbsm;
    }

    public void setBdcdxxgldbsm(String bdcdxxgldbsm) {
        this.bdcdxxgldbsm = bdcdxxgldbsm;
    }

    public String getZrzydjdyhzh() {
        return zrzydjdyhzh;
    }

    public void setZrzydjdyhzh(String zrzydjdyhzh) {
        this.zrzydjdyhzh = zrzydjdyhzh;
    }
}
