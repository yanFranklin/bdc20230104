package cn.gtmap.realestate.exchange.core.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "bdc_xxcxjl"
)
public class BdcXxcxjl implements Serializable {
    @Id
    private String jlid;
    private String cxbh;
    private String cxrmc;
    private String cxrzjh;
    private String cxzl;
    private String czrmc;
    private Date czsj;
    private String czip;
    private String cxlb;
    private String czlb;
    private String cxcqzh;
    private String cxmd;
    private String cxjqbh;
    private String cxrzplj;
    private String ycxjlid;

    public BdcXxcxjl() {
    }

    public String getCxmd() {
        return this.cxmd;
    }

    public void setCxmd(String cxmd) {
        this.cxmd = cxmd;
    }

    public String getJlid() {
        return this.jlid;
    }

    public void setJlid(String jlid) {
        this.jlid = jlid;
    }

    public String getCxbh() {
        return this.cxbh;
    }

    public void setCxbh(String cxbh) {
        this.cxbh = cxbh;
    }

    public String getCxrmc() {
        return this.cxrmc;
    }

    public void setCxrmc(String cxrmc) {
        this.cxrmc = cxrmc;
    }

    public String getCxrzjh() {
        return this.cxrzjh;
    }

    public void setCxrzjh(String cxrzjh) {
        this.cxrzjh = cxrzjh;
    }

    public String getCxzl() {
        return this.cxzl;
    }

    public void setCxzl(String cxzl) {
        this.cxzl = cxzl;
    }

    public String getCzrmc() {
        return this.czrmc;
    }

    public void setCzrmc(String czrmc) {
        this.czrmc = czrmc;
    }

    public Date getCzsj() {
        return this.czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCxlb() {
        return this.cxlb;
    }

    public void setCxlb(String cxlb) {
        this.cxlb = cxlb;
    }

    public String getCzip() {
        return this.czip;
    }

    public void setCzip(String czip) {
        this.czip = czip;
    }

    public String getCzlb() {
        return this.czlb;
    }

    public void setCzlb(String czlb) {
        this.czlb = czlb;
    }

    public String getCxcqzh() {
        return this.cxcqzh;
    }

    public void setCxcqzh(String cxcqzh) {
        this.cxcqzh = cxcqzh;
    }

    public String getCxjqbh() {
        return this.cxjqbh;
    }

    public void setCxjqbh(String cxjqbh) {
        this.cxjqbh = cxjqbh;
    }

    public String getCxrzplj() {
        return this.cxrzplj;
    }

    public void setCxrzplj(String cxrzplj) {
        this.cxrzplj = cxrzplj;
    }

    public String getYcxjlid() {
        return this.ycxjlid;
    }

    public void setYcxjlid(String ycxjlid) {
        this.ycxjlid = ycxjlid;
    }
}
