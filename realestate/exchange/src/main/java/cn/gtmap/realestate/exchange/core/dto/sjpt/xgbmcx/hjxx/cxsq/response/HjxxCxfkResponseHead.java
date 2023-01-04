package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.hjxx.cxsq.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-09
 * @description
 */
public class HjxxCxfkResponseHead {

    @JSONField(name = "msg")
    private String msg;

    @JSONField(name = "code")
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
