package cn.gtmap.realestate.exchange.core.dto.changzhou.fssrdzh.request;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/12/16
 * @description 明细列表节点
 */
public class FssrDetails {

    /**
     * nontaxcode : 0401   非税收入项目代码（需要和非税收入票据电子化系统一致）
     * chargingstandard : 收费标准
     * amount : 400    金额
     * order_items :   单位
     * order_number : 1     数量
     * hold1 :  预留字段
     * hold2 :  预留字段
     * hold3 :  预留字段
     */

    private String nontaxcode;
    private String chargingstandard;
//    private Double amount;
    private String amount;
    private String order_items;
//    private Integer order_number;
    private String order_number;
    private String hold1;
    private String hold2;
//    private Double hold3;
    private String hold3;

    public String getNontaxcode() {
        return nontaxcode;
    }

    public void setNontaxcode(String nontaxcode) {
        this.nontaxcode = nontaxcode;
    }

    public String getChargingstandard() {
        return chargingstandard;
    }

    public void setChargingstandard(String chargingstandard) {
        this.chargingstandard = chargingstandard;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_items() {
        return order_items;
    }

    public void setOrder_items(String order_items) {
        this.order_items = order_items;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getHold1() {
        return hold1;
    }

    public void setHold1(String hold1) {
        this.hold1 = hold1;
    }

    public String getHold2() {
        return hold2;
    }

    public void setHold2(String hold2) {
        this.hold2 = hold2;
    }

    public String getHold3() {
        return hold3;
    }

    public void setHold3(String hold3) {
        this.hold3 = hold3;
    }

    @Override
    public String toString() {
        return "{" +
                "nontaxcode='" + nontaxcode + '\'' +
                ", chargingstandard='" + chargingstandard + '\'' +
                ", amount='" + amount + '\'' +
                ", order_items='" + order_items + '\'' +
                ", order_number='" + order_number + '\'' +
                ", hold1='" + hold1 + '\'' +
                ", hold2='" + hold2 + '\'' +
                ", hold3='" + hold3 + '\'' +
                '}';
    }
}


