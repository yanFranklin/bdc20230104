package cn.gtmap.realestate.common.core.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 推送第三方抵押审批表返回状态
 * @date : 2020/7/31 16:27
 */
public enum TsDyaSpbResponseEnum {
    CODE_SUCCESS("0000","推送成功"),
    CODE_DATAFALSE("1000","数据初始失败"),
    CODE_ERROR("2000","代码未知错误"),
    CODE_TOKENERROR("2010","安全token错误");
    TsDyaSpbResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;

    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TsDyaSpbResponseEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static String getMessage(String code) {
        String message = "";
        if(StringUtils.isNotBlank(code)) {
            for(TsDyaSpbResponseEnum tsDyaSpbResponseEnum : TsDyaSpbResponseEnum.values()) {
                if(StringUtils.equals(tsDyaSpbResponseEnum.getCode(),code)) {
                    message = tsDyaSpbResponseEnum.getMessage();
                }
            }
        }
        return message;
    }
}
