package cn.gtmap.realestate.exchange.core.dto.hefei.fcjy.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-01
 * @description
 */
public class BaxxReponseModel {

    private String code;
    private List<BaxxResponseData> data;
    private String msg;

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

    public List<BaxxResponseData> getData() {
        return data;
    }

    public void setData(List<BaxxResponseData> data) {
        this.data = data;
    }
}
