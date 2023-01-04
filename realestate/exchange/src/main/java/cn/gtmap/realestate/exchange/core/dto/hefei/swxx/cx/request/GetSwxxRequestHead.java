package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.cx.request;
/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/11/28
 * @description 查询税务信息请求头信息
 */
public class GetSwxxRequestHead {

    private String channel_id;//传入分配的称，例如马鞍山：AHMSBDC
    private String tran_date;//调取的时间，格式yyyymmdd
    private String tran_id;//调取的方法名称
    private String tran_seq;//32位uuid
    private String token;//各分配的密码，调取时候验证

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTran_date() {
        return tran_date;
    }

    public void setTran_date(String tran_date) {
        this.tran_date = tran_date;
    }

    public String getTran_id() {
        return tran_id;
    }

    public void setTran_id(String tran_id) {
        this.tran_id = tran_id;
    }

    public String getTran_seq() {
        return tran_seq;
    }

    public void setTran_seq(String tran_seq) {
        this.tran_seq = tran_seq;
    }
}
