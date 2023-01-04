package cn.gtmap.realestate.certificate.core.model.domain;

import java.io.Serializable;

public class BdcDzzzCxtjDo implements Serializable {
    //用户名称
    private String username;
    //持证主体
    private String czzt;
    //持证主体代码
    private String czztdm;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCzztdm() {
        return czztdm;
    }

    public void setCzztdm(String czztdm) {
        this.czztdm = czztdm;
    }

    public String getCzzt() {
        return czzt;
    }

    public void setCzzt(String czzt) {
        this.czzt = czzt;
    }
}
