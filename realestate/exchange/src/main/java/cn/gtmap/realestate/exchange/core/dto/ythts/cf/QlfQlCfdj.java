package cn.gtmap.realestate.exchange.core.dto.ythts.cf;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author wyh
 */
public class QlfQlCfdj {

    @JSONField(name = "YWH")
    private String YWH;

    @JSONField(name = "CFJG")
    private String CFJG;

    @JSONField(name = "CFLX")
    private String CFLX;

    @JSONField(name = "CFWJ")
    private String CFWJ;

    @JSONField(name = "CFWH")
    private String CFWH;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "CFQSSJ",format = "yyyy-MM-dd HH:mm:ss")
    private Date CFQSSJ;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "CFJSSJ",format = "yyyy-MM-dd HH:mm:ss")
    private Date CFJSSJ;

    @JSONField(name = "CFFW")
    private String CFFW;

    @JSONField(name = "CFYY")
    private String CFYY;

    @JSONField(name = "DBR")
    private String DBR;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "DJSJ",format = "yyyy-MM-dd HH:mm:ss")
    private Date DJSJ;

    @JSONField(name = "JFWH")
    private String JFWH;

    @JSONField(name = "FJ")
    private String FJ;

    @JSONField(name = "QXDM")
    private String QXDM;

    @JSONField(name = "DJJG")
    private String DJJG;

    public void setYWH(String YWH) {
        this.YWH = YWH;
    }

    public String getYWH() {
        return YWH;
    }

    public void setCFJG(String CFJG) {
        this.CFJG = CFJG;
    }

    public String getCFJG() {
        return CFJG;
    }

    public void setCFLX(String CFLX) {
        this.CFLX = CFLX;
    }

    public String getCFLX() {
        return CFLX;
    }

    public void setCFWJ(String CFWJ) {
        this.CFWJ = CFWJ;
    }

    public String getCFWJ() {
        return CFWJ;
    }

    public void setCFWH(String CFWH) {
        this.CFWH = CFWH;
    }

    public String getCFWH() {
        return CFWH;
    }

    public void setCFQSSJ(Date CFQSSJ) {
        this.CFQSSJ = CFQSSJ;
    }

    public Date getCFQSSJ() {
        return CFQSSJ;
    }

    public void setCFJSSJ(Date CFJSSJ) {
        this.CFJSSJ = CFJSSJ;
    }

    public Date getCFJSSJ() {
        return CFJSSJ;
    }

    public void setCFFW(String CFFW) {
        this.CFFW = CFFW;
    }

    public String getCFFW() {
        return CFFW;
    }

    public void setCFYY(String CFYY) {
        this.CFYY = CFYY;
    }

    public String getCFYY() {
        return CFYY;
    }

    public void setDBR(String DBR) {
        this.DBR = DBR;
    }

    public String getDBR() {
        return DBR;
    }

    public void setDJSJ(Date DJSJ) {
        this.DJSJ = DJSJ;
    }

    public Date getDJSJ() {
        return DJSJ;
    }

    public void setJFWH(String JFWH) {
        this.JFWH = JFWH;
    }

    public String getJFWH() {
        return JFWH;
    }

    public void setFJ(String FJ) {
        this.FJ = FJ;
    }

    public String getFJ() {
        return FJ;
    }

    public void setQXDM(String QXDM) {
        this.QXDM = QXDM;
    }

    public String getQXDM() {
        return QXDM;
    }

    public void setDJJG(String DJJG) {
        this.DJJG = DJJG;
    }

    public String getDJJG() {
        return DJJG;
    }

}