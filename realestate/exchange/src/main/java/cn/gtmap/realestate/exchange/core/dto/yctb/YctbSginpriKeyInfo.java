package cn.gtmap.realestate.exchange.core.dto.yctb;

import java.io.Serializable;

public class YctbSginpriKeyInfo implements Serializable {

    private static final long serialVersionUID = 1580799352491528275L;

    private String id; //string 秘钥主键
    private String channel; //string 渠道编码
    private String key; //string 私钥
    private String type; //string 秘钥类型
    private String pubkey; //string 公钥
    private String qxdm; //string 区县代码

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPubkey() {
        return pubkey;
    }

    public void setPubkey(String pubkey) {
        this.pubkey = pubkey;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @Override
    public String toString() {
        return "YctbSginpriKeyInfo{" +
                "id='" + id + '\'' +
                ", channel='" + channel + '\'' +
                ", key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", pubkey='" + pubkey + '\'' +
                ", qxdm='" + qxdm + '\'' +
                '}';
    }
}
