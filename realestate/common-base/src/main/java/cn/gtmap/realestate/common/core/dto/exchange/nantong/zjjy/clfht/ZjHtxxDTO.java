package cn.gtmap.realestate.common.core.dto.exchange.nantong.zjjy.clfht;

import io.swagger.annotations.ApiModel;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/11/17
 * @description 住建合同信息DTO
 */
@ApiModel(value = "ZjHtxxDTO", description = "住建合同信息DTO")
public class ZjHtxxDTO {

    /**
     * BH : 2577574
     * FWQY : 婺城区
     * HTJE : 910330
     * FWZL : 元和街道乐苑新村1幢101室
     * YCQZH : 20167012125
     * CMRXM : 钱芳
     * CMRZJH : 310108196908274826
     * JJJG : 安美投资管理有限公司
     * JJJGBAH : 008347
     * BASJ : 2018-03-26 11:27:17
     * HTZT : 1
     * MSRXM : 李宏芳,郑一凡
     * MSRZJH : 320621196807174746,320621199801010525
     * DZHT :
     */

    private String BH;
    private String FWQY;
    private String HTJE;
    private String FWZL;
    private String YCQZH;
    private String CMRXM;
    private String CMRZJH;
    private String JJJG;
    private String JJJGBAH;
    private String BASJ;
    private String HTZT;
    private String MSRXM;
    private String MSRZJH;
    private String DZHT;

    public String getBH() {
        return BH;
    }

    public void setBH(String BH) {
        this.BH = BH;
    }

    public String getFWQY() {
        return FWQY;
    }

    public void setFWQY(String FWQY) {
        this.FWQY = FWQY;
    }

    public String getHTJE() {
        return HTJE;
    }

    public void setHTJE(String HTJE) {
        this.HTJE = HTJE;
    }

    public String getFWZL() {
        return FWZL;
    }

    public void setFWZL(String FWZL) {
        this.FWZL = FWZL;
    }

    public String getYCQZH() {
        return YCQZH;
    }

    public void setYCQZH(String YCQZH) {
        this.YCQZH = YCQZH;
    }

    public String getCMRXM() {
        return CMRXM;
    }

    public void setCMRXM(String CMRXM) {
        this.CMRXM = CMRXM;
    }

    public String getCMRZJH() {
        return CMRZJH;
    }

    public void setCMRZJH(String CMRZJH) {
        this.CMRZJH = CMRZJH;
    }

    public String getJJJG() {
        return JJJG;
    }

    public void setJJJG(String JJJG) {
        this.JJJG = JJJG;
    }

    public String getJJJGBAH() {
        return JJJGBAH;
    }

    public void setJJJGBAH(String JJJGBAH) {
        this.JJJGBAH = JJJGBAH;
    }

    public String getBASJ() {
        return BASJ;
    }

    public void setBASJ(String BASJ) {
        this.BASJ = BASJ;
    }

    public String getHTZT() {
        return HTZT;
    }

    public void setHTZT(String HTZT) {
        this.HTZT = HTZT;
    }

    public String getMSRXM() {
        return MSRXM;
    }

    public void setMSRXM(String MSRXM) {
        this.MSRXM = MSRXM;
    }

    public String getMSRZJH() {
        return MSRZJH;
    }

    public void setMSRZJH(String MSRZJH) {
        this.MSRZJH = MSRZJH;
    }

    public String getDZHT() {
        return DZHT;
    }

    public void setDZHT(String DZHT) {
        this.DZHT = DZHT;
    }

    @Override
    public String toString() {
        return "ZjHtxxDTO{" +
                "BH='" + BH + '\'' +
                ", FWQY='" + FWQY + '\'' +
                ", HTJE='" + HTJE + '\'' +
                ", FWZL='" + FWZL + '\'' +
                ", YCQZH='" + YCQZH + '\'' +
                ", CMRXM='" + CMRXM + '\'' +
                ", CMRZJH='" + CMRZJH + '\'' +
                ", JJJG='" + JJJG + '\'' +
                ", JJJGBAH='" + JJJGBAH + '\'' +
                ", BASJ='" + BASJ + '\'' +
                ", HTZT='" + HTZT + '\'' +
                ", MSRXM='" + MSRXM + '\'' +
                ", MSRZJH='" + MSRZJH + '\'' +
                ", DZHT='" + DZHT + '\'' +
                '}';
    }
}
