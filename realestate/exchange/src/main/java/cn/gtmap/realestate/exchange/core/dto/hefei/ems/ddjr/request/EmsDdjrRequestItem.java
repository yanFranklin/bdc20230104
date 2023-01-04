package cn.gtmap.realestate.exchange.core.dto.hefei.ems.ddjr.request;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-16
 * @description
 */
public class EmsDdjrRequestItem {
    // 商品名称
    private String itemName;

    // 商品数量
    private String number;

    // 商品单价（单位：分）
    private String itemValue;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }
}
