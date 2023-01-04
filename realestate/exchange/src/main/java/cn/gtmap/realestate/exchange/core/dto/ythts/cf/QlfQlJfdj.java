package cn.gtmap.realestate.exchange.core.dto.ythts.cf;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author wyh
 */
public class QlfQlJfdj {

    @JSONField(name = "YWH")
    private String YWH;

    @JSONField(name = "JFJG")
    private String JFJG;

    @JSONField(name = "JFWJ")
    private String JFWJ;

    @JSONField(name = "JFWH")
    private String JFWH;

    @JSONField(name = "JFDBR")
    private String JFDBR;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "JFDJSJ",format = "yyyy-MM-dd HH:mm:ss")
    private Date JFDJSJ;

    @JSONField(name = "JFYY")
    private String JFYY;

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

    public void setJFJG(String JFJG) {
        this.JFJG = JFJG;
    }

    public String getJFJG() {
        return JFJG;
    }

    public void setJFWJ(String JFWJ) {
        this.JFWJ = JFWJ;
    }

    public String getJFWJ() {
        return JFWJ;
    }

    public void setJFWH(String JFWH) {
        this.JFWH = JFWH;
    }

    public String getJFWH() {
        return JFWH;
    }

    public void setJFDBR(String JFDBR) {
        this.JFDBR = JFDBR;
    }

    public String getJFDBR() {
        return JFDBR;
    }

    public void setJFDJSJ(Date JFDJSJ) {
        this.JFDJSJ = JFDJSJ;
    }

    public Date getJFDJSJ() {
        return JFDJSJ;
    }

    public void setJFYY(String JFYY) {
        this.JFYY = JFYY;
    }

    public String getJFYY() {
        return JFYY;
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