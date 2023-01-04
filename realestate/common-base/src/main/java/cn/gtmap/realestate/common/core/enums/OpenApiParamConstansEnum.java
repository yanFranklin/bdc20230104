package cn.gtmap.realestate.common.core.enums;

public enum OpenApiParamConstansEnum {

    /**
     * 返回是对象
     */
    INTERFACE_PARAM_EXT_IS_OBJECT_YES("isObject","1","返回是对象"),

    INTERFACE_PARAM_EXT_IS_OBJECT_NO("isObject","0","返回非对象"),

    INTERFACE_PARAM_EXT_IS_LIST_YES("isList","1","返回结果是列表"),

    INTERFACE_PARAM_EXT_IS_LIST_NO("isList","0","返回结果非列表"),

    INTERFACE_PARAM_EXT_SQL("sql","0","具体sql"),

    INTERFACE_PARAM_EXT_CURRENT_JKID("jkid","0","当前对象的jkid"),

    /**
     * 接口参数扩展属性：是否配置sql：0否
     */
    INTERFACE_PARAM_EXT_GET_RESULT_WITHOUT_ALONE_SQL("isAloneSql","0","非单独配置sql获取"),

    /**
     * 接口参数扩展属性：是否配置sql：1是
     */
    INTERFACE_PARAM_EXT_GET_RESULT_WITH_ALONE_SQL("isAloneSql","1","单独配置sql获取");


    private String attrValue;
    private String attrKey;
    private String attrDesc;

    OpenApiParamConstansEnum(String attrKey, String attrValue,String attrDesc) {
        this.attrKey = attrKey;
        this.attrValue = attrValue;
        this.attrDesc = attrDesc;
    }

    public String getKey() {
        return attrKey;
    }

    public String getValue() {
        return attrValue;
    }

}
