package cn.gtmap.realestate.certificate.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:wenyuanwu@gtmap.cn">wenyuanwu</a>
 * @version 1.0 2019/1/21
 * @description  响应数据
 */
public class ResponseData {

    /**
    * 异常代码
    */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String result;

    /**
    * 异常信息
    */
    private String message;

    public ResponseData(String result, String message) {
        this.result = result;
        this.message = message;
    }

    public ResponseData(String message) {
        this.message = message;
    }

    public ResponseData(){}

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
