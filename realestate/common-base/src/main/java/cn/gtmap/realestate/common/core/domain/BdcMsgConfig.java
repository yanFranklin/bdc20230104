package cn.gtmap.realestate.common.core.domain;



import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "bdc_msg_config")
public class BdcMsgConfig {
    @Id
    /**
     * 主键id
     */
    private String configid;
    /**
     * 模板code
     */
    private String mbcode;
    /**
     * 短信类型
     */
    private String msgtype;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getConfigid() {
        return configid;
    }

    public void setConfigid(String configid) {
        this.configid = configid;
    }

    public String getMbcode() {
        return mbcode;
    }

    public void setMbcode(String mbcode) {
        this.mbcode = mbcode;
    }
}
