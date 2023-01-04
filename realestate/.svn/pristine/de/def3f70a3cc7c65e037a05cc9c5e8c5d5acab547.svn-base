package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/4/28
 * @description 电子证照错误信息枚举类
 */
public enum ECertificateErrorMsgEnum {
    MSGN1("-1", "数据库插入数据失败!"),
    MSGN2("-2", "数据库更新数据失败!"),
    MSGN3("-3", "数据库删除数据失败!"),
    MSG1("1", "未获得授权!"),
    MSG2("2", "服务超时!"),
    MSG3("3", "未检索到记录!"),
    MSG4("4", "请求参数错误!"),
    MSG5("5", "缺失必填字段!"),
    MSG6("6", "已生成过电子证照PDF!"),
    MSG7("7", "电子证照信息已入库!"),
    MSG8("8", "上传附件中心失败!"),
    MSG9("9", "生成电子证照PDF失败!"),
    MSG10("10", "电子证照签章失败!"),
    MSG11("11", "电子证照已注销!"),
    MSG12("12", "生成zzbs失败!"),
    MSG13("13", "验证数字签名失败!"),
    MSG22("22", "base64码转文件失败!");


    /**
     * 状态代码
     */
    private String code;
    /**
     * 状态描述
     */
    private String msg;

    ECertificateErrorMsgEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * @param code 代码
     * @return ECertificateErrorMsgEnum 对应的枚举信息
     * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
     * @description 根据code获取对应的枚举类型
     */
    public static ECertificateErrorMsgEnum getType(String code) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        for (ECertificateErrorMsgEnum msgEnum : ECertificateErrorMsgEnum.values()) {
            if (StringUtils.equals(msgEnum.code, code)) {
                return msgEnum;
            }
        }
        return null;
    }

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
