package cn.gtmap.realestate.exchange.core.dto.fssr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 1.0  2022/5/24 9:59
 * @description 缴费办理
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JfblDataDetailDTO {

    // 非税收入项目代码
    @JSONField(name = "NONTAXCODE")
    private String NONTAXCODE;

    // -收费标准
    @JSONField(name = "CHARGINGSTANDARD")
    private String CHARGINGSTANDARD;

    // 金额
    @JSONField(name = "AMOUNT")
    private String AMOUNT;

    // 单位
    @JSONField(name = "ORDER_ITEMS")
    private String ORDER_ITEMS;

    // 数量
    @JSONField(name = "ORDER_NUMBER")
    private String ORDER_NUMBER;

    public String getNONTAXCODE() {
        return NONTAXCODE;
    }

    public void setNONTAXCODE(String NONTAXCODE) {
        this.NONTAXCODE = NONTAXCODE;
    }

    public String getCHARGINGSTANDARD() {
        return CHARGINGSTANDARD;
    }

    public void setCHARGINGSTANDARD(String CHARGINGSTANDARD) {
        this.CHARGINGSTANDARD = CHARGINGSTANDARD;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getORDER_ITEMS() {
        return ORDER_ITEMS;
    }

    public void setORDER_ITEMS(String ORDER_ITEMS) {
        this.ORDER_ITEMS = ORDER_ITEMS;
    }

    public String getORDER_NUMBER() {
        return ORDER_NUMBER;
    }

    public void setORDER_NUMBER(String ORDER_NUMBER) {
        this.ORDER_NUMBER = ORDER_NUMBER;
    }

    @Override
    public String toString() {
        return "JfblDataDetailDTO{" +
                "NONTAXCODE='" + NONTAXCODE + '\'' +
                ", CHARGINGSTANDARD='" + CHARGINGSTANDARD + '\'' +
                ", AMOUNT='" + AMOUNT + '\'' +
                ", ORDER_ITEMS='" + ORDER_ITEMS + '\'' +
                ", ORDER_NUMBER='" + ORDER_NUMBER + '\'' +
                '}';
    }
}
