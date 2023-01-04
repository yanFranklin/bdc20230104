package cn.gtmap.realestate.common.core.dto.exchange.wwsq.dydjxxcx.request;

import java.util.Date;
import java.util.List;

/**
 * 6.1.46抵押登记查询QO
 */
public class DydjxxcxCxQO {

    private String nwslbh;
    private String wwslbh;
    private String bjly;
    private String sqksrq;
    private String sqjsrq;
    private String sqlxdm;
    private List<DyqrItem> dyqrList;
    private List<String> gzldyids;

    public static class DyqrItem{
        String dyqrmc;

        public String getDyqrmc() {
            return dyqrmc;
        }

        public void setDyqrmc(String dyqrmc) {
            this.dyqrmc = dyqrmc;
        }
    }

    public String getNwslbh() {
        return nwslbh;
    }

    public void setNwslbh(String nwslbh) {
        this.nwslbh = nwslbh;
    }

    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    public String getBjly() {
        return bjly;
    }

    public void setBjly(String bjly) {
        this.bjly = bjly;
    }

    public String getSqksrq() {
        return sqksrq;
    }

    public void setSqksrq(String sqksrq) {
        this.sqksrq = sqksrq;
    }

    public String getSqjsrq() {
        return sqjsrq;
    }

    public void setSqjsrq(String sqjsrq) {
        this.sqjsrq = sqjsrq;
    }

    public String getSqlxdm() {
        return sqlxdm;
    }

    public void setSqlxdm(String sqlxdm) {
        this.sqlxdm = sqlxdm;
    }

    public List<DyqrItem> getDyqrList() {
        return dyqrList;
    }

    public void setDyqrList(List<DyqrItem> dyqrList) {
        this.dyqrList = dyqrList;
    }

    public List<String> getGzldyids() {
        return gzldyids;
    }

    public void setGzldyids(List<String> gzldyids) {
        this.gzldyids = gzldyids;
    }
}
