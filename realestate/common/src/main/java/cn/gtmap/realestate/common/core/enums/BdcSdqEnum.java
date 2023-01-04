package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/6/1
 * @description 水电气业务类型枚举类
 */
public enum BdcSdqEnum {

    S(1, "水业务"),
    D(2,"电业务"),
    Q(3, "气业务"),
    GD(4,"广电业务"),
    WL(5,"网络业务");

    private final Integer key;

    private final String name;

    BdcSdqEnum(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String value(){
        return name;
    }

    public Integer key(){
        return key;
    }
}
