package cn.gtmap.realestate.certificate.core.model.domain;


import java.io.Serializable;
import java.util.Date;

/*
 * @author <a href="mailto:zhangyu@gtmap.cn">zhangyu</a>
 * @version 1.0, zhangyu
 * @description   电子证照流水号实体
 */
public class BdcDzzzLshDo implements Serializable {
    private static final long serialVersionUID = 3122970691698024263L;
    //主键
    private String lshid;
    //流水号
    private String lsh;
    //创建时间
    private Date cjsj;
    //证照类型代码
    private String zzlxdm;
    //证照颁发机构
    private String zzbfjgdm;

    public String getLshid() {
        return lshid;
    }

    public void setLshid(String lshid) {
        this.lshid = lshid;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getZzlxdm() {
        return zzlxdm;
    }

    public void setZzlxdm(String zzlxdm) {
        this.zzlxdm = zzlxdm;
    }

    public String getZzbfjgdm() {
        return zzbfjgdm;
    }

    public void setZzbfjgdm(String zzbfjgdm) {
        this.zzbfjgdm = zzbfjgdm;
    }
}
