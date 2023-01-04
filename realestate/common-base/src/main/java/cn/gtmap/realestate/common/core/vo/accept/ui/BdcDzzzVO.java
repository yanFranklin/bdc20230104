package cn.gtmap.realestate.common.core.vo.accept.ui;

import java.io.Serializable;

/**
 * @program: realestate
 * @description: 电子证照传输对象
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-01-20 15:38
 **/
public class BdcDzzzVO implements Serializable {
    private static final long serialVersionUID = -6173345665152020267L;

    private String typecode;

    private String qlrmc;

    private String qlrzjh;

    private String lxdh;

    private String ewmnr;

    private String pdflx;

    private String gzlslid;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getEwmnr() {
        return ewmnr;
    }

    public void setEwmnr(String ewmnr) {
        this.ewmnr = ewmnr;
    }

    public String getPdflx() {
        return pdflx;
    }

    public void setPdflx(String pdflx) {
        this.pdflx = pdflx;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String typecode) {
        this.typecode = typecode;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Override
    public String toString() {
        return "BdcDzzzVO{" +
                "typecode='" + typecode + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", ewmnr='" + ewmnr + '\'' +
                ", pdflx='" + pdflx + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
