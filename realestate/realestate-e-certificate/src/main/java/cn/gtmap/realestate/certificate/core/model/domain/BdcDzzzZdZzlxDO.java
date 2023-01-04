package cn.gtmap.realestate.certificate.core.model.domain;

import java.io.Serializable;

public class BdcDzzzZdZzlxDO implements Serializable {

    private static final long serialVersionUID = -2288793763816355951L;
    // 证照类型代码
    private String zzlxdm;
    // 证照类型名称
    private String zzlxmc;

    public String getZzlxdm() {
        return zzlxdm;
    }

    public void setZzlxdm(String zzlxdm) {
        this.zzlxdm = zzlxdm;
    }

    public String getZzlxmc() {
        return zzlxmc;
    }

    public void setZzlxmc(String zzlxmc) {
        this.zzlxmc = zzlxmc;
    }
}
