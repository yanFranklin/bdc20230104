package cn.gtmap.realestate.common.core.dto.certificate.eCertificate;

import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2020/04/30
 * @description 电子证照证明主体信息实体DTO
 */
public class HefeiDzzzDTO {
    private String zzbs;

    private String zzdz;

    private String zzmlbm;

    private Date zzqfsj;

    private String data;

    private String zzid;

    public String getZzbs() {
        return zzbs;
    }

    public void setZzbs(String zzbs) {
        this.zzbs = zzbs;
    }

    public String getZzdz() {
        return zzdz;
    }

    public void setZzdz(String zzdz) {
        this.zzdz = zzdz;
    }

    public String getZzmlbm() {
        return zzmlbm;
    }

    public void setZzmlbm(String zzmlbm) {
        this.zzmlbm = zzmlbm;
    }

    public Date getZzqfsj() {
        return zzqfsj;
    }

    public void setZzqfsj(Date zzqfsj) {
        this.zzqfsj = zzqfsj;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getZzid() {
        return zzid;
    }

    public void setZzid(String zzid) {
        this.zzid = zzid;
    }

    @Override
    public String toString() {
        return "HefeiDzzzDTO{" +
                "zzbs='" + zzbs + '\'' +
                ", zzdz='" + zzdz + '\'' +
                ", zzmlbm='" + zzmlbm + '\'' +
                ", zzqfsj=" + zzqfsj +
                ", data='" + data + '\'' +
                ", zzid='" + zzid + '\'' +
                '}';
    }
}
