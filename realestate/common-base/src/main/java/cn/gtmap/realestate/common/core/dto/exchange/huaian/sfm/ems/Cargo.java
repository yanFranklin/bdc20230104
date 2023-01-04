package cn.gtmap.realestate.common.core.dto.exchange.huaian.sfm.ems;


/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @date 2022/11/02  16:23
 * @description 淮安 中国邮政 寄件商品信息
 */
public class Cargo {

    // 商品名称
    private String cargo_name;

    // 商品类型
    private String cargo_category;

    // 商品数量
    private String cargo_quantity;

    // 商品单价
    private String cargo_value;

    // 商品重量
    private String cargo_weight;

    // 订单号
    private String order_no;

    // 长
    private String length;

    // 宽
    private String width;

    // 高
    private String high;


    public String getCargo_name() {
        return cargo_name;
    }

    public void setCargo_name(String cargo_name) {
        this.cargo_name = cargo_name;
    }

    public String getCargo_category() {
        return cargo_category;
    }

    public void setCargo_category(String cargo_category) {
        this.cargo_category = cargo_category;
    }

    public String getCargo_quantity() {
        return cargo_quantity;
    }

    public void setCargo_quantity(String cargo_quantity) {
        this.cargo_quantity = cargo_quantity;
    }

    public String getCargo_value() {
        return cargo_value;
    }

    public void setCargo_value(String cargo_value) {
        this.cargo_value = cargo_value;
    }

    public String getCargo_weight() {
        return cargo_weight;
    }

    public void setCargo_weight(String cargo_weight) {
        this.cargo_weight = cargo_weight;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "Cargo{" +
                "cargo_name='" + cargo_name + '\'' +
                ", cargo_category='" + cargo_category + '\'' +
                ", cargo_quantity='" + cargo_quantity + '\'' +
                ", cargo_value='" + cargo_value + '\'' +
                ", cargo_weight='" + cargo_weight + '\'' +
                ", order_no='" + order_no + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", high='" + high + '\'' +
                '}';
    }
}
