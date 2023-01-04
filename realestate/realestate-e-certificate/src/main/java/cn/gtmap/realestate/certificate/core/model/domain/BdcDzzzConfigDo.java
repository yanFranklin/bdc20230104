package cn.gtmap.realestate.certificate.core.model.domain;



/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description  电子证照系统配置表
 */


import java.io.Serializable;

public class BdcDzzzConfigDo implements Serializable{
    private static final long serialVersionUID = 310720257225921791L;
    //单位代码
    private String dwdm;
    //所在市县全称
    private String szsxqc;
    //证照签章人
    private String zzqzr;
    //证照签章名称
    private String zzqzmc;
    //证照颁发机构代码
    private String zzbfjgdm;
    //意源签章容器名
    private String containerName;
    //意源签章访问码
    private String accessCode;
    //签章名称
    private String keysn;
    //应用名称
    private String yymc;


    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getZzqzr() {
        return zzqzr;
    }

    public void setZzqzr(String zzqzr) {
        this.zzqzr = zzqzr;
    }

    public String getZzqzmc() {
        return zzqzmc;
    }

    public void setZzqzmc(String zzqzmc) {
        this.zzqzmc = zzqzmc;
    }

    public String getZzbfjgdm() {
        return zzbfjgdm;
    }

    public void setZzbfjgdm(String zzbfjgdm) {
        this.zzbfjgdm = zzbfjgdm;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKeysn() {
        return keysn;
    }

    public void setKeysn(String keysn) {
        this.keysn = keysn;
    }

    public String getYymc() {
        return yymc;
    }

    public void setYymc(String yymc) {
        this.yymc = yymc;
    }
}
