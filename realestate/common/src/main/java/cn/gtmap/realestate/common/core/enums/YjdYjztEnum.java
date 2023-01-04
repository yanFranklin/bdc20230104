package cn.gtmap.realestate.common.core.enums;

public enum  YjdYjztEnum {
    SWJJ(4, "尚未交接"),
    YJJ(5, "已交接，尚未接受"),
    JJCG(6, "交接成功"),
    JJSB(7, "交接失败，退回");


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态内容
     */
    private String msg;


    YjdYjztEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
