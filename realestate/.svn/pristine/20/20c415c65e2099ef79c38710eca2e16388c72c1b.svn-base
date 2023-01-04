package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/9/24 15:43
 * @description 字段类型
 */
public enum OpenApiFieldType {
    STRING("String","java.lang.String"),
    INTEGER("Integer","java.lang.Integer"),
    BOOLEAN("Boolean","java.lang.Boolean"),
    DOUBLE("Double","java.lang.Double"),
    FLOAT("Float","java.lang.Float"),
    DATE("Date","java.util.Date"),
    LIST("List","java.util.List"),
    MAP("Map","java.util.Map"),
    ARRAY("Array",""),
    OBJECT("Object","");

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * class
     */
    private String className;

    public static String getClassName(String fieldType){
        for (OpenApiFieldType f : OpenApiFieldType.values()) {
            if (f.fieldType.equals(fieldType)) {
                return f.className;
            }
        }
        return fieldType;
    }

    public static String getFieldType(String className){
        for (OpenApiFieldType f : OpenApiFieldType.values()) {
            if (f.className.equals(className)) {
                return f.fieldType;
            }
        }
        return className;
    }

    private OpenApiFieldType(String fieldType,String className) {
        this.fieldType = fieldType;
        this.className = className;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
