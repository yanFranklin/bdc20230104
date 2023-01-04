package cn.gtmap.realestate.exchange.core.dto.hefei.fcjyxgxx.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-30
 * @description
 */
public class FcXgxxResponseModel {

    private String code;

    private String msg;

    private FcXgxxResponseData data;

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

    public FcXgxxResponseData getData() {
        return data;
    }

    public void setData(FcXgxxResponseData data) {
        this.data = data;
    }
}
