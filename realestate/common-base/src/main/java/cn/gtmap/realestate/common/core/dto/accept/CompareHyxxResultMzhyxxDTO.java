package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;

import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-28
 * @description 民政婚姻信息比对结果
 */
public class CompareHyxxResultMzhyxxDTO {

    private String sqrid;

    private String hyzt;

    private String poxm;

    private String pozjzl;

    private String pozjh;

    private String hydjjg;

    private Date djrq;

    /**
     *  子女信息
     */
    private List<BdcSlJtcyDO> znJtcyList;

    public String getHyzt() {
        return hyzt;
    }

    public void setHyzt(String hyzt) {
        this.hyzt = hyzt;
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public String getPozjzl() {
        return pozjzl;
    }

    public void setPozjzl(String pozjzl) {
        this.pozjzl = pozjzl;
    }

    public String getPozjh() {
        return pozjh;
    }

    public void setPozjh(String pozjh) {
        this.pozjh = pozjh;
    }

    public String getHydjjg() {
        return hydjjg;
    }

    public void setHydjjg(String hydjjg) {
        this.hydjjg = hydjjg;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }


    public List<BdcSlJtcyDO> getZnJtcyList() {
        return znJtcyList;
    }

    public void setZnJtcyList(List<BdcSlJtcyDO> znJtcyList) {
        this.znJtcyList = znJtcyList;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }
}
