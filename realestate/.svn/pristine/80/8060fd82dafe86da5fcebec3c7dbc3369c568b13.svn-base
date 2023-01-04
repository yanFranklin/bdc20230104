package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/4/26
 * @description 登簿日志返回状态码说明枚举类
 */
public enum AccessLogStausEnum {
    STATUS_0("0200", "上报成功"),
    STATUS_1("04001", "xml格式错误"),
    STATUS_2("04002", "AreaCode节点缺失"),
    STATUS_3("04003", "AreaCode节点值为空"),
    STATUS_4("04004", "xzTotalInfo节点缺失"),
    STATUS_5("04005", "RegisterInfo节点缺失"),
    STATUS_6("04006", "AccessInfo节点缺失"),
    STATUS_7("04007", "RegisterList节点缺失"),
    STATUS_8("04008", "Register节点缺失"),
    STATUS_9("04009", "AccessList节点缺失"),
    STATUS_10("04010", "Access节点缺失"),
    STATUS_11("04011", "AccessList中totalNum与上报清单数量不一致"),
    STATUS_12("04012", "RegisterList中totalNum与上报清单数量不一致");


    AccessLogStausEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 编码
     */
    private String code;

    /**
     * 返回msg
     */
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
