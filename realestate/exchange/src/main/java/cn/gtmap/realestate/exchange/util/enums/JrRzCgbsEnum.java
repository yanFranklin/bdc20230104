package cn.gtmap.realestate.exchange.util.enums;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-29
 * @description 上报 记录日志 成功标识（CGBS）枚举值
 */
public enum JrRzCgbsEnum {

    NO_ACCESS(-2, "不能上报", "nosb"),
    // 请求上报失败
    ACCESS_FAIL(-1, "请求上报失败", "sbbs"),
    // 请求上报成功 未获取到响应
    WATING_RESP(0, "未获取到响应", "whqxyxx"),
    // 已获取响应报文，并反馈结果为成功
    RESP_SUCCESS(1, "成功", "cg"),
    // 已获取响应报文，并反馈结果为失败
    RESP_FAIL(2, "上报失败", "sbsb"),


    /**
     * 调用接入上报工作流事件开始，，上报状态为“未上报
     */
    BEGIN_WSB(3, "等待生成报文", "begin_wsb"),

    /**
     * 连接前置机失败，更新接入日志表，状态为“连接前置机异常”，
     */
    EXCEPTION_WSB(4, "连接前置机异常", "exception_wsb"),

    /**
     * 报文组织报错，则捕获异常，更新接入日志表，状态为“报文组织失败”，
     */
    BWZZSB_WSB(5, "报文组织失败", "bwzzsb_wsb"),
    /**
     * 4、	若报文组织成功，存储接入报文，同时进行XSD校验，若XSD校验不通过，更新状态为“XSD校验失败”，
     */
    XSD_WSB(6, "XSD校验不通过", "xsd_wsb"),

    DB_WJR(7, "登簿未接入数据", "db_wjr"),


    //作废的历史数据
    HISTORY_DATA(9, "作废的历史数据", "ls");
    private Integer bs;

    private String msg;

    private String code;

    JrRzCgbsEnum(Integer bs, String msg, String code) {
        this.bs = bs;
        this.msg = msg;
        this.code = code;
    }

    public Integer getBs() {
        return bs;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public static String getCodeByBs(Integer bs) {
        for (JrRzCgbsEnum value : JrRzCgbsEnum.values()) {
            if (value.getBs().equals(bs)) {
                return value.getCode();
            }
        }
        return null;
    }

    public static String getMsgByBs(Integer bs) {
        for (JrRzCgbsEnum value : JrRzCgbsEnum.values()) {
            if (value.getBs().equals(bs)) {
                return value.getMsg();
            }
        }
        return null;
    }
}
