package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/1/5
 * @description 量化类型枚举类
 */
public enum BdcLhlxEnum {

    LHSD(1, "量化首登"),
    LHDY(2, "量化抵押"),
    LHCF(3, "量化查封");

    /**
     * 量化类型 Key
     */
    private Integer code;
    /**
     * 量化类型 名称
     */
    private String name;

    BdcLhlxEnum(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameByCode(Integer code){
        for (BdcLhlxEnum lhlxEnum : BdcLhlxEnum.values()){
            if(lhlxEnum.getCode().equals(code)){
                return lhlxEnum.getName();
            }
        }
        return null;
    }
}
