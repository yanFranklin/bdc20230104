package cn.gtmap.realestate.common.core.enums;

public enum ExceptionMessageEnum {
    DB_EX("数据库异常，请联系管理员"),
    IO_EX("输入输出异常，请联系管理员"),
    MQ_EX("消息队列异常，请联系管理员"),
    UN_SUPPORT_EX("不支持操作"),
    CLASS_EX("类访问异常，请联系管理员"),
    SERVER_EX("系统异常，请联系管理员"),
    TIMEOUT_EX("超时异常，请联系管理员")
    ;

    ExceptionMessageEnum(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
