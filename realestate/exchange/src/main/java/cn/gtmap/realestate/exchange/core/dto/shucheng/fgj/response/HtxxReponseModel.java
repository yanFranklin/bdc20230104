package cn.gtmap.realestate.exchange.core.dto.shucheng.fgj.response;

import cn.gtmap.realestate.exchange.core.dto.hefei.fcjy.response.BaxxResponseData;

import java.util.List;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/4/20
 * @description 商品房、二手房合同信息
 */
public class HtxxReponseModel {

    private String code;
    private String msg;
    private List<HtxxResponseData> data;


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

    public List<HtxxResponseData> getData() {
        return data;
    }

    public void setData(List<HtxxResponseData> data) {
        this.data = data;
    }
}
