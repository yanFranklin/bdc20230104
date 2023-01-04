package cn.gtmap.realestate.common.core.enums;

public enum  OpenApiConstansEnum {

    /**
     * 包装返回
     */
    PACKAGE_RESPONSE(1,"包装返回参数"),

    /**
     * 未发布
     */
    NO_PACKAGE_RESPONSE(0,"不包装返回参数");

    private Integer attrKey;
    private String attrName;

    OpenApiConstansEnum(Integer attrKey, String attrName) {
        this.attrKey = attrKey;
        this.attrName = attrName;
    }

    public Integer getCode() {
        return attrKey;
    }

    public String getMessage() {
        return attrName;
    }

}
