package cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.baseInfo;

import cn.gtmap.realestate.common.core.dto.exchange.naturalresources.police.identitycheck.PoliceIdentityCheckReturnDTO;

import java.util.List;

/**
 * @author <a href="mailto:zedeqiang@gtmap.cn">zedq</a>
 * @version 1.0
 * @date 2021/08/04 15:49
 * @description
 */
public class PoliceQueryBaseInfoResponseDTO {

    private String csd_gjdqm;
    private String csd_ssxdqm;
    private String csrq;
    private String gmsfzh;
    private String gmsfzh_ppdd;
    private String mzdm;
    private String swbs;
    private String xbdm;
    private String xm;
    private String xm_ppddm;

    public String getCsd_gjdqm() {
        return csd_gjdqm;
    }

    public void setCsd_gjdqm(String csd_gjdqm) {
        this.csd_gjdqm = csd_gjdqm;
    }

    public String getCsd_ssxdqm() {
        return csd_ssxdqm;
    }

    public void setCsd_ssxdqm(String csd_ssxdqm) {
        this.csd_ssxdqm = csd_ssxdqm;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getGmsfzh() {
        return gmsfzh;
    }

    public void setGmsfzh(String gmsfzh) {
        this.gmsfzh = gmsfzh;
    }

    public String getGmsfzh_ppdd() {
        return gmsfzh_ppdd;
    }

    public void setGmsfzh_ppdd(String gmsfzh_ppdd) {
        this.gmsfzh_ppdd = gmsfzh_ppdd;
    }

    public String getMzdm() {
        return mzdm;
    }

    public void setMzdm(String mzdm) {
        this.mzdm = mzdm;
    }

    public String getSwbs() {
        return swbs;
    }

    public void setSwbs(String swbs) {
        this.swbs = swbs;
    }

    public String getXbdm() {
        return xbdm;
    }

    public void setXbdm(String xbdm) {
        this.xbdm = xbdm;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXm_ppddm() {
        return xm_ppddm;
    }

    public void setXm_ppddm(String xm_ppddm) {
        this.xm_ppddm = xm_ppddm;
    }
}
