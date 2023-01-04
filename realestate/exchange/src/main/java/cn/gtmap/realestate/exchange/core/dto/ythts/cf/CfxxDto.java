package cn.gtmap.realestate.exchange.core.dto.ythts.cf;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author wyh
 */
public class CfxxDto {
    @JSONField(name = "YWBH")
    private String YWBH;

    @JSONField(name = "SJYWMC")
    private String SJYWMC;

    @JSONField(name = "KTT_FW_H")
    @JsonProperty("KTT_FW_H")
    private List<KttFwH> KttFwH;

    @JSONField(name = "QLF_QL_CFDJ")
    @JsonProperty("QLF_QL_CFDJ")
    private List<QlfQlCfdj> QlfQlCfdj;

    @JSONField(name = "ZTT_GY_QLR")
    @JsonProperty("ZTT_GY_QLR")
    private List<ZttGyQlr> ZttGyQlr;

    public void setKTT_FW_H(List<KttFwH> KttFwH) {
        this.KttFwH = KttFwH;
    }
    @JSONField(name = "KTT_FW_H")
    public List<KttFwH> getKTT_FW_H() {
        return KttFwH;
    }

    public void setQLF_QL_CFDJ(List<QlfQlCfdj> QlfQlCfdj) {
        this.QlfQlCfdj = QlfQlCfdj;
    }

    @JSONField(name = "QLF_QL_CFDJ")
    public List<QlfQlCfdj> getQLF_QL_CFDJ() {
        return QlfQlCfdj;
    }

    public void setZTT_GY_QLR(List<ZttGyQlr> ZttGyQlr) {
        this.ZttGyQlr = ZttGyQlr;
    }

    @JSONField(name = "ZTT_GY_QLR")
    public List<ZttGyQlr> getZTT_GY_QLR() {
        return ZttGyQlr;
    }

    public void setYWBH(String YWBH) {
        this.YWBH = YWBH;
    }

    public String getYWBH() {
        return YWBH;
    }

    public void setSJYWMC(String SJYWMC) {
        this.SJYWMC = SJYWMC;
    }

    public String getSJYWMC() {
        return SJYWMC;
    }

}