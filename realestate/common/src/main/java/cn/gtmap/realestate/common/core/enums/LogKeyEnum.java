package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/9/25.
 * @description 日志记录名称枚举类
 */
public enum LogKeyEnum {

    PRINCIPAL("principal","用户名"),
    ALIAS("alias","姓名"),
    EVENT_NAME("eventName","日志类型"),
    PARAM_CHA("paramCha","操作内容"),
    VIEW_TYPE_NAME("viewTypeName","功能名称"),
    ZYTZ_NAME("zyTzName","所属资源"),
    SLBH("slbh","受理编号"),
    MODEL_URL("modelUrl","打印模板路径"),
    DATA_URL("dataUrl","打印数据源"),
    XML_STR("xmlStr","打印数据源xml"),
    TIME("time","操作时间"),
    METHOD_NAME("methodName","方法名"),
    METHOD_ARGS("methodArgs", "方法参数"),
    METHOD_RESPONSE("methodResponse","方法返回值"),
    IP("ip","客户端IP"),
    ;


    /**
     * 日志查询key
     */
    private String key;
    /**
     * 日志查询名称
     */
    private String name;

    LogKeyEnum(String key,String name){
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
