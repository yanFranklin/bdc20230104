package cn.gtmap.realestate.exchange.core.dto.hefei.twjm.response;

import java.util.List;

public class BdcTwjmDTO {
    // 接口响应信息
    private String msg;

    // 接口响应码
    private String code;

    private List<TwjmDTO> list;

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

    public List<TwjmDTO> getList() {
        return list;
    }

    public void setList(List<TwjmDTO> list) {
        this.list = list;
    }
}
