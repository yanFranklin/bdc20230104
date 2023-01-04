package cn.gtmap.realestate.exchange.core.dto.hefei.fcjyYcfByFwbm.response;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-18
 * @description
 */
public class FcjyYcfByFwbmResponseDTO {

    private String msg;

    private String code;

    private List<FcjyYcfByFwbmResponseData> data;

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

    public List<FcjyYcfByFwbmResponseData> getData() {
        return data;
    }

    public void setData(List<FcjyYcfByFwbmResponseData> data) {
        this.data = data;
    }
}
