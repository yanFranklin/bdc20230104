package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/5/14
 * @description 收费状态枚举类
 */
public enum BdcSfztEnum {

    WJF(1, "未缴费"),
    YJF(2, "已缴费"),
    BFJF(3, "部分缴费"),
    TKZ(4, "退款中"),
    TKCG(5, "退款成功"),
    TKSB(6, "退款失败"),
    YHY(7, "已核验"),
    YZF(-1, "已作废"),
    BSF(0, "不收费");

    private final Integer key;

    private final String name;

    BdcSfztEnum(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String value(){
        return name;
    }

    public Integer key(){
        return key;
    }

    public static String getNameByKey(Integer key){
        for (BdcSfztEnum c : BdcSfztEnum.values()) {
            if (c.key().equals(key)) {
                return c.name;
            }
        }
        return null;
    }

}
