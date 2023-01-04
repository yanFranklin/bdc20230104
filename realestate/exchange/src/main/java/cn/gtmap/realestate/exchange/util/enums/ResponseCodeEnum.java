package cn.gtmap.realestate.exchange.util.enums;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-02-05
 * @description 第三方子系统 标准版响应码
 */
public enum ResponseCodeEnum {

    SUCCESS("200", "成功"),
    SUCCESS_YW("200", "success"),

    PARAM_ERROR("400", "参数错误"),
    PARAM_WRONG("415", "参数错误，附件无法解析"),
    DATA_LACK("4000", "查无该业务数据！"),

    SERVER_ERROR("500", "服务异常");

    public String code;

    public String msg;

    ResponseCodeEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

}
