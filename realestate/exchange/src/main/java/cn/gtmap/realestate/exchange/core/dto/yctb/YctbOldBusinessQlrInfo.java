package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;
import java.util.List;

public class YctbOldBusinessQlrInfo implements Serializable {

    private static final long serialVersionUID = 104312514736971023L;

    private String gj; //string 国家 默认值 142（中华人民共和国）
    private String dh; //string 电话
    private String qlrmc; //string 权利人名称
    private String sshy; //string 所属行业
    private String qlrlx; //string 权利人类型 见字典权利人类型
    private String yb; //string 邮编
    private String xb; //string 性别 见字典性别
    private String hjszss; //string 户籍所在省市 本字典表采用《中华人民 共和国行政区划代码》GB/T 2260
    private String zjhm; //string 证件号码
    private String gyfs; //string 共有方式 见字典共有方式
    private String sxh; //string 顺序号
    private String qlbl; //string 权利比例（当共有方式为按份共有时， 必填）
    private String dz; //string 地址
    private String qlrlb; //string 权利人类别 见字典权利人类别
    private String qlrid; //string 权利人 ID
    private String zjzl; //string 证件种类 见字典证件种类
    private List<YctbWwcjGxrInfo> gxrlist; //关系人

    public List<YctbWwcjGxrInfo> getGxrlist() {
        return gxrlist;
    }

    public void setGxrlist(List<YctbWwcjGxrInfo> gxrlist) {
        this.gxrlist = gxrlist;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getHjszss() {
        return hjszss;
    }

    public void setHjszss(String hjszss) {
        this.hjszss = hjszss;
    }

    public String getZjhm() {
        return zjhm;
    }

    public void setZjhm(String zjhm) {
        this.zjhm = zjhm;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getSxh() {
        return sxh;
    }

    public void setSxh(String sxh) {
        this.sxh = sxh;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrid() {
        return qlrid;
    }

    public void setQlrid(String qlrid) {
        this.qlrid = qlrid;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }
}
