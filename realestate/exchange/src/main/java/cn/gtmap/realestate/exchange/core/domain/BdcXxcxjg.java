package cn.gtmap.realestate.exchange.core.domain;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(
        name = "bdc_xxcxjg"
)
public class BdcXxcxjg {
    @Id
    private String jgid;
    private String bdcdyh;
    private String cqzh;
    private String qlr;
    private String dyzt;
    private String dysj;
    private String jbr;
    private String xxnr;
    private String jlid;
    private String Upload;
    private String jgbz;
    private String dyfs;
    private String jgwjlj;

    public BdcXxcxjg() {
    }

    public String getJgwjlj() {
        return this.jgwjlj;
    }

    public void setJgwjlj(String jgwjlj) {
        this.jgwjlj = jgwjlj;
    }

    public String getDyfs() {
        return this.dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public String getJgbz() {
        return this.jgbz;
    }

    public void setJgbz(String jgbz) {
        this.jgbz = jgbz;
    }

    public String getJgid() {
        return this.jgid;
    }

    public void setJgid(String jgid) {
        this.jgid = jgid;
    }

    public String getBdcdyh() {
        return this.bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCqzh() {
        return this.cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getQlr() {
        return this.qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getDyzt() {
        return this.dyzt;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public String getDysj() {
        return this.dysj;
    }

    public void setDysj(String dysj) {
        this.dysj = dysj;
    }

    public String getJbr() {
        return this.jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getXxnr() {
        return this.xxnr;
    }

    public void setXxnr(String xxnr) {
        this.xxnr = xxnr;
    }

    public String getJlid() {
        return this.jlid;
    }

    public void setJlid(String jlid) {
        this.jlid = jlid;
    }

    public String getUpload() {
        return this.Upload;
    }

    public void setUpload(String upload) {
        this.Upload = upload;
    }
}
