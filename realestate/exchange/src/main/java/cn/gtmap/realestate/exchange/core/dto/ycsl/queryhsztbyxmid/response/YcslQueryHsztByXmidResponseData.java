package cn.gtmap.realestate.exchange.core.dto.ycsl.queryhsztbyxmid.response;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-25
 * @description 1已完税 0未完税
 */
public class YcslQueryHsztByXmidResponseData {

    private String wszt;

    private String msg;

    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
