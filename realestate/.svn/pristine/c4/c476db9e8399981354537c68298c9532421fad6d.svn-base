package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href ="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.3, 2021/12/15
 * @description 锁定类型ENUM
 */
public enum BdcSdlxEnum {

    YBSD(0, "一般锁定"),
    QTSD(7, "其他锁定"),
    ZSSD(8, "征收锁定"),
    YCJ(9, "已裁决");


    /**
     * 锁定类型Key
     */
    private Integer code;
    /**
     * 锁定类型 名称
     */
    private String name;

    BdcSdlxEnum(Integer code, String name) {
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
        for (BdcSdlxEnum sdlxEnum : BdcSdlxEnum.values()){
            if(sdlxEnum.getCode().equals(code)){
                return sdlxEnum.getName();
            }
        }
        return null;
    }
}
