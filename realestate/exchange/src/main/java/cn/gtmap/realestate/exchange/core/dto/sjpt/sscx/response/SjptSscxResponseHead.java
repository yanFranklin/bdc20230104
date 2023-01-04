package cn.gtmap.realestate.exchange.core.dto.sjpt.sscx.response;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2019/1/10
 * @description
 */

public class SjptSscxResponseHead {
    private String xzqdm;
    private String code;
    private String msg;

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

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
}
