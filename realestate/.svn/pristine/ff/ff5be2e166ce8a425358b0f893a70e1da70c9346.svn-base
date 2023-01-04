package cn.gtmap.realestate.common.core.enums;


/**
 * 日志 event 类型枚举类
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version 11:25 上午 2020/4/26
 */
public enum LogEventEnum {
    /**
     * 补录修改数据时记录的日志
     */
    XXBL("XXBL", "补录修改日志"),
    /**
     * 信息补录修改流程初始化数据
     */
    BLLC_MODIFY("bllc_modify", "信息补录修改流程初始化数据"),
    SDDBLOG("SDDB", "手动登簿"),
    MQSENDLOG("MQ_SEND","MQ队列发送");

    /**
     * 日志事件名称，区分大小写
     */
    private final String key;
    /**
     * 日志事件名称
     */
    private final String value;

    LogEventEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }
}
