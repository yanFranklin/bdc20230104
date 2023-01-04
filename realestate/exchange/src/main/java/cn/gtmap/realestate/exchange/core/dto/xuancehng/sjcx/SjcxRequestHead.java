package cn.gtmap.realestate.exchange.core.dto.xuancehng.sjcx;

/**
 * @author <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version 1.0  2022-11-11
 * @description 宣城市级查询 查询相关服务 头信息
 */
public class SjcxRequestHead {

    private String authCode;

    private String senderID;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
