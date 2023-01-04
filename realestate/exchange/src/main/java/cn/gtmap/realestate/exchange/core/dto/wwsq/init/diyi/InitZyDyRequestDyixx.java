package cn.gtmap.realestate.exchange.core.dto.wwsq.init.diyi;

import cn.gtmap.realestate.common.core.dto.exchange.wwsq.FjclDTOForZhlc;
import cn.gtmap.realestate.exchange.core.dto.wwsq.init.zydy.InitZyDyRequestQlrxx;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/7/1
 * @description 组合流程-地役信息
 */
public class InitZyDyRequestDyixx {


    /**
     * dyqnr :
     * xmid :
     * dyqqssj :
     * dyqjssj :
     * fj :
     * bdclx :
     * bdclxmc :
     * zdzhmj :
     * zdyt :
     * zdytmc :
     * zdqlxz :
     * zdqlxzmc :
     * xydbdcdyh :
     * xydqlrmc :
     * gydqlrmc :
     */

    private String dyqnr;
    private String xmid;
    private String dyqqssj;
    private String dyqjssj;
    private String fj;
    private String bdclx;
    private String bdclxmc;
    private String zdzhmj;
    private String zdyt;
    private String zdytmc;
    private String zdqlxz;
    private String zdqlxzmc;
    private String xydbdcdyh;
    private String xydqlrmc;
    private String gydqlrmc;
    private List<InitZyDyRequestQlrxx> qlrxx;
    private List<FjclDTOForZhlc> fjxx;


    public List<InitZyDyRequestQlrxx> getQlrxx() {
        return qlrxx;
    }

    public void setQlrxx(List<InitZyDyRequestQlrxx> qlrxx) {
        this.qlrxx = qlrxx;
    }

    public List<FjclDTOForZhlc> getFjxx() {
        return fjxx;
    }

    public void setFjxx(List<FjclDTOForZhlc> fjxx) {
        this.fjxx = fjxx;
    }

    public String getDyqnr() {
        return dyqnr;
    }

    public void setDyqnr(String dyqnr) {
        this.dyqnr = dyqnr;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDyqqssj() {
        return dyqqssj;
    }

    public void setDyqqssj(String dyqqssj) {
        this.dyqqssj = dyqqssj;
    }

    public String getDyqjssj() {
        return dyqjssj;
    }

    public void setDyqjssj(String dyqjssj) {
        this.dyqjssj = dyqjssj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdclxmc() {
        return bdclxmc;
    }

    public void setBdclxmc(String bdclxmc) {
        this.bdclxmc = bdclxmc;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getZdyt() {
        return zdyt;
    }

    public void setZdyt(String zdyt) {
        this.zdyt = zdyt;
    }

    public String getZdytmc() {
        return zdytmc;
    }

    public void setZdytmc(String zdytmc) {
        this.zdytmc = zdytmc;
    }

    public String getZdqlxz() {
        return zdqlxz;
    }

    public void setZdqlxz(String zdqlxz) {
        this.zdqlxz = zdqlxz;
    }

    public String getZdqlxzmc() {
        return zdqlxzmc;
    }

    public void setZdqlxzmc(String zdqlxzmc) {
        this.zdqlxzmc = zdqlxzmc;
    }

    public String getXydbdcdyh() {
        return xydbdcdyh;
    }

    public void setXydbdcdyh(String xydbdcdyh) {
        this.xydbdcdyh = xydbdcdyh;
    }

    public String getXydqlrmc() {
        return xydqlrmc;
    }

    public void setXydqlrmc(String xydqlrmc) {
        this.xydqlrmc = xydqlrmc;
    }

    public String getGydqlrmc() {
        return gydqlrmc;
    }

    public void setGydqlrmc(String gydqlrmc) {
        this.gydqlrmc = gydqlrmc;
    }
}
