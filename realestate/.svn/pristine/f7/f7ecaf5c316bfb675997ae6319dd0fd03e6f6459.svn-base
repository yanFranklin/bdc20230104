package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.respones;

import java.io.Serializable;

/**
 * 盐城一体化通用返回实体
 */
public class YthCommonResponse implements Serializable {

    private static final long serialVersionUID = -4613164859142881272L;

    public static final String SUCCESS = "success";

    public static final String FAIL = "fail";

    private static final String SUCCESS_MSG = "数据接收成功";

    /**
     * 返回类型：success fail
     */
    private String type;

    /**
     * 返回描述 数据接收成功 or 失败原因
     */
    private String data;

    /**
     * 业务标识
     */
    private String uuid;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public YthCommonResponse() {
    }

    public YthCommonResponse(String type, String data) {
        this.type = type;
        this.data = data;
    }

    public static YthCommonResponse ok() {
        YthCommonResponse resp = new YthCommonResponse();
        resp.type = SUCCESS;
        resp.data = SUCCESS_MSG;
        return resp;
    }

    public static YthCommonResponse ok(String message) {
        YthCommonResponse resp = new YthCommonResponse();
        resp.type = SUCCESS;
        resp.data = message;
        return resp;
    }

    public static YthCommonResponse fail(String message) {
        YthCommonResponse resp = new YthCommonResponse();
        resp.type = FAIL;
        resp.data = message;
        return resp;
    }
}
