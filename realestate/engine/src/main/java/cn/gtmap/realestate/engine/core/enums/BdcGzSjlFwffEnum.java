package cn.gtmap.realestate.engine.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/15
 * @description 数据流服务对应的HTTP方法
 */
public enum BdcGzSjlFwffEnum {
    GET("1", "GET方法"),
    POST("2", "POST方法");

    /**
     * 编码
     */
    private String code;
    /**
     * 方法
     */
    private String method;


    BdcGzSjlFwffEnum(String code, String method){
        this.code = code;
        this.method = method;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
