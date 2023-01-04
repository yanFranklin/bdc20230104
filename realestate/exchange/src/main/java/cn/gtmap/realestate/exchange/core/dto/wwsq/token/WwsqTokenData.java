package cn.gtmap.realestate.exchange.core.dto.wwsq.token;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/1
 * @description 外网申请token data
 */
public class WwsqTokenData {

    /**
     * time : 2022-08-28 15:44:41
     * token : 38716a85b4af3f751f04e4c3ea6cc9e1
     */

    private String time;
    private String token;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "WwsqTokenData{" +
                "time='" + time + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
